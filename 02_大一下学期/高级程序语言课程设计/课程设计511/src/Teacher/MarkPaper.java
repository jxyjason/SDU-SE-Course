package Teacher;

import Student.AnswerConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import Student.*;

public class MarkPaper extends JFrame {
    String name;
    public MarkPaper(){
        super("批改主观题");
        setSize(700, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(350,50);
        init();
        addListener();
        addQuestionFromPaper();
    }

    public MarkPaper(String stuName){
        super("批改主观题");
        name = stuName;
        setSize(700, 800);
        setLocation(350,50);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        addListener();
        addQuestionFromPaper();
    }

    Font font1 = new Font("仿宋",Font.BOLD,17);

    JPanel jPanel = new JPanel(null);
    JLabel idLabel = new JLabel("第1题:");
    JLabel quesLabel = new JLabel("题目：");
    JScrollPane quesPane = new JScrollPane();
    JTextArea quesArea = new JTextArea();
    JLabel standardLabel = new JLabel("标准答案：");
    JScrollPane standardPane = new JScrollPane();
    JTextArea standardArea = new JTextArea();
    JLabel stuAnsLabel = new JLabel("学生答案：");
    JScrollPane stuAnsPane = new JScrollPane();
    JTextArea stuAnsArea = new JTextArea();
    JLabel scoreLabel = new JLabel("得分：");
    JTextField scoreField = new JTextField();
    JButton preBut = new JButton("上一题");
    JButton nextBut = new JButton("下一题");
    JButton saveBut = new JButton("保存批改结果");
    JButton backBut = new JButton("返回");

    private AnswerConn answerConn = null;
    private PaperConn ppConn = null;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;

    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/mark.jpg");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());

        idLabel.setForeground(Color.BLACK);
        idLabel.setFont(font1);
        quesLabel.setForeground(Color.BLACK);
        quesLabel.setFont(font1);
        standardLabel.setForeground(Color.BLACK);
        standardLabel.setFont(font1);
        stuAnsLabel.setForeground(Color.BLACK);
        stuAnsLabel.setFont(font1);
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(font1);
        quesArea.setFont(font1);
        standardArea.setFont(font1);
        stuAnsArea.setFont(font1);

        preBut.setFont(font1);
        preBut.setBackground(Color.CYAN);
        nextBut.setFont(font1);
        nextBut.setBackground(Color.CYAN);
        saveBut.setFont(font1);
        saveBut.setBackground(Color.CYAN);
        backBut.setFont(font1);
        backBut.setBackground(Color.CYAN);

    }

    public void init(){
        beautify();

        idLabel.setBounds(50,30,90,25);
        quesLabel.setBounds(50,80,90,25);
        quesPane.setBounds(140,80,500,150);
        quesPane.setViewportView(quesArea);
        standardLabel.setBounds(50,260,90,25);
        standardPane.setBounds(140,260,500,150);
        standardPane.setViewportView(standardArea);
        stuAnsLabel.setBounds(50,430,90,25);
        stuAnsPane.setBounds(140,430,500,150);
        stuAnsPane.setViewportView(stuAnsArea);
        scoreLabel.setBounds(50,620,90,25);
        scoreField.setBounds(120,620,90,25);
        preBut.setBounds(320,620,120,25);
        nextBut.setBounds(530,620,120,25);
        saveBut.setBounds(320,680,200,25);
        backBut.setBounds(530,680,120,25);


        jPanel.add(idLabel);
        jPanel.add(quesLabel);
        jPanel.add(quesPane);
        jPanel.add(standardLabel);
        jPanel.add(standardPane);
        jPanel.add(stuAnsLabel);
        jPanel.add(stuAnsPane);
        jPanel.add(scoreLabel);
        jPanel.add(scoreField);
        jPanel.add(preBut);
        jPanel.add(nextBut);
        jPanel.add(saveBut);
        jPanel.add(backBut);
        setContentPane(jPanel);
        jPanel.updateUI();

    }

    public void addListener(){
        nextBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveScore();
                    nextQue();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        preBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveScore();
                    preQue();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }            }
        });

        saveBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveScore();
                    JOptionPane.showMessageDialog(null, "批改结果已保存！","提示",1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TeacherMarkChoose();
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void addQuestionFromPaper(){
        ppConn = new PaperConn();//老师的试卷与标准答案
        conn = ppConn.getConnection();
        answerConn  = new AnswerConn();//学生答案
        Connection conn1 = answerConn.getConnection();
        String sql = "SELECT * FROM papers.paper1 where type=3";
        try {
            Statement stmt = conn.createStatement();
            Statement stmt1 = conn1.createStatement();
            ResultSet rs = stmt.executeQuery(sql);//老师试卷
            if (rs.next()){
                int id = rs.getInt(1);
                String sql1 = "SELECT * FROM studentanswer."+name+" where type=3 and id="+id;
                ResultSet rs1 = stmt1.executeQuery(sql1);//学生答案
                if (rs1.next()) {
                    String question = rs.getString(4);
                    idLabel.setText("第 " + id + " 题");
                    quesArea.setText(question);
                    standardArea.setText(rs.getString(5));
                    stuAnsArea.setText(rs1.getString(4));
                    scoreField.setText(String.valueOf(rs1.getInt(5)));

                    rs.close();
                    rs1.close();
                    stmt.close();
                    stmt1.close();
                    conn1.close();
                    conn.close();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void nextQue(){
        ppConn = new PaperConn();
        conn = ppConn.getConnection();
        answerConn  = new AnswerConn();
        Connection conn1 = answerConn.getConnection();//学生答案
        String sql = "SELECT * FROM papers.paper1 where type=3";//学生答案

        try {
            String nowArr[] = idLabel.getText().split(" ");
            int now = Integer.parseInt(nowArr[1]);
            String sql1 = "SELECT * FROM studentanswer."+name+" where type=3 and id="+(now+1);
            Statement stmt = conn.createStatement();
            Statement stmt1 = conn1.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSet rs1 = stmt1.executeQuery(sql1);
            while(rs.next()){
                if(rs.getInt(1)==now)break;
            }
            if (rs.next()){
                if (rs1.next()) {
                    int id = rs.getInt(1);
                    String question = rs.getString(4);
                    idLabel.setText("第 " + id + " 题:");
                    quesArea.setText(question);
                    standardArea.setText(rs.getString(5));
                    stuAnsArea.setText(rs1.getString(4));
                    scoreField.setText(String.valueOf(rs1.getInt(5)));

                    rs.close();
                    rs1.close();
                    stmt.close();
                    stmt1.close();
                    conn.close();
                    conn1.close();
                }else {
                    JOptionPane.showMessageDialog(null, "这是此学生做的最后一题","提示",2);
                }
            }else{
                JOptionPane.showMessageDialog(null, "这是此学生做的最后一题","提示",2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void preQue(){
        ppConn = new PaperConn();
        conn = ppConn.getConnection();
        answerConn  = new AnswerConn();
        Connection conn2 = answerConn.getConnection();
        try {
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn2.createStatement();
            String nowArr[] = idLabel.getText().split(" ");
            String now = nowArr[1];
            int pre = Integer.parseInt(now)-1;
            String sql2 = "SELECT * FROM studentanswer."+name+" where type=3 and id="+pre;
            now = String.valueOf(pre);
            String sql1 = "select * from papers.paper1 where id= "+now+" and type=3";
            ResultSet rs = stmt.executeQuery(sql1);
            ResultSet rs2 = stmt2.executeQuery(sql2);
            while(!rs.next()&&pre!=0){  //注意要加上不等于零，否则到第一个他也会向前移动
                pre--;
                now = String.valueOf(pre);
                sql1 = "select * from papers.paper1 where id="+now+" and type=3";
                rs = stmt.executeQuery(sql1);
            }
            ResultSet rs1 = stmt.executeQuery(sql1);

            if (rs1.next()){
                if (rs2.next()) {
                    int id = rs1.getInt(1);
                    String question = rs1.getString(4);
                    idLabel.setText("第 " + id + " 题");
                    quesArea.setText(question);
                    standardArea.setText(rs1.getString(5));
                    stuAnsArea.setText(rs2.getString(4));
                    scoreField.setText(String.valueOf(rs2.getInt(5)));

                    rs.close();
                    rs1.close();
                    rs2.close();
                    stmt.close();
                    stmt2.close();
                    conn.close();
                    conn2.close();
                }
            }else{
                JOptionPane.showMessageDialog(null, "这是此学生做的第一道题","提示",2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveScore() throws SQLException {
        answerConn = new AnswerConn();
        conn = answerConn.getConnection();
        if (!scoreField.getText().equals((""))){
            Statement stmt = conn.createStatement();
            String nowArr[] = idLabel.getText().split(" ");
            int now = Integer.parseInt(nowArr[1]);
            String sql = "UPDATE studentanswer."+name+" SET grade = "+scoreField.getText()+" WHERE id="+now;
            stmt.executeUpdate(sql);
        }
    }















    public static void main(String[] args) {
        new MarkPaper();
    }


}
