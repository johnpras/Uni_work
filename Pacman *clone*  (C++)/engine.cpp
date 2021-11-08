#include "engine.h"
#include <vector>

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <cstdlib>
#include <unistd.h>
#include <iostream>



using namespace std;

Engine::Engine(){

}

//απλά παίρνω το όνομα του δυαδ. αρχείου απο εδώ.
void Engine::getarg(string s){
    argforbin = s;
}

//πρόκειται για την κίνηση του gnome + αν φάει ο gnome τον μπάμπη καλεί την message που καλεί μέθοδο από hiscore
//για υπολογισμό score ονομα κτλπ. και εμφάνηση στο τέλος
// ότι κίνηση κάνει ο μπάμπης, ίδια θα κάνει και το gnome, θα τον αντιγράψει.
void Engine::smartmoveg(){
    
 if(mapstring[g->gety()][g->getx()+1] == 'P'){
    testnum = 1;
    message(1);
}
if (ch == KEY_RIGHT && isitlegitmove(g->gety(),g->getx()+1) == 1 ){
            mvaddch(g->gety(),g->getx(),' ');
            mapstring[g->gety()][g->getx()] = ' ';
            mvaddch(g->gety(),g->getx()+1,'G');
            mapstring[g->gety()][g->getx()+1] = 'G';
            g->setx(g->getx()+1);
}

if(mapstring[g->gety()+1][g->getx()] == 'P'){
    testnum = 1;
    message(1);
}
if (ch == KEY_DOWN && isitlegitmove(g->gety()+1,g->getx()) == 1  ){
            mvaddch(g->gety(),g->getx(),' ');
            mapstring[g->gety()][g->getx()] = ' ';
            mvaddch(g->gety()+1,g->getx(),'G');
            mapstring[g->gety()+1][g->getx()] = 'G';
            g->sety(g->gety()+1);         
}

if(mapstring[g->gety()][g->getx()-1] == 'P'){
    testnum = 1;
    message(1);
}
if(ch == KEY_LEFT && isitlegitmove(g->gety(),g->getx()-1) == 1){
            mvaddch(g->gety(),g->getx(),' ');
            mapstring[g->gety()][g->getx()] = ' ';
            mvaddch(g->gety(),g->getx()-1,'G');
            mapstring[g->gety()][g->getx()-1] = 'G';
            g->setx(g->getx()-1);          
}
if(mapstring[g->gety()-1][g->getx()] == 'P'){
    testnum = 1;
    message(1);
}

if (ch == KEY_UP && isitlegitmove(g->gety()-1,g->getx()) == 1  ){
            mvaddch(g->gety(),g->getx(),' ');
            mapstring[g->gety()][g->getx()] = ' ';
            mvaddch(g->gety()-1,g->getx(),'G');
            mapstring[g->gety()-1][g->getx()] = 'G';
            g->sety(g->gety()-1);
}
refresh(); 
}


//ανάλογα με το αν έχασε ή νίκησε ο μπάμπης, καλεί μέθοδο από hiscore και τυπώνεται μήνυμα μαζί με πληροφορίες.
void Engine::message(int nmbr){

h = new Hiscore();
h->calc(nmbr,score,argforbin);

}

//πρόκειται για την κίνηση του gnome + αν φάει ο traal τον μπάμπη καλεί την message που καλεί μέθοδο από hiscore
//για υπολογισμό score ονομα κτλπ. και εμφάνηση στο τέλος
// ότι κίνηση κάνει ο μπάμπης, ίδια θα κάνει και το traal, θα τον αντιγράψει. Καλεί και την μέθοδο κίνησης του gnome στο τέλος.
void Engine::smartmovet(){

string nna;
 if(mapstring[t->gety()][t->getx()+1] == 'P'){
    testnum = 1;
    message(1);
}
if (ch == KEY_RIGHT && isitlegitmove(t->gety(),t->getx()+1) == 1 ){
            mvaddch(t->gety(),t->getx(),' ');
            mapstring[t->gety()][t->getx()] = ' ';
            mvaddch(t->gety(),t->getx()+1,'T');
            mapstring[t->gety()][t->getx()+1] = 'T';
            t->setx(t->getx()+1);
}
if(mapstring[t->gety()+1][t->getx()] == 'P'){
    testnum = 1;
    message(1);
}
if (ch == KEY_DOWN && isitlegitmove(t->gety()+1,t->getx()) == 1  ){
            mvaddch(t->gety(),t->getx(),' ');
            mapstring[t->gety()][t->getx()] = ' ';
            mvaddch(t->gety()+1,t->getx(),'T');
            mapstring[t->gety()+1][t->getx()] = 'T';
            t->sety(t->gety()+1);     
}
if(mapstring[t->gety()][t->getx()-1] == 'P'){
    testnum = 1;
    message(1);
}
if(ch == KEY_LEFT && isitlegitmove(t->gety(),t->getx()-1) == 1){
            mvaddch(t->gety(),t->getx(),' ');
            mapstring[t->gety()][t->getx()] = ' ';
            mvaddch(t->gety(),t->getx()-1,'T');
            mapstring[t->gety()][t->getx()-1] = 'T';
            t->setx(t->getx()-1);          
}
if(mapstring[t->gety()-1][t->getx()] == 'P'){
    testnum = 1;
    message(1);
}
if (ch == KEY_UP && isitlegitmove(t->gety()-1,t->getx()) == 1  ){
            mvaddch(t->gety(),t->getx(),' ');
            mapstring[t->gety()][t->getx()] = ' ';
            mvaddch(t->gety()-1,t->getx(),'T');
            mapstring[t->gety()-1][t->getx()] = 'T';
            t->sety(t->gety()-1);
}
refresh(); 
smartmoveg();
}


//καλείται έπειτα από κάθε κίνηση 
//ουσιαστικά αν περνάει πάνω απο πετράδια τα μαζεύει, κρατάει σκορ και εμφανίζει την περγαμηνή αν τα μαζέψει όλα
//αν πέσει πάνω σε τέρατα φέρνει την message που καλεί την calc της highscore κλάσης και εμφανίζει ανάλογο μήνυμα με στοιχεία κτλπ.
//αν πέσει σε περγαμηνή, δηλαδή έχει μαζέψει τα πετράδια, παίρνει παραπάνω πόντους και καλεί την message παλι.
void Engine::crash(int n){

int num = n;
string nna;
if(num == 2){
    stonenum++;
    score += 10;
    if(stonenum == 10){
        placescroll();
        stonenum=0;
    }
}
if(num == 4){
    testnum = 1;
    message(1);
}
if(num == 5){
    score += 100;
    testnum = 0;
    message(0);
}
refresh();
}


//δημιουργία τυχαίου έγκυρου ζεύγους τιμών για την περγαμηνή
void Engine::placescroll(){

srand (clock());
int i=0, cx, cy;
while(1){
    cx = rand() % (mapstring[1].size()-1) +1;
    cy = rand() % (mapstring.size()-1) +1;
    int h = isitlegitmove(cy,cx);
        if(h == 1){
            mvaddch(cy,cx,'$');
            mvchgat(cy,cx, 1, A_BLINK, 2, NULL);
            mapstring[cy][cx] = '$';
            break;
        }
    }
refresh();
}


// απλός έλεγχος θέσης για το τι "περιέχει".
int Engine::isitlegitmove(unsigned int iy, unsigned int ix){

int returnnum = 0;
    if(mapstring[iy][ix] == ' '){   
        returnnum = 1;
    } else if(mapstring[iy][ix] == 'S'){ 
        returnnum = 2;
    } else if (mapstring[iy][ix] == '*'){
        returnnum = 3;
    } else if (mapstring[iy][ix] == 'G' || mapstring[iy][ix] == 'T'){
        returnnum = 4;
    } else if (mapstring[iy][ix] == '$'){
        returnnum = 5;
    }
return returnnum;
}


//εδώ γίνεται η κίνηση
//έλεγχος της θέσεις που θέλει να πάει ο μπάμπης.
//αν είναι άδεια ή περιέχει πετράδια γίνεται κανονικά η κίνηση.
//έπειτα από κάθε κίνηση του μπάμπη καλείται η smartmovet ( και μέσω αυτής η smartmoveg) για την κίνηση των τεράτων.
//backspase τερματισμός, spacebar μένει στάσιμος ο μπάμπης
void Engine::moveto(){

raw();	
keypad(stdscr, TRUE);
noecho();
int a;
while (1)
{
    ch = getch();
    switch(ch) {

            case KEY_UP:
            crash(isitlegitmove(p->gety()-1,p->getx()));
            if( (isitlegitmove(p->gety()-1,p->getx())) == 1 || (isitlegitmove(p->gety()-1,p->getx()) == 2) ) {
                p->UP(p->getx(),p->gety(),mapstring);
                p->sety(p->gety()-1);
            }
            smartmovet();
            break;   

            case KEY_DOWN:
            crash(isitlegitmove(p->gety()+1,p->getx()));
            if( (isitlegitmove(p->gety()+1,p->getx())) == 1 || (isitlegitmove(p->gety()+1,p->getx()) == 2) ){
                p->DOWN(p->getx(),p->gety(),mapstring);
                p->sety(p->gety()+1);
            }
            smartmovet();
            break;     

            case KEY_LEFT:
            crash(isitlegitmove(p->gety(),p->getx()-1));
            if( (isitlegitmove(p->gety(),p->getx()-1)) == 1  || isitlegitmove(p->gety(),p->getx()-1) == 2) {
                p->LEFT(p->getx(),p->gety(),mapstring);
                p->setx(p->getx()-1);
            }
            smartmovet();
            break;

            case KEY_RIGHT:
            crash(isitlegitmove(p->gety(),p->getx()+1));
            if( (isitlegitmove(p->gety(),p->getx()+1)) == 1 || isitlegitmove(p->gety(),p->getx()+1) == 2){
                p->RIGHT(p->getx(),p->gety(),mapstring);
                p->setx(p->getx()+1);
            }
            smartmovet();
            break;

            case KEY_BACKSPACE:
            endwin();
            exit(1);

            case 32:
            smartmovet();
            break;
        }
    if (ch == KEY_BACKSPACE){
        endwin(); 
        break;
    }
}    
refresh(); 
endwin();  
} 


//δημιουργία τυχαίου έγκυρου ζεύγους τιμών για τον μπάμπη και εμφάνισή του/τοποθέτηση στο map.
void Engine::placepotter(){

p = new Potter();
srand (clock());
int i=0, cx, cy;
while (1){
 cx = rand() % (mapstring[1].size()-1) +1;
 cy = rand() % (mapstring.size()-1) +1; 
 int h = isitlegitmove(cy,cx);  
    if(h == 1){
        mvaddch(cy,cx,'P');
        mapstring[cy][cx] = 'P';
        break;
    }   
}
p->setx(cx);
p->sety(cy);
refresh();
}


//δημιουργία τυχαίου έγκυρου ζεύγους τιμών για τα πετράδια και τοποθέτηση στο map.
void Engine::placestones(){

srand (clock());
int i=0, cx, cy;
while(1){
    cx = rand() % (mapstring[1].size()-1) +1;
    cy = rand() % (mapstring.size()-1) +1;
    int h = isitlegitmove(cy,cx);
        if(h == 1){
            mvaddch(cy,cx,'S');
            mvchgat(cy,cx, 1, A_BOLD, 1, NULL);
            mapstring[cy][cx] = 'S';
            i++;
        }
        if(i == 10){
            break;
        }
    }
refresh();
}

//δημιουργία τυχαίου έγκυρου ζεύγους τιμών για το traal και εμφάνισή του/τοποθέτηση στο map.
void Engine::placetraal(){

t = new Traal();
srand (clock());
int i=0, cx, cy;
while (1){
cx = rand() % (mapstring[1].size()-1) +1;
cy = rand() % (mapstring.size()-1) +1;
int h = isitlegitmove(cy,cx);
    if(h == 1){
        mvaddch(cy,cx,'T');
        mapstring[cy][cx] = 'T';
        break;
    }
}            
t->setx(cx);
t->sety(cy);    
refresh();
}


//δημιουργία τυχαίου έγκυρου ζεύγους τιμών για το gnome και εμφάνισή του/τοποθέτηση στο map.
void Engine::placegnome(){

g = new Gnome();
srand (clock());
int i=0, cx, cy;
while (1){
    cx = rand() % (mapstring[1].size()-1) +1;
    cy = rand() % (mapstring.size()-1) +1;
    int h = isitlegitmove(cy,cx);
    if(h == 1){
        move(cy,cx);
        addch('G');
        mapstring[cy][cx] = 'G';
        break;
    }
}
g->setx(cx);
g->sety(cy); 
refresh();
}


//φόρτωση του χάρτη + έλεγχος + τύπωμα στην οθόνη.
void Engine::loadmap(string s){

    ifstream file;
        file.open(s);
        try{
            if(file){
                while (getline(file, line)) {
                mapstring.push_back(line);
                } 
            }else{
            throw exception();
            }
        }
        catch(exception e){
            cout << "Wrong file...Check again." << endl;
            exit(0);
        }      
    file.close(); 
    initscr();
    refresh();
    keypad(stdscr, TRUE);
    for(int i=0; i<mapstring.size();i++){
        mvwprintw(stdscr, i, 0, mapstring[i].c_str());
    }
    wrefresh(stdscr);
refresh();

}
