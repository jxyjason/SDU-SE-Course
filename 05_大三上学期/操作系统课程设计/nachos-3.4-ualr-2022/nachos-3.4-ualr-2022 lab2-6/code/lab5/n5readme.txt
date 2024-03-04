Lab5 具有二级索引的文件系统
2022.7.15

Nachos系统原有的文件系统只支持单级索引，最大能存储30 * 128 = 3840字节大小的文件。
Lab5设计并实现具有二级索引的文件系统，且文件具有最后修改时间属性，并增加了打印磁盘总体信息的Nachos命令行选项-DI。。

本目录下的可执行程序n5为Lab5的一个参考实现。
其实现的一些具体细节如下：

一级索引项数：29项

二级索引块数：1块
二级索引块中的项数：32项

单个文件最大：(29 + 32) * 128 = 61 * 128 = 7808字节

在FileHeader类的析构函数~FileHeader中，判断文件长度改变时自动写回文件头

在FileHeader的构造函数中增加了：
    memset(dataSectors, 0, sizeof(dataSectors));


可输入下面的命令行，进行测试：
reset
rm -f DISK
./n5 -f
./n5 -DI
./n5 -cp test/huge huge
./n5 -D
ls --full-time test/huge    # 对比检查Nachos文件huge的修改时间应与test/huge相同
./n5 -ap test/huge huge
./n5 -D    # 包括检查Nachos文件huge的修改时间应为当前时间
./n5 -ap test/small huge
./n5 -cp test/small small
./n5 -cp test/medium medium
./n5 -DI
./n5 -r huge
./n5 -r small
./n5 -r medium
./n5 -DI

对自己编写生成的程序，命令行为：
reset
rm -f DISK
./nachos -f
./nachos -DI
./nachos -cp test/huge huge
./nachos -D
ls --full-time test/huge    # 对比检查Nachos文件huge的修改时间应与test/huge相同
./nachos -ap test/huge huge
./nachos -D    # 包括检查Nachos文件huge的修改时间应为当前时间
./nachos -ap test/small huge
./nachos -cp test/small small
./nachos -cp test/medium medium
./nachos -DI
./nachos -r huge
./nachos -r small
./nachos -r medium
./nachos -DI

