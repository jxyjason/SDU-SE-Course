package Student;


import Teacher.PaperConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.concurrent.TimeUnit;

public class MultiQue extends JFrame implements Runnable{
    static int endFlag = 1;

    public void run(){
        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (timeLabel.getText().equals("考试结束，请等待老师批改")){
                endFlag = 0;
                break;
            }
        }
    }

    public MultiQue(){
        super("考试进行中");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(350,50);
        init();
        addListener();
        addQuestionFromPaper();
    }

    Font font1 = new Font("仿宋",Font.BOLD,17);
    String name = StudentGuiChoose.welcomeLabel.getText().split(" ")[1];

    JPanel jPanel = new JPanel(null);
    JLabel choseLabel = new JLabel("点击按钮切换题目类型：");
    JButton singleBut = new JButton("单选题");
    JButton multiBut = new JButton("多选题");
    JButton subBut = new JButton("主观题");

    JLabel idLabel = new JLabel("第1题：");
    JScrollPane scrollPane = new JScrollPane();
    JTextArea questionArea = new JTextArea();
    JCheckBox aBox = new JCheckBox("A");
    JCheckBox bBox = new JCheckBox("B");
    JCheckBox cBox = new JCheckBox("C");
    JCheckBox dBox = new JCheckBox("D");
    JButton preBut = new JButton("上一题");
    JButton nextBut = new JButton("下一题");
    JButton finishBut = new JButton("提交试卷");

    JLabel timeLabel = BeginExam.timerLabel;

    private AnswerConn answerConn = null;
    private PaperConn ppConn = null;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;

    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/1.jpg");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());

        idLabel.setForeground(Color.BLACK);
        idLabel.setFont(font1);
        choseLabel.setForeground(Color.BLACK);
        choseLabel.setFont(font1);
        singleBut.setFont(font1);
        singleBut.setBackground(Color.CYAN);
        multiBut.setFont(font1);
        multiBut.setBackground(Color.CYAN);
        subBut.setFont(font1);
        subBut.setBackground(Color.CYAN);
        questionArea.setFont(font1);
        aBox.setFont(font1);
        bBox.setFont(font1);
        cBox.setFont(font1);
        dBox.setFont(font1);
        preBut.setFont(font1);
        preBut.setBackground(Color.CYAN);
        nextBut.setFont(font1);
        nextBut.setBackground(Color.CYAN);
        finishBut.setFont(font1);
        finishBut.setBackground(Color.RED);



    }

    public void init(){
        beautify();


        choseLabel.setBounds(50,30,300,25);
        singleBut.setBounds(50,70,90,25);
        multiBut.setBounds(200,70,90,25);
        subBut.setBounds(350,70,90,25);
        idLabel.setBounds(50,150,90,25);
        scrollPane.setBounds(50,200,450,250);
        scrollPane.setViewportView(questionArea);
        aBox.setBounds(50,470,90,25);
        bBox.setBounds(180,470,90,25);
        cBox.setBounds(330,470,90,25);
        dBox.setBounds(480,470,90,25);
        preBut.setBounds(50,570,90,25);
        nextBut.setBounds(420,570,90,25);
        finishBut.setBounds(500,680,150,25);

        multiBut.setEnabled(false);

        timeLabel.setBounds(50,680,400,25);
        jPanel.add(timeLabel);

        jPanel.add(choseLabel);
        jPanel.add(singleBut);
        jPanel.add(multiBut);
        jPanel.add(subBut);
        jPanel.add(idLabel);
        jPanel.add(scrollPane);
        jPanel.add(aBox);
        jPanel.add(bBox);
        jPanel.add(cBox);
        jPanel.add(dBox);
        jPanel.add(preBut);
        jPanel.add(nextBut);
        jPanel.add(finishBut);
        setContentPane(jPanel);
        jPanel.updateUI();
    }

    public void addListener(){
        singleBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (endFlag==1) {
                    new SingleQue().setVisible(true);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "考试已结束，请点击交卷按钮交卷。",
                            "提示",1);
                    clearAll();
                }

            }
        });

        subBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (endFlag==1){
                    new SubQue().setVisible(true);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "考试已结束，请点击交卷按钮交卷。",
                            "提示",1);
                    clearAll();
                }


            }
        });

        nextBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (endFlag==1) {
                    addToAnswer();
                    nextQue();

                }else {
                    JOptionPane.showMessageDialog(null, "考试已结束，请点击交卷按钮交卷。",
                            "提示",1);
                    clearAll();
                }
            }
        });

        preBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (endFlag==1) {
                    addToAnswer();
                    preQue();

                }else {
                    JOptionPane.showMessageDialog(null, "考试已结束，请点击交卷按钮交卷。",
                            "提示",1);
                    clearAll();
                }
            }
        });

        finishBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "确认交卷吗?", "请再次确认",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    addToAnswer();
                    new AfterExam().setVisible(true);
                    dispose();
                }
            }
        });
    }

    public void addQuestionFromPaper(){
        ppConn = new PaperConn();
        conn = ppConn.getConnection();
        answerConn = new AnswerConn();
        Connection conn1 = answerConn.getConnection();
        String sql = "SELECT * FROM papers.paper1 where type=2";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Statement stmt1 = conn1.createStatement();

            if (rs.next()){
                    int id = rs.getInt(1);
                    String sql1 = "SELECT * FROM studentanswer."+name+" where type=2 and id="+id;
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                if (rs1.next()) {
                    String question = rs.getString(4);
                    idLabel.setText("第 " + id + " 题");
                    questionArea.setText(question);
                    String answer = rs1.getString(4);
                    String ansArr[] = answer.split(" ");
                    for (int i=0;i<2;i++) {
                        answer = ansArr[i];
                        if (answer.equals("A")) aBox.setSelected(true);
                        else if (answer.equals("B")) bBox.setSelected(true);
                        else if (answer.equals("C")) cBox.setSelected(true);
                        else if (answer.equals("D")) dBox.setSelected(true);
                    }

                    rs.close();
                    rs1.close();
                    stmt.close();
                    stmt1.close();
                    conn.close();
                    conn1.close();
                }else {
                    String question = rs.getString(4);
                    idLabel.setText("第 " + id + " 题");
                    questionArea.setText(question);
                    rs.close();
                    rs1.close();
                    stmt.close();
                    stmt1.close();
                    conn.close();
                    conn1.close();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addToAnswer(){
        answerConn = new AnswerConn();
        conn = answerConn.getConnection();
        ppConn = new PaperConn();
        Connection conn3 = ppConn.getConnection();


        String now[] = idLabel.getText().split(" ");
        int nowNum = Integer.parseInt(now[1]);
        String sql1 = "select id from studentanswer."+name+" where id="
                + nowNum;
        String sql = "insert into studentanswer."+name+" (id,type,question,answer,grade) values(?,?,?,?,?)";
        String sql3 = "SELECT * FROM papers.paper1 WHERE id = "+nowNum;
        if (aBox.isSelected()||bBox.isSelected()||cBox.isSelected()||dBox.isSelected()) {
            try {
                stmt = conn.createStatement();
                Statement stmt3 = conn3.createStatement();
                rs = stmt.executeQuery(sql1);
                ResultSet rs3 = stmt3.executeQuery(sql3);
                if (!rs.next()&&rs3.next()) {
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, nowNum);
                    pstmt.setInt(2,2);
                    pstmt.setString(3, questionArea.getText());
                    String answer = "";
                    if (aBox.isSelected()) answer += "A ";
                    if (bBox.isSelected()) answer += "B ";
                    if (cBox.isSelected()) answer += "C ";
                    if (dBox.isSelected()) answer += "D ";
                    pstmt.setString(4, answer);
                    String basicArr[] = answer.split(" ");
                    String basic = basicArr[0]+basicArr[1];
                    if (basic.equalsIgnoreCase(rs3.getString(5)))pstmt.setInt(5,4);
                    else pstmt.setInt(5,0);
                    pstmt.executeUpdate();

                    rs.close();
                    rs3.close();
                    stmt.close();
                    stmt3.close();
                    conn3.close();
                }else {
                    if (rs3.next()) {
                        String answer = "";
                        if (aBox.isSelected()) answer += "A ";
                        if (bBox.isSelected()) answer += "B ";
                        if (cBox.isSelected()) answer += "C ";
                        if (dBox.isSelected()) answer += "D ";
                        int grade = 0;
                        String basicArr[] = answer.split(" ");
                        String basic = basicArr[0]+basicArr[1];
                        if (basic.equalsIgnoreCase(rs3.getString(5))) grade = 4;
                        String answerSql = "UPDATE studentanswer."+name+" SET answer = \"" + answer
                                + "\" WHERE id=" + nowNum;
                        String scoreSql = "UPDATE studentanswer."+name+" SET grade = " + grade
                                + " WHERE id=" + nowNum;
                        Statement stmt = conn.createStatement();
                        Statement stmt2 = conn.createStatement();
                        stmt.executeUpdate(answerSql);
                        stmt2.executeUpdate(scoreSql);

                        rs.close();
                        rs3.close();
                        stmt.close();
                        stmt2.close();
                        stmt3.close();
                        conn3.close();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null)
                        pstmt.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void nextQue(){
        ppConn = new PaperConn();
        conn = ppConn.getConnection();
        answerConn = new AnswerConn();
        Connection conn1 = answerConn.getConnection();
        String sql = "SELECT * FROM papers.paper1 where type=2";
        try {
            Statement stmt = conn.createStatement();
            Statement stmt1 = conn1.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String nowArr[] = idLabel.getText().split(" ");
            int now = Integer.parseInt(nowArr[1]);
            String sql1 = "SELECT * FROM studentanswer."+name+" where type=2 and id="+(now+1);
            ResultSet rs1 = stmt1.executeQuery(sql1);
            while(rs.next()){
                if(rs.getInt(1)==now)break;
            }
            if (rs.next()){
                if (rs1.next()) {
                    aBox.setSelected(false);
                    bBox.setSelected(false);
                    cBox.setSelected(false);
                    dBox.setSelected(false);
                    int id = rs.getInt(1);
                    String question = rs.getString(4);
                    idLabel.setText("第 " + id + " 题");
                    questionArea.setText(question);
                    String answer = rs1.getString(4);
                    String ansArr[] = answer.split(" ");
                    for (int i=0;i<2;i++) {
                        answer = ansArr[i];
                        if (answer.equals("A")) aBox.setSelected(true);
                        else if (answer.equals("B")) bBox.setSelected(true);
                        else if (answer.equals("C")) cBox.setSelected(true);
                        else if (answer.equals("D")) dBox.setSelected(true);
                    }

                    rs.close();
                    rs1.close();
                    stmt.close();
                    stmt1.close();
                    conn.close();
                    conn1.close();
                }else {
                    int id = rs.getInt(1);
                    String question = rs.getString(4);
                    idLabel.setText("第 " + id + " 题");
                    questionArea.setText(question);
                    aBox.setSelected(false);
                    bBox.setSelected(false);
                    cBox.setSelected(false);
                    dBox.setSelected(false);

                    rs.close();
                    rs1.close();
                    stmt.close();
                    stmt1.close();
                    conn.close();
                    conn1.close();
                }
            }else{
                JOptionPane.showMessageDialog(null, "已经是最后一道多选题了","提示",2);
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
            String nowArr[] = idLabel.getText().split(" ");
            String now = nowArr[1];
            int pre = Integer.parseInt(now)-1;
            String sql2 = "SELECT * FROM studentanswer."+name+" where type=2 and id="+pre;
            Statement stmt2 = conn2.createStatement();
            now = String.valueOf(pre);
            String sql1 = "select * from papers.paper1 where id= "+now+" and type=2";
            ResultSet rs = stmt.executeQuery(sql1);
            ResultSet rs2 = stmt2.executeQuery(sql2);
            while(!rs.next()&&pre!=0){  //注意要加上不等于零，否则到第一个他也会向前移动
                pre--;
                now = String.valueOf(pre);
                sql1 = "select * from papers.paper1 where id="+now+" and type=2";
                rs = stmt.executeQuery(sql1);
            }
            ResultSet rs1 = stmt.executeQuery(sql1);

            if (rs1.next()){
                if (rs2.next()) {
                    aBox.setSelected(false);
                    bBox.setSelected(false);
                    cBox.setSelected(false);
                    dBox.setSelected(false);
                    int id = rs1.getInt(1);
                    String question = rs1.getString(4);
                    idLabel.setText("第 " + id + " 题");
                    questionArea.setText(question);
                    String answer = rs2.getString(4);
                    String ansArr[] = answer.split(" ");
                    for (int i=0;i<2;i++) {
                        answer = ansArr[i];
                        if (answer.equals("A")) aBox.setSelected(true);
                        else if (answer.equals("B")) bBox.setSelected(true);
                        else if (answer.equals("C")) cBox.setSelected(true);
                        else if (answer.equals("D")) dBox.setSelected(true);
                    }

                    rs.close();
                    rs1.close();
                    rs2.close();
                    stmt.close();
                    stmt2.close();
                    conn.close();
                    conn2.close();
                }else {
                    int id = rs1.getInt(1);
                    String question = rs1.getString(4);
                    idLabel.setText("第 " + id + " 题");
                    questionArea.setText(question);
                    aBox.setSelected(false);
                    bBox.setSelected(false);
                    cBox.setSelected(false);
                    dBox.setSelected(false);

                    rs.close();
                    rs1.close();
                    rs2.close();
                    stmt.close();
                    stmt2.close();
                    conn.close();
                    conn2.close();
                }
            }else{
                JOptionPane.showMessageDialog(null, "已经是第一道多选题了","提示",2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void clearAll() {
        singleBut.setEnabled(false);
        multiBut.setEnabled(false);
        subBut.setEnabled(false);
        questionArea.setText("");
        preBut.setEnabled(false);
        nextBut.setEnabled(false);
    }



    public static void main(String[] args) {
        new MultiQue();
    }



















}
