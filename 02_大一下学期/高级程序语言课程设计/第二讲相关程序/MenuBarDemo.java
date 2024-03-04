package GuiTest;

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarDemo extends Frame implements ActionListener
{
    public static void main(String[] args)
    {
        MenuBarDemo frame = new MenuBarDemo();
    }

    public MenuBarDemo()
    {
        setSize(400, 200);
        MenuBar mb = new MenuBar();
        Menu m1 = new Menu("File");
        MenuItem mi1 = new MenuItem("New", new MenuShortcut('o')); // 定义时设置快捷键
        mi1.addActionListener(this);
        MenuItem mi2 = new MenuItem("Load");
        mi2.setShortcut(new MenuShortcut('x'));// 单独设置快捷键
        mi2.addActionListener(this);
        MenuItem mi3 = new MenuItem("Save");
        mi3.addActionListener(this);
        MenuItem mi4 = new MenuItem("Quit");
        mi4.addActionListener(this);
        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi4);

        Menu m2 = new Menu("Edit");

        MenuItem edit_copy = new MenuItem("copy");
        edit_copy.addActionListener(this);
        Menu paste = new Menu("paste");// 使用二级菜单
        MenuItem paste_part = new MenuItem("part paste");
        paste_part.addActionListener(this);
        MenuItem paste_all = new MenuItem("part all");
        paste_all.addActionListener(this);
        paste.add(paste_part);
        paste.add(paste_all);
        m2.add(edit_copy);
        m2.add(paste);
        Menu m3 = new Menu("Help");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        setMenuBar(mb);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt)
    {
        String arg = evt.getActionCommand();
        System.out.println(arg);
    }
}
