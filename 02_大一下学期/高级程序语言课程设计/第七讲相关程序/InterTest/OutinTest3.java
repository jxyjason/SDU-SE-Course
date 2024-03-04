package InterTest;

class Outer3 {
	int i = 8; // 外部类成员变量
	class Inter // 创建非静态内部类
	{
		public void myVoid() // 内部类成员变量
		{
			System.out.println("外部类中的成员变量值为：" + i);
		}
	}
}

public class OutinTest3 {
	public static void main(String args[]) {
		Outer3 ou = new Outer3(); // 创建外部类对象
		Outer3.Inter oi2 = ou.new Inter();// 创建内部类对象
		oi2.myVoid(); // 调用内部类中成员
	}
}