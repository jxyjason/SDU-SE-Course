
/*
 *
输入：任意极化关系下图的邻接矩阵（注意边有正负）
输出：是否含有负向边圈

所谓“极化关系”，指的是网络中的关系分为“友好”和“敌对”两种，这在人际关系和国际关系的一些特定时期都是显著的。
 在这样的模型中，结构的稳定性是关注的重点，即一个网络结构中的各个关系性质是趋向于不变，
 还是趋向于改变（从友好变为敌对，或者反过来）？落实到计算问题上，就是要检测图中是否存在包含奇数个敌对边（负向边）的圈。

*/



#include <iostream>
#include "queue"
using namespace std;




void begDFS(int **graph,int len,int begNode,int * flag,int * states){
    queue<int> theQueue;
    theQueue.push(begNode);
    flag[begNode] = 1;
    states[begNode] = 0;
    while (theQueue.size()!=0) {
        int targetNode = theQueue.front();
        theQueue.pop();
        for (int i = 0; i < len; ++i)
            if (graph[targetNode][i] == 1 && flag[i] != 1){
                theQueue.push(i);
                flag[i] = 1;
                states[i] = states[targetNode]+1;
            }

    }

}

void dfs(int **graph,int len,int * states){
    int * flag = new int[len];
    for (int i = 0; i < len; ++i) flag[i] = 0;
    for (int i = 0; i < len; ++i) {
        for (int j = 0; j < len; ++j) {
            cout<<graph[i][j]<<" ";
        }
        cout << endl;
    }
    for (int i = 0; i < len; ++i) {
        if (flag[i]==0) begDFS(graph,len,i,flag,states);
    }
}

//检查同级顶点有无相连情况
bool checkIsMeet(int ** graph,int levelLen,int * levelNode){
    for (int i = 0; i < levelLen-1; ++i) {
        for (int j = i+1; j < levelLen; ++j) {
            if (graph[levelNode[i]][levelNode[j]]==1)return false;
        }
    }
    return true;
}


int main() {

    //5
    //0 -1 -1 0 0 -1 0 0 0 0 -1 0 0 -1 -1 0 0 -1 0 -1 0 0 -1 -1 0
    //4
    //0 1 1 0 1 0 0 1 1 0 0 1 0 1 1 0

    int length;
    cin >> length;
    int ** graphMartix = new int*[length];
    int * states = new int[length];



    for (int i = 0; i < length; ++i) {
        graphMartix[i] = new int[length];
    }


    //创建图
    for (int i = 0; i < length; ++i) {
        for (int j = 0; j < length; ++j) {
            cin >> graphMartix[i][j];
        }
    }

    //初始化图(把-1权值的边看作边，其余的皆不看做)
    for (int i = 0; i < length; ++i) {
        for (int j = 0; j < length; ++j) {
            if (graphMartix[i][j] == -1)graphMartix[i][j] = 1;
            else graphMartix[i][j] = 0;
        }
    }

    int result = false;

    //判断每一级
    for (int i = 0; i < length; ++i) {
        int * sameLevel = new int[length];
        for (int j = 0; j < length; ++j) {
            sameLevel[j] = -1;
        }
        int count = 0;
        for (int j = 0; j < length; ++j) {
            if (states[j] == i)sameLevel[count++] = j;
        }
        if (!checkIsMeet(graphMartix, count, sameLevel))result = true;
    }

    if (result) cout << "Contains odd-degree negative edge loops, the structure is unstable!";
    else cout << "It does not contain odd-degree negative edge circles, and this structure is stable!";

    return 0;
}
