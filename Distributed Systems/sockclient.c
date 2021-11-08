#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

void error(char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
    int sockfd, portno, n, t,choice,a,t1,t2,t3,t4,t5,returnavg,t6;
    int returnarray1[2];
    int returnarray2[n];
    struct sockaddr_in serv_addr;
    struct hostent *server;
    char str[100];
    int array[n];

//κλασικός κώδικας socket
    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
    portno = atoi(argv[2]);
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) 
        error("ERROR opening socket");

    server = gethostbyname(argv[1]);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, 
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
    serv_addr.sin_port = htons(portno);

    printf("Trying to connect...\n");

    if (connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) 
        error("ERROR connecting");

        printf("Connected.\n");

//όσο δεν επιλέγει ο χρήστης την επιλογή 4-quit, στέλνονται τα δεδομένα που δίνει στον client
//και παίρνει τις ανάλογες απαντήσεις και εμφανίζονται στην οθόνη
        do {    

            printf("\nGive n(arrray length): ");     
            scanf("%d",&n); 
            send(sockfd, &n, sizeof(n),0);
            t1 = recv(sockfd,&n,sizeof(n),0);

            printf("\nfill the array: ");
            for(int i=0; i<n;i++){
                scanf("%d",&array[i]);
            }
            send(sockfd,&array,sizeof(int)*n,0);
            t6 = recv(sockfd,&array,sizeof(int)*n,0);


            printf("\nGive a: ");     
            scanf("%d",&a);
            send(sockfd, &a, sizeof(a),0);
            t2 = recv(sockfd,&a,sizeof(a),0);


            printf("\nMENU\n");    
            printf("1. min-max\n");
            printf("2. mult a*Y\n");
            printf("3. avg\n");
            printf("4. quit");
            printf("\n");    

            printf("Give your choice: ");
            scanf("%d",&choice);
            send(sockfd, &choice, sizeof(choice),0);
            t=recv(sockfd, &choice, sizeof(choice), 0);


if(choice==1){
    printf("you chose the min-max function\n");
    t3=recv(sockfd,&returnarray1,sizeof(int)*2,0);
    printf("minimun = %d maximum = %d\n",returnarray1[0],returnarray1[1]);

}else if(choice==2){
    printf("you chose the mul function\n");
    t4=recv(sockfd,&returnarray2,sizeof(int)*n,0);
    printf("array a*Y has:");
    for(int i=0;i<n;i++){
			printf("%d ",returnarray2[i]);
	}
	printf("\n");

}else if(choice==3){
        printf("you chose the avg function\n");
        t5=recv(sockfd,&returnavg,sizeof(int),0);
		printf("avg = %d\n",returnavg);

}
if(choice>=4){
    //return 0;
}

        } while (choice != 4);

        close(sockfd);

    return 0;
}





