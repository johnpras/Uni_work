// tmima D1-B deutera 14:00-16:00
// Prasidis Ioannis cse 46373
// askisi 2


#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"
#define SIZE 10

int main(int argc, char *argv[]){

 int p,rank,N,i,j,k,diagj,result,numm,num;
 int max_diag,min_local,mini,minj,min;
 int max=0;
 int offset=0;
 int token=0;
 int sum_grammis=0;
 int Table[SIZE][SIZE],Tablenew[SIZE][SIZE],displs[SIZE],sendcounts[SIZE],Table2[SIZE][SIZE];
 
 
 MPI_Init(&argc,&argv);
 MPI_Comm_size(MPI_COMM_WORLD,&p);
 MPI_Comm_rank(MPI_COMM_WORLD,&rank);


if(rank == 0){

//δίνει ο χρήστης το 'N'
 printf("Poio thes na einai to N?\n");
 scanf("%d",&N);
 k=N*N;
	
//δημιουργία του πίνακα
 printf("\nDwse %d arithmous gia na siblirwthei o pinakas\n", k);
  for(i=0;i<N;i++){
   for(j=0;j<N;j++){
    scanf("%d", &Table[i][j]);
  }
 }

//εμφάνιση του πίνακα
 printf("\no pinakas einai:\n");	
  for(i=0;i<N;i++){
   for(j=0;j<N;j++){
    printf(" %d", Table[i][j]);
   }
   printf("\n");
 }

 }


//στέλνουμε το Ν σε όλες τις διεργασίες
MPI_Bcast(&N,1,MPI_INT,0,MPI_COMM_WORLD);

num=N/p;
numm=N%p;


//υπολογισμός των sendcounts και displs για την scatterv
 for(i=0;i<p;i++){
  sendcounts[i]=num;
   if( numm > 0){ 
    sendcounts[i]++;
     numm--;
    }
   sendcounts[i] *=SIZE;
   displs[i]=offset;
   offset += sendcounts[i];
  }

//μοιράζουμε τον δισδιάστατο πίνακα στις διεργασίες	
MPI_Scatterv(Table,sendcounts,displs,MPI_INT,Tablenew,sendcounts[rank],MPI_INT,0,MPI_COMM_WORLD);

//ελέγχουμε αν είναι αυστηρά διαγώνια δεσπόζων
for(i=0;i<sendcounts[rank]/SIZE;i++){
 diagj = i + (displs[rank]/SIZE);
  for(j=0;j<N;j++){
   if( j != diagj  ){
	sum_grammis += abs(Tablenew[i][j]);
   }
  }
   if( sum_grammis >= abs(Tablenew[i][diagj]) ) {
	token = 0;
   } else { 
   token = 1; 
   }
   sum_grammis = 0;
}


MPI_Reduce(&token,&result,1,MPI_INT,MPI_SUM,0,MPI_COMM_WORLD);
//αν το token->result είναι ίσο με τον αριθμό των διεργασιών τότε ο πίνακας πληρεί τις προϋποθέσεις για διαγώνια δεσπόζων


if(rank==0){

//εμφανίζουμε στον χρήστη αν είναι αυστηρά διαγώνια δεσπόζων ή όχι ο πίνακας
 if(result == p){
  printf("\nExoume austira diagwnia despozwn pinaka - YES \n");
 } else {
  printf("\nDen exoume austira diagwnia despozwn pinaka - NO \n");
 }

}


MPI_Bcast(&result,1,MPI_INT,0,MPI_COMM_WORLD);

//υπολογίζουμε το max διαγωνίου
if(result == p){
 max_diag = Tablenew[0][0];
 for(i=0;i<sendcounts[rank]/SIZE;i++){
  diagj = i + (displs[rank]/SIZE);
   if(max_diag < Tablenew[i][diagj]){
    max_diag = Tablenew[i][diagj];
   }
 }
}

MPI_Reduce(&max_diag,&max,1,MPI_INT,MPI_MAX,0,MPI_COMM_WORLD);

//εμφανίζουμε το μέγιστο στοιχείο της διαγωνίου
if(rank==0){
 if(result == p){
 printf("\nTo megisto stoixeio tis diagwnioy toy pinaka einai to: %d\n",max);
 }
}


MPI_Bcast(&max,1,MPI_INT,0,MPI_COMM_WORLD);

//δημιουργία του πίνακα Β
min_local = max;
for(i=0;i<sendcounts[rank]/SIZE;i++){
 diagj= i + (displs[rank]/SIZE);
 for(j=0;j<N;j++){
  if(j!=diagj){
   Tablenew[i][j] = max-abs(Tablenew[i][j]);
  }
  if(min_local > Tablenew[i][j]){
    min_local = Tablenew[i][j];
  } 
 } 
}


MPI_Reduce(&min_local,&min,1,MPI_INT,MPI_MIN,0,MPI_COMM_WORLD);

MPI_Gatherv(Tablenew,sendcounts[rank],MPI_INT,Table2,sendcounts,displs,MPI_INT,0,MPI_COMM_WORLD);


if(rank==0){

//εμφανίζουμε τον πίνακα Β
if(result==p){
printf("\no pinakas B einai o exhs: \n");
for(i=0;i<N;i++){
 for(j=0;j<N;j++){
  printf("%d ", Table2[i][j]);
   if(Table2[i][j] == min){
    mini=i;
    minj=j;
   }
 }
 printf("\n");
}

//εμφανίζουμε το ελάχιστο σε τιμή στοιχείο και τη θέση του στον πίνακα Β
printf("\nto elaxisto stoixeio tou pinaka B einai to: %d \n",min);
printf("\nkai vriskete stin thesi: %d,%d \n",mini,minj);

}
}
    MPI_Finalize();
}
