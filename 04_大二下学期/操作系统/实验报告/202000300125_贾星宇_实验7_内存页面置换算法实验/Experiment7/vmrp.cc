/*
* Filename : vmrp.cc
* copyright : (C) 2006 by zhonghonglie
* Function : 模拟虚拟内存页置换算法的程序
*/
#include "vmrp.h"

Replace::Replace()
{
    int i;

    //设定总得访问页数,并分配相应的引用页号和淘汰页号记录数组空间
    cout << "Please input page numbers :";
    cin >> PageNumber;
    ReferencePage = new int[sizeof(int) * PageNumber];
    EliminatePage = new int[sizeof(int) * PageNumber];

    //输入引用页号序列(页面走向),初始化引用页数组
    cout << "Please input reference page string :";
    for (i = 0; i < PageNumber; i++)
        cin >> ReferencePage[i]; //引用页暂存引用数组

    //设定内存实页数(帧数),并分配相应的实页号记录数组空间(页号栈)
    cout << "Please input page frames :";
    cin >> FrameNumber;
    PageFrames = new int[sizeof(int) * FrameNumber];
}

Replace::~Replace(){
}

void Replace::InitSpace(char * MethodName)
{
    int i;

    cout << endl << MethodName << endl;
    FaultNumber = 0;
    //引用还未开始,-1表示无引用页
    for (i = 0; i < PageNumber; i++)
        EliminatePage[i] = -1;
    for(i = 0; i < FrameNumber; i++)
        PageFrames[i] = -1;
}

//分析统计选择的算法对于当前输入的页面走向的性能
void Replace::Report(void)
{
    //报告淘汰页顺序
    cout << endl << "Eliminate page:";
    for(int i=0; EliminatePage[i]!=-1; i++)
        cout << EliminatePage[i] << " ";

    //报告缺页数和缺页率
    cout << endl << "Number of page faults = " << FaultNumber << endl;
    cout << setw(6) << setprecision(3) ;
    cout << "Rate of page faults = " << 100*(float)FaultNumber/(float)PageNumber <<
    "%" <<endl;
}

//最近最旧未用置换算法
void Replace::Lru(void)
{
    int i,j,k,l,next;

    InitSpace("LRU");
    //循环装入引用页
    for(k = 0, l = 0; k < PageNumber; k++) {
        next = ReferencePage[k];
        //检测引用页当前是否已在实存
        for(i = 0; i < FrameNumber; i++) {
            if(next == PageFrames[i]) {
                //引用页已在实存将其调整到页记录栈顶
                next = PageFrames[i];
                for(j = i; j > 0; j--)
                    PageFrames[j] = PageFrames[j-1];
                PageFrames[0]=next;
                break;
            }
        }

        if(PageFrames[0] == next) {
            //如果引用页已放栈顶，则为不缺页，报告当前内存页号
            for(j = 0; j < FrameNumber; j++)
                if(PageFrames[j]>=0)
                    cout << PageFrames[j] << " ";
            cout << endl;
            continue; //继续装入下一页
        }
        else
            // 如果引用页还未放栈顶，则为缺页，缺页数加1
            FaultNumber++;

        //栈底页号记入淘汰页数组中
        EliminatePage[l] = PageFrames[FrameNumber-1];
        //向下压栈
        for(j = FrameNumber - 1; j > 0; j--)
            PageFrames[j] = PageFrames[j - 1];
        PageFrames[0]=next; //引用页放栈顶
        //报告当前实存中页号
        for(j = 0; j < FrameNumber; j++)
            if(PageFrames[j] >= 0)
                cout << PageFrames[j] << " ";

        //报告当前淘汰的页号
        if(EliminatePage[l] >= 0)
            cout << "->" << EliminatePage[l++] << endl;
        else
           cout << endl;
    }

    //分析统计选择的算法对于当前引用的页面走向的性能
    Report();
}

//先进先出置换算法
void Replace::Fifo(void)
{
    int i,j,k,l,next;

    InitSpace("FIFO");
    //循环装入引用页
    for(k = 0,j = l = 0; k < PageNumber; k++) {
        next = ReferencePage[k];
        //如果引用页已在实存中，报告实存页号
        for(i = 0; i < FrameNumber; i++)
            if(next == PageFrames[i])
                break;

        if(i < FrameNumber) {
            for(i = 0; i < FrameNumber; i++)
                if(PageFrames[i] >= 0)
                    cout << PageFrames[i] << " ";
            cout << endl;
            continue; // 继续引用下一页
        }

        //引用页不在实存中，缺页数加1
        FaultNumber++;
        EliminatePage[l] = PageFrames[j]; //最先入页号记入淘汰页数组
        PageFrames[j] = next; //引用页号放最先入页号处
        j = (j + 1) % FrameNumber; //最先入页号循环下移

        //报告当前实存页号和淘汰页号
        for(i = 0; i < FrameNumber; i++)
            if(PageFrames[i] >= 0)
                cout << PageFrames[i] << " ";

        if(EliminatePage[l] >= 0)
            cout << "->" << EliminatePage[l++] << endl;
        else
            cout << endl;
    }

    //分析统计选择的算法对于当前引用的页面走向的性能
    Report();
}

//未实现的其他页置换算法入口
void Replace::Clock(void)
{
InitSpace("CLOCK");

int pointer = 0;
int i,j,k,l,next;
//shezhiyinyongwei
int * quote = new int[sizeof(int) * FrameNumber];
int quoteCount = 0;

for(int m=0;m<FrameNumber;m++) quote[m] = 0;

for(k = 0,j = l= 0; k < PageNumber; k++) {
        next = ReferencePage[k];
        for(i = 0; i < FrameNumber; i++)
            if(next == PageFrames[i]){
                quote[i]=1;
                break;
            }
        if(i < FrameNumber) {
            for(i = 0; i < FrameNumber; i++)
                if(PageFrames[i] >= 0)
                    cout << PageFrames[i] << " ";
            cout << endl;
            continue; // 继续引用下一页
        }else{
            FaultNumber++;
            while(quote[quoteCount]==1){
                quote[quoteCount]=0;
                quoteCount=(quoteCount+1)%FrameNumber;

            }
            EliminatePage[l] = PageFrames[quoteCount];
            PageFrames[quoteCount] = next;
            quoteCount=(quoteCount+1)%FrameNumber;
            //报告当前实存页号和淘汰页号
        for(i = 0; i < FrameNumber; i++)
            if(PageFrames[i] >= 0)
                cout << PageFrames[i] << " ";

        if(EliminatePage[l] >= 0)
            cout << "->" << EliminatePage[l++] << endl;
        else
            cout << endl;
        }
    }
    Report();
}

void Replace::Eclock (void)
{
InitSpace("ECLOCK");

int pointer = 0;
int i,j,k,l,next;
//shezhiyinyongwei
int * quote = new int[sizeof(int) * FrameNumber];
int * changed = new int[sizeof(int) * FrameNumber];
int quoteCount = 0;
int changeCount = 0;

for(int m=0;m<FrameNumber;m++) {
quote[m] = 0;
changed[m] = 0;
}

for(k = 0,j = l= 0; k < PageNumber; k++) {
        next = ReferencePage[k];
        for(i = 0; i < FrameNumber; i++)
            if(next == PageFrames[i]){
                quote[i]=1;
                break;
            }
            //+if changed then changed[i]=1;


        if(i < FrameNumber) {
            for(i = 0; i < FrameNumber; i++)
                if(PageFrames[i] >= 0)
                    cout << PageFrames[i] << " ";
            cout << endl;
            continue; // 继续引用下一页
        }else{
            FaultNumber++;
            while(quote[quoteCount]==1||changed[changeCount]==1){
                quote[quoteCount]=0;
                quoteCount=(quoteCount+1)%FrameNumber;
                changed[changeCount]=0;
                changeCount = (changeCount+1)%FrameNumber;
            }

            EliminatePage[l] = PageFrames[quoteCount];
            PageFrames[quoteCount] = next;
            quoteCount=(quoteCount+1)%FrameNumber;
            //报告当前实存页号和淘汰页号
        for(i = 0; i < FrameNumber; i++)
            if(PageFrames[i] >= 0)
                cout << PageFrames[i] << " ";

        if(EliminatePage[l] >= 0)
            cout << "->" << EliminatePage[l++] << endl;
        else
            cout << endl;
        }
    }
    Report();
}

void Replace::Lfu(void)
{


InitSpace("LFU");

int pointer = 0;
int i,j,k,l,next;
//shezhiyinyongwei
int * quote = new int[sizeof(int) * FrameNumber];

for(int m=0;m<FrameNumber;m++) quote[m] = 0;

for(k = 0,j = l= 0; k < PageNumber; k++) {
        next = ReferencePage[k];
        for(i = 0; i < FrameNumber; i++)
            if(next == PageFrames[i]){
                quote[i]++;
                break;
            }
        if(i < FrameNumber) {
            for(i = 0; i < FrameNumber; i++)
                if(PageFrames[i] >= 0)
                    cout << PageFrames[i] << " ";
            cout << endl;
            continue; // 继续引用下一页
        }else{
            FaultNumber++;
            int minindex = 0;
            for(i=0;i<FrameNumber;i++){
                if(quote[minindex]>quote[i])minindex=i;
            }
            EliminatePage[l] = PageFrames[minindex];
            PageFrames[minindex] = next;
            quote[minindex]=1;
            //报告当前实存页号和淘汰页号
        for(i = 0; i < FrameNumber; i++)
            if(PageFrames[i] >= 0)
                cout << PageFrames[i] << " ";

        if(EliminatePage[l] >= 0)
            cout << "->" << EliminatePage[l++] << endl;
        else
            cout << endl;
        }
    }
    Report();


}

void Replace::Mfu(void)
{
int pointer = 0;
int i,j,k,l,next;
//shezhiyinyongwei
int * quote = new int[sizeof(int) * FrameNumber];

for(int m=0;m<FrameNumber;m++) quote[m] = 0;

for(k = 0,j = l= 0; k < PageNumber; k++) {
        next = ReferencePage[k];
        for(i = 0; i < FrameNumber; i++)
            if(next == PageFrames[i]){
                quote[i]++;
                break;
            }
        if(i < FrameNumber) {
            for(i = 0; i < FrameNumber; i++)
                if(PageFrames[i] >= 0)
                    cout << PageFrames[i] << " ";
            cout << endl;
            continue; // 继续引用下一页
        }else{
            FaultNumber++;
            int maxindex = 0;
            for(i=0;i<FrameNumber;i++){
                if(quote[maxindex]<quote[i])maxindex=i;
            }
            EliminatePage[l] = PageFrames[maxindex];
            PageFrames[maxindex] = next;
            quote[maxindex]=1;
            //报告当前实存页号和淘汰页号
        for(i = 0; i < FrameNumber; i++)
            if(PageFrames[i] >= 0)
                cout << PageFrames[i] << " ";

        if(EliminatePage[l] >= 0)
            cout << "->" << EliminatePage[l++] << endl;
        else
            cout << endl;
        }
    }
    Report();

}

int main(int argc, char *argv[])
{
    Replace * vmrp = new Replace();
    vmrp->Fifo();
    vmrp->Lru();
    vmrp->Clock();
    vmrp->Eclock();
    vmrp->Lfu();
    vmrp->Mfu();

    return 0;
}
