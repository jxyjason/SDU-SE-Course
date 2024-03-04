#include <iostream>
using namespace std;

//得到相邻的a节点数目
int getNeiborA(int **graph,char *things,int length,int theNode){
    int result = 0;
    for (int i = 0; i < length; ++i) {
        if (graph[theNode][i]==1&&things[i]=='a')result++;
    }
    return result;
}


//得到相邻的b节点数目
int getNeiborB(int **graph,char *things,int length,int theNode){
    int result = 0;
    for (int i = 0; i < length; ++i) {
        if (graph[theNode][i]==1&&things[i]=='b')result++;
    }
    return result;
}


//更新节点,如果进入且更新了返回true
bool updateOneNode(int **graph,char *things,int length,int theNode){
    double a = getNeiborA(graph,things,length,theNode);
    double b = getNeiborB(graph,things,length,theNode);
    if ((double )(b/(a+b))>=0.4){
        things[theNode]='b';
        return true;
    }
    return false;
}


int main() {

    //利用邻接矩阵存储有向图
    //5
    //0 1 1 1 0 1 0 0 1 1 1 0 0 1 0 1 1 1 0 1 0 1 0 1 0 b b a a a
    int length;
    cin >> length;
    int ** graphMartix = new int*[length];

    for (int i = 0; i < length; ++i) {
        graphMartix[i] = new int[length];
    }

    //创建图
    for (int i = 0; i < length; ++i) {
        for (int j = 0; j < length; ++j) {
            cin >> graphMartix[i][j];
        }
    }

    //创建节点--事物对应关系
    char * things = new char[length];
    for (int i = 0; i < length; ++i) {
        cin >> things[i];
    }

    //一次次更新节点
    while (true){
        int flag = 0;

        for (int i = 0; i < length; ++i) {
            if (things[i]=='a'){
                if (updateOneNode(graphMartix,things,length,i)) flag=1;
            }
        }

        for (int i = 0; i < length; ++i) {
            cout << things[i]<<", ";
        }
        cout << endl;

        if (flag==0)break;
    }

    return 0;
}
