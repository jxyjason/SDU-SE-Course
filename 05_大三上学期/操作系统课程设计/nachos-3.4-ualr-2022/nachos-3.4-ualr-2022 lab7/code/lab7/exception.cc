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
#include "machine.h"

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
void StartProcess(int intfilename)
{

	char *filename = (char *)intfilename;
	OpenFile *executable = fileSystem->Open(filename);
	AddrSpace *space;
	printf("User program: %s", filename);
	if (executable == NULL)
	{
		printf("Unable to open file %s\n", filename);
		return;
	}
	space = new AddrSpace(executable);
	currentThread->space = space;

	delete executable; // close file

	space->InitRegisters(); // set the initial register values
	space->RestoreState();	// load page table register

	machine->Run(); // jump to the user progam
	ASSERT(FALSE);	// machine->Run never returns;
					// the address space exits
					// by doing the syscall "exit"
}

void ExceptionHandler(ExceptionType which)
{
	int type = machine->ReadRegister(2);

	if ((which == SyscallException) && (type == SC_Halt))
	{
		DEBUG('a', "Shutdown, initiated by user program.\n");
		interrupt->Halt();
	}
	else if ((which == SyscallException) && (type == SC_Exec))
	{
		interrupt->Exec();
		machine->WriteRegister(PrevPCReg, machine->ReadRegister(PCReg));		 //将当前PC放在prev
		machine->WriteRegister(PCReg, machine->ReadRegister(NextPCReg));		 //将下一个要执行的PC放到下一个指针
		machine->WriteRegister(NextPCReg, machine->ReadRegister(NextPCReg) + 4); //将指针向下移动四个byte
	}
	else if ((which == SyscallException) && (type == SC_PrintInt))
	{
		int address = machine->ReadRegister(4);
		printf("%d\n", address);
		machine->WriteRegister(PrevPCReg, machine->ReadRegister(PCReg));		 //将当前PC放在prev
		machine->WriteRegister(PCReg, machine->ReadRegister(NextPCReg));		 //将下一个要执行的PC放到下一个指针
		machine->WriteRegister(NextPCReg, machine->ReadRegister(NextPCReg) + 4); //将指针向下移动四个byte
	}
	else if ((which == SyscallException) && (type == SC_Exit))
	{
		int num = machine->ReadRegister(4);
		printf("exit!the A[0] is %d\n", num);
		machine->WriteRegister(PCReg, machine->ReadRegister(PCReg) + 4);
		machine->WriteRegister(NextPCReg, machine->ReadRegister(NextPCReg) + 4);
		currentThread->Finish();
	}
	else if (which == PageFaultException)
	{
		int badVAddr = (int)machine->ReadRegister(BadVAddrReg);
		int vpn = (unsigned)badVAddr / PageSize;
		printf("Demand page %d", vpn);
		int k = currentThread->space->FIFO(badVAddr);
		stats->numPageFaults++;
		stats->numWriteBack = stats->numWriteBack + k;
	}
	else
	{
		printf("Unexpected user mode exception %d %d\n", which, type);
		ASSERT(FALSE);
	}
}
