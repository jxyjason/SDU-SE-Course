#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <sys/time.h>
#include <stdalign.h>
#include <string.h>
#include <pthread.h>

unsigned int howMuchDk;
unsigned int howMuch;
double* distance;

struct fun_para{
int k; int n; int dim;  int M;int whichP;unsigned int begin;unsigned int end;int* maxDisSumPivots;int* combineData;int* minDisSumPivots;
double* coord;double* maxDistanceSum;  double* minDistanceSum;       
};

//sort
void sort(int *array,int len){
    int i;
    for (i = 0; i < len-1; i++)
    {
        int min = i,j=i+1;
        for (; j < len; j++)
        {
            if (array[j]<array[min])
            {
                min=j;
            }
        }
        int temp = array[min];
        array[min] = array[i];
        array[i] = temp;
    }
}

//chabiao   memset   xianglianghua   bianyicanshu 
//gcc -O3 pivot.c -lm -o pivot -lpthread


// Calculate sum of distance while combining different pivots. Complexity : O( n^2 )
inline static double SumDistance(const int k, const int n, const int dim, double* coord, int* pivots){
    double* rebuiltCoord = (double*)aligned_alloc(8,sizeof(double) * n * k);
    int i;
    memset(rebuiltCoord,0,n*k);
    for(i=0; i<n; i+=1){
        double* rcp = rebuiltCoord+i*k;
        int ki;
        for(ki=0; ki<k; ki+=1){

            // double distance = 0;
            // int j;
            // for(j=0; j<dim; j+=1){
            //     distance += (coord[pivots[ki]*dim+j] - coord[i*dim + j])*
            //         (coord[pivots[ki]*dim+j] - coord[i*dim + j]);
                
            // }
            // // rebuiltCoord[i*k + ki] = sqrt(distance);
            // // *(rcp++) = sqrt(distance);
            // distance = sqrt(distance);
            // memcpy(rcp++,&distance,sizeof(double));

            double dis = distance[i*n+pivots[ki]];
            memcpy(rcp++,&dis,sizeof(double));
        }
    }
    

    // Calculate the sum of Chebyshev distance with rebuilt coordinates between every points
    double chebyshevSum = 0;
    for(i=0; i<n-1; i+=1){
        int j;
        for(j=i+1; j<n; j+=1){
            double chebyshev = 0;
            int ki;
            for(ki=0; ki<k; ki+=1){
                double dis = fabs(rebuiltCoord[i*k + ki] - rebuiltCoord[j*k + ki]);
                chebyshev = dis>chebyshev ? dis : chebyshev;
            }
            chebyshevSum += chebyshev;
        }
    }
    free(rebuiltCoord);
    

    return chebyshevSum * 2;
}

// Recursive function Combination() : combine pivots and calculate the sum of distance while combining different pivots.
// ki  : current depth of the recursion
// k   : number of pivots
// n   : number of points
// dim : dimension of metric space
// M   : number of combinations to store,1000
// coord  : coordinates of points
// pivots : indexes of pivots
// maxDistanceSum  : the largest M distance sum
// maxDisSumPivots : the top M pivots combinations
// minDistanceSum  : the smallest M distance sum
// minDisSumPivots : the bottom M pivots combinations



void* Combination(void* in){
    struct fun_para *arg = (struct fun_para *)in;
    int *pivots = (int*)aligned_alloc(8,sizeof(int) * arg->k);
    int i,indexNum;
    // printf("begin%d:from:%d,to:%d\n",arg->whichP,arg->begin,arg->end);

    for (i = arg->begin; i < arg->end; i+=arg->k)
    {
        int * pp = pivots;
        int j;
        for (j = 0; j < arg->k; j++)
        {
            // *(pp++) = arg->combineData[i+j];
            memcpy(pp++,arg->combineData+i+j,sizeof(int));
            // printf("data:%d\n",arg->combineData[i+j]);
            
        }
        indexNum = i/arg->k;
        double distanceSum = SumDistance(arg->k, arg->n, arg->dim, arg->coord, pivots);

        // printf("%lf\n",arg->minDistanceSum[0]);

        int mBar = arg->M+1;


        memcpy(arg->maxDistanceSum+(arg->M+1)*(arg->whichP+1)-1,&distanceSum,sizeof(double));
        memcpy(arg->minDistanceSum+(arg->M+1)*(arg->whichP+1)-1,&distanceSum,sizeof(double));


        for (j = 0; j < arg->k; j++)
        {
            memcpy(arg->maxDisSumPivots+(arg->M+1)*arg->k*(arg->whichP) + arg->M*arg->k+j,arg->combineData+i+j,sizeof(int));
            memcpy(arg->minDisSumPivots+(arg->M+1)*arg->k*(arg->whichP) + arg->M*arg->k+j,arg->combineData+i+j,sizeof(int));
        }

        
        for (j = (arg->M+1)*(arg->whichP+1)-1; j > (arg->M+1)*(arg->whichP) +1; j-=1)
        {
            if (arg->maxDistanceSum[j]>arg->maxDistanceSum[j-1])
            {
                double temp = arg->maxDistanceSum[j];
                arg->maxDistanceSum[j] = arg->maxDistanceSum[j-1];
                arg->maxDistanceSum[j-1] = temp;
                int kj;
                for(kj=0; kj<arg->k; kj+=1){
                    int temp = arg->maxDisSumPivots[j*arg->k + kj];
                    arg->maxDisSumPivots[j*arg->k + kj] = arg->maxDisSumPivots[(j-1)*arg->k + kj];
                    arg->maxDisSumPivots[(j-1)*arg->k + kj] = temp;
                        
                }
            }

            if (arg->minDistanceSum[j]<arg->minDistanceSum[j-1])
            {

                double temp = arg->minDistanceSum[j];
                arg->minDistanceSum[j] = arg->minDistanceSum[j-1];
                arg->minDistanceSum[j-1] = temp;
                int kj;
                for(kj=0; kj<arg->k; kj+=1){
                    int temp = arg->minDisSumPivots[j*arg->k + kj];
                    arg->minDisSumPivots[j*arg->k + kj] = arg->minDisSumPivots[(j-1)*arg->k + kj];
                    arg->minDisSumPivots[(j-1)*arg->k + kj] = temp;
                }
            }
            
        }
        

    }

    // printf("end%d:from:%d,to:%d\n",arg->whichP,arg->begin,arg->end);

    if(!arg->end==howMuch)pthread_exit(0);

}


int len = 0;
void combine(int pos,int num,int n,int k,int* temp,int *a)
{
    if (pos==k)   // 已有k个数
    {
       for (int i=0;i<k;i++){
           a[len++] = temp[i];
       }
       return;
    }
    if(k-pos>n-num+1) return ;
    for(int i=num;i<=n;i++)
    {
        temp[pos]=i;
        combine(pos+1,i+1,n,k,temp,a);
    }
}






int main(int argc, char* argv[]){

    unsigned int number;
    printf("number of threads:");
    scanf("%d",&number);
    number--;

    // filename : input file namespace
    char* filename = (char*)"uniformvector-2dim-5h.txt";
    if( argc==2 ) {
        filename = argv[1];
    }  else if(argc != 1) {
        printf("Usage: ./pivot <filename>\n");
        return -1;
    }
    // M : number of combinations to store
    const int M = 1000;
    // dim : dimension of metric space
    int dim;
    // n : number of points
    int n;
    // k : number of pivots
    int k;

    // Read parameter
    FILE* file = fopen(filename, "r");
    if( file == NULL ) {
        printf("%s file not found.\n", filename);
        return -1;
    }
    fscanf(file, "%d", &dim);
    fscanf(file, "%d", &n);
    fscanf(file, "%d", &k);
    printf("dim = %d, n = %d, k = %d\n", dim, n, k);

    // FILE* combineDataFile = fopen((char*)"processedData.txt","r");//combined
    
    // Start timing
    struct timeval start;

    // Read Data
    double* coord = (double*)aligned_alloc(8,sizeof(double) * dim * n);
    int i;
    for(i=0; i<n; i++){
        int j;
        for(j=0; j<dim; j++){
            fscanf(file, "%lf", &coord[i*dim + j]);
        }
    }
    fclose(file);

    gettimeofday(&start, NULL);

    //compute how much
    int flag = 0;
    unsigned int tempk = 1;
    for (i = 1; i <= k; i++)
    {
        tempk*=i;
    }
    howMuchDk = 1;
    for (i = 0; i < k; i+=1)
    {
        if(flag==0)
        if(howMuchDk%tempk==0){
            howMuchDk/=tempk;
            flag=1;
        }
        howMuchDk*=(n-i);
    }
    // printf("%d\n",howMuchDk);

    howMuch = howMuchDk*k;

    

    //combined
    int * combined = (int*)malloc(sizeof(int) * howMuch);
    int * tempCom = (int*)malloc(sizeof(int) * k);
    combine(0,0,n-1,k,tempCom,combined);
    
    //distance
    distance = (double*)aligned_alloc(8,sizeof(double) * n*n);
    double *dp = distance;
    for (i = 0; i < n; i+=1)
    {
        int j;
        for(j=0; j<n; j+=1){
            double dis = 0;
            int k;
            for (k = 0; k < dim; k+=1)
            {
                dis += (coord[i*dim+k] - coord[j*dim + k])*
                (coord[i*dim+k] - coord[j*dim + k]); 
            }
        dis = sqrt(dis);
        memcpy(dp++,&dis,sizeof(double));
        }
    }
    




    // maxDistanceSum : the largest M distance sum
    double* maxDistanceSum = (double*)aligned_alloc(8,sizeof(double) * (number+1)*(M+1));
    // double * p = maxDistanceSum;*(number+1)
    // for(i=0; i<n*(n-1)/k; i++){
    //     // *(p++) = 0;
    //     maxDistanceSum[i] = 0;
    // }
    memset(maxDistanceSum,0,(number+1)*(M+1));
    // maxDisSumPivots : the top M pivots combinations
    int* maxDisSumPivots = (int*)malloc(sizeof(int)* (number+1)*(M+1)*k);
    // for(i=0; i<n*(n-1)/k; i++){
    //     int ki;
    //     for(ki=0; ki<k; ki++){
    //         maxDisSumPivots[i*k + ki] = 0;
    //     }
    // }
    memset(maxDisSumPivots,0,(number+1)*(M+1)*k);
    // minDistanceSum : the smallest M distance sum
    double* minDistanceSum = (double*)aligned_alloc(8,sizeof(double) *  (number+1)*(M+1));
    for(i=0; i<(number+1)*(M+1); i++){
        minDistanceSum[i] = __DBL_MAX__;
    }
    // memset(minDistanceSum,__DBL_MAX__,(M+1));

    // minDisSumPivots : the bottom M pivots combinations
    int* minDisSumPivots = (int*)malloc(sizeof(int) *  (number+1)*(M+1)*k);
    // for(i=0; i<n*(n-1)/k; i++){
    //     int ki;
    //     for(ki=0; ki<k; ki++){
    //         minDisSumPivots[i*k + ki] = 0;
    //     }
    // }
    memset(minDisSumPivots,0,(number+1)*(M+1)*k);


  
    // Main loop. Combine different pivots with recursive function and evaluate them. Complexity : O( n^(k+2) )
    // Combination(k, n, dim, M, combined,coord, maxDistanceSum, maxDisSumPivots, minDistanceSum, minDisSumPivots);


    
    
    struct fun_para paras[number+1];
    pthread_t threads[number];
    int status;

    unsigned int every_count = howMuch/(number+1);
    unsigned int pre_num = 0;

    #pragma omp parallel for
    for (i = 0; i < number; i++)
    {
        
        paras[i].n = n;
        paras[i].k = k;
        paras[i].dim = dim;
        paras[i].M = M;
        paras[i].combineData = combined;
        paras[i].coord = coord;
        paras[i].maxDistanceSum = maxDistanceSum;
        paras[i].maxDisSumPivots = maxDisSumPivots;
        paras[i].minDistanceSum = minDistanceSum;
        paras[i].minDisSumPivots = minDisSumPivots;
        paras[i].begin = pre_num;
        paras[i].end = pre_num+every_count;
        paras[i].whichP = i;
        pre_num += every_count;
        status = pthread_create(&threads[i],NULL,
        Combination,
        (void*)&paras[i]);
        if(status!=0){//线程创建不成功，打印错误信息
    		printf("pthread_create returned error code %d\n",status);
    		exit(-1);
		}
    }

    paras[i].n = n;
    paras[i].k = k;
    paras[i].dim = dim;
    paras[i].M = M;
    paras[i].combineData = combined;
    paras[i].coord = coord;
    paras[i].maxDistanceSum = maxDistanceSum;
    paras[i].maxDisSumPivots = maxDisSumPivots;
    paras[i].minDistanceSum = minDistanceSum;
    paras[i].minDisSumPivots = minDisSumPivots;
    paras[i].begin = pre_num;
    paras[i].end = howMuch;
    paras[i].whichP = i;
    Combination((void*)&paras[i]);

    for(i=0;i<number;i++){
		pthread_join(threads[i],NULL);
	}



    //sort pivot

    // for (i = 0; i < M*k; i+=k)
    // {
    //     sort(maxDisSumPivots+i,k);
    //     sort(minDisSumPivots+i,k);
    // }
    


    int indexNum;
    int howMuchDistance = (number+1)*(M+1);
    for (i = 0; i < M; i++)
    {
        int maxIndex;
        int minIndex;
        int j;
        for (maxIndex=minIndex=j = i; j < howMuchDistance; j++)
        {
            if (maxDistanceSum[j]>maxDistanceSum[maxIndex])
            {
                maxIndex = j;
            }
            if (minDistanceSum[j]<minDistanceSum[minIndex])
            {
                minIndex = j;
            }
        }

        double temp = maxDistanceSum[maxIndex];
        maxDistanceSum[maxIndex] = maxDistanceSum[i];
        maxDistanceSum[i] = temp;
        int kj;
        for(kj=0; kj<k; kj+=1){
            int temp = maxDisSumPivots[maxIndex*k + kj];
            maxDisSumPivots[maxIndex*k + kj] = maxDisSumPivots[i*k + kj];
            maxDisSumPivots[i*k + kj] = temp;
        }
        sort(maxDisSumPivots+i*k,k);

        temp = minDistanceSum[minIndex];
        minDistanceSum[minIndex] = minDistanceSum[i];
        minDistanceSum[i] = temp;
        for(kj=0; kj<k; kj+=1){
            
            int temp = minDisSumPivots[minIndex*k + kj];
            minDisSumPivots[minIndex*k + kj] = minDisSumPivots[i*k + kj];
            minDisSumPivots[i*k + kj] = temp;
            
        }
        sort(minDisSumPivots+i*k,k);

        // printf("%f , %f \n",maxDistanceSum[i],minDistanceSum[i]);
        
    }




    // End timing
    struct timeval end;
    gettimeofday (&end, NULL);
    printf("Using time : %f ms\n", (end.tv_sec-start.tv_sec)*1000.0+(end.tv_usec-start.tv_usec)/1000.0);

    // Store the result
    FILE* out = fopen("result.txt", "w");
    for(i=0; i<M; i++){
        int ki;
        for(ki=0; ki<k-1; ki++){
            fprintf(out, "%d ", maxDisSumPivots[i*k + ki]);
        }
        fprintf(out, "%d\n", maxDisSumPivots[i*k + k-1]);
    }
    for(i=0; i<M; i++){
        int ki;
        for(ki=0; ki<k-1; ki++){
            fprintf(out, "%d ", minDisSumPivots[i*k + ki]);
        }
        fprintf(out, "%d\n", minDisSumPivots[i*k + k-1]);
    }
    fclose(out);

    // Log
    int ki;
    printf("max : ");
    for(ki=0; ki<k; ki++){
        printf("%d ", maxDisSumPivots[ki]);
    }
    printf("%lf\n", maxDistanceSum[0]);
    printf("min : ");
    for(ki=0; ki<k; ki++){
        printf("%d ", minDisSumPivots[ki]);
    }
    printf("%lf\n", minDistanceSum[0]);

    return 0;
}





