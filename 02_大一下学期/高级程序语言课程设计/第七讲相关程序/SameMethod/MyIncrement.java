package InterTest.SameMethod;

public class MyIncrement {
	public void increment() {
		System.out.println("Other increment()");
	}

	static void f(MyIncrement f) {
		f.increment();
	}

}
