package GuiTest;

import java.awt.Container;

import javax.swing.*;
public class WindowClosingDemo
{
  public static final int WIDTH = 300;
  public static final int HEIGHT = 200;

  public static void main(String[] args)
  {
    JFrame f = new JFrame();
    f.setSize(WIDTH, HEIGHT);
   
    Container con=f.getContentPane();
	JPanel pan=new JPanel();
	JLabel myLabel = new JLabel("´°¿Ú¹Ø±Õ³ÌÐò");
	pan.add(myLabel);
	
	con.add(pan);

    WindowDestroyer myListener = new WindowDestroyer();
    f.addWindowListener(myListener);

    f.setVisible(true);
  }
}
