package ThreadTest;

public class WithSynchronizedTest {
	public static void main(String args[]) {
		UserThread2 Obj = new UserThread2();
		Thread t1 = new Thread(new UserMultThread2(Obj, 1));
		Thread t2 = new Thread(new UserMultThread2(Obj, 2));
		Thread t3 = new Thread(new UserMultThread2(Obj, 3));

		t1.start();
		t2.start();
		t3.start();
	}
}

class UserMultThread2 implements Runnable {
	UserThread2 UserObj;
	int num;

	UserMultThread2(UserThread2 o, int n) {
		UserObj = o;
		num = n;
	}

	public  void run() {
		synchronized(UserObj) {
		UserObj.Play(num);}
	}
}

class UserThread2 { // 资源冲突
	void Play(int n) {
		System.out.println("运行线程 NO：" + n);

		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			System.out.println("线程异常, NO：" + n);
		}

		System.out.println("结束线程 NO：" + n);
	}
}
