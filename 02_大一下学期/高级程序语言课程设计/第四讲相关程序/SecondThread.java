package ThreadTest;

public class SecondThread {
	public static void main(String args[]) {
		ThreadTest r = new ThreadTest();
		Thread t = new Thread(r);
		t.start();
	}
}

class ThreadTest implements Runnable {
	int i;

	public void run() {
		i = 0;
		while (true) {
			System.out.println("Hello " + i++);
			if (i == 50) {
				break;
			}
		}
	}
}
