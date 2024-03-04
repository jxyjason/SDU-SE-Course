Lab2 具有优先级的线程调度
2022.5.9

本目录下的可执行程序n2为Lab2的一个参考实现。
其实现的一些具体参数如下：

线程的优先级priority为0-99，0最高，99最低。
默认优先级为9，可在Thread类的构造函数中设置。

在函数ThreadTest中相继new 3个Thread：t1, t2, t3。
并把t1, t2, t3 的优先级priority依次设置为 1, 2, 3。

主线程main为默认优先级9，无需在函数ThreadTest中设置。

在函数ThreadTest的最后依次执行：
    t1->Fork(SimpleThread, 1);
    t2->Fork(SimpleThread, 2);
    t3->Fork(SimpleThread, 3);
    SimpleThread(0);
