#include "moving.h"

using namespace std;

bool Moving::isitlegit(unsigned int iy, unsigned int ix,vector<string> &avec){

    if(avec[iy][ix] == '*'){
        return false;
    }else{
        return true;
    }
}

void Moving::UP(int x1, int y1,vector<string> &avec){
        mvaddch(y1,x1,' ');
        avec[y1][x1] = ' ';
        mvaddch(y1-1,x1,'P');
        avec[y1-1][x1] = 'P';
}

void Moving::DOWN(int x1, int y1,vector<string> &avec){
        mvaddch(y1,x1,' ');
        avec[y1][x1] = ' ';
        mvaddch(y1+1,x1,'P');
        avec[y1+1][x1] = 'P';
}

void Moving::LEFT(int x1, int y1,vector<string> &avec){
        mvaddch(y1,x1,' ');
        avec[y1][x1] = ' ';
        mvaddch(y1,x1-1,'P');
        avec[y1][x1-1] = 'P';
}

void Moving::RIGHT(int x1, int y1,vector<string> &avec){
        mvaddch(y1,x1,' ');
        avec[y1][x1] = ' ';
        mvaddch(y1,x1+1,'P');
        avec[y1][x1+1] = 'P';
}
