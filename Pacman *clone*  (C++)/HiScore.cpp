#include "HiScore.h"

#include <stdio.h>
#include <stdlib.h>
#include <cstdlib>
#include <unistd.h>
#include <iostream>
#include <ncurses.h>
#include <curses.h>
#include <fstream>
#include <sstream>
#include <vector>
#include <cstring>
#include <cstdio>

using namespace std;

Hiscore::Hiscore(){

}


//δεν κατάφερα να υλοποιήσω τα <<
void Hiscore::operator <<(int x){

}

//δεν κατάφερα να υλοποιήσω τα <<
Hiscore& Hiscore::operator <<(string n){

    return *this;
}


//εμφανίζει τα υπάρχων score
//το εχω σε σχολια, δεν δουλευει.
//ήταν μια σκεψη για να εμφανίζω τα περιεχομενα του δυαδ. αρχείου
void Hiscore::showtopfive(){
// int i=0;
//     binfile.open("binaryfile.bin", ios::binary | ios::in | ios::out);
//     while(binfile.read((char*)&datarray, (sizeof(datarray)))){
//         cout << datarray[i].s << datarray[i].sc <<endl;
//         i++;
//     }
//     binfile.close();
}


//εμφανίζει μήνυμα στην οθόνη με το όνομα και το σκορ του μπάμπη.
//γράφει το όνομα και το σκορ σε δυαδικό αρχείο.
//δεν κάνω έλεγχο και δυστυχώς δεν έχω προλάβει να κάνω πολλά πάνω σε αυτό
//μονο ένα βασικο να τοποθετω το score στο δυαδ. αρχείο.
void Hiscore::calc(int nm, int scr, string x){

Hiscore* h = new Hiscore();
data d[5];
string nna;
arrnum = 0;
data dat;


if(nm == 1){

    endwin();
    cout << "You LOST..."<< endl;
    cout << "What is your name?"<<endl;
    h->sc = scr;
    cin >> nna;
    h->name = nna;
    binfile.open(x, ios::binary | ios::in | ios::out | ios::app);
    //strncpy(h->s, nna.c_str(), sizeof(h->s));
    binfile.write(reinterpret_cast<char*>(&h),sizeof(h));
    cout << h->name << " " << h->sc << endl;
    binfile.close();

exit(1);

    
}else {


    endwin();
    cout << "Congratulations u WON..."<< endl;
    cout << "What is your name?"<<endl;
    h->sc = scr;
    cin >> nna;
    h->name = nna;
    binfile.open(x, ios::binary | ios::in | ios::out | ios::app);
    //strncpy(h->s, nna.c_str(), sizeof(h->s));
    binfile.write(reinterpret_cast<char*>(&h),sizeof(h));
    cout << h->name << " " << h->sc << endl;
    binfile.close();
exit(1);
} 

}
