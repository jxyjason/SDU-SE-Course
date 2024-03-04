// addrspace.cc
//	Routines to manage address spaces (executing user programs).
//
//	In order to run a user program, you must:
//
//	1. link with the -N -T 0 option
//	2. run coff2noff to convert the object file to Nachos format
//		(Nachos object code format is essentially just a simpler
//		version of the UNIX executable object code format)
//	3. load the NOFF file into the Nachos file system
//		(if you haven't implemented the file system yet, you
//		don't need to do this last step)
//
// Copyright (c) 1992-1993 The Regents of the University of California.
// All rights reserved.  See copyright.h for copyright notice and limitation
// of liability and disclaimer of warranty provisions.

#include "copyright.h"
#include "system.h"
#include "addrspace.h"
#include "noff.h"
#include <ctime>
#include <sys/timeb.h>

//----------------------------------------------------------------------
// SwapHeader
// 	Do little endian to big endian conversion on the bytes in the
//	object file header, in case the file was generated on a little
//	endian machine, and we're now running on a big endian machine.
//----------------------------------------------------------------------

static void
SwapHeader(NoffHeader *noffH)
{
    noffH->noffMagic = WordToHost(noffH->noffMagic);
    noffH->code.size = WordToHost(noffH->code.size);
    noffH->code.virtualAddr = WordToHost(noffH->code.virtualAddr);
    noffH->code.inFileAddr = WordToHost(noffH->code.inFileAddr);
    noffH->initData.size = WordToHost(noffH->initData.size);
    noffH->initData.virtualAddr = WordToHost(noffH->initData.virtualAddr);
    noffH->initData.inFileAddr = WordToHost(noffH->initData.inFileAddr);
    noffH->uninitData.size = WordToHost(noffH->uninitData.size);
    noffH->uninitData.virtualAddr = WordToHost(noffH->uninitData.virtualAddr);
    noffH->uninitData.inFileAddr = WordToHost(noffH->uninitData.inFileAddr);
}

//----------------------------------------------------------------------
// AddrSpace::AddrSpace
// 	Create an address space to run a user program.
//	Load the program from a file "executable", and set everything
//	up so that we can start executing user instructions.
//
//	Assumes that the object code file is in NOFF format.
//
//	First, set up the translation from program memory to physical
//	memory.  For now, this is really simple (1:1), since we are
//	only uniprogramming, and we have a single unsegmented page table
//
//	"executable" is the file containing the object code to load into memory
//----------------------------------------------------------------------
BitMap *
    AddrSpace::freeMap = new BitMap(NumPhysPages);
BitMap *
    AddrSpace::swapMap = new BitMap(NumPhysPages);

OpenFile *AddrSpace::swapFile = fileSystem->Open("SWAP0");

AddrSpace::AddrSpace(OpenFile *executable)
{
    pagenum=0;
    for (int i = 0; i < 128; i++)
    {
        if (spaceIDs[i] == 0)
        {
            spaceID = i;
            spaceIDs[i] = 1;
            break;
        }
    }

    NoffHeader noffH;
    unsigned int i, size;
    this->executable = executable;
    executable->ReadAt((char *)&noffH, sizeof(noffH), 0);
    if ((noffH.noffMagic != NOFFMAGIC) &&
        (WordToHost(noffH.noffMagic) == NOFFMAGIC))
        SwapHeader(&noffH);
    ASSERT(noffH.noffMagic == NOFFMAGIC);
    // how big is address space?
    size = noffH.code.size + noffH.initData.size + noffH.uninitData.size + UserStackSize; // we need to increase the size
                                                                                          // to leave room for the stack
    printf(", SpaceId: %d, Memory size: %d\n", spaceID, size);
    printf("Max frames per user process: %d, Swap file: SWAP0, Page replacement algorithm: FIFO\n", MemPages);

    numPages = divRoundUp(size, PageSize);
    size = numPages * PageSize;
    ASSERT(numPages <= NumPhysPages); 

    DEBUG('a', "Initializing address space, num pages %d, size %d\n",
          numPages, size);

    InitPageTable();
    InitInFileAddr();
    Print();

}

//----------------------------------------------------------------------
// AddrSpace::~AddrSpace
// 	Dealloate an address space.  Nothing for now!
//----------------------------------------------------------------------

AddrSpace::~AddrSpace()
{
    delete[] pageTable;
}

//----------------------------------------------------------------------
// AddrSpace::InitRegisters
// 	Set the initial values for the user-level register set.
//
// 	We write these directly into the "machine" registers, so
//	that we can immediately jump to user code.  Note that these
//	will be saved/restored into the currentThread->userRegisters
//	when this thread is context switched out.
//----------------------------------------------------------------------

void AddrSpace::InitRegisters()
{
    int i;

    for (i = 0; i < NumTotalRegs; i++)
        machine->WriteRegister(i, 0);

    // Initial program counter -- must be location of "Start"
    machine->WriteRegister(PCReg, 0);

    // Need to also tell MIPS where next instruction is, because
    // of branch delay possibility
    machine->WriteRegister(NextPCReg, 4);

    // Set the stack register to the end of the address space, where we
    // allocated the stack; but subtract off a bit, to make sure we don't
    // accidentally reference off the end!
    machine->WriteRegister(StackReg, numPages * PageSize - 16);
    DEBUG('a', "Initializing stack register to %d\n", numPages * PageSize - 16);
}

//----------------------------------------------------------------------
// AddrSpace::SaveState
// 	On a context switch, save any machine state, specific
//	to this address space, that needs saving.
//
//	For now, nothing!
//----------------------------------------------------------------------

void AddrSpace::SaveState()
{
}

//----------------------------------------------------------------------
// AddrSpace::RestoreState
// 	On a context switch, restore the machine state so that
//	this address space can run.
//
//      For now, tell the machine where to find the page table.
//----------------------------------------------------------------------

void AddrSpace::RestoreState()
{
    machine->pageTable = pageTable;
    machine->pageTableSize = numPages;
}
void AddrSpace::Print()
{
    printf("SpaceId: %d, page table dump: %d pages in total\n", spaceID, numPages);
    printf("============================================\n");
    printf("\tPage, \tFrame,\tValid,\tUse,\tDirty\n");
    for (int i = 0; i < numPages; i++)
    {
        printf("\t%d,\t%d,\t%d,\t%d,\t%d\n", pageTable[i].virtualPage, pageTable[i].physicalPage, pageTable[i].valid,
               pageTable[i].use, pageTable[i].dirty);
    }
    printf("============================================\n\n");
}

void AddrSpace::InitPageTable()
{
    point_vm = 0;
    pageTable = new TranslationEntry[numPages];

    for (int i = 0; i < numPages; i++)
    {
        pageTable[i].virtualPage = i;
        pageTable[i].use = false;
        pageTable[i].dirty = false;
        pageTable[i].readOnly = false;
        pageTable[i].inFileAddr = -1;

        if (i >= numPages - StackPages)
            pageTable[i].type = vmuserStack;
            pageTable[i].physicalPage = -1;
            pageTable[i].valid = false;
    }
}

void AddrSpace::InitInFileAddr()
{
    NoffHeader noffH;
    executable->ReadAt((char *)&noffH, sizeof(noffH), 0);
    if ((noffH.noffMagic != NOFFMAGIC) &&
        (WordToHost(noffH.noffMagic) == NOFFMAGIC))
        SwapHeader(&noffH);
    ASSERT(noffH.noffMagic == NOFFMAGIC);
    if (noffH.code.size > 0)
    {
        unsigned int numP = divRoundUp(noffH.code.size, PageSize);
        for (int i = 0; i < numP; i++)
        {
            pageTable[i].inFileAddr = noffH.code.inFileAddr + i * PageSize;
            pageTable[i].type = vmcode;
            if (pageTable[i].valid)
            {
                executable->ReadAt(&(machine->mainMemory[pageTable[i].physicalPage * PageSize]), PageSize,
                                   pageTable[i].inFileAddr);
            }
        }
    }
    if (noffH.initData.size > 0)
    {
        unsigned int numP, firstP;
        numP = divRoundUp(noffH.initData.size, PageSize);
        firstP = divRoundUp(noffH.initData.virtualAddr, PageSize);
        for (int i = firstP; i < numP + firstP; i++)
        {
            pageTable[i].inFileAddr = noffH.initData.inFileAddr + (i - firstP) * PageSize;
            pageTable[i].type = vminitData;
            if (pageTable[i].valid)
            {
                executable->ReadAt(&(machine->mainMemory[pageTable[i].physicalPage * PageSize]), PageSize,
                                   pageTable[i].inFileAddr);
            }
        }
    }
    if (noffH.uninitData.size > 0)
    {
        unsigned int numP, firstP;
        numP = divRoundUp(noffH.uninitData.size, PageSize);
        firstP = divRoundUp(noffH.uninitData.virtualAddr, PageSize);
        for (int i = firstP; i < numP + firstP; i++)
        {
            pageTable[i].type = vmuninitData;
        }
    }
}

void AddrSpace::Translate(int addr, unsigned int *vpn, unsigned int *offset)
{

    int page = -1;
    int off = 0;
    page = addr / PageSize;
    off = addr % PageSize;

    *vpn = page;
    *offset = off;
}

int AddrSpace::FIFO(int badVAddr)
{
    //use one num to show the point
    unsigned int oldPage;
    unsigned int newPage;
    unsigned int tmp;
    Translate(badVAddr, &newPage, &tmp);
    pagenum+=1;
    if (pagenum<=5)
    {
        pageTable[newPage].virtualPage = newPage;
        pageTable[newPage].use = true;
        pageTable[newPage].dirty = false;
        pageTable[newPage].readOnly = false;
        pageTable[newPage].physicalPage = pagenum-1;
        pageTable[newPage].valid = true;
        printf(" in(frame %d)\n", pagenum-1);
        virtualMem[pagenum-1] = newPage;
        ReadIn(newPage);
        Print();
        return 0;
    }
    else
    {
        pagenum=6;
        oldPage = virtualMem[point_vm];
        printf(" in(frame %d)\n", point_vm);
        ASSERT(newPage < numPages);
        virtualMem[point_vm] = newPage;
        point_vm = (point_vm + 1) % MemPages;
        return Swap(oldPage, newPage);
    }   
}

int AddrSpace::Swap(int oldPage, int newPage)
{
    int error = WriteBack(oldPage);

    if (oldPage == newPage)
    {
        pageTable[newPage].physicalPage = pageTable[oldPage].physicalPage;
    }
    else
    {
        pageTable[newPage].physicalPage = pageTable[oldPage].physicalPage;
        pageTable[oldPage].physicalPage = -1;
        pageTable[oldPage].valid = FALSE;
    }

    pageTable[newPage].valid = true;
    pageTable[newPage].use = true;
    pageTable[newPage].dirty = false;

    ReadIn(newPage);
    Print();
    return error;
}

int AddrSpace::WriteBack(int oldPage)
{

    if (pageTable[oldPage].dirty)
    {
        switch (pageTable[oldPage].type)
        {
        case vmcode:
            executable->WriteAt(&(machine->mainMemory[pageTable[oldPage].physicalPage * PageSize]), PageSize,
                                pageTable[oldPage].inFileAddr);
            break;
        case vminitData:
            executable->WriteAt(&(machine->mainMemory[pageTable[oldPage].physicalPage * PageSize]), PageSize,
                                pageTable[oldPage].inFileAddr);
            break;
        case vmuninitData:
        case vmuserStack:
            pageTable[oldPage].inFileAddr =
                (swapMap->FindIn(spaceID * NumPhysPages, (spaceID + 1) * NumPhysPages)) * PageSize;
            swapFile->WriteAt(&(machine->mainMemory[pageTable[oldPage].physicalPage * PageSize]), PageSize,
                              pageTable[oldPage].inFileAddr);
            break;
        }
        pageTable[oldPage].dirty = false;

        return 1;
    }
    return 0;
}

void AddrSpace::ReadIn(int newPage)
{
    switch (pageTable[newPage].type)
    {
    case vmcode:
    case vminitData:
        executable->ReadAt(&(machine->mainMemory[pageTable[newPage].physicalPage * PageSize]), PageSize,
                           pageTable[newPage].inFileAddr);
        break;
    case vmuninitData:
    case vmuserStack:

        if (pageTable[newPage].inFileAddr >= 0)
        {
            swapFile->ReadAt(&(machine->mainMemory[pageTable[newPage].physicalPage * PageSize]), PageSize,
                             pageTable[newPage].inFileAddr);
            swapMap->Clear(pageTable[newPage].inFileAddr / PageSize);
            pageTable[newPage].inFileAddr = -1;
        }
        break;
    }
}
