/**  
 * @Title:  JTabbedPaneDemo.java
 * @Package GuiTest
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Dangzhang
 * @date  2020-3-1 下午2:55:21
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package GuiTest;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * @ClassName: JTabbedPaneDemo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Dangzhang
 * @date 2020-3-1 下午2:55:21
 * 
 */
public class JTabbedPaneDemo
{
    public static void main(String args[])
    {
        MyWin mw = new MyWin();
    }
}

class MyWin extends JFrame
{
    JTabbedPane p;
    Icon icon[];
    String ImageName[] =
    { "src\\images\\hardware1.jpg", "src\\images\\hardware2.jpg",
            "src\\images\\hardware3.jpg", "src\\images\\hardware4.jpg",
            "src\\images\\hardware5.jpg" };

    public MyWin()
    {
        setBounds(300, 300, 800, 800);
        icon = new Icon[ImageName.length];
        for (int i = 0; i < ImageName.length; i++)
            icon[i] = new ImageIcon(ImageName[i]);
        p = new JTabbedPane(JTabbedPane.TOP);
        for (int i = 0; i < icon.length; i++)
        {
            int m = i + 1;
            p.add("观看第" + m + "张图片", new JButton(icon[i]));
        }
        p.validate();
        add(p, BorderLayout.CENTER);
        validate();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
