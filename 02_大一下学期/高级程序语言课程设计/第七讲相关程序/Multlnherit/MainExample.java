package InterTest.MultInherit;

public class MainExample {

	private class test1 extends Example1

	{
		public String name() {
			return super.name();
		}
	}

	private class test2 extends Example2 {
		public int age() {
			return super.age();
		}

	}

	public String name() {
		return new test1().name();
	}

	public int age() {
		return new test2().age();
	}

	public static void main(String args[]) {
		MainExample mi = new MainExample();
		System.out.println("ĞÕÃû:" + mi.name());
		System.out.println("ÄêÁä:" + mi.age());
	}
}
