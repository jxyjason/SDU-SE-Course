/*
* Filename : psched.c
* Copyright : (C) 2006 by zhanghonglie
* Last update : 2022.1.19
* Function : 父进程创建3个子进程，为它们设置不同的优先数和调度策略
* 注意提高进程优先级需要超级用户的权限才可以起作用
* Usage:
    sudo ./psched [pri0 [pri1 [pri2 [pol0 [pol1 [pol2]]]]]]
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sched.h>
#include <sys/resource.h>

#define LOOPS 200000000UL


unsigned long cal(unsigned long nLoops)
{
    unsigned long i, sum = 0UL;

    for(i = 0UL; i < nLoops; i++)
        sum++;

    return sum;
}


int main(int argc, char *argv[])
{
    int i;
    int pid[3]; //存放进程号
    struct sched_param p[3]; //设置调度策略时使用的数据结构
    int pol[3], pri[3];  // policy & priority

    for(i = 0; i < 3; i++) {
        //循环创建3个子进程
        if((pid[i] = fork()) > 0) {  // Parent
            //取进程优先数放在调度策略数据结构中
            if(argc > (i + 4))
                pol[i] = atoi(argv[i + 4]);
            else
                pol[i] = SCHED_OTHER;

            if(argc > (i + 1))
                pri[i] = atoi(argv[i + 1]);
            else
                pri[i] = 10;

            if(pol[i] == SCHED_OTHER)
                p[i].sched_priority = 0;
            else
                p[i].sched_priority = pri[i];

            //父进程设置子进程的调度策略.如果命令行第4,5,6 参数指定了3个策略值则按指定的数设置,否则都为默认策略
            sched_setscheduler(pid[i], pol[i], &p[i]);
            //父进程设置子进程的优先数,如果命令行第1,2,3 参数指定了3个优先数则按指定的数设置,否则都为10
            setpriority(PRIO_PROCESS, pid[i], pri[i]);
        }

        //各子进程循环报告其优先数和调度策略
        else{  // Child
            sleep(3);  // Wait parent run. Not reliable, for demo only!
            //每个子进程报告10次进程号和优先级，两次报告之间调用一次耗费CPU的cal函数
            for(i = 0; i < 10; i++)
                printf("Child PID = %d  %d  priority = %d   @%lu\n", getpid(), i, getpriority(PRIO_PROCESS, 0), cal(LOOPS));

            exit(EXIT_SUCCESS);
        }
    }

    //父进程报告子进程调度策略后先行退出
    for(i = 0; i < 3; i++)
        printf("My child %d policy is %d\n", pid[i], sched_getscheduler(pid[i]));

    return EXIT_SUCCESS;
}





















