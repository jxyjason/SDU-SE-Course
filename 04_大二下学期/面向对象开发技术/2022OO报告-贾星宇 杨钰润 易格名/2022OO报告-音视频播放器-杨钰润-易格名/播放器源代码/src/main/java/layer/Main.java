package layer;

import models.Audio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main extends JFrame implements ActionListener {
    Main audio,video;
    ImageIcon i = new ImageIcon("D:\\UIresources\\InitBkg2.png");
    Image photo = i.getImage();
    RButton btnA, btnV;

    public Main() {
        ImageIcon icon = new ImageIcon("D:\\UIresources\\icon.png");
        this.setIconImage(icon.getImage());//更改窗体图标
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置可点击右上小按钮关闭窗口
        this.setVisible(true);
        this.setLayout(null);
        this.setFocusable(true);
        this.setBounds(new Rectangle(1600, 900));//所有窗体比例均设置为16:9
        this.setLocationRelativeTo(null);//设置窗体居中，且窗体位置不受显示器分辨率改变的影响。
        this.setTitle("Audio & Video Player");
    }

    public void init() {
        JPanel p = new gPanel(photo);
        p.setBounds(this.getBounds());
        p.setLayout(null);
        btnA = new RButton("");
        btnV = new RButton("");
        btnA.setBounds(490, 450, 100, 100);
        btnV.setBounds(920, 450, 100, 100);
        p.add(btnA);
        p.add(btnV);
        this.setContentPane(p);

        btnA.addActionListener(this);
        btnV.addActionListener(this);
    }

    public static void main(String[] args) {
        Main start = new Main();
        start.init();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnA) {

            audio = new LocalAudio(new Audio());
            System.out.println("音乐库");
        } else {
            System.out.println("视频库");
            LocalVideo application = new LocalVideo("video");
            application.initialize();
            application.setVisible(true);

        }
    }
}



