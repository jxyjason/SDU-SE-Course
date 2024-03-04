Lab4 基本文件系统扩展
2022.5.14

lab4b：
  在OpenFile类析构函数~OpenFile中自动写回文件头(不判断文件长度或文件头是否已改变)。

  FileHeader的构造函数中增加了：
    memset(dataSectors, 0, sizeof(dataSectors));


可输入下面的命令行，进行测试：
reset
rm -f DISK
./n4b -f
./n4b -cp test/big big
./n4b -D
ls --full-time test/big    # 对比检查Nachos文件big的修改时间应与test/big相同
./n4b -ap test/small big
./n4b -D    # 包括检查Nachos文件big的修改时间应为当前时间
./n4b -hap test/small big
./n4b -D
./n4b -ap test/small small
./n4b -nap small small2
./n4b -D    # 包括检查Nachos文件small及small2的修改时间应不是当前时间
./n4b -nap small small2
./n4b -D    # 包括检查Nachos文件small2的修改时间应为当前时间jhk

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

