/*
* Filename: reader.c
* Copyright: (C) 2006 by zhonghonglie, revised 2021.5.13
* Function: 建立并模拟读者进程
*/

#include "ipc.h"

int main(int argc, char *argv[])
{
    int i = 0;
    int t_reading = 3, t_break = 0;
    Msg_buf msg_arg;

    // 可在在命令行第1个参数指定一个模拟读过程的秒数, 第2个参数指定一个模拟离开的秒数
    if(argc > 1) {
        t_reading = atoi(argv[1]);
        if(argc > 2)
            t_break = atoi(argv[2]);
    }

    //附加一个要读内容的共享内存
    buff_key = 101;
    buff_num = STRSIZ + 1;
    shm_flg = IPC_CREAT | 0644;
    buff_ptr = (char *)set_shm(buff_key, buff_num, shm_flg);

    //联系一个请求消息队列
    quest_flg = IPC_CREAT | 0644;
    quest_key = 201;
    quest_id = set_msq(quest_key, quest_flg);

    //联系一个响应消息队列
    respond_flg = IPC_CREAT | 0644;
    respond_key = 202;
    respond_id = set_msq(respond_key, respond_flg);

    //循环请求读
    msg_arg.mid = getpid();
    while(1) {
        // 发读请求消息
        msg_arg.mtype = READERQUEST;
        msgsnd(quest_id, &msg_arg, sizeof(msg_arg.mid), 0);
        printf("%d reader quest %d\n", msg_arg.mid, ++i);

        // 等待允许读消息
        msgrcv(respond_id, &msg_arg, sizeof(msg_arg.mid), msg_arg.mid, 0);
        printf("%d reading: %s\n", msg_arg.mid, buff_ptr);
        sleep(t_reading);

        // 发读完成消息
        msg_arg.mtype = FINISHED;
        msgsnd(quest_id, &msg_arg, sizeof(msg_arg.mid), 0);

        if(t_break)
            sleep(t_break);
    }

    return EXIT_SUCCESS;
}
