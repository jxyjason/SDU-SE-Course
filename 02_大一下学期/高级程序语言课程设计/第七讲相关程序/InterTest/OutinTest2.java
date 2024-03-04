package InterTest;

class Outer2 {
	class Inter // 创建非静态内部类
	{
		int i = 5; // 内部类成员
		int ii = 6;
	}
}

public class OutinTest2 {
	public static void main(String args[]) {
		Outer2.Inter oi1 = new Outer2().new Inter();
		Outer2 o = new Outer2();
		Outer2.Inter oi2 = o.new Inter();
		System.out.println("内部类中的变量i的值为：" + oi1.i);
		System.out.println("内部类中的变量ii的值为：" + oi2.ii);
	}
}
