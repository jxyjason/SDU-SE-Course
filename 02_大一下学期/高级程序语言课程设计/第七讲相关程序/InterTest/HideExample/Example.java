package InterTest.HideExample;

public class Example {
	  private class InsideClass implements InterfaceTest

	    {
	         public void test()
	         {
	             System.out.println("这是一个测试");
	         }
	    }

	    public InterfaceTest getIn()
	    {
	        return new InsideClass();
	    }
}
