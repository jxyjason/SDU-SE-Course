// exception.cc 
//	Entry point into the Nachos kernel from user programs.
//	There are two kinds of things that can cause control to
//	transfer back to here from user code:
//
//	syscall -- The user code explicitly requests to call a procedure
//	in the Nachos kernel.  Right now, the only function we support is
//	"Halt".
//
//	exceptions -- The user code does something that the CPU can't handle.
//	For instance, accessing memory that doesn't exist, arithmetic errors,
//	etc.  
//
//	Interrupts (which can also cause control to transfer from user
//	code into the Nachos kernel) are handled elsewhere.
//
// For now, this only handles the Halt() system call.
// Everything else core dumps.
//
// Copyright (c) 1992-1993 The Regents of the University of California.
// All rights reserved.  See copyright.h for copyright notice and limitation 
// of liability and disclaimer of warranty provisions.

#include "copyright.h"
#include "system.h"
#include "syscall.h"

//----------------------------------------------------------------------
// ExceptionHandler
// 	Entry point into the Nachos kernel.  Called when a user program
//	is executing, and either does a syscall, or generates an addressing
//	or arithmetic exception.
//
// 	For system calls, the following is the calling convention:
//
// 	system call code -- r2
//		arg1 -- r4
//		arg2 -- r5
//		arg3 -- r6
//		arg4 -- r7
//
//	The result of the system call, if any, must be put back into r2. 
//
// And don't forget to increment the pc before returning. (Or else you'll
// loop making the same system call forever!
//
//	"which" is the kind of exception.  The list of possible exceptions 
//	are in machine.h.
//----------------------------------------------------------------------

void
ExceptionHandler(ExceptionType which)
{
    int type = machine->ReadRegister(2);

    if ((which == SyscallException) && (type == SC_Halt)) {
	DEBUG('a', "Shutdown, initiated by user program.\n");
   	interrupt->Halt();
    } 
       else if ((which == SyscallException) && (type == SC_Exec))
	{
		char FileN[50];
		int address = machine->ReadRegister(4);
		int i = 0;
		do
		{
			machine->ReadMem(address + i, 1, (int *)&FileN[i]);
		} while (FileN[i++] != '\0');
		printf("Exec(%s):\n", FileN);

		Thread *thread = new Thread("fork thread");
		// 开始新的进程
		thread->Fork(StartProcess, (int)(&FileN));
		currentThread->Yield();

		machine->WriteRegister(PrevPCReg, machine->ReadRegister(PCReg)); //将当前PC放在prev
		machine->WriteRegister(PCReg, machine->ReadRegister(NextPCReg)); //将下一个要执行的PC放到下一个指针 
		machine->WriteRegister(NextPCReg, machine->ReadRegister(NextPCReg) + 4); //将指针向下移动四个byte
	}
	else if ((which == SyscallException) && (type == SC_PrintInt))
	{
		int address = machine->ReadRegister(1);
		printf("%d\n",address);
		machine->WriteRegister(PrevPCReg, machine->ReadRegister(PCReg)); //将当前PC放在prev
		machine->WriteRegister(PCReg, machine->ReadRegister(NextPCReg)); //将下一个要执行的PC放到下一个指针 
		machine->WriteRegister(NextPCReg, machine->ReadRegister(NextPCReg) + 4); //将指针向下移动四个byte
	}
    else {
	printf("Unexpected user mode exception %d %d\n", which, type);
	ASSERT(FALSE);
    }
}
