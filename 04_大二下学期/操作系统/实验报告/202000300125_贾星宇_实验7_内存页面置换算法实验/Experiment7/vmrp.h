/*
* Filename : vmrp.h
* copyright : (C) 2006 by zhonghonglie
* Function : 声明虚拟内存页置换类
*/
using namespace std;
#include <iostream>
#include <iomanip>
#include <malloc.h>

class Replace{
public:
    Replace();
    ~Replace();
    void InitSpace(char * MethodName); //初始化页号记录
    void Report(void); // 报告算法执行情况
    void Fifo(void); //先进先出算法
    void Lru(void); //最近最旧未用算法
    void Clock(void); //时钟(二次机会）置换算法
    void Eclock(void); //增强二次机会置换算法
    void Lfu(void); //最不经常使用置换算法
    void Mfu(void); //最经常使用置换算法

private:
    int * ReferencePage ; //存放要访问到的页号
    int * EliminatePage ; //存放淘汰页号
    int * PageFrames ; //存放当前正在实存中的页号
    int PageNumber; //访问页数
    int FrameNumber; //实存帧数
    int FaultNumber; //失败页数
};
