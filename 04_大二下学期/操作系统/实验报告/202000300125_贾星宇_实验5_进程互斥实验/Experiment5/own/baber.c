#include "ipc.h"
#include <sys/types.h>
#include <unistd.h>

int main(int argc,char *argv[])
{
  //  int i;
    int rate;
    Msg_buf msg_arg;
    //可在在命令行第一参数指定一个进程睡眠秒数,以调解进程执行速度
    if(argv[1] != NULL) rate = atoi(argv[1]);
    else rate = 3;

    //联系一个请求消息队列
    wait_quest_flg = IPC_CREAT| 0644;
    wait_quest_key = 101;
    wait_quest_id = set_msq(wait_quest_key,wait_quest_flg);
    //联系一个响应消息队列
    wait_respond_flg = IPC_CREAT| 0644;
    wait_respond_key = 102;
    wait_respond_id = set_msq(wait_respond_key,wait_respond_flg);

    //联系一个请求消息队列
    sofa_quest_flg = IPC_CREAT| 0644;
    sofa_quest_key = 201;
    sofa_quest_id = set_msq(sofa_quest_key,sofa_quest_flg);
    //联系一个响应消息队列
    sofa_respond_flg = IPC_CREAT| 0644;
    sofa_respond_key = 202;
    sofa_respond_id = set_msq(sofa_respond_key,sofa_respond_flg);

    //信号量使用的变量
    costomer_key = 301;//顾客同步信号灯键值
    account_key = 302;//账簿互斥信号灯键值
    sem_flg = IPC_CREAT | 0644;
    //顾客同步信号灯初值设为0
    sem_val = 0;
    //获取顾客同步信号灯,引用标识存 costomer_sem
    costomer_sem = set_sem(costomer_key,sem_val,sem_flg);
    //账簿互斥信号灯初值设为 1
    sem_val = 1;
    //获取消费者同步信号灯,引用标识存 cons_sem
    account_sem = set_sem(account_key,sem_val,sem_flg);

    int pid1, pid2;
    pid1=fork();
    if(pid1==0) {
        while(1) {
            wait_quest_flg=IPC_NOWAIT;
	    sleep(rate);

            if(msgrcv(sofa_quest_id, &msg_arg, sizeof(msg_arg), 0, wait_quest_flg)>=0) {
                msgsnd(sofa_respond_id, &msg_arg,sizeof(msg_arg), 0);
                printf("%d barber is serving for %d customer \n", getpid(), msg_arg.mid);

                down(account_sem);
                printf("%d barber is collect %d customer's money\n", getpid(), msg_arg.mid);
                up(account_sem);
            }else {
                    printf("%d barber is sleeping\n", getpid());

            }
        }
    } else {
        pid2=fork();
        if(pid2==0) {
            while(1) {
               wait_quest_flg=IPC_NOWAIT;
		sleep(rate);
               if(msgrcv(sofa_quest_id, &msg_arg, sizeof(msg_arg), 0, wait_quest_flg)>=0) {
                    msgsnd(sofa_respond_id, &msg_arg,sizeof(msg_arg), 0);
                    printf("%d barber is serving for %d customer \n", getpid(), msg_arg.mid);

                    down(account_sem);
                    printf("%d barber is collect %d customer's money\n", getpid(), msg_arg.mid);
                    up(account_sem);
               } else {
                    printf("%d barber is sleeping\n", getpid());

               }
            }
        } else {
             while(1) {
               wait_quest_flg=IPC_NOWAIT;
		sleep(rate);
               if(msgrcv(sofa_quest_id, &msg_arg, sizeof(msg_arg), 0, wait_quest_flg)>=0) {
                    msgsnd(sofa_respond_id, &msg_arg,sizeof(msg_arg), 0);
                    printf("%d barber is serving for %d customer \n", getpid(), msg_arg.mid);

                    down(account_sem);
                    printf("%d barber is collect %d customer's money\n", getpid(), msg_arg.mid);
                    up(account_sem);
               } else {
                    printf("%d barber is sleeping\n", getpid());

               }
            }
        }
    }
    return 0;
}

