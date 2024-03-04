package ThreadTest;

public class WithoutSynchronizedTest {
	public static void main(String args[]) {
		UserThread Obj = new UserThread();
		Thread t1 = new Thread(new UserMultThread(Obj, 1));
		Thread t2 = new Thread(new UserMultThread(Obj, 2));
		Thread t3 = new Thread(new UserMultThread(Obj, 3));

		t1.start();
		t2.start();
		t3.start();
	}
}

class UserMultThread implements Runnable {
	UserThread UserObj;
	int num;

	UserMultThread(UserThread o, int n) {
		UserObj = o;
		num = n;
	}

	public  void run() {
		UserObj.Play(num);
	}
}

class UserThread { // 资源冲突
	 void  Play(int n) {
		System.out.println("运行线程 NO：" + n);

		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			System.out.println("线程异常, NO：" + n);
		}

		System.out.println("结束线程 NO：" + n);
	}
}
