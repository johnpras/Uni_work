#ifndef HISCORE_H
#define HISCORE_H

#include <ncurses.h>
#include <curses.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <cstring>

using namespace std;

class Hiscore{

private:

    struct data{
        char* s;
        int sc;
    };

    string name;
    char* s;
    int sc;
    int arrnum;
    data d;
    fstream binfile;
public:

    Hiscore();
    void calc(int a, int b, string x);
    void showtopfive();
    void operator <<(int x);
    Hiscore& operator <<(string n);
		    

};

#endif