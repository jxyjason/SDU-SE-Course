/*
* Filename : ipc.c
* copyright : (C) 2006 by zhonghonglie, revised 2022.1.19
* Function : 一组建立IPC机制的函数
*/

#include "ipc.h"

//生产消费者共享缓冲区即其有关的变量
key_t buff_key;
int buff_num;
char *buff_ptr;

//生产者放产品位置的共享指针
key_t pput_key;
int pput_num;
int *pput_ptr;

//消费者取产品位置的共享指针
key_t cget_key;
int cget_num;
int *cget_ptr;

//生产者有关的信号量
key_t prod_key;
key_t pmtx_key;
int prod_sem;
int pmtx_sem;

//消费者有关的信号量
extern key_t constoba_key;
extern key_t cmtxtoba_key;
extern int constoba_sem;
extern int cmtxtoba_sem;


extern key_t conspaper_key;
extern key_t cmtxpaper_key;
extern int conspaper_sem;
extern int cmtxpaper_sem;


extern key_t consglue_key;
extern key_t cmtxglue_key;
extern int consglue_sem;
extern int cmtxglue_sem;


/*
* get_ipc_id() 从/proc/sysvipc/文件系统中获取IPC的id号
* pfile: 对应/proc/sysvipc/目录中的IPC文件分别为
* msg-消息队列,sem-信号量,shm-共享内存
* key: 对应要获取的IPC的id号的键值
*/
int get_ipc_id(char *proc_file, key_t key)
{
    FILE *pf;
    int i, j;
    char line[BUFSZ], colum[BUFSZ];

    if((pf = fopen(proc_file,"r")) == NULL) {
        perror("Proc file not open");
        exit(EXIT_FAILURE);
    }

    fgets(line, BUFSZ, pf);
    while(!feof(pf)) {
        i = j = 0;
        fgets(line, BUFSZ, pf);
        while(line[i] == ' ')
            i++;
        while(line[i] !=' ')
            colum[j++] = line[i++];
        colum[j] = '\0';
        if(atoi(colum) != key)
            continue;
        j = 0;
        while(line[i] == ' ')
            i++;
        while(line[i] !=' ')
            colum[j++] = line[i++];
        colum[j] = '\0';
        i = atoi(colum);
        fclose(pf);
        return i;
    }
    fclose(pf);
    return -1;
}

/*
* 信号灯上的down/up操作
* semid:信号灯数组标识符
* semnum:信号灯数组下标
* buf:操作信号灯的结构
*/
int down(int sem_id)
{
    struct sembuf buf;
    buf.sem_op = -1;
    buf.sem_num = 0;
    buf.sem_flg = SEM_UNDO;
    if((semop(sem_id, &buf, 1)) < 0) {
        perror("down error ");
        exit(EXIT_FAILURE);
    }
    return EXIT_SUCCESS;
}

int up(int sem_id)
{
    struct sembuf buf;
    buf.sem_op = 1;
    buf.sem_num = 0;
    buf.sem_flg = SEM_UNDO;
    if((semop(sem_id, &buf, 1)) < 0) {
        perror("up error ");
        exit(EXIT_FAILURE);
    }
    return EXIT_SUCCESS;
}

/*
* set_sem函数建立一个具有n个信号灯的信号量
* 如果建立成功，返回一个信号灯数组的标识符sem_id
* 输入参数：
* sem_key 信号灯数组的键值
* sem_val 信号灯数组中信号灯的个数
* sem_flag 信号等数组的存取权限
*/
int set_sem(key_t sem_key, int sem_val, int sem_flg)
{
    int sem_id;
    Sem_uns sem_arg;

    //测试由sem_key标识的信号灯数组是否已经建立
    if((sem_id = get_ipc_id("/proc/sysvipc/sem", sem_key)) < 0) {

        //semget新建一个信号灯,其标号返回到sem_id
        if((sem_id = semget(sem_key, 1, sem_flg)) < 0) {
            perror("semaphore create error");
            exit(EXIT_FAILURE);
        }

        //设置信号灯的初值
        sem_arg.val = sem_val;
        if(semctl(sem_id,0, SETVAL, sem_arg) < 0) {
            perror("semaphore set error");
            exit(EXIT_FAILURE);
        }
    }
    return sem_id;
}

/*
* set_shm函数建立一个具有n个字节 的共享内存区
* 如果建立成功，返回 一个指向该内存区首地址的指针shm_buf
* 输入参数：
* shm_key 共享内存的键值
* shm_val 共享内存字节的长度
* shm_flag 共享内存的存取权限
*/
char * set_shm(key_t shm_key, int shm_num, int shm_flg)
{
    int i, shm_id;
    char * shm_buf;

    //测试由shm_key标识的共享内存区是否已经建立
    if((shm_id = get_ipc_id("/proc/sysvipc/shm", shm_key)) < 0) {
        //shmget新建 一个长度为shm_num字节的共享内存,其标号返回到shm_id
        if((shm_id = shmget(shm_key, shm_num, shm_flg)) < 0) {
            perror("shareMemory set error");
            exit(EXIT_FAILURE);
        }

        //shmat将由shm_id标识的共享内存附加给指针shm_buf
        if((shm_buf = (char *)shmat(shm_id, 0, 0)) < (char *)0) {
            perror("get shareMemory error");
            exit(EXIT_FAILURE);
        }

        for(i = 0; i < shm_num; i++) shm_buf[i] = 0; //初始为0
    }

    //shm_key 标识的共享内存区已经建立,将由shm_id 标识的共享内存附加给指针shm_buf
    if((shm_buf = (char *)shmat(shm_id, 0, 0)) < (char *)0) {
        perror("get shareMemory error");
        exit(EXIT_FAILURE);
    }
    return shm_buf;
}

/*
* set_msq 函数建立一个消息队列
* 如果建立成功，返回一个消息队列的标识符msq_id
* 输入参数：
* msq_key 消息队列的键值
* msq_flag 消息队列的存取权限
*/
int set_msq(key_t msq_key, int msq_flg)
{
    int msq_id;

    //测试由msq_key标识的消息队列是否已经建立
    if((msq_id = get_ipc_id("/proc/sysvipc/msg", msq_key)) < 0) {
        //msgget新建一个消息队列,其标号返回到msq_id
        if((msq_id = msgget(msq_key, msq_flg)) < 0) {
            perror("messageQueue set error");
            exit(EXIT_FAILURE);
        }
    }
    return msq_id;
}
