#ifndef GNOME_H
#define GNOME_H

#include <ncurses.h>
#include <curses.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include "moving.h"

using namespace std;



class Gnome : public Moving {

    private:
        int gny, gnx;
    public:

        void setx(int xxx);
        void sety(int yyy);
        int getx();
        int gety();
        Gnome();
};



#endif