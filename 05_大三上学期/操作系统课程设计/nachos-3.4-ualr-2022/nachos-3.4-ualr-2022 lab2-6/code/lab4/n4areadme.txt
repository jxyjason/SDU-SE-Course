Lab4 基本文件系统扩展
2022.5.14

lab4a：
  在OpenFile类中，增加WriteBack方法，手动调用此方法写回文件头。

  FileHeader的构造函数中增加了：
    memset(dataSectors, 0, sizeof(dataSectors));


可输入下面的命令行，进行测试：
reset
rm -f DISK
./n4a -f
./n4a -cp test/big big
./n4a -D
ls --full-time test/big    # 对比检查Nachos文件big的修改时间应与test/big相同
./n4a -ap test/small big
./n4a -D    # 包括检查Nachos文件big的修改时间应为当前时间
./n4a -hap test/small big
./n4a -D
./n4a -ap test/small small
./n4a -nap small small2
./n4a -D    # 包括检查Nachos文件small及small2的修改时间应不是当前时间
./n4a -nap small small2
./n4a -D    # 包括检查Nachos文件small2的修改时间应为当前时间

对自己编写生成的程序，命令行为：
reset
rm -f DISK
./nachos -f
./nachos -cp test/big big
./nachos -D
ls --full-time test/big    # 对比检查Nachos文件big的修改时间应与test/big相同
./nachos -ap test/small big
./nachos -D    # 包括检查Nachos文件big的修改时间应为当前时间
./nachos -hap test/small big
./nachos -D
./nachos -ap test/small small
./nachos -nap small small2
./nachos -D    # 包括检查Nachos文件small及small2的修改时间应不是当前时间
./nachos -nap small small2
./nachos -D    # 包括检查Nachos文件small2的修改时间应为当前时间

