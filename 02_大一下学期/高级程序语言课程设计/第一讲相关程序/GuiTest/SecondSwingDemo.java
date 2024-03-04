package GuiTest;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SecondSwingDemo {
    public  static  void  main(String[]  args) {
        JFrame  f = new  JFrame();
        f.setTitle("My First JFrame");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 300);
        f.setVisible(true);
        Container con = f.getContentPane();
        JPanel panel = new JPanel();
        JButton butt = new JButton("Press Me");
        panel.add(butt);
        con.add(panel);

}
}