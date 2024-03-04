/*
 * 输入：给定m个人对n个项目按排序的投票。
输出：
1）确定其中是否隐含有孔多塞悖论（涉及到在有向图上尝试节点的拓扑排序）。
2）如果没有，就直接给出群体序，如果有，就按照一个特定的属性序，指出哪些投票是不满足单峰性质的，认为它们是“废票”，剔除后按照中位项定理给出群体排序。

 */

#include <iostream>
#include "stack"
using namespace std;


//喜好矩阵L:Lij表示第i个人对于选项j的优先级（1~j）
//根据输入的n个人（行）对m个选项（列）的nxm喜好矩阵L，构造出mxm矩阵P：Pij=1表示选项i优先级>选项j
void getPriority(int ** L,int ** P,int n,int m){
    //初始化P
    for (int i = 0; i < m; ++i) {
        for (int j = 0; j < m; ++j) {
            P[i][j] = 0;
        }
    }

    for (int i = 0; i < m; ++i) {
        for (int j = i+1; j < m; ++j) {
            //比较物品i和j
            int votes = 0;
            for (int k = 0; k < n; ++k) {
                //如果i优先于j,votes++;j优先于i,votes--;
                //最终votes>0,i win;votes<0,j win
                if (L[k][i]<L[k][j])votes++;
                else if(L[k][i]>L[k][j])votes--;
            }
            if (votes>0){
                P[i][j] = 1;
                P[j][i] = -1;
            }else if (votes<0){
                P[i][j] = -1;
                P[j][i] = 1;
            }
        }
    }
}


//判断是否含有孔多塞悖论：图中是否可以构造出拓扑序列---有向图是否五环
//每次找一个入度为零的点，将所有和它相连的点的入度减一（删除相连的边），重复此步骤（有n个点，每次选择一个入度为0的点，进行n次）。
// 如果这时还有入度不为零的点，证明有环，输出NO，反之输出YES。
//true表示含有悖论，需要删掉不满足单峰性质的
bool ifCondorcet(int **P,int item){
    //计算每个顶点的度数
    int * nodeDegree = new int[item];
    for (int i = 0; i < item; ++i) {
        nodeDegree[i] = 0;
        for (int j = 0; j < item; ++j) {
            if (P[j][i]==1)
                nodeDegree[i]++;
        }
    }

    stack<int> zeroDegreeNode;
    for (int i = 0; i < item; ++i) {
        if (nodeDegree[i]==0)zeroDegreeNode.push(i);
    }
    while (!zeroDegreeNode.empty()){
        int theNode = zeroDegreeNode.top();
        nodeDegree[theNode]=-1;//避免此点被再次入栈
        zeroDegreeNode.pop();
        for (int i = 0; i < item; ++i) {
            if (P[theNode][i]==1)
                nodeDegree[i]--;
        }
        for (int i = 0; i < item; ++i) {
            if (nodeDegree[i]==0)
                zeroDegreeNode.push(i);
        }
    }

    for (int i = 0; i < item; ++i) {
        if (nodeDegree[i]>=0)return true;
    }
    return false;
}

//根据表L找出不满足单峰性质的人并将其内容全改为-1
//假设items的某性质权值按照从高到低的顺序排列（矩阵中编号小的items权值大）
void deleteNoSingle(int **L,int people,int item){
    //对每个人分析
    for (int i = 0; i < people; ++i) {
        for (int j = 0; j < item; ++j) {
            for (int k = j+1; k < item; ++k) {
                if (L[i][j]==1||L[i][k]==1)
                if ((abs(k-j)>abs(L[i][k]-L[i][j]))){
                    for (int l = 0; l < item; ++l) {
                        L[i][l] = -1;
                    }
                }
            }
        }
    }
}


//给出群体排序
int * sortRes(int ** L,int item,int people){
    int * result = new int[item];
    for (int i = 0; i < item; ++i) {
        int theNum = 0;
        for (int j = 0; j < people; ++j) {
            if (L[j][i]>0)theNum+=L[j][i];
        }
        result[i] = theNum;
    }

    return result;
}






int main() {

    //初始化L和P
    int people,item;
    cout << "people nums and item nums:" << endl;
    cin >> people >> item;
    int ** L = new int *[people];
    for (int i = 0; i < people; ++i) {
        L[i] = new int[item];
    }
    int ** P = new int *[item];
    for (int i = 0; i < item; ++i) {
        P[i] = new int[item];
    }
    cout << "people like:"<<endl;
    for (int i = 0; i < people; ++i) {
        for (int j = 0; j < item; ++j) {
            cin >> L[i][j];
        }
    }



    getPriority(L,P,people,item);
    cout << ifCondorcet(P,item)<<endl;
    if (ifCondorcet(P,item))deleteNoSingle(L,people,item);
    for (int i = 0; i < people; ++i) {
        for (int j = 0; j < item; ++j) {
            cout << L[i][j]<<",";
        }
        cout << endl;
    }


    cout << "group result:"<<endl;
    int * result = new int[item];
    result = sortRes(L,item,people);
    for (int i = 0; i < item; ++i) {
        cout << result[i]<<",";
    }

    return 0;
}
