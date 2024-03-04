package GuiTest;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener extends JFrame implements ActionListener {
	private JButton button;

	public MyActionListener() {
		button = new JButton("¿ªÊ¼");
		add(button);
		button.addActionListener(this);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == button) {
			System.out.println("Start¡­");
		}
	}

	/** Main method */
	public static void main(String[] args) {
		JFrame frame = new MyActionListener();
		frame.setTitle("MyActionListener");
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
}

