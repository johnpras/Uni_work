#include "potter.h"

using namespace std;


Potter::Potter(){
    px=0;
    py=0;
}

void Potter::setx(int xx){
    px = xx;
}
void Potter::sety(int yy){
    py = yy;
}
int Potter::getx(){
    return px;
}

int Potter::gety(){
    return py;
}