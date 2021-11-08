//cse 46373 Prasidis Ioannis
//to programma douleuei gia thread pollaplasia twn N
//orismenes fores den vriskei to swsto max??
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#define SIZE 100

int N,p;
int A[SIZE][SIZE];
int temp[SIZE];
int part=0;
int glomax;
int D[SIZE][SIZE];

void *friend(void *k){
  int maximum=A[0][0];
  int a = k;
  int thread_part = part++;
  int i,j;
//kathe thread vriskei to max twn grammwn pou exei parei
  for (i = thread_part*a; i < thread_part*a + a; i++) {
    for(j=0; j < N; j++){
      if(A[i][j] > maximum){
        maximum = A[i][j];
      }
    }
  }
//ta toopothetoume se enan pinaka
  for(i=0;i<p;i++){
    temp[i]=maximum;
  }
//kai vriskoume to megalutero apo ola auta. diladi to max mas
  glomax = temp[0];
  for(i=0;i<p;i++){
    if(temp[i] > glomax){
      glomax = temp[i];
  }
  }
//dimiourgoume ton pinaka D pou deixnei tis apostaseis twn stoixeiwn tou pinaka A apo to max
for(i=0;i<N;i++){
  for(j=0;j<N;j++){
    D[i][j] = abs(glomax-A[i][j]);
  }
}

  pthread_exit(NULL);
}


int main() {

  int i,j,k;

  printf("Poio thes na einai to N?\n");
  scanf("%d",&N);

 printf("\nDwse %d arithmous gia na siblirwthei o pinakas\n",N*N);
 for(i=0;i<N;i++){
  for(j=0;j<N;j++){
   scanf("%d", &A[i][j]);
 }
}

  printf("\no pinakas A einai:\n");
  for (i = 0; i < N; i++) {
    for(j=0; j< N; j++){
      printf("%d ",A[i][j]);
    }
    printf("\n");
  }

  printf("Posa thread thes\n");
  scanf("%d",&p);

k=N/p; //poses grammes tha pairnei kathe thread

pthread_t threads[p];
  for(i=0;i<p;i++){
    pthread_create(&threads[i],NULL,friend,(void *) k);
  }
  for(i=0;i<p;i++){
    pthread_join(threads[i],NULL);
  }

printf("To max einai %d\n",glomax );

printf("O pinakas D einai\n");
for(i=0;i<N;i++){
  for(j=0;j<N;j++){
    printf("%d ",D[i][j]);
  }
  printf("\n");
}

  pthread_exit(NULL);
  return 0;
}
