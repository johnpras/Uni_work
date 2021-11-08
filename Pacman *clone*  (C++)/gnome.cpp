#include "gnome.h"

using namespace std;


Gnome::Gnome(){
    gny=0;
    gnx=0;
}

void Gnome::setx(int xxx){
    gnx = xxx;
}
void Gnome::sety(int yyy){
    gny = yyy;
}
int Gnome::getx(){
    return gnx;
}

int Gnome::gety(){
    return gny;
}