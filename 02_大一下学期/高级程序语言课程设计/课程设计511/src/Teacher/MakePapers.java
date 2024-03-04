package Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/*
每套试卷25道题目
15道单选题*每道2分=30分
5道多选题每道4分=20分（选不全得2分）
5道主观题*每道10分=50分
 */
public class MakePapers extends JFrame {
    public static void main(String[] args) {
        new MakePapers();
    }
    public MakePapers(){
        setTitle("组卷方式选择");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,400);
        setLocation(450,320);
        init();
        addListener();
    }

    Font font = new Font("仿宋",Font.BOLD,27);
    Font font1 = new Font("仿宋",Font.BOLD,17);

    JPanel jPanel = new JPanel(null);
    JLabel jLabel = new JLabel("请选择组卷方式：");
    JButton humButton = new JButton("人工组卷");
    JButton autoButton = new JButton("自动组卷");
    JButton backBut = new JButton("返回");

    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/login.png");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());

        jLabel.setForeground(Color.BLACK);
        jLabel.setFont(font);
        humButton.setFont(font1);
        humButton.setBackground(Color.CYAN);
        autoButton.setFont(font1);
        autoButton.setBackground(Color.CYAN);
        backBut.setFont(font1);
        backBut.setBackground(Color.CYAN);
    }


    public void init(){
        beautify();

        jLabel.setBounds(100,40,300,90);
        humButton.setBounds(100,160,120,25);
        autoButton.setBounds(250,160,120,25);
        backBut.setBounds(340,300,120,25);

        jPanel.add(jLabel);
        jPanel.add(humButton);
        jPanel.add(autoButton);
        jPanel.add(backBut);
        setContentPane(jPanel);
        jPanel.updateUI();
    }

    public void addListener(){
        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TeacherGuiChoose();
                dispose();
            }
        });

        humButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HumMakePaper();
                dispose();
            }
        });

        autoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AutoMakePaper();
                dispose();
            }
        });
    }




}
