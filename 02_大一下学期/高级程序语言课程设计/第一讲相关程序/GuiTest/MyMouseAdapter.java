package GuiTest;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MyMouseAdapter extends MouseAdapter {

	public void mouseClicked(MouseEvent event) {
		System.out.println("鼠标在" + event.getX() + "," + event.getY() + "进行了点击");
	}

	public void mouseMoved(MouseEvent event) {
		System.out.println("鼠标移动到了" + event.getX() + "," + event.getY());
	}

	public void mouseWheelMoved(MouseWheelEvent event) {
		System.out.println("鼠标滚轮进行了滚动");
	}

}
