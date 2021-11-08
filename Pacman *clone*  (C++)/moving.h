#ifndef MOVING_H
#define MOVING_H

#include <ncurses.h>
#include <curses.h>
#include <iostream>
#include <sstream>
#include <vector>
#include <fstream>


using namespace std;

class Moving {

protected:

    int x;
    int y;

public:

    int ch,a;
    string line;
    bool isitlegit(unsigned int x, unsigned int y,vector<string> &avec);
    void UP(int x, int y,vector<string> &a);
    void DOWN(int x, int y,vector<string> &a);
    void LEFT(int x, int y,vector<string> &a);
    void RIGHT(int x, int y,vector<string> &a);

};

#endif
