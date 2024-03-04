## 文件目录

本实验最终文件目录如下所示：

```
pivot
├─ pivot.c
├─ uniformvector-2dim-5h.txt
└─ uniformvector-4dim-1h.txt
```

## 运行环境

本实验采用Pthread进行优化，运行代码的服务器主机应该支持Pthread程序的运行。

本实验的运行环境应该达到或者能够兼容以下条件：

```
Linux version 4.15.0-76-generic (buildd@lcy01-amd64-029) 
gcc version 7.5.0 (Ubuntu 7.5.0-3ubuntu1~18.04) 
```

## 编译和运行指令

在运行指令之前，需要确保文件目录如下所示：

```
pivot
├─ pivot.c
├─ uniformvector-2dim-5h.txt
└─ uniformvector-4dim-1h.txt
```

其中代码文件和输入数据的文件放在同一个目录下，进入该文件夹所在的目录，如果要修改输入数据的txt文件，请修改 ```pivot.c``` 文件的第 ```212``` 行，如下所示：

````c
char* filename = (char*)"uniformvector-2dim-5h.txt";
````

修改完需输入的txt文件之后，运行如下指令：

```sh
gcc -O3 pivot.c -lm -o pivot -lpthread
```

```pivot.c``` 源文件

```pivot``` 目标生成文件

```-O3``` 本实验采用```O3``` 经过测试，其他编译优化选项的优化效果均不如```O3``` ，在 ```gcc version 7.5.0 (Ubuntu 7.5.0-3ubuntu1~18.04) ``` 环境下支持```O3``` 优化选项

在生成可执行文件```pivot```  之后，直接运行该文件即可：

```
./pivot
```

然后需要输入运行的线程数，这里以64线程为例：

```
number of threads: 64
```

当运行不同的线程数目是，只需要在命令行窗口输入不同的 ```number of threads``` 即可。

**本实验经过多次测试，不一定在第一次运行时就可以达到最优结果，对于每一个输入的线程数目 ```number of threads``` ，均需要通过多轮测试，取最好的结果作为当前  ```number of threads``` 下的测试结果。**

本小组采用的测试环境下 ```64``` 线程取得了最优秀的效果。





