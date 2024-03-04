package GuiTest;

import javax.swing.JFrame;
import java.awt.*;

public class TestGraphics extends JFrame  {

	 
	public static void main(String[] args) {
		TestGraphics gp=new TestGraphics();
		gp.setSize(400,400);
		gp.setVisible(true);
	}
    public void paint(Graphics g){
    	super.paint(g);
    	Color c=Color.BLUE;
      	g.setColor(c);
    	g.fillRect(100,100,50,50);
  
    	//设置文本
		Font f1 = new Font("宋书", Font.BOLD, 28);
		Font f2 = new Font("楷体", Font.BOLD + Font.ITALIC, 22);
    	g.setFont(f1);
    	g.drawString("高级程序设计语言", 100, 300);
    	g.setFont(f2);
    	g.drawString("Java课程设计", 100, 350);
    	
    }
}
