#ifndef ENGINE_H
#define ENGINE_H

#include <ncurses.h>
#include <curses.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <cstring>
#include "moving.h"
#include "gnome.h"
#include "traal.h"
#include "potter.h"
#include "HiScore.h"

using namespace std;

class Engine
{
private:
    vector<string>mapstring;
    string line;
    WINDOW* win;
    unsigned int x;
    unsigned int y;
    Potter* p;
    Gnome* g;
    Traal* t;
    Hiscore* h;
    int stonenum = 0;
    int score=0;
    int ch;
    int testnum;
    string nna;
    string argforbin;

public:

    Engine();
    void loadmap(string s);
    int isitlegitmove(unsigned int x, unsigned int y);
    void placepotter();
    void placegnome();
    void placetraal();
    void placestones();
    void placescroll();
    void moveto();
    void crash(int n);
    void smartmoveg();
    void smartmovet();
    void message(int nmbr);
    void getarg(string s);

};

#endif