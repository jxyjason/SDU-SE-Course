public class Dog extends Animal {
	public void speak(Animal p) {
		System.out.println("Íô!");
	}

	public void speak(Dog t) {
		System.out.println("ÍôÍô");
	}
	
	
	public static void main(String[]arg) {
		Animal  p1 = new Animal () ;
		Animal  p2 = new Dog () ;
		Dog p3 = new Dog () ;
		 
		p1.speak(  p1  ) ;
		p1.speak(  p2  ) ;
		p1.speak(  p3  ) ;
		 
		p2.speak(  p1  ) ;
		p2.speak(  p2  ) ;
		p2.speak(  p3  ) ;
		 
		p3.speak(  p1  ) ;
		p3.speak(  p2  ) ;
		p3.speak(  p3  ) ;
	}
}