/*
* Filename: control.c
* Copyright: (C) 2006 by zhonghonglie, revised 2021.5.13
* Function: 建立并模拟控制者进程
*/

#include "ipc.h"

int main(int argc, char *argv[])
{
    int i;
    int count = MAXVAL;
    Msg_buf msg_arg;
    int wr_mid_next;

    // 建立一个共享内存先写入一串 A 字符模拟要读写的内容
    buff_key = 101;
    buff_num = STRSIZ + 1;
    shm_flg = IPC_CREAT | 0644;
    buff_ptr = (char *)set_shm(buff_key, buff_num, shm_flg);
    for(i = 0; i < STRSIZ; i++)
        buff_ptr[i] = 'A';
    buff_ptr[i] = '\0';

    // 建立一条请求消息队列
    quest_flg = IPC_CREAT | 0644;
    quest_key = 201;
    quest_id = set_msq(quest_key, quest_flg);

    // 建立一条响应消息队列
    respond_flg = IPC_CREAT | 0644;
    respond_key = 202;
    respond_id = set_msq(respond_key, respond_flg);

    // 控制进程准备接收和响应读写者的消息
    printf("Wait quest\n");
    while(1) {
        wr_mid_next = 0;  // 无需要延迟处理的对写者的响应

        // 状态1：初始状态；或空闲状态；或有读者在读，但无写者正在写，也无需要延迟处理的对写者的响应
        if(count > 0) {
            // 以非阻塞方式接收消息
            if(msgrcv(quest_id, &msg_arg, sizeof(msg_arg.mid), FINISHED, IPC_NOWAIT) >= 0) {
                // 有读者完成
                count++;
                printf("%d reader finished\n", msg_arg.mid);
            }

            else if(msgrcv(quest_id, &msg_arg, sizeof(msg_arg.mid), READERQUEST, IPC_NOWAIT) >= 0) {
                // 有读者请求, 允许读者读
                count--;
                msg_arg.mtype = msg_arg.mid;
                msgsnd(respond_id, &msg_arg, sizeof(msg_arg.mid), 0);
                printf("%d quest read\n", msg_arg.mid);
            }

            else if(msgrcv(quest_id, &msg_arg, sizeof(msg_arg.mid), WRITERQUEST, IPC_NOWAIT) >= 0) {
                // 有写者请求, 看是否立即允许写者写，还是需要等读者(们)读完再写
                if(count == MAXVAL) { // 当前无读者，立即响应写者
                    msg_arg.mtype = msg_arg.mid;
                    msgsnd(respond_id, &msg_arg, sizeof(msg_arg.mid), 0);
                    printf("%d quest write\n", msg_arg.mid);
                }
                else  // 当前有读者，延迟对写者的响应
                    wr_mid_next = msg_arg.mid;
                count -= MAXVAL;
            }
        } // if(count > 0)

        // 状态2：当 count == 0 时说明1个写者正在写, 我们等待写完成
        if(count == 0) {
            msgrcv(quest_id, &msg_arg, sizeof(msg_arg.mid), FINISHED, 0); // 以阻塞方式接收消息
            count = MAXVAL;
            printf("%d write finished\n", msg_arg.mid);
        }

        // 状态3：当 count < 0 时说明有读者(们)正在读, 且有1个写者的请求已收到但尚未响应
        if(count < 0) {
            // 等待读者(们)读完
            while(count < 0) {
                msgrcv(quest_id, &msg_arg, sizeof(msg_arg.mid), FINISHED, 0); // 以阻塞方式接收消息
                count++;
                printf("%d reader finish\n", msg_arg.mid);
            }
            // count == 0
            if(wr_mid_next) {  // 有需要延迟处理的对写者的响应
                msg_arg.mtype = msg_arg.mid = wr_mid_next;
                msgsnd(respond_id, &msg_arg, sizeof(msg_arg.mid), 0);
                printf("%d quest write\n", wr_mid_next);
            }
        }

    }

    return EXIT_SUCCESS;
}
