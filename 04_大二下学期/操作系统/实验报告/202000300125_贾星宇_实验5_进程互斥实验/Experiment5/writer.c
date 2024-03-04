/*
* Filename: writer.c
* Copyright: (C) 2006 by zhonghonglie, revised 2021.5.13
* Function: 建立并模拟写者进程
*/

#include "ipc.h"

int main(int argc, char *argv[])
{
    int i, j = 0, k = 0;
    int t_writing = 3, t_break = 0;
    Msg_buf msg_arg;

    // 可在在命令行第1个参数指定一个模拟写过程的秒数, 第2个参数指定一个模拟离开的秒数
    if(argc > 1) {
        t_writing = atoi(argv[1]);
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

    //循环请求写
    msg_arg.mid = getpid();
    while(1) {
        //发写请求消息
        msg_arg.mtype = WRITERQUEST;
        msgsnd(quest_id, &msg_arg, sizeof(msg_arg.mid), 0);
        printf("%d writer quest %d\n", msg_arg.mid, ++k);

        //等待允许写消息
        msgrcv(respond_id, &msg_arg, sizeof(msg_arg.mid), msg_arg.mid, 0);
        //写入 STRSIZ 个相同的字符
        for(i = 0; i < STRSIZ; i++)
            buff_ptr[i] = 'A' + j;
        j = (j + 1) % STRSIZ; //按 STRSIZ 循环变换字符
        printf("%d writing: %s\n", msg_arg.mid, buff_ptr);
        sleep(t_writing);

        //发写完成消息
        msg_arg.mtype = FINISHED;
        msgsnd(quest_id, &msg_arg, sizeof(msg_arg.mid), 0);

        if(t_break)
            sleep(t_break);
    }

    return EXIT_SUCCESS;
}
