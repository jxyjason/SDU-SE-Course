package Teacher;

import Student.AnswerConn;
import User.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TeacherMarkChoose extends JFrame {
    public TeacherMarkChoose() throws SQLException {
        super("学生选择");
        setSize(700, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(350,50);
        init();
        addListener();
    }

    Font font1 = new Font("仿宋",Font.BOLD,17);

    JPanel jPanel = new JPanel(null);
    JLabel tipLabel = new JLabel("请选择要批改的学生：");
    JButton stuBut[] = new JButton[getStuNum()];
    JButton backBut = new JButton("返回");

    private DBConnection dbConn = null;
    private PaperConn ppConn = null;
    private ResultSet rs = null;
    private Connection conn = null;
    private Statement stmt = null;
    private AnswerConn answerConn = null;



    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/mark.jpg");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());

        tipLabel.setForeground(Color.BLACK);
        tipLabel.setFont(font1);

        backBut.setFont(font1);
        backBut.setBackground(Color.CYAN);

    }

    public void init() throws SQLException {
        beautify();

        tipLabel.setBounds(60,30,300,25);

        nameButton();
        for (int i=0;i<stuBut.length;i++){
            stuBut[i].setBounds(280,70+i*50,120,25);
            jPanel.add(stuBut[i]);
        }
        for (int i=0;i<stuBut.length;i++){
            stuBut[i].setFont(font1);
            stuBut[i].setBackground(Color.CYAN);
        }
        backBut.setBounds(540,700,120,25);

        jPanel.add(tipLabel);
        jPanel.add(backBut);
        setContentPane(jPanel);
        jPanel.updateUI();
    }

    public void nameButton() throws SQLException {
        dbConn = new DBConnection();
        conn = dbConn.getConnection();
        stmt = conn.createStatement();
        String sql = "SELECT * FROM user.new_table WHERE isTeacher = 0";
        rs = stmt.executeQuery(sql);
        int i = 0;
        while (rs.next()){
            stuBut[i] = new JButton(rs.getString(2));
            i++;
        }
        rs.close();
        stmt.close();
        conn.close();
    }

    public void addListener(){
        for (int i=0;i<stuBut.length;i++){
            int finalI = i;
            stuBut[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (isHasFinish(stuBut[finalI].getText())) {
                            new MarkPaper(stuBut[finalI].getText());
                            dispose();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            });
        }

        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TeacherGuiChoose().setVisible(true);
                dispose();
            }
        });

    }

    public boolean isHasFinish(String name) throws SQLException {
        answerConn = new AnswerConn();
        conn = answerConn.getConnection();
        stmt = conn.createStatement();
        String sql = "SELECT * FROM studentanswer."+name+" WHERE type=3";
        Boolean result = true;
        try {
            ResultSet rs = stmt.executeQuery(sql);
        }catch(SQLSyntaxErrorException e){
            result = false;
            JOptionPane.showMessageDialog(null, "该学生还未完成试卷",
                    "提示",2);
        }
        return result;
    }

    public int getStuNum() throws SQLException {
        dbConn = new DBConnection();
        conn = dbConn.getConnection();
        stmt = conn.createStatement();
        String sql = "SELECT * FROM user.new_table WHERE isTeacher = 0";
        rs = stmt.executeQuery(sql);
        int i = 0;
        while (rs.next()){
            i++;
        }
        return i;
    }


    public static void main(String[] args) throws SQLException {
        new TeacherMarkChoose();
    }







}
