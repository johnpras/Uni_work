#include <stdio.h>
#include <mpi.h>
#define SIZE 1000


int main(int argc, char *argv[]){
int N, A[SIZE],rank, p, i,var,k,num,total_var,var2,position;
int menu_item,zeroprocesslast;

//initialize MPI

 MPI_Status status;
 MPI_Init(&argc,&argv);
 MPI_Comm_size(MPI_COMM_WORLD,&p);
 MPI_Comm_rank(MPI_COMM_WORLD,&rank);


do{

//διαβάζει από τον χρήστη αριθμούς και τους αποθηκεύει σε έναν πίνακα
  if(rank == 0) {
   printf("Posoys arithmous thes?\n");
   scanf("%d", &N);
   printf("Dwse %d arithmous:\n", N);
    for(i=0;i<N;i++){
     scanf("%d", &A[i]);
    }



  num=N/p; //δηλώνω πόσους αριθμούς θα πάρει ο κάθε επεξεργαστής
  k = num;

	for( i=1; i<p; i++) {
	 MPI_Send(&num,1,MPI_INT,i,5,MPI_COMM_WORLD);//στέλνει το num και στους υπόλοιπους επεξεργαστές για να το γνωρίζουν
	 MPI_Send(&A[k],num,MPI_INT,i,10,MPI_COMM_WORLD);
	  k+=num; //για να προσπεράσει num στοιχεία από τον πίνακα και στη συνέχεια να πάει στον επόμενο επεξεργαστή
			
	}
			
  }else{

	MPI_Recv(&num,1,MPI_INT,0,5,MPI_COMM_WORLD,&status); //για να γνωρίζουν το num και οι άλλοι επεξεργαστές
	MPI_Recv(&A[0],num,MPI_INT,0,10,MPI_COMM_WORLD,&status);
			
 }
 

//βλέπω αν ο πίνακας είναι απλά ταξινομημένος κατά αύξουσα σειρά
 position=0;
	for (i=0;i<num-1;i++) {

		if( A[i] > A[i+1] ){
		 position=A[i];
		 break;
		}
	}
 
	if ( i == num-1) {
		//αν ειναι ταξινομημενος ο πινακας ενος επεξεργαστη τοτε var=1
		 var = 1;
	 } else {
		//αν δεν ειναι ταξινομημενος ο πινακας ενος επεξεργαστη τοτε var=0
		 var = 0; 
	}

	
	
if(rank!=0){
MPI_Send(&position,1,MPI_INT,rank-1,44,MPI_COMM_WORLD);
}
if(rank!=p-1){
MPI_Recv(&position,1,MPI_INT,rank+1,44,MPI_COMM_WORLD,&status);
}


//παίρνουμε το κάθε πρώτο στοιχείο των επεξεργαστών για να το συγκρίνουμε μετά με το τελευταίο του προηγούμενου επεξεργαστή

if(rank!=0){
		
		MPI_Send(&A[0],p,MPI_INT,0,15,MPI_COMM_WORLD); //παίρνω το πρώτο στοιχείο 
		MPI_Send(&A[num-1],p,MPI_INT,0,30,MPI_COMM_WORLD); //παίρνω το τελευταίο στοιχείο
		
		
}else{
		
		zeroprocesslast=A[num-1];  //δηλώνω το τελευταίο στοιχείο του επεξεργαστή 0
		var2=1;
	
	for( i=1; i<p; i++){
		
		MPI_Recv(&A[0],p,MPI_INT,i,15,MPI_COMM_WORLD,&status);
		MPI_Recv(&A[num-1],p,MPI_INT,i,30,MPI_COMM_WORLD,&status);
		
		if ( A[0] > zeroprocesslast ) {
			zeroprocesslast = A[num-1];
		}else{
			var2=0;		//αν var2=0 τότε κάποιο στοιχείο ενός επεξεργαστή είναι μεγαλύτερο απο το πρώτο στοιχείο του επόμενου και άρα δεν είναι ταξινομημένα μεταξύ τους
			if(position==0){
			position = A[0];}
		}
	}


}


if (rank != 0) {
	MPI_Send(&var,1,MPI_INT,0,20,MPI_COMM_WORLD);	//στέλνω στον επεξεργαστή 0 τη μεταβλητή που βλέπω αν τα στοιχεία του επεξεργαστή είναι ταξινομημένα ( =1 είναι ταξινομημένο, =0 δεν είναι ταξινομημένο)
	MPI_Recv(&menu_item,1,MPI_INT,0,100,MPI_COMM_WORLD,&status);// παίρνω και στους άλλους επεξεργαστές την επιλογή του χρήστη όσον αφορά το μενου
	
}else{
			total_var=var; //αν το total_var είναι ίσο με p τότε τα στοιχεία κάθε επεξεργαστή είναι ταξινομημένα



			for( i=1;i<p;i++) {
				MPI_Recv(&var,1,MPI_INT,i,20,MPI_COMM_WORLD,&status);		
				total_var+=var;
   			  		  }


				if ( (total_var == p) && (var2 != 0) ){ //ελέγχω αν είναι ταξινομημένα τα στοιχεία κάθε επεξεργαστή καθώς και τα ακραία ενδιάμεσα στοιχεία (τελευταία στοιχείο ενος επεξεργαστή μαζί με το πρώτο στοιχείο του επόμενου)
						printf("YES-Its sorted!\n");
				}
					else{
					
					     printf("NO-Its not sorted!xalaei sto stoixeio %d\n",position); 
    				}
//Ξεκινάει το μενού με τις επιλογές που δίνει στο χρήστη.αν πατάει τον αριθμό '1' θα ξανατρέχει το πρόγραμμα.Στο τέλος του προγράμματος θα ξαναεμφανίζεται το menu.(emfanizete epanaliptika  mexri o xristis na patisei tin epilogi 'exit')
	printf("----------------------MENU-----------------------\n");
	printf("Press 1 to continue\n");
	printf("Press any key to exit\n");
	printf("-------------------------------------------------\n");
	scanf("%d", &menu_item);
	for(i=1;i<p;i++){
		MPI_Send(&menu_item,1,MPI_INT,i,100,MPI_COMM_WORLD);
	}
					
			}
	
}while(menu_item == 1);
  MPI_Finalize();
	
  }
