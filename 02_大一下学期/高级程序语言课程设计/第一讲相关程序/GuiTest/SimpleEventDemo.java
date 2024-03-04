package GuiTest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

public class SimpleEventDemo extends JFrame {
	public SimpleEventDemo() {
    JButton jbtOK = new JButton("OK");
    JButton jbt1 = new JButton("SORRY");
    setLayout(new FlowLayout());
    add(jbtOK);
    add(jbt1);
    ActionListener listener = new OKListener();
    jbtOK.addActionListener(listener);
  }
  /** Main method */
  public static void main(String[] args) {
    JFrame frame = new SimpleEventDemo();
    frame.setTitle("SimpleEventDemo");
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(100, 80);
    frame.setVisible(true);
  }
}

class OKListener implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
		System.out.println("it is ok !");
		}
}


