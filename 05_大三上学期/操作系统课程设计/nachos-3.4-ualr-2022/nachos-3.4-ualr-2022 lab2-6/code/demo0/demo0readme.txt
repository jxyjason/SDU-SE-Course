demo0 3个Nachos线程轮流执行
2022.5.8

本目录演示如何在新的文件夹内修改一部分Nachos内核的源代码文件及Makefile，而不修改Nachos原来的代码文件。


code/demo0对原Nachos做了如下修改：

1. 对Thread类增加Println方法，功能为打印线程名并回车(Thread类原有Print方法，功能为打印线程名但无回车)。为此修改了thread.h和thread.cc两个文件。

2. 修改了threadtest.cc中的ThreadTest()函数。修改后的ThreadTest()会Fork两个线程(加上Nachos原有的默认线程共3个线程)，调用Println方法打印线程名，并在Nachos的默认线程调度算法下按指定顺序执行。

3. 在Makefile.local中的INCPATH中增加-I-选项。


可输入下面的命令行，进行测试：
./nachos
