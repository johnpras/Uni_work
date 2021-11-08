#include <ncurses.h>
#include <curses.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>

#include "moving.h"
#include "engine.h"
#include "potter.h"
#include "gnome.h"
#include "traal.h"
#include <thread>
#include <pthread.h>


using namespace std;


int main(int argc, char *argv[]){

initscr();
start_color();
init_pair(1, COLOR_CYAN, COLOR_BLACK);
init_pair(2, COLOR_RED, COLOR_BLACK);

try{
    if (argc == 3) {
        Engine en1;
        string myfilename = argv[1];
        string mybinfilename = argv[2];
        en1.getarg(mybinfilename);
        en1.loadmap(myfilename);
        en1.placestones();
        en1.placegnome();
        en1.placetraal();
        en1.placepotter();
        en1.moveto();
    } else{
        throw exception();
    }
}
catch(exception e){
    cout << "check your arguments...\n" << endl;
    return 0;
}
return 0;

}
