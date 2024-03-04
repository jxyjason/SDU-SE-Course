package Student;

import Teacher.PaperConn;
import User.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GetScore extends JFrame {
    public GetScore() throws SQLException {
        super("我的成绩");
        setSize(700, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(350,50);

        init();
        addInfo();
        addListener();
    }

    Font font1 = new Font("仿宋",Font.BOLD,17);

    String name = StudentGuiChoose.welcomeLabel.getText().split(" ")[1];
    int queNum = getQueNum()+5;
    JPanel jPanel = new JPanel(null);
    JLabel nameLabel = new JLabel("学生姓名："+name);
    JScrollPane reportPane = new JScrollPane();
    String score[][] = new String[queNum][queNum];
    String colName[] = {"成绩类型","得分（分）"};
    JTable jTable= new JTable(score,colName);
    JButton backBut = new JButton("返回");

    DBConnection dbConn = new DBConnection();
    private AnswerConn answerConn = null;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;

    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/grade.jpg");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());

        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(font1);
        jTable.setForeground(Color.BLACK);
        jTable.setFont(font1);
        backBut.setForeground(Color.BLACK);
        backBut.setBackground(Color.CYAN);
        backBut.setFont(font1);

    }

    public void init(){
        beautify();
        nameLabel.setBounds(50,30,200,25);
        reportPane.setBounds(50,50,580,610);
        reportPane.setViewportView(jTable);
        backBut.setBounds(540,680,90,25);

        jPanel.add(nameLabel);
        jPanel.add(reportPane);
        jPanel.add(backBut);
        setContentPane(jPanel);
        jPanel.updateUI();
    }

    public void addListener(){
        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AfterExam().setVisible(true);
                dispose();
            }
        });
    }

    public void addInfo(){
        //连接数据库
        dbConn = new DBConnection();
        Connection conn1 = null;
        Statement stmt1 = null;
        try {

            answerConn = new AnswerConn();
            conn1 = answerConn.getConnection();
            stmt1 = conn1.createStatement();
            //设置分类题型得分
            score[0][0] = "总成绩";
            score[0][1] = String.valueOf(getAllScore());//getAllScore
            score[1][0] = "单选题得分";
            score[1][1] = String.valueOf(getSepScore(1));//getSepScore
            score[2][0] = "多选题得分";
            score[2][1] = String.valueOf(getSepScore(2));
            score[3][0] = "主观题得分";
            score[3][1] = String.valueOf(getSepScore(3));
            String sql1 = "SELECT * FROM studentanswer."+name;
            ResultSet rs1 = stmt1.executeQuery(sql1);
            //设置每一道题得分
            for (int i=4;i<queNum-1;i++){
                if (rs1.next()) {
                    score[i][0] = "第 " + rs1.getInt(1) + " 题得分";
                    score[i][1] = String.valueOf(getEveryScore(rs1.getInt(1)));//getEveryScore
                }
            }
            conn = dbConn.getConnection();
            stmt = conn.createStatement();
            score[queNum-1][0] = "排名";
            String sql = "SELECT sturank FROM user.new_table where name = \""+name+"\"";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                score[queNum-1][1] = String.valueOf(rs.getInt(1));
            jTable.updateUI();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                if(stmt1!= null)
                    stmt1.close();
                if(conn1!= null)
                    conn1.close();
                if(stmt!= null)
                    stmt.close();
                if(conn!= null)
                    conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

    }
    //得到题目总数
    public int getQueNum() {
        answerConn = new AnswerConn();
        int result = 0;
        try {
            conn = answerConn.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT grade FROM studentanswer."+name;
            rs = stmt.executeQuery(sql);
            while (rs.next()) result ++;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                if(stmt!= null)
                    stmt.close();
                if(conn!= null)
                    conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return result;

    }
    //得到总成绩
    public int getAllScore() {
        answerConn = new AnswerConn();
        int result = 0;
        try {
            conn = answerConn.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT grade FROM studentanswer."+name;
            rs = stmt.executeQuery(sql);

            while (rs.next()) result += rs.getInt(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                if(stmt!= null)
                    stmt.close();
                if(conn!= null)
                    conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return result;
    }
    //根据输入的题目类型得到对应类型题目的总分
    public int getSepScore(int type) {
        answerConn = new AnswerConn();
        int result = 0;
        try {
            conn = answerConn.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT grade FROM studentanswer."+name+" WHERE type="+type;
            rs = stmt.executeQuery(sql);

            while (rs.next()) result += rs.getInt(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                if(stmt!= null)
                    stmt.close();
                if(conn!= null)
                    conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return result;
    }
    //根据输入的题号得到对应题目分数
    public int getEveryScore(int num) {
        answerConn = new AnswerConn();
        int result = 0;
        try {
            conn = answerConn.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT grade FROM studentanswer."+name+" WHERE id="+num;
            rs = stmt.executeQuery(sql);

            if (rs.next()) result = rs.getInt(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                if(stmt!= null)
                    stmt.close();
                if(conn!= null)
                    conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return result;
    }



    public static void main(String[] args) throws SQLException {
        new GetScore();
    }

}
