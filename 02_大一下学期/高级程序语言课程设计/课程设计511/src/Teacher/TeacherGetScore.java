package Teacher;

import Student.AnswerConn;
import User.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;

public class TeacherGetScore extends JFrame {
    public TeacherGetScore() throws SQLException {
        super("学生成绩统计");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(350,50);

        init();
        addListener();
    }

    Font font1 = new Font("仿宋",Font.BOLD,17);
    int stuNum = getStuNum();
    int queNum = getQusNum();
    String nameArray[] = new String[stuNum];

    JPanel jPanel = new JPanel(null);
    JScrollPane reportPane = new JScrollPane();
    JScrollPane reportPane1 = new JScrollPane();
    String score[][] = new String[stuNum][stuNum];
    String colName[] = {"学生姓名","得分（分）"};
    JTable jTable = new JTable(score,colName);
    JLabel tipLabel = new JLabel("如下是每道题目的平均得分情况：");
    String average[][] = new String[queNum][queNum];
    String averageCol[] = {"题目编号","平均分"};
    JTable averageTable = new JTable(average,averageCol);
    JButton backBut = new JButton("返回");

    DBConnection dbConn = new DBConnection();
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
        jTable.setFont(font1);
        tipLabel.setFont(font1);
        averageTable.setFont(font1);
        backBut.setFont(font1);
        backBut.setBackground(Color.CYAN);
    }


    public void init() throws SQLException {
        beautify();

        reportPane.setBounds(50,50,580,200);
        reportPane.setViewportView(jTable);
        tipLabel.setBounds(50,270,400,25);
        reportPane1.setBounds(50,300,580,350);
        reportPane1.setViewportView(averageTable);

        addInfo();
        backBut.setBounds(540,680,90,25);

        jPanel.add(reportPane);
        jPanel.add(tipLabel);
        jPanel.add(reportPane1);
        jPanel.add(backBut);
        setContentPane(jPanel);
        jPanel.updateUI();
    }

    public void addListener(){
        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TeacherGuiChoose().setVisible(true);
                dispose();
            }
        });
    }

    public void addInfo()  {
        try {
            giveName();
            for (int i=0;i<nameArray.length;i++) {
                score[i][0] = nameArray[i];
                score[i][1] = String.valueOf(getAllScore(nameArray[i]));
            }
            //排序
            for (int i=0;i<nameArray.length;i++){
                int max = i;
                for (int j=i;j<nameArray.length;j++){
                    if (Integer.parseInt(score[j][1])>Integer.parseInt(score[i][1]))max = j;
                }
                String nameTemp = score[i][0];
                score[i][0] = score[max][0];
                score[max][0] = nameTemp;
                String gradeTemp = score[i][1];
                score[i][1] = score[max][1];
                score[max][1] = gradeTemp;
            }
            giveRank();
            updateName();
            //每道题目的平均分
            for (int i=0;i<queNum;i++){
                average[i][0] = "第"+(i+1)+"题";
                average[i][1] = getEveryAverage(i+1);
            }
        } finally {
                try {
                    if(stmt!= null)
                        stmt.close();
                    if(conn!= null)
                        conn.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
    }


    public String getEveryAverage(int num) {
        DecimalFormat b = new DecimalFormat("0.##");
        String result = "";
        answerConn = new AnswerConn();

        try {
            conn = answerConn.getConnection();
            stmt = conn.createStatement();
            int total = 0;
            for (int i=0;i<stuNum;i++){
                String sql = "SELECT grade FROM studentanswer."+nameArray[i]+" where id = "+num;
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next())
                    total += rs.getInt(1);
            }
            double res = (double) total/(double) stuNum;
            result = String.valueOf(b.format(res));
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


    public void giveRank()  {
        dbConn = new DBConnection();
        try {
            conn = dbConn.getConnection();
            stmt = conn.createStatement();
            for (int i=0;i<nameArray.length;i++){
                String sql2 = "UPDATE user.new_table SET sturank = "+(i+1)+" where name = "+"\""+score[i][0]+"\"";
                stmt.executeUpdate(sql2);
            }
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

    }

    public void giveName() {
        dbConn = new DBConnection();
        try {
            conn = dbConn.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user.new_table WHERE isTeacher = 0";
            rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()){
                nameArray[i] = rs.getString(2);
                i++;
            }
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

    }

    public void updateName() {
        for (int i=0;i<nameArray.length;i++){
            if (Integer.parseInt(score[i][1])>=100){
                score[i][1] +="                  第"+ (i+1)+"名";
            }else score[i][1] +="                   第"+ (i+1)+"名";
        }
    }


    public int getAllScore(String name)  {
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



    public int getStuNum()  {
        dbConn = new DBConnection();

        int i = 0;
        try {
            conn = dbConn.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user.new_table WHERE isTeacher = 0";
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                i++;
            }
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

        return i;
    }

    public int getQusNum() {
        ppConn = new PaperConn();

        int result = 0;

        try {
            conn = ppConn.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM papers.paper1";
            rs = stmt.executeQuery(sql);
            while (rs.next())result++;
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

    }

}
