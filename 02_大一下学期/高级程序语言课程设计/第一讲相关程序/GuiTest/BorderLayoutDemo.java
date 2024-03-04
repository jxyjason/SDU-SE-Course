package GuiTest;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
public class BorderLayoutDemo extends JFrame {
	private JButton north = new JButton("北");
	private JButton south = new JButton("南");
	private JButton east = new JButton("东");
	private JButton west = new JButton("西");
	private JButton center = new JButton("中");
	public BorderLayoutDemo() {
		setSize(300, 300);
		setLocation(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置布局方式为BorderLayout
		setLayout(new BorderLayout());
		//添加按键，注意设置布局方式之后任何对组件进行设置的方法，例如setSize、setLocation等都会失效
		add(north,BorderLayout.NORTH);
		add(south,BorderLayout.SOUTH);
		add(east,BorderLayout.EAST);
		add(west,BorderLayout.WEST);
		add(center,BorderLayout.CENTER);
		setVisible(true);

	}
    public  static  void  main(String[]  args) {
    	BorderLayoutDemo  f = new BorderLayoutDemo();
  }

}
