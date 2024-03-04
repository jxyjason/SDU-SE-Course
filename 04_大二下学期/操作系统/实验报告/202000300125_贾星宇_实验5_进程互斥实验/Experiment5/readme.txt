实验五、进程互斥实验的示例程序 修改测试版
2021.5.13
###### 尚待测试，特别是尚未测试是否写者优先，以及是否没有饥饿 ######


主要修改之处：


1. 所有msgrcv及msgsnd函数消息长度参数的修改
比如：
msgsnd(quest_id,&msg_arg,sizeof(msg_arg),0);
改为：
msgsnd(quest_id, &msg_arg, sizeof(msg_arg.mid), 0);

msgrcv的也类似。


2. control.c的主循环逻辑上有问题，修改之。


3. 为reader及writer增加命令行第2个参数，用于模拟两次读或两次写之间的时间间隔


4. 原reader.c主循环最后一条语句，msgsnd的最后一个参数有bug：
msgsnd(quest_id,&msg_arg,sizeof(msg_arg),quest_flg);
改为：
msgsnd(quest_id, &msg_arg, sizeof(msg_arg.mid), 0);


5. 其他微小的修改
包括删除未使用的变量等。
