//Prasidis Ioannis cse46373
//To programma douleuei gia pollaplasio arithmo stoixeiwn me threads
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#define SIZE 100000

pthread_mutex_t mut=PTHREAD_MUTEX_INITIALIZER;

int k;
int tsum =0;
int part=0;

void *friend(void *A){  // each thread calculates the sum
  int *a = (int *) A;
  int lsum=0;
  int i;
  int thread_part=part++;
  int start = thread_part*k;
  int end = start + k;
  for(i=start; i<end; i++){
      lsum += a[i]*a[i];
  }
  pthread_mutex_lock(&mut);
  tsum += lsum; //critical area
  pthread_mutex_unlock(&mut);
  pthread_exit(NULL);
}


int main(int argc, char const *argv[]) {
  int A[SIZE];
  int N,i,p;
srand(time(NULL));
//o xristis dinei to megethos tou dianismatos kai gemizei me tuxaious arithmous
printf("Posa stoixeia thes na exei to dianisma?\n");
scanf("%d",&N );
for(i=0;i<N;i++){
  A[i]=rand()&101;
}
//emfanisi pinaka
printf("O pinakas einai o exhs:\n");
for(i=0;i<N;i++){
  printf("%d ", A[i]);
}
printf("\n");
//o xristis dinei to plithos twn threads
printf("Posa threads theleis? \n");
scanf("%d",&p );
k = N/p; //posous arithmous tha parei kathe thread
  pthread_t threads[p];
  for(i=0;i<p;i++){
    pthread_create(&threads[i],NULL,friend,(void *) A);
  }
  for(i=0;i<p;i++){
    pthread_join(threads[i],NULL);
  }
  printf("Total sum of the array: %d\n",tsum ); //print the result
  pthread_exit(NULL);
  return 0;
}
