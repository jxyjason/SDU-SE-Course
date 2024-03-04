package ThreadTest;

public class FirstThread extends Thread {
	  private int countDown = 4;
	  private static int threadCount = 0;
	  private int threadNumber = ++threadCount;
	  public FirstThread() {
	    System.out.println("Making " + threadNumber);
	  }
	  public void run() {
	    while(true) {
	      System.out.println("Thread " + 
	        threadNumber + "(" + countDown + ")");
	      if(--countDown == 0) return;
	    }
	  }
	public static void main(String[] args) {
	    for(int i = 0; i < 5; i++)
	      new FirstThread().start();
	    System.out.println("All Threads Started");
	  }
	} 