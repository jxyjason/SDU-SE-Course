package InterTest;

class Outer4 {
	int i = 8; // 外部类成员变量

	class Inter // 创建非静态内部类
	{
		int i = 9;
		Outer4 ou = new Outer4();

		public void myVoid() // 内部类成员变量
		{
			System.out.println("内部类中的成员变量值为：" + i);
			System.out.println("外部类中的成员变量值为：" + ou.i);
		}
	}
}

public class OutinTest4 {
	public static void main(String args[]) {
		Outer4 w = new Outer4(); // 创建外部类对象
		Outer4.Inter wn2 = w.new Inter(); // 创建内部类对象
		wn2.myVoid(); // 调用内部类中成员
	}
}
