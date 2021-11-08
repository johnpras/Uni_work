#include "traal.h"

using namespace std;


Traal::Traal(){
    yt=0;
    xt=0;
}

void Traal::setx(int xxx){
    xt = xxx;
}
void Traal::sety(int yyy){
    yt = yyy;
}
int Traal::getx(){
    return xt;
}

int Traal::gety(){
    return yt;
}