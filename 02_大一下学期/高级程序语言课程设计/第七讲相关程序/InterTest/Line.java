package InterTest;

public class Line                        	//直线类，外部类
{
    protected Point p1,p2;             //直线的起点和终点
    protected class Point                //点类，内部类
    {
        protected int x,y;                	//内部类的成员变量
        protected Point(int x,int y)  //内部类的构造方法
        {
            this.x = x;
            this.y = y;
        }
    }
}

