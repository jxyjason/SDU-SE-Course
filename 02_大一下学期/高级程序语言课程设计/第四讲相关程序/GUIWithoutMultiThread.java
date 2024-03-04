package ThreadTest;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;

public class GUIWithoutMultiThread extends JFrame {
	private int count = 0;
	private JButton start = new JButton("开始"),
			onOff = new JButton("结束");
	private JTextField t = new JTextField(10);
	private boolean runFlag = true;

	public GUIWithoutMultiThread() {
		setSize(400, 100);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(t);
		start.addActionListener(new StartL());
		this.add(start);
		onOff.addActionListener(new OnOffL());
		this.add(onOff);
	}

	public void go() {
		while (true) { // 无限循环
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
			}
			if (runFlag)// 同一个线程，文本框不刷新
				t.setText(Integer.toString(count++));
		}
	}

	class StartL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			go();
		}
	}

	class OnOffL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			runFlag = !runFlag;
		} // 同一个线程，没机会执行
	}

	public static void main(String[] args) {
		new GUIWithoutMultiThread().setVisible(true);
	}
}
