#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(int argc, char **argv){
 int pid1,pid2,pid3,pid4,pid5,status;
 printf("P0 here parent of all with PID=%d and PPID=%d\n",getpid(),getppid() );
 waitpid(pid2,&status,WUNTRACED);
 pid1=fork();
   if(pid1>0){
    pid2=fork();
    wait(&status);
     if(pid2==0){
      printf("P2 here with PID=%d and PPID=%d\n",getpid(),getppid() );
      pid3=fork();
      wait(&status);
       if(pid3==0){
        printf("P3 here with PID=%d and PPID=%d\n",getpid(),getppid() );
       } else{
          pid4=fork();
           if(pid4==0){
            printf("P4 here with PID=%d and PPID=%d\n",getpid(),getppid() );
           } else{
             pid5=fork();
             if(pid5==0){
              printf("P5 here with PID=%d and PPID=%d\n",getpid(),getppid() );
             }
           }
       }
      }
   }
   wait(&status);
   if(pid1==0){
    printf("P1 here with PID=%d and PPID=%d\n",getpid(),getppid() );
   }
   //execl("/bin/ps","ps","-f",NULL);
return 0;
}
