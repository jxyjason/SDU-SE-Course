import javax.swing.*;
import java.awt.*;

public class Hello extends JFrame {
    public Hello(){
        //对此frame的基本定义
        super("开始");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setExtendedState(6);
        //绘制frame
        init();
        beautify();
        addListener();
    }
//需要的元素
    Font font = new Font("华文行楷",Font.BOLD,40);
    Font font1 = new Font("仿宋",Font.BOLD,60);
    JPanel jPanel = new JPanel(null);
    String words = "欢迎使用JExam——线上智能考试系统";
    JLabel helloLabel = new JLabel(words);
    JLabel jExamLabel = new JLabel("JExam");
    JLabel picLabel = new JLabel();


    //对元素的美化以及插入背景图片
    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/hello.jpg");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());
        helloLabel.setFont(font);
        picLabel.setIcon(new ImageIcon("/IDEA/课程设计/src/images/hello1.jpg"));
        jExamLabel.setFont(font1);
    }
    //将元素加入其中
    public void init(){
        helloLabel.setBounds(355,120,900,200);
        picLabel.setBounds(400,50,100,100);
        jExamLabel.setBounds(600,50,200,100);
        jPanel.add(helloLabel);
        jPanel.add(picLabel);
        jPanel.add(jExamLabel);
        setContentPane(jPanel);
        jPanel.updateUI();
    }
    //添加监听器
    public void addListener(){

    }
//测试
    public static void main(String[] args) {
        new Hello().setVisible(true);
    }
}
