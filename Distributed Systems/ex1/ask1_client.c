#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <netinet/in.h> 
#include "ask1.h"

int choice,a,n,k,l,m,o;
	

void error(char *msg)
{
    perror(msg);
    exit(1);
}

void ask1_prog_1(char *host,int argc, char *argv[])
{
	CLIENT *clnt;
	intminmax  *result_1;
	intpair  minmax_1_arg;
	intmul  *result_2;
	intpair  mul_1_arg;
	intavg  *result_3;
	intpair  avg_1_arg;

	bool_t flag=1;

#ifndef	DEBUG
	clnt = clnt_create (host, ASK1_PROG, ASK1_VERS, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	///DEBUG 




int sockfd, newsockfd, portno, clilen, done,n0,n1,n2,n3,array[n];
     char str[100];

     struct sockaddr_in serv_addr, cli_addr;
//κλασικός κώδικας sockets
     if (argc < 2) {
         fprintf(stderr,"ERROR, no port provided\n");
         exit(1);
     }

     sockfd = socket(AF_INET, SOCK_STREAM, 0); 
     if (sockfd < 0) 
         error("ERROR opening socket");

     bzero((char *) &serv_addr, sizeof(serv_addr));

	portno = atoi(argv[2]);

     serv_addr.sin_family = AF_INET;

     serv_addr.sin_port = htons(portno);


     serv_addr.sin_addr.s_addr = INADDR_ANY;

     if (bind(sockfd, (struct sockaddr *) &serv_addr,
              sizeof(serv_addr)) < 0)
                  error("ERROR on binding");

     listen(sockfd,5);

   for (;;)  {
     printf("Waiting for a connection...\n");
     clilen = sizeof(cli_addr);
     newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);
     if (newsockfd < 0) 
          error("ERROR on accept");

            if (fork() == 0)  { 
                close (sockfd); 
                printf("Connected.\n");
do{


//αν συνδεθούμε στο socket τότε παίρνουμε τις μεταβλητές που μας έχει στείλει ο client 
				  n1 = recv(newsockfd,&n,sizeof(n),0);
				  send(newsockfd,&n,n1,0);

				  n3 = recv(newsockfd,&array,sizeof(int)*n,0);
				  send(newsockfd,&array,n3,0);

				  n2 = recv(newsockfd,&a,sizeof(n),0);
				  send(newsockfd,&a,n2,0);

				  n0 = recv(newsockfd,&choice,sizeof(choice),0);
				  printf("CHOICE=%d\n",choice);
				  send(newsockfd,&choice,n0,0);

if(choice==1){

//δήλωση μεταβλητών, γέμισμα του πίνακα με τις τιμές που έχει δώσει ο χρήστης και έχουν τοποθετηθεί στον πίνακα 'array'
minmax_1_arg.a=a;
minmax_1_arg.n=n;
minmax_1_arg.Y.Y_len = minmax_1_arg.n;
minmax_1_arg.Y.Y_val= (int *) malloc(minmax_1_arg.n * sizeof(int)); 
printf("===========================================\n");
printf("minmax function \n");
for(int i=0;i<n;i++){
	minmax_1_arg.Y.Y_val[i]=array[i];
}


	result_1 = minmax_1(&minmax_1_arg, clnt);
	if (result_1 == (intminmax *) NULL) {
		clnt_perror (clnt, "call failed");
	}else {
		printf("array has: ");
		for(int i=0;i<n;i++){
			printf("%d ",minmax_1_arg.Y.Y_val[i]);
		}
		printf("\n");
//βλέπουμε τις μεταβλητες min,max (τον πίνακα 2 θέσεων)	//προεραιτικό απλά για να βλέπω ότι επιστέφει ότι πρέπει o server 	
		printf("minimun = %d maximum = %d\n",result_1->MM.MM_val[0],result_1->MM.MM_val[1]);
		printf("===========================================\n");
		printf("\n");
//αρχικοποίηση πίνακα 2 θέσεων για να στείλουμε στον client τα min,max ,αν ως επιλογή διαλέξει τη συνάρτηση min-max		
		int returnarray1[2];
		for(int i=0;i<2;i++){
			returnarray1[i] = result_1->MM.MM_val[i];
		}
		send(newsockfd,&returnarray1,sizeof(int)*2,0);

	}

}else if(choice==2){

//δήλωση μεταβλητών, γέμισμα του πίνακα με τις τιμές που έχει δώσει ο χρήστης και έχουν τοποθετηθεί στον πίνακα 'array'
mul_1_arg.a=a;
mul_1_arg.n=n;
mul_1_arg.Y.Y_len = mul_1_arg.n;
mul_1_arg.Y.Y_val= (int *) malloc(mul_1_arg.n * sizeof(int)); 
printf("===========================================\n");
printf("mul function \n");
for(int i=0;i<n;i++){
	mul_1_arg.Y.Y_val[i]=array[i];
}



	result_2 = mul_1(&mul_1_arg, clnt);
	if (result_2 == (intmul *) NULL) {
		clnt_perror (clnt, "call failed");
	}else{
//εμφάνιση του νέου πίνακα		
		printf("array a*Y has: ");
		for(int i=0;i<n;i++){
			printf("%d ",result_2->aY.aY_val[i]);
		}
		printf("\n");
		printf("===========================================\n");
		printf("\n");
//αρχικοποίηση πίνακα και γέμισμα για να στείλουμε στον client τον νέο πίνακα a*Y	
		int returnarray2[n];
		for(int i=0;i<n;i++){
			returnarray2[i] = result_2->aY.aY_val[i];
		}
		send(newsockfd,&returnarray2,sizeof(int)*n,0);
	}

}else if(choice==3){

//δήλωση μεταβλητών, γέμισμα του πίνακα με τις τιμές που έχει δώσει ο χρήστης και έχουν τοποθετηθεί στον πίνακα 'array'
avg_1_arg.a=a;
avg_1_arg.n=n;
avg_1_arg.Y.Y_len = avg_1_arg.n;
avg_1_arg.Y.Y_val= (int *) malloc(avg_1_arg.n * sizeof(int)); 
printf("===========================================\n");
printf("avg function \n");
for(int i=0;i<n;i++){
	avg_1_arg.Y.Y_val[i]=array[i];
}


	result_3 = avg_1(&avg_1_arg, clnt);
	if (result_3 == (intavg *) NULL) {
		clnt_perror (clnt, "call failed");
	}else{
		printf("array has: ");
		for(int i=0;i<n;i++){
			printf("%d ",minmax_1_arg.Y.Y_val[i]);
		}
		printf("\n");
		printf("avg = %d\n",result_3->avg);
		printf("n = %d\n",avg_1_arg.n);
		printf("===========================================\n");
		printf("\n");
//για να στείλουμε στον client τη μεταβλητή avg		
		int returnavg=0;
		returnavg=result_3->avg;
		send(newsockfd,&returnavg,sizeof(int),0);
	}


}
			}while(choice!=4);
                exit (0) ; 
             }

     close(newsockfd);
   }





#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 // DEBUG 
}



int main (int argc, char *argv[])
{
	char *host;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	ask1_prog_1(host,argc,argv);

exit (0);
}
