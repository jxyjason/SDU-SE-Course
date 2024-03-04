// threadtest.cc 
//	Simple test case for the threads assignment.
//
//	Create two threads, and have them context switch
//	back and forth between themselves by calling Thread::Yield, 
//	to illustratethe inner workings of the thread system.
//
// Copyright (c) 1992-1993 The Regents of the University of California.
// All rights reserved.  See copyright.h for copyright notice and limitation 
// of liability and disclaimer of warranty provisions.

#include "copyright.h"
#include "system.h"

//----------------------------------------------------------------------
// SimpleThread
// 	Loop 5 times, yielding the CPU to another ready thread 
//	each iteration.
//
//	"which" is simply a number identifying the thread, for debugging
//	purposes.
//----------------------------------------------------------------------

void
SimpleThread(_int which)
{
    int num;
    
    for (num = 0; num < 5; num++) {
	printf("*** thread %d looped %d times\n", (int) which, num);
        currentThread->Yield();
    }
}

//----------------------------------------------------------------------
// ThreadTest
// 	Set up a ping-pong between two threads, by forking a thread 
//	to call SimpleThread, and then calling SimpleThread ourselves.
//----------------------------------------------------------------------

void
ThreadTest()
{
    DEBUG('t', "Entering SimpleTest");

    Thread *ta = new Thread("forked thread a");
    Thread *tb = new Thread("forked thread b");
    ta->Println();
    tb->Println();

    // 3 threads, running sequence: 0, 1, 2, 0, 1, 2...
    ta->Fork(SimpleThread, 1);
    tb->Fork(SimpleThread, 2);
    SimpleThread(0);
/*
    // 3 threads, running sequence: 2, 1, 0, 2, 1, 0...
    ta->Fork(SimpleThread, 1);
    tb->Fork(SimpleThread, 0);
    SimpleThread(2);
*/
}

