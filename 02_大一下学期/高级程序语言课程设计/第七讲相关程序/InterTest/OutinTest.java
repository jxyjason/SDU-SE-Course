package InterTest;

class Outer {
	class Inter // 创建非静态内部类
	{
		int i = 5; // 内部类成员
	}

	public void myVoid() // 外部类成员
	{
		Inter n = new Inter(); // 创建一个内部类对象
		int ii = n.i; // 访问内部类成员
		System.out.println("内部类的变量值为：" + ii);
	}
}

public class OutinTest {
	public static void main(String args[]) {
		Outer outer = new Outer();
		outer.myVoid();
	}
}
