package layer;

import java.awt.*;
import javax.swing.*;

public class RButton extends JButton {
    public RButton(String label) {
        super(label);
        // 这些声明把按钮扩展为一个圆而不是一个椭圆。
        Dimension size = new Dimension(120,120);
        setPreferredSize(size);
        //这个调用使JButton不画背景，而允许我们画一个圆的背景。
        setContentAreaFilled(false);
        this.setOpaque(false);//设置按钮透明
        this.setBorderPainted(false);//改成false就能隐去边框
    }

    //icon自适应按钮大小
    public void setIcon(String file,JButton com)
    {
        ImageIcon ico=new ImageIcon(file);
        Image temp=ico.getImage().getScaledInstance(com.getWidth(),com.getHeight(),ico.getImage().SCALE_DEFAULT);
        ico=new ImageIcon(temp);
        com.setIcon(ico);
    }
}
