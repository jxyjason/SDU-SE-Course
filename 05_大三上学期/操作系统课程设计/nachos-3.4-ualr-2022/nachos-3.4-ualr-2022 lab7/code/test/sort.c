/* sort.c 
 *    Test program to sort a large number of integers.
 *
 *    Intention is to stress virtual memory system.
 *
 *    Ideally, we could read the unsorted array off of the file system,
 *	and store the result back to the file system!
 */

#include "syscall.h"

/* size of physical memory; with code, we'll run out of space!*/
#define ARRAYSIZE 100

int A[ARRAYSIZE];

int
main()
{
    int i, j, tmp;

    /* first initialize the array, in reverse sorted order */
    for (i = 0; i < ARRAYSIZE; i++)		
        A[i] = ARRAYSIZE - i - 1;

    /* then sort! */
    for (i = 0; i < (ARRAYSIZE - 1); i++)
        for (j = 0; j < ((ARRAYSIZE - 1) - i); j++)
            if (A[j] > A[j + 1]) {  /* out of order -> need to swap ! */
                tmp = A[j];
                A[j] = A[j + 1];
                A[j + 1] = tmp;
            }
    PrintInt(A[0]);  /* and then we're done -- should be 0! */
    PrintInt(A[1]);  /* should be 1 */
    PrintInt(A[ARRAYSIZE - 2]);  /* should be ARRAYSIZE - 2 */
    PrintInt(A[ARRAYSIZE - 1]);  /* should be ARRAYSIZE - 1 */
    PrintInt(ARRAYSIZE);  /* should be ARRAYSIZE */
    Halt();
}
