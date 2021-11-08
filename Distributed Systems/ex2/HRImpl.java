import java.io.*;
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;


public class HRImpl extends java.rmi.server.UnicastRemoteObject implements HRInterface{

// 5 arrays που θα κρατάνε τα ονόμα πελατών για κάθε τύπο
private String RoomsTypeA []= new String[30];
private String RoomsTypeB []= new String[45];
private String RoomsTypeC []= new String[25];
private String RoomsTypeD []= new String[10];
private String RoomsTypeE []= new String[5];
// θα το χρειαστούμε για να αρχικοποιήσουμε/γεμίσουμε κάθε array με την λέξη "empty"
private String str="empty";
// μέτρημα πόσων άδειων θέσεων έχει κάθε πίνακας
private int counterA = 0;
private int counterB = 0;
private int counterC = 0;
private int counterD = 0;
private int counterE = 0;
// θα επιστρέφουμε θέση πίνακα με αριθμό άδειων δωματίων . πχ counterarray[0] για τα τυπου Α κτλπ...
String [] counterarray = {"30","45","25","10","5"};


//γέμισμα όλων των πινάκων με τον χαρακτήρα "empty"
public void init(){

    for (int i=0; i<RoomsTypeA.length; i++){
        this.RoomsTypeA[i] = str;
    }
    for (int j=0; j<RoomsTypeB.length; j++){
        this.RoomsTypeB[j]= str;
    }

    for (int k=0; k<RoomsTypeC.length; k++){
        this.RoomsTypeC[k] = str;
    }

    for (int l=0; l<RoomsTypeD.length; l++){
        this.RoomsTypeD[l] = str;
    }

    for (int m=0; m<RoomsTypeE.length; m++){
        this.RoomsTypeE[m] = str;
    }
}

//το καλούμε μια φορά όταν ξεκινήσει το πρόγραμμα (την init)
    public HRImpl() throws java.rmi.RemoteException{
        init();
    }
    

//η γενική ιδέα είναι:
//ψάχνουμε τον κάθε πίνακα για κάθε τύπο και όπου βρίσκουμε τη λέξη empty την κρατάμε    
//το σύνολο αυτό είναι ο αριθμός των άδειων δωματίων
public String list(){

    for(int j=0; j < RoomsTypeA.length;j++){
        if(RoomsTypeA[j].contains(str)){
            counterA++;
        }
    }
    for(int j=0; j < RoomsTypeB.length;j++){
      if(RoomsTypeB[j].contains(str)){
          counterB++;
     }
    }
    for(int j=0; j < RoomsTypeC.length;j++){
        if(RoomsTypeC[j].contains(str)){
            counterC++;
       }
      }
    for(int j=0; j < RoomsTypeD.length;j++){
        if(RoomsTypeD[j].contains(str)){
            counterD++;
       }
      }
      for(int j=0; j < RoomsTypeE.length;j++){
        if(RoomsTypeE[j].contains(str)){
            counterE++;
       }
      }

//ο πίνακας που θα επιστρέψουμε, 1η θέση θα έχει τον αριθμό άδειων δωματίων του τύπου Α, 2η θέση του τύπου Β κ.ο.κ      
counterarray[0]=Integer.toString(counterA);    
counterarray[1]=Integer.toString(counterB);
counterarray[2]=Integer.toString(counterC);    
counterarray[3]=Integer.toString(counterD);    
counterarray[4]=Integer.toString(counterE);

//για να μην υπολογίζονται ξανά x2 κάθε φορά που καλούμε την list
counterA = 0;
counterB = 0;
counterC = 0;
counterD = 0;
counterE = 0;

return Arrays.toString(counterarray);

}


//γενική ιδέα: όταν κάνει κάποιος book, θα γεμίζει η θέση/θέσεις του αντίστοιχου πίνακα με το όνομα 
//που έκανε κράτηση x φορές όπου x ο αριθμός δωματίων που θέλει
//πρώτα βρίσκω κενές θέσεις, τις "μαρκάρω" με το "1" και έπειτα ψάχνω τον πίνακα και όπου έχω "1" το ανικαθιστώ με το όνομα του
//Επαναλαμβάνεται η διαδικασία  5 φορές ( για κάθε τύπο )
public String book(String a, String b, String c){
 
String result =" ";
int y = Integer.parseInt(b);    //πόσες θέσεις θέλω να κλείσω
int ya = Integer.parseInt(counterarray[0]); //πόσες κενές θέσεις έχει ο πίνακας Α
int yb = Integer.parseInt(counterarray[1]); //πόσες κενές θέσεις έχει ο πίνακας Β
int yc = Integer.parseInt(counterarray[2]); //πόσες κενές θέσεις έχει ο πίνακας Γ
int yd = Integer.parseInt(counterarray[3]); //πόσες κενές θέσεις έχει ο πίνακας Δ
int ye = Integer.parseInt(counterarray[4]); //πόσες κενές θέσεις έχει ο πίνακας Ε

int j=0;
int k=0;

// Αν έκανε book στον τύπο Α

if(a.equals("A")){

    if(ya >=y ){          //Αν ο πίνακας Α έχει κενές θέσεις για να γίνει η κράτηση ( δηλαδή το ya >= y)
        while(j<y){                  //Για y φορές (πόσες θέσεις θέλει να κλείσει)
            if(RoomsTypeA[k].equals(str))       //ψάψνω τον πίνακα και όταν βρίσκω κενή θέση (δηλαδή να περιέχει το "empty")
            {                                   //γεμίζω το αντίστοιχο κελή με το "1" και συνεχίζω μέχρι να ολοκληρωθεί y φορές
                RoomsTypeA[k] = "1";
                j++;
            }
            k++;
        }
                                                        // όπου έχει "1" ο πίνακας το αντικαθιστώ με το όνομα που έγινε η κράτηση
            for(int i=0;i<RoomsTypeA.length;i++){
                //if(RoomsTypeA.equals("1")){
                if(RoomsTypeA[i] == "1"){
                    RoomsTypeA[i] = c;
                }
            }
                                                    //επιστροφή μηνύματος
            result = "egine kratisi sto onoma "+c+" gia "+y+" dwmatia typou "+a+" sinolikou kostous "+y*50+" eurw ";
    }
                             //Αν δεν υπάρχουν όλες οι θέσεις που ζήτησε
    if((ya < y && ya > 0) || (y>30 && ya>0) || y>30 ) {    //αν έχουμε κενές θέσεις (ya > 0) και χωράει ένα μέρος της κράτησης
        result = "Den vrethikan diathesima ola ta dwmatia pou zitisate..Thelete na kanete kratisi ligotera?";
    }
    
    if(ya == 0){    //αν δεν έχουμε κενές θέσεις
        result = "Apotixia kratisis";
    }

//ίδια ακριβώς διαδικασία και για τους υπόλοιπους τύπους δωματίων
}else if(a.equals("B")){

    if(yb > y || yb == y){ 
        while(j<y){
            if(RoomsTypeB[k].equals(str))
            {       
                RoomsTypeB[k] = "1";
                j++;
            }
            k++;
        }

            for(int i=0;i<RoomsTypeB.length;i++){
                if(RoomsTypeB[i] == "1"){
                    RoomsTypeB[i] = c;
                }
            }
            
            result = "egine kratisi sto onoma "+c+" gia "+y+" dwmatia typou "+a+" sinolikou kostous "+y*70+" eurw ";
    }

    if((yb < y && yb > 0) || (y>30 && yb>0) || y>30 ) {    //αν έχουμε κενές θέσεις (yb > 0) και χωράει ένα μέρος της κράτησης
        result = "Den vrethikan diathesima ola ta dwmatia pou zitisate..Thelete na kanete kratisi ligotera?";
    }
    
    if(yb == 0){    //αν δεν έχουμε κενές θέσεις
        result = "Apotixia kratisis";
    }


}else if(a.equals("C")){

    if(yc > y || yc == y){ 
        while(j<y){
            if(RoomsTypeC[k].equals(str))
            {       
                RoomsTypeC[k] = "1";
                j++;
            }
            k++;
        }

            for(int i=0;i<RoomsTypeC.length;i++){
                if(RoomsTypeC[i] == "1"){
                    RoomsTypeC[i] = c;
                }
            }
            
            result = "egine kratisi sto onoma "+c+" gia "+y+" dwmatia typou "+a+" sinolikou kostous "+y*80+" eurw ";
    }

    if((yc < y && yc > 0) || (y>30 && yc>0) || y>30 ) {    //αν έχουμε κενές θέσεις (yc > 0) και χωράει ένα μέρος της κράτησης
        result = "Den vrethikan diathesima ola ta dwmatia pou zitisate..Thelete na kanete kratisi ligotera?";
    }
    
    if(yc == 0){    //αν δεν έχουμε κενές θέσεις
        result = "Apotixia kratisis";
    }


}else if(a.equals("D")){
    if(yd > y || yd == y){ 
        while(j<y){
            if(RoomsTypeD[k].equals(str))
            {       
                RoomsTypeD[k] = "1";
                j++;
            }
            k++;
        }

            for(int i=0;i<RoomsTypeD.length;i++){
                if(RoomsTypeD[i] == "1"){
                    RoomsTypeD[i] = c;
                }
            }
            
            result = "egine kratisi sto onoma "+c+" gia "+y+" dwmatia typou "+a+" sinolikou kostous "+y*120+" eurw ";
    }

    if((yd < y && yd > 0) || (y>30 && yd>0) || y>30 ) {    //αν έχουμε κενές θέσεις (yd > 0) και χωράει ένα μέρος της κράτησης
        result = "Den vrethikan diathesima ola ta dwmatia pou zitisate..Thelete na kanete kratisi ligotera?";
    }
    
    if(yd == 0){    //αν δεν έχουμε κενές θέσεις
        result = "Apotixia kratisis";
    }


}else if(a.equals("E")){
    if(ye > y || ye == y){ 
        while(j<y){
            if(RoomsTypeE[k].equals(str))
            {       
                RoomsTypeE[k] = "1";
                j++;
            }
            k++;
        }

            for(int i=0;i<RoomsTypeE.length;i++){
                if(RoomsTypeE[i] == "1"){
                    RoomsTypeE[i] = c;
                }
            }
            
            result = "egine kratisi sto onoma "+c+" gia "+y+" dwmatia typou "+a+" sinolikou kostous "+y*150+" eurw ";
    }

    if((ye < y && ye > 0) || (y>30 && ye>0) || y>30 ) {    //αν έχουμε κενές θέσεις (ye > 0) και χωράει ένα μέρος της κράτησης
        result = "Den vrethikan diathesima ola ta dwmatia pou zitisate..Thelete na kanete kratisi ligotera?";
    }
    
    if(ye == 0){    //αν δεν έχουμε κενές θέσεις
        result = "Apotixia kratisis";
    }

// αν δώσει διαφορετικό γράμμα απο Α-Ε, εμφάνιση λάθους 
}else{
    result= "Wrong input";
}

return result;
}


//Για κάθε δωμάτιο δημιουργείται μια λίστα. Μέσα σε αυτήν συγκεντρώνονται όλα τα ονόματα με κράτηση
//έπειτα με ένα Hashset χρησιμοποιώντας το collections.frequency παίρνουμε πόσες φορές υπάρχει ένα όνομα σε κάθε λίστα
//ώστε να πάρω ως αποτέλεσμα το όναμα και το πόσα δωμάτια έκλεισε
public String guests(){

    String testA = "";
    String testB = "";
    String testC = "";
    String testD = "";
    String testE = "";
    List<String> roomsnameA = new ArrayList<String>();
    List<String> roomsnameB = new ArrayList<String>();
    List<String> roomsnameC = new ArrayList<String>();
    List<String> roomsnameD = new ArrayList<String>();
    List<String> roomsnameE = new ArrayList<String>();
    List<String> returninglistA = new ArrayList<String>();
    List<String> returninglistB = new ArrayList<String>();
    List<String> returninglistC = new ArrayList<String>();
    List<String> returninglistD = new ArrayList<String>();
    List<String> returninglistE = new ArrayList<String>();

//γεμίζω για κάθε τύπο δωματίου μια λίστα με τα ονόματα
    for(int i=0; i < RoomsTypeA.length;i++){
        if(!RoomsTypeA[i].contains(str)){
            testA = RoomsTypeA[i];
            roomsnameA.add((testA));
        }
    }
    for(int i=0; i < RoomsTypeB.length;i++){
        if(!RoomsTypeB[i].contains(str)){
            testB = RoomsTypeB[i];
            roomsnameB.add((testB));
        }
    }
    for(int i=0; i < RoomsTypeC.length;i++){
        if(!RoomsTypeC[i].contains(str)){
            testC = RoomsTypeC[i];
            roomsnameC.add((testC));
        }
    }
    for(int i=0; i < RoomsTypeD.length;i++){
        if(!RoomsTypeD[i].contains(str)){
            testD = RoomsTypeD[i];
            roomsnameD.add((testD));
        }
    }
    for(int i=0; i < RoomsTypeE.length;i++){
        if(!RoomsTypeE[i].contains(str)){
            testE = RoomsTypeE[i];
            roomsnameE.add((testE));
        }
    }

//μετράω πόσες φορές βρίσκεται ένα όνομα στην κάθε λίστα    
//και γεμίσω μια λίστα που θα επιστρέψω,  με στυλ πχ. Γιάννης: 12 δωμάτια τύπου Α συνολικού κόστους (12*50=) 600E
// αυτό γίνεται για κάθε τύπο δωματίων
Set<String> uniqueWordsA = new HashSet<String>(roomsnameA);
for (String word : uniqueWordsA) {
    returninglistA.add(word + ": " + Collections.frequency(roomsnameA, word)+" dwmatia tipou A sinolikou kostous "+Collections.frequency(roomsnameA, word)*50+" E\n");
}
Set<String> uniqueWordsB = new HashSet<String>(roomsnameB);
for (String word : uniqueWordsB) {
    returninglistB.add(word + ": " + Collections.frequency(roomsnameB, word)+" dwmatia tipou B sinolikou kostous "+Collections.frequency(roomsnameB, word)*70+" E\n");
}
Set<String> uniqueWordsC = new HashSet<String>(roomsnameC);
for (String word : uniqueWordsC) {
    returninglistC.add(word + ": " + Collections.frequency(roomsnameC, word)+" dwmatia tipou C sinolikou kostous "+Collections.frequency(roomsnameC, word)*80+" E\n");
}
Set<String> uniqueWordsD = new HashSet<String>(roomsnameD);
for (String word : uniqueWordsD) {
    returninglistD.add(word + ": " + Collections.frequency(roomsnameD, word)+" dwmatia tipou D sinolikou kostous "+Collections.frequency(roomsnameD, word)*120+" E\n");
}
Set<String> uniqueWordsE = new HashSet<String>(roomsnameE);
for (String word : uniqueWordsE) {
    returninglistE.add(word + ": " + Collections.frequency(roomsnameE, word)+" dwmatia tipou E sinolikou kostous "+Collections.frequency(roomsnameE, word)*150+" E\n");
}


//ενώνω τις λίστες και  τις επιστρέφω αφού τις κάνω πρώτα String
List<String> newList = new ArrayList<String>(returninglistA);
newList.addAll(returninglistB);
newList.addAll(returninglistC);
newList.addAll(returninglistD);
newList.addAll(returninglistE);
//και την κάνω string και την επιστρέφω
String listotoarray="";
listotoarray = String.join(",", newList);

return listotoarray;

}



public String cancel(String a, String b, String c) {


String testA;
String testB;
String testC;
String testD;
String testE;
List<String> roomsnameA = new ArrayList<String>();
List<String> roomsnameB = new ArrayList<String>();
List<String> roomsnameC = new ArrayList<String>();
List<String> roomsnameD = new ArrayList<String>();
List<String> roomsnameE = new ArrayList<String>();
int ycancel = Integer.parseInt(b);                  //πόσες θέσεις θέλω να ακυρώσω
int j=0;
int k=0;
String listotoarray="";
List<String> returninglistA = new ArrayList<String>();   
List<String> returninglistB = new ArrayList<String>();  
List<String> returninglistC = new ArrayList<String>();   
List<String> returninglistD = new ArrayList<String>();  
List<String> returninglistE = new ArrayList<String>();  



if(a.equals("A")){

    if(ycancel > 0){                     // αν υπάρχουν θέσεις να ακυρώσω
        while(j<ycancel){                //κάνω την αντίστροφη διαδικασία που έκανα στο book
            if(RoomsTypeA[k].equals(c))     //ψάχνω τις θέσεις με το όνομα του και τις κάνω "empty"
            {       
                RoomsTypeA[k] = str;
                j++;
            }
            k++;
        }
                                                    //έπειτα κάνω ότι έκανα στο guests γιατι θέλω να επιστρέφω αν τυχόν έχει κι' άλλα δωμάτια
        for(int i=0; i < RoomsTypeA.length;i++){
            if(!RoomsTypeA[i].contains(str)){
                testA = RoomsTypeA[i];
                roomsnameA.add((testA));
            }
        }
    
        
        Set<String> uniqueWordsA = new HashSet<String>(roomsnameA);
        returninglistA.add(c + ": " + Collections.frequency(roomsnameA, c)+" dwmatia tipou A sinolikou kostous "+Collections.frequency(roomsnameA, c)*50+" E\n");

    
        listotoarray = String.join(",", returninglistA);

        if(listotoarray.equals("")){

            returninglistA.add("0 dwmatia");
            listotoarray = String.join(",", returninglistA);

        }




      try{
        doCallbacks();
      }  catch(Exception e){
      }
  




    }
    if(ycancel == 0 || ycancel < 0 ){

        returninglistA.add("apotixia akirwshs. Dwse dwsto arithmo..");
        listotoarray = String.join(",", returninglistA);
    }  
    
    //δεν ελέγχω αν ο χρήστης ακυρώσει παραπάνω δωμάτια από όσα έκλεισε 
    

}
    
    if(a.equals("B")){

        if(ycancel > 0){  
            while(j<ycancel){
                if(RoomsTypeB[k].equals(c))
                {       
                    RoomsTypeB[k] = str;
                    j++;
                }
                k++;
            }
        
            for(int i=0; i < RoomsTypeB.length;i++){
                if(!RoomsTypeB[i].contains(str)){
                    testB = RoomsTypeB[i];
                    roomsnameB.add((testB));
                }
            }
        
            
            Set<String> uniqueWordsB = new HashSet<String>(roomsnameB);
                    returninglistB.add(c + ": " + Collections.frequency(roomsnameB, c)+" dwmatia tipou B sinolikou kostous "+Collections.frequency(roomsnameB, c)*70+" E\n");
        
            listotoarray = String.join(",", returninglistB);
    
            if(listotoarray.equals("")){
    
                returninglistB.add("0 dwmatia");
                listotoarray = String.join(",", returninglistB);
    
            }
    
        }     
        if(ycancel == 0 || ycancel < 0 ){

            returninglistB.add("apotixia akirwshs. Dwse dwsto arithmo..");
            listotoarray = String.join(",", returninglistB);
        }  
    }

    if(a.equals("C")){

        if(ycancel > 0){  
            while(j<ycancel){
                if(RoomsTypeC[k].equals(c))
                {       
                    RoomsTypeC[k] = str;
                    j++;
                }
                k++;
            }
        
            for(int i=0; i < RoomsTypeC.length;i++){
                if(!RoomsTypeC[i].contains(str)){
                    testC = RoomsTypeC[i];
                    roomsnameC.add((testC));
                }
            }
        
            
            Set<String> uniqueWordsC = new HashSet<String>(roomsnameC);
                    returninglistC.add(c + ": " + Collections.frequency(roomsnameC, c)+" dwmatia tipou C sinolikou kostous "+Collections.frequency(roomsnameC, c)*80+" E\n");
        
            listotoarray = String.join(",", returninglistC);
    
            if(listotoarray.equals("")){
    
                returninglistC.add("0 dwmatia");
                listotoarray = String.join(",", returninglistC);
    
            }
    
        }     
        if(ycancel == 0 || ycancel < 0 ){

            returninglistC.add("apotixia akirwshs. Dwse dwsto arithmo..");
            listotoarray = String.join(",", returninglistC);
        }  
    }

    if(a.equals("D")){

        if(ycancel > 0){  //an uparxoun theseis gia na akurwsw
            while(j<ycancel){
                if(RoomsTypeD[k].equals(c))
                {       
                    RoomsTypeD[k] = str;
                    j++;
                }
                k++;
            }
        
            for(int i=0; i < RoomsTypeD.length;i++){
                if(!RoomsTypeD[i].contains(str)){
                    testD = RoomsTypeD[i];
                    roomsnameD.add((testD));
                }
            }
        
            
            Set<String> uniqueWordsD = new HashSet<String>(roomsnameD);
                    returninglistD.add(c + ": " + Collections.frequency(roomsnameD, c)+" dwmatia tipou D sinolikou kostous "+Collections.frequency(roomsnameD, c)*120+" E\n");
        
            listotoarray = String.join(",", returninglistD);
    
            if(listotoarray.equals("")){
    
                returninglistD.add("0 dwmatia");
                listotoarray = String.join(",", returninglistD);
    
            }
    
        }     
        if(ycancel == 0 || ycancel < 0 ){

            returninglistD.add("apotixia akirwshs. Dwse dwsto arithmo..");
            listotoarray = String.join(",", returninglistD);
        }  
    }


    if(a.equals("E")){

        if(ycancel > 0){  //an uparxoun theseis gia na akurwsw
            while(j<ycancel){
                if(RoomsTypeE[k].equals(c))
                {       
                    RoomsTypeE[k] = str;
                    j++;
                }
                k++;
            }
        
            for(int i=0; i < RoomsTypeE.length;i++){
                if(!RoomsTypeE[i].contains(str)){
                    testE = RoomsTypeE[i];
                    roomsnameE.add((testE));
                }
            }
        
            
            Set<String> uniqueWordsE = new HashSet<String>(roomsnameE);
                    returninglistE.add(c + ": " + Collections.frequency(roomsnameE, c)+" dwmatia tipou E sinolikou kostous "+Collections.frequency(roomsnameE, c)*70+" E\n");
        
            listotoarray = String.join(",", returninglistE);
    
            if(listotoarray.equals("")){
    
                returninglistE.add("0 dwmatia");
                listotoarray = String.join(",", returninglistE);
    
            }
    
        }     
        if(ycancel == 0 || ycancel < 0 ){

            returninglistE.add("apotixia akirwshs. Dwse dwsto arithmo..");
            listotoarray = String.join(",", returninglistE);
        }  
    }



return listotoarray;

}


//Για εγγραφή client στη λίστα

List<HRClientInterface> clientsList = new ArrayList<>();   

public void addtolist(HRClientInterface HRClientObject) throws RemoteException
{
    clientsList.add(HRClientObject);
    //System.out.println(clientsListA);
    System.out.println("client registered");

   
}


private synchronized void doCallbacks() throws RemoteException{

        for (int i = 0; i < clientsList.size(); i++){
            clientsList.get(i).notifyme();
          } 
   
    
} 


}

    
