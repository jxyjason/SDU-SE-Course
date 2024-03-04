package GuiTest;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SimpleMouseEventDemo extends JFrame {
	JLabel label = new JLabel("在控制栏中显示鼠标的移动、点击等操作");

	public SimpleMouseEventDemo() {
		setSize(300, 300);
		setLocation(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyMouseAdapter myAdapter=new MyMouseAdapter();
		this.addMouseListener(myAdapter);
		this.addMouseMotionListener(myAdapter);
		this.addMouseWheelListener(myAdapter);
		setLayout(new BorderLayout());
		add(label, BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] args) {
		SimpleMouseEventDemo frame = new SimpleMouseEventDemo();
	 
	}
}