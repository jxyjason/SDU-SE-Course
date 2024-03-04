#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <wait.h>
//进程自定义的键盘中断信号处理函数
typedef void (*sighandler_t) (int);
void sigcat() { printf("%d Process continue\n", getpid()); }
