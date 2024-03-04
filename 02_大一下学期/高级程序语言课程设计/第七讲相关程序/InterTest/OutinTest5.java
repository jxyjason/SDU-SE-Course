package InterTest;

class Outer5 {
	public void myVoid() {
		class Inter // 定义一个局部内部类
		{
			int i = 5; // 局部内部类的成员变量
		}
		Inter in = new Inter();
		System.out.println("局部内部类的成员变量为：" + in.i);
	}
}

public class OutinTest5 {
	public static void main(String args[]) {
		Outer5 o = new Outer5(); // 创建外部类对象
		o.myVoid(); // 调用内部类中成员
	}
}