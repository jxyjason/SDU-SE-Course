
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include "copyright.h"
#include "system.h"
#include "time.h"
#include "synch.h"


#define N_THREADS  10    // the number of threads
#define N_TICKS    1000  // the number of ticks to advance simulated time
#define MAXLEN     48



int count = 0;

Semaphore *mutex, *barrier,*test;

Thread *threads[N_THREADS];
char thread_names[N_THREADS][MAXLEN];

void MakeTicks(int n)  // advance n ticks of simulated time
{
   time_t start = time(NULL);
   while ((time(NULL)-start)<n/1000);
}


void BarThread(_int which)
{
    test->P();
    test->V();
    // MakeTicks(N_TICKS);
    // sleep(N_TICKS/1000);
    printf("Thread %d rendezvous\n", which);
    mutex->P();
    count += 1;
    if (count==N_THREADS)
    {
        printf("Thread %d is the last\n", which);
        barrier->V();
    }
    mutex->V();

    // if (count==N_THREADS)
    // {
    //     printf("Thread %d is the last\n", which);
    //     barrier->V();
    // }

    barrier->P();
    barrier->V();

    printf("Thread %d critical point\n", which);

}


void ThreadsBarrier()
{
    mutex = new Semaphore("mutex",1);
    barrier = new Semaphore("barrier",0);
    test = new Semaphore("test",1);
    test->P();
    // Create and fork N_THREADS threads 
    for(int i = 0; i < N_THREADS; i++) {
        sprintf(thread_names[i],"thread_%d",i);
        threads[i] = new Thread(thread_names[i]);
        threads[i]->Fork(BarThread, i);
    };
    test->V();

}