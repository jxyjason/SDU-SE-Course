package GuiTest;

import java.awt.Component;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupMenuDemo extends MouseAdapter
{
    PopupMenu pm;
    Frame fr;

    public static void main(String[] args)
    {
        PopupMenuDemo frame = new PopupMenuDemo();
    }

    public PopupMenuDemo()
    {
        fr = new Frame();
        fr.setSize(500, 500);
        pm = new PopupMenu("Popup");
        MenuItem s = new MenuItem("Save");
        MenuItem l = new MenuItem("Load");
        pm.add(s);
        pm.add(l);
        fr.add(pm);
        fr.addMouseListener(this);
        fr.setVisible(true);
    }

    public void mouseReleased(MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            pm.show((Component) e.getSource(), e.getX(), e.getY());
        }
    }
}
