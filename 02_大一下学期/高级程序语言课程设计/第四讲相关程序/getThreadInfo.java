package ThreadTest;

public class getThreadInfo {
    public static void main(String args[ ]) {
		String name;	 
		int p;
		Thread curr; 
		curr=Thread.currentThread( ); 
		System.out.println("当前线程: "+curr);
		name=curr.getName( );
		p=curr.getPriority( );
		System.out.println("线程名: "+name);
		System.out.println("优先级 :"+p);
	}

}
