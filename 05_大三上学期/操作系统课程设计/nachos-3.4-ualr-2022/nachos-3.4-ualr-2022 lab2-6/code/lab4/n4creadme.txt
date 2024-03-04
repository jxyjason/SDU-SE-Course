Lab4 基本文件系统扩展
2022.5.14

lab4c：
  在OpenFile类的析构函数~OpenFile中，判断文件长度改变时自动写回文件头。

  FileHeader的构造函数中增加了：
    memset(dataSectors, 0, sizeof(dataSectors));


可输入下面的命令行，进行测试：
reset
rm -f DISK
./n4c -f
./n4c -cp test/big big
./n4c -D
ls --full-time test/big    # 对比检查Nachos文件big的修改时间应与test/big相同
./n4c -ap test/small big
./n4c -D    # 包括检查Nachos文件big的修改时间应为当前时间
./n4c -hap test/small big
./n4c -D
./n4c -ap test/small small
./n4c -nap small small2
./n4c -D    # 包括检查Nachos文件small及small2的修改时间应不是当前时间
./n4c -nap small small2
./n4c -D    # 包括检查Nachos文件small2的修改时间应为当前时间

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

