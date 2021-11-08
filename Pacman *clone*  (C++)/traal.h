#ifndef TRAAL_H
#define TRAAL_H

#include <ncurses.h>
#include <curses.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include "moving.h"

using namespace std;



class Traal: public Moving {

    private:
        int yt, xt;
    public:

        void setx(int xx);
        void sety(int yy);
        int getx();
        int gety();
        Traal();
};

#endif
