package InterTest.SameMethod;

public class Callee2 extends MyIncrement

{
	private int i = 0;
	private void incr()
	{
		i++;
		System.out.println(i);
	}

	private class Closure implements Incrementable
	{
		public void increment()
		{
			incr();
		}
	}

	Incrementable getCallbackReference()
	{
		return new Closure();
	}
}
