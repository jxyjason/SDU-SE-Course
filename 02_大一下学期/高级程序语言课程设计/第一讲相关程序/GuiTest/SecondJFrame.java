package GuiTest;

import java.awt.Image;

import javax.swing.*;

public class SecondJFrame extends JFrame {
	public  SecondJFrame() {
      super("My Second JFrame");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setExtendedState(MAXIMIZED_BOTH);
      setVisible(true);

   }

	public static void main(String[] args) {
		SecondJFrame f = new SecondJFrame();
	}
}
