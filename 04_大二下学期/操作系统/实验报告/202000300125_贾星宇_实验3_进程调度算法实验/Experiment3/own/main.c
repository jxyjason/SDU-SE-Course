#include <stdio.h>
#include <stdlib.h>
#include <sched.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <unistd.h>
#include <signal.h>

int father = 0;
int child = 0;

typedef void(*sighandler_t)(int);
void fathersigint() {
    ++father;
    setpriority(PRIO_PROCESS, getpid(), father);
}
typedef void(*sighandler_t)(int);
void childsigint() {
    ++child;
    setpriority(PRIO_PROCESS, getpid(), child);
}
typedef void(*sighandler_t)(int);
void fathersigstp(){
    --father;
    setpriority(PRIO_PROCESS, getpid(), father);
}
typedef void(*sighandler_t)(int);
void childsigstp(){
    --child;
    setpriority(PRIO_PROCESS, getpid(), child);
}


int main(int argc, char* argv[])
{
    int pid = fork();
    if (pid > 0) {
        signal(SIGINT, (sighandler_t)fathersigint);//ctrl-c ++
        signal(SIGTSTP, (sighandler_t)fathersigstp);//ctrl-z --
        for (int i = 0; i < 50; i++)
        {
            printf("I am parent process %d and my priority is %d and my policy is %d\n",
                getpid(), getpriority(PRIO_PROCESS, getpid()), sched_getscheduler(getpid()));
            sleep(1);
        }
    }
    if (pid == 0) {
        signal(SIGINT, (sighandler_t)childsigint);
        signal(SIGTSTP, (sighandler_t)childsigstp);
        for (int i = 0; i < 50; i++)
        {
            printf("I am childprocess %d and my priority is %d and my policy is %d\n",
                getpid(), getpriority(PRIO_PROCESS, getpid()), sched_getscheduler(getpid()));
            sleep(1);
        }
    }
    return EXIT_SUCCESS;
}

//sudo     tongshi?

