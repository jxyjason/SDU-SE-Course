package InterTest;
class Outer6 {
	static class Inter // 创建静态内部类
	{
		int i = 5; // 内部类成员
	}

	public void myVoid() // 外部类成员
	{
		Inter in = new Inter(); // 创建一个内部类对象
		int ii = in.i; // 访问内部类成员
		System.out.println("静态内部类的变量值为：" + ii);
	}
}

public class OutinTest6{
	public static void main(String args[]) {
		Outer ou = new Outer();
		ou.myVoid();
	}
}
