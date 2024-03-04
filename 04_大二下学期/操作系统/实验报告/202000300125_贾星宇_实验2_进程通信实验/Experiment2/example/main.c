/*
* Filename : ppipe.c
* copyright : (C) 2006 by zhanghonglie
* Function : 利用管道实现在父子进程间传递整数
*/
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
int main(int argc, char *argv[])
{
int pid; //进程号
int pipe1[2]; //存放第一个无名管道标号
int pipe2[2]; //存放第二个无名管道标号
int x; // 存放要传递的整数
//使用 pipe()系统调用建立两个无名管道。建立不成功程序退出，执行终止
if(pipe(pipe1) < 0){
perror("pipe not create");
exit(EXIT_FAILURE);
}
if(pipe(pipe2) < 0){
perror("pipe not create");
exit(EXIT_FAILURE);
}
//使用 fork()系统调用建立子进程,建立不成功程序退出，执行终止
if((pid=fork()) <0){
perror("process not create");
exit(EXIT_FAILURE);
}
//子进程号等于 0 表示子进程在执行,
else if(pid == 0){
//子进程负责从管道 1 的 0 端读,管道 2 的 1 端写，
//所以关掉管道 1 的 1 端和管道 2 的 0 端。
close(pipe1[1]);
close(pipe2[0]);
//每次循环从管道 1 的 0 端读一个整数放入变量 X 中,
//并对 X 加 1 后写入管道 2 的 1 端，直到 X 大于 10
do{
read(pipe1[0],&x,sizeof(int));
printf("child %d read: %d\n",getpid(),x++);
write(pipe2[1],&x,sizeof(int));
}while( x<=9 );
//读写完成后,关闭管道
close(pipe1[0]);
close(pipe2[1]);
//子进程执行结束
exit(EXIT_SUCCESS);
}
//子进程号大于 0 表示父进程在执行,
else{
//父进程负责从管道 2 的 0 端读,管道 1 的 1 端写，
//所以关掉管道 1 的 0 端和管道 2 的 1 端。
close(pipe1[0]);
close(pipe2[1]);
x=1;
//每次循环向管道 1 的 1 端写入变量 X 的值,并从
//管道 2 的 0 端读一整数写入 X 再对 X 加 1，直到 X 大于 10
do{
write(pipe1[1],&x,sizeof(int));
read(pipe2[0],&x,sizeof(int));
printf("parent %d read: %d\n",getpid(),x++);
}while(x<=9);
//读写完成后,关闭管道
close(pipe1[1]);
close(pipe2[0]);
}
//父进程执行结束
return EXIT_SUCCESS;
}
