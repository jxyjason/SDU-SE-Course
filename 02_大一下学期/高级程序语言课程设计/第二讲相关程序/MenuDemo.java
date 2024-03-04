package GuiTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuDemo extends JFrame
{

    public MenuDemo()
    {

        JMenuBar bar = new JMenuBar(); // 创建一个空的菜单条
        setJMenuBar(bar);

        JMenu fileMenu = new JMenu("文件");

        JMenuItem newf = new JMenuItem("新建");
        JMenuItem open = new JMenuItem("打开");
        JMenuItem save = new JMenuItem("保存");

        save.setEnabled(false);

        fileMenu.add(newf);
        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(save);

        JMenu editMenu = new JMenu("编辑");
        JMenuItem cut = new JMenuItem("剪切");
        JMenuItem copy = new JMenuItem("复制");
        JMenuItem paste = new JMenuItem("粘贴");
        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        bar.add(fileMenu);
        bar.add(editMenu);

        MenuListener ml = new MenuListener();
        open.addActionListener(ml);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

    }

    public static void main(String[] args)
    {
        MenuDemo md = new MenuDemo();
    }

}

class MenuListener implements ActionListener
{

    public void actionPerformed(ActionEvent arg0)
    {
        System.out.println("点击了”打开“菜单");
    }
}
