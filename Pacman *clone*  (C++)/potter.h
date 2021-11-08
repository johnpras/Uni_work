#ifndef POTTER_H
#define POTTER_H

#include <ncurses.h>
#include <curses.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include "moving.h"

using namespace std;



class Potter: public Moving {

    private:
        int px;
        int py;
    public:

        void setx(int xx);
        void sety(int yy);
        int getx();
        int gety();
        Potter();
};

#endif