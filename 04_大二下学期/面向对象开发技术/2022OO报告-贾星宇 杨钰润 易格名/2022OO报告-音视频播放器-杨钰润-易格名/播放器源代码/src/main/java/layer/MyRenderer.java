package layer;

import javax.swing.*;
import java.awt.*;

public class MyRenderer extends JScrollPane implements ListCellRenderer{
    private String name;
    private ImageIcon icon;
    //记录背景色
    private Color backGround;
    //记录前景色
    private Color foreGround;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        //重置成员变量
        this.name=value.toString();
        this.icon= new ImageIcon("img\\"+name+".png");
        this.backGround= isSelected? new Color(255,255,255,50):new Color(255,255,255,0);//list.getBackground();
        this.foreGround= isSelected? Color.white:Color.white;//list.getForeground();
        return this;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,60);
    }
    //绘制列表内容
    @Override
    public void paint(Graphics g) {
        int imageWidth=icon.getImage().getWidth(null);
        int imageHeight=icon.getImage().getHeight(null);
        //填充背景矩形
        g.setColor(backGround);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        //绘制头像
        g.drawImage(icon.getImage(),this.getWidth()/2-imageWidth/2,10,null);
        //绘制昵称
        //设置前景色
        g.setColor(foreGround);
        g.setFont(new java.awt.Font("FZFW ZhenZhu Tis L", 0, 20));
        g.drawString(this.name,this.getWidth()/2-this.name.length()*10/2,10+imageHeight+20);
    }
}
