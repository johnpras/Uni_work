//cse 46373 Prasidis Ioannis
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>

sem_t sem1,sem2,sem3;

void *gg(void *i){

  int id = (int) i;

  while (1) {
    if(id==1){
      sem_wait(&sem3);
        printf("<ONE>");
        sem_post(&sem1);
    } else if(id==2){
      sem_wait(&sem1);
        printf("<TWO>");
        sem_post(&sem2);
    } else{
      sem_wait(&sem2);
        printf("<THREE>");
        sem_post(&sem3);
    }
  }

}

int main() {
  int i;
  pthread_t thread;
  sem_init(&sem1, 0 ,0);
  sem_init(&sem2, 0 ,0);
  sem_init(&sem3, 0 ,1);
  for (i = 1; i < 4; i++) {
    pthread_create(&thread,NULL, gg,(void *) i);
  }
  for (i = 1; i < 4; i++) {
    pthread_join(thread,NULL);
  }
  sem_destroy(&sem1);
  sem_destroy(&sem2);
  sem_destroy(&sem3);
  pthread_exit(NULL);
  return 0;
}
