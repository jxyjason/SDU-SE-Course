// filehdr.cc
//	Routines for managing the disk file header (in UNIX, this
//	would be called the i-node).
//
//	The file header is used to locate where on disk the
//	file's data is stored.  We implement this as a fixed size
//	table of pointers -- each entry in the table points to the
//	disk sector containing that portion of the file data
//	(in other words, there are no indirect or doubly indirect
//	blocks). The table size is chosen so that the file header
//	will be just big enough to fit in one disk sector,
//
//      Unlike in a real system, we do not keep track of file permissions,
//	ownership, last modification date, etc., in the file header.
//
//	A file header can be initialized in two ways:
//	   for a new file, by modifying the in-memory data structure
//	     to point to the newly allocated data blocks
//	   for a file already on disk, by reading the file header from disk
//
// Copyright (c) 1992-1993 The Regents of the University of California.
// All rights reserved.  See copyright.h for copyright notice and limitation
// of liability and disclaimer of warranty provisions.

#include "copyright.h"

#include "system.h"
#include "filehdr.h"
#include "time.h"

//----------------------------------------------------------------------
// FileHeader::Allocate
// 	Initialize a fresh file header for a newly created file.
//	Allocate data blocks for the file out of the map of free disk blocks.
//	Return FALSE if there are not enough free blocks to accomodate
//	the new file.
//
//	"freeMap" is the bit map of free disk sectors
//	"fileSize" is the bit map of free disk sectors
//----------------------------------------------------------------------
FileHeader::FileHeader(int sector)
{
    memset(dataSectors, 0, sizeof(dataSectors));
    this->sector = sector;
}
FileHeader::FileHeader()
{
    this->sector = -1;
    memset(dataSectors, 0, sizeof(dataSectors));
}

bool FileHeader::Allocate(BitMap *freeMap, int fileSize)
{
    numBytes = fileSize;

    int tempNumSectors = divRoundUp(numBytes, SectorSize);
    numSectors = tempNumSectors;

    // numSectors  = divRoundUp(numBytes, SectorSize);
    if (freeMap->NumClear() < tempNumSectors)
        return FALSE; // not enough space

    if (tempNumSectors < NumDirect - 1)
    {

        for (int i = 0; i < divRoundUp(numBytes, SectorSize); i++)
            dataSectors[i] = freeMap->Find();
        dataSectors[NumDirect - 1] = -1;

        // printf("temp:%d,have:%d,now last:%d\n", tempNumSectors, NumDirect - 1, dataSectors[NumDirect - 1]);
    }
    else
    {

        for (int i = 0; i < NumDirect; i++)
        {
            dataSectors[i] = freeMap->Find();
        }
        int dataSector2[NumDirect2];
        for (int i = 0; i < tempNumSectors - (NumDirect - 1); i++)
        {
            dataSector2[i] = freeMap->Find();
        }
        synchDisk->WriteSector(dataSectors[NumDirect - 1], (char *)dataSector2);
    }

    // for (int i = 0; i < divRoundUp(numBytes, SectorSize); i++)
    //     dataSectors[i] = freeMap->Find();
    return TRUE;
}

//----------------------------------------------------------------------
// FileHeader::Deallocate
// 	De-allocate all the space allocated for data blocks for this file.
//
//	"freeMap" is the bit map of free disk sectors
//----------------------------------------------------------------------

void FileHeader::Deallocate(BitMap *freeMap)
{
    int temp = numSectors;
    numSectors = divRoundUp(numBytes, SectorSize);

    // for (int i = 0; i < numSectors; i++)
    // {
    //     ASSERT(freeMap->Test((int)dataSectors[i])); // ought to be marked!
    //     freeMap->Clear((int)dataSectors[i]);
    // }

    if (dataSectors[NumDirect - 1] == -1)
    {
        for (int i = 0; i < numSectors; i++)
        {
            ASSERT(freeMap->Test((int)dataSectors[i])); // ought to be marked!
            freeMap->Clear((int)dataSectors[i]);
        }
    }
    else
    {
        for (int i = 0; i < NumDirect - 1; i++)
        {
            ASSERT(freeMap->Test((int)dataSectors[i])); // ought to be marked!
            freeMap->Clear((int)dataSectors[i]);
        }

        int dataSectors2[NumDirect2];
        synchDisk->ReadSector(dataSectors[NumDirect - 1], (char *)dataSectors2);
        freeMap->Clear((int)dataSectors[NumDirect - 1]);
        for (int i = 0; i < numSectors - (NumDirect - 1); i++)
        {
            freeMap->Clear((int)dataSectors2[i]);
        }
    }

    numSectors = temp;
}

//----------------------------------------------------------------------
// FileHeader::FetchFrom
// 	Fetch contents of file header from disk.
//
//	"sector" is the disk sector containing the file header
//----------------------------------------------------------------------

void FileHeader::FetchFrom(int sector)
{
    synchDisk->ReadSector(sector, (char *)this);
}

//----------------------------------------------------------------------
// FileHeader::WriteBack
// 	Write the modified contents of the file header back to disk.
//
//	"sector" is the disk sector to contain the file header
//----------------------------------------------------------------------

void FileHeader::WriteBack(int sector)
{
    synchDisk->WriteSector(sector, (char *)this);
}

//----------------------------------------------------------------------
// FileHeader::ByteToSector
// 	Return which disk sector is storing a particular byte within the file.
//      This is essentially a translation from a virtual address (the
//	offset in the file) to a physical address (the sector where the
//	data at the offset is stored).
//
//	"offset" is the location within the file of the byte in question
//----------------------------------------------------------------------

int FileHeader::ByteToSector(int offset)
{
    if (offset / SectorSize < NumDirect - 1)
    {
        return (dataSectors[offset / SectorSize]);
    }
    else
    {
        int dataSectors2[NumDirect2];
        synchDisk->ReadSector(dataSectors[NumDirect - 1], (char *)dataSectors2);
        return (dataSectors2[offset / SectorSize - (NumDirect - 1)]);
    }

    // return (dataSectors[offset / SectorSize]);
}

//----------------------------------------------------------------------
// FileHeader::FileLength
// 	Return the number of bytes in the file.
//----------------------------------------------------------------------

int FileHeader::FileLength()
{
    return numBytes;
}

// void
// FileHeader::setNumBytes(int numBytes){
//     this->numBytes = numBytes;
// }

//----------------------------------------------------------------------
// FileHeader::Print
// 	Print the contents of the file header, and the contents of all
//	the data blocks pointed to by the file header.
//----------------------------------------------------------------------

void FileHeader::Print()
{

    printf("begin print content: last is :%d\n", dataSectors[NumDirect - 1]);
    int i, j, k, temp;
    char *data = new char[SectorSize];
    if (dataSectors[NumDirect - 1] == -1)
    {
        time_t thetime = numSectors + 16 * 3600;
        struct tm *ptr = localtime(&thetime);
        char str[80];
        strftime(str, 100, "%c", ptr);
        printf("FileHeader contents.  File size: %d.   File modification time:%s\n", numBytes, str);

        temp = numSectors;
        numSectors = divRoundUp(numBytes, SectorSize);

        // printf("numBytes:%d\n",numBytes);
        for (i = 0; i < numSectors; i++)
            printf("%d ", dataSectors[i]);
        printf("\nFile contents:\n");
        for (i = k = 0; i < numSectors; i++)
        {
            synchDisk->ReadSector(dataSectors[i], data);
            for (j = 0; (j < SectorSize) && (k < numBytes); j++, k++)
            {
                if ('\040' <= data[j] && data[j] <= '\176') // isprint(data[j])
                    printf("%c", data[j]);
                else
                    printf("\\%x", (unsigned char)data[j]);
            }
            printf("\n");
        }
    }
    else
    {

        int dataSectors2[NumDirect2];
        synchDisk->ReadSector(dataSectors[NumDirect - 1], (char *)dataSectors2);

        time_t thetime = numSectors + 16 * 3600;
        struct tm *ptr = localtime(&thetime);
        char str[80];
        strftime(str, 100, "%c", ptr);
        printf("FileHeader contents.  File size: %d.   File modification time:%s\n", numBytes, str);

        temp = numSectors;
        numSectors = divRoundUp(numBytes, SectorSize);

        for (i = 0; i < NumDirect - 1; i++)
        {
            printf("%d ", dataSectors[i]);
        }
        for (; i < numSectors; i++)
        {
            printf("%d ", dataSectors2[i - (NumDirect - 1)]);
        }
        printf("\nFile contents:\n");
        for (i = k = 0; i < NumDirect - 1; i++)
        {
            synchDisk->ReadSector(dataSectors[i], data);
            for (j = 0; (j < SectorSize) && (k < numBytes); j++, k++)
            {
                if ('\040' <= data[j] && data[j] <= '\176') // isprint(data[j])
                    printf("%c", data[j]);
                else
                    printf("\\%x", (unsigned char)data[j]);
            }
        }

        printf("\nstart print second level:\n");

        for (i = 0; i < numSectors - (NumDirect - 1); i++)
        {
            synchDisk->ReadSector(dataSectors2[i], data);
            for (j = 0; (j < SectorSize) && (k < numBytes); j++, k++)
            {
                if ('\040' <= data[j] && data[j] <= '\176') // isprint(data[j])
                    printf("%c", data[j]);
                else
                    printf("\\%x", (unsigned char)data[j]);
            }
        }

        printf("\nend second level\n");
    }

    delete[] data;

    numSectors = temp;
}

// void FileHeader::Print()
// {
//     int i, j, k;
//     char *data = new char[SectorSize];

//     time_t thetime = numSectors + 16 * 3600;
//     struct tm *ptr = localtime(&thetime);
//     char str[80];
//     strftime(str, 100, "%c", ptr);
//     printf("FileHeader contents.  File size: %d.   File modification time:%s\n", numBytes, str);

//     int temp = numSectors;
//     numSectors = divRoundUp(numBytes, SectorSize);

//     // printf("numBytes:%d\n",numBytes);
//     for (i = 0; i < numSectors; i++)
//         printf("%d ", dataSectors[i]);
//     printf("\nFile contents:\n");
//     for (i = k = 0; i < numSectors; i++)
//     {
//         synchDisk->ReadSector(dataSectors[i], data);
//         for (j = 0; (j < SectorSize) && (k < numBytes); j++, k++)
//         {
//             if ('\040' <= data[j] && data[j] <= '\176') // isprint(data[j])
//                 printf("%c", data[j]);
//             else
//                 printf("\\%x", (unsigned char)data[j]);
//         }
//         printf("\n");
//     }
//     delete[] data;

//     numSectors = temp;
// }

bool FileHeader::setNumBytes(int numBytes)
{
    int temp = numSectors;
    numSectors = divRoundUp(this->numBytes, SectorSize);

    int NewNumSectors = divRoundUp(numBytes, SectorSize);
    if (NewNumSectors == numSectors) //sector do not change
    {
        this->numBytes = numBytes;
        return true;
    }

    int difSector = NewNumSectors - numSectors;

    OpenFile *bitmapfile = new OpenFile(0);
    BitMap *freeMap = new BitMap(NumSectors);
    freeMap->FetchFrom(bitmapfile);

    if (NewNumSectors > (NumDirect - 1 + NumDirect2) || freeMap->NumClear() < difSector)
    {
        return false;
    }

    if (NewNumSectors < NumDirect)
    {
        // printf("begin append!\n");
        for (int i = numSectors; i < NewNumSectors; i++)
        {
            dataSectors[i] = freeMap->Find();
        }
    }
    else
    { //the new file need two level index
        // printf("begin extend and append!\n");
        if (numSectors < NumDirect) //formal file do not have two level index
        {
            for (int i = numSectors; i < NumDirect; i++)
            {
                dataSectors[i] = freeMap->Find();
            }
            int dataSectors2[NumDirect2];
            for (int i = 0; i < NewNumSectors - (NumDirect - 1); i++)
            {
                dataSectors2[i] = freeMap->Find();
            }
            synchDisk->WriteSector(dataSectors[NumDirect - 1], (char *)dataSectors2);
        }
        else
        { //formal file have two level index
            int dataSectors2[NumDirect2];
            synchDisk->ReadSector(dataSectors[NumDirect - 1], (char *)dataSectors2);
            for (int i = numSectors - (NumDirect - 1); i < NewNumSectors - (NumDirect - 1); i++)
            {
                dataSectors2[i] = freeMap->Find();
            }
            synchDisk->WriteSector(dataSectors[NumDirect - 1], (char *)dataSectors2);
        }
    }

    this->numBytes = numBytes;

    freeMap->WriteBack(bitmapfile);

    // printf("end extend and append!\n");

    this->numSectors = temp;

    return true;
}

// bool FileHeader::setNumBytes(int numBytes)
// {
//     int temp = numSectors;
//     numSectors = divRoundUp(this->numBytes, SectorSize);

//     if (this->numBytes > numBytes)
//         return FALSE;
//     if (this->numBytes == numBytes)
//         return TRUE;
//     int nowNumSectors = divRoundUp(numBytes, SectorSize);
//     if (nowNumSectors == numSectors)
//     {
//         this->numBytes = numBytes;
//         return TRUE;
//     }
//     int moreSectors = nowNumSectors - numSectors;
//     OpenFile *bitmapfile = new OpenFile(0);
//     BitMap *freeMap = new BitMap(NumSectors);
//     freeMap->FetchFrom(bitmapfile);
//     if (nowNumSectors > NumDirect || freeMap->NumClear() < moreSectors)
//         return FALSE;

//     int i;
//     for (i = numSectors; i < nowNumSectors; i++)
//     {
//         dataSectors[i] = freeMap->Find();
//         freeMap->WriteBack(bitmapfile);
//         printf("num:%d,nowNum:%d,moreNum:%d,assign sector:%d\n", numSectors,
//                nowNumSectors, moreSectors, dataSectors[i]);
//     }

//     this->numBytes = numBytes;
//     this->numSectors = nowNumSectors;

//     numSectors = temp;

//     return TRUE;
// }

void FileHeader::setNumSectors(int sectors)
{
    numSectors = sectors;
}

int FileHeader::getNumSectors()
{
    return numSectors;
}

FileHeader::~FileHeader()
{
    if (sector != -1 && ifChanged)
    {
        WriteBack(sector);
        printf("inodes have been written back\n");
    }
}
