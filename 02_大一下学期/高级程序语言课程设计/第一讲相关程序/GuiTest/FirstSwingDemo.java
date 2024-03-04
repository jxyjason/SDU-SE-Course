package GuiTest;

import javax.swing.*;

public class FirstSwingDemo {
	  public static final int WIDTH = 300;
	  public static final int HEIGHT = 200;

	  public static void main(String[] args)
	  {
	    JFrame myWindow = new JFrame();
	    myWindow.setSize(WIDTH, HEIGHT);
	    JLabel myLabel = new JLabel("请点击按钮");
	    myWindow.add(myLabel);
	    myWindow.setVisible(true);
	  }

}
