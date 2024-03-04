package Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CheckByDif extends JFrame{
    public static void main(String[] args) {
        new CheckByDif();
    }
    public CheckByDif(){
        setTitle("按题目难度系数查询");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700,800);
        setLocation(350,50);
        init();
        addListener();

    }

    Font font1 = new Font("仿宋",Font.BOLD,17);

    JPanel jPanel = new JPanel(null);
    JLabel idLabel = new JLabel("题目编号：");
    JLabel typeLabel = new JLabel("题型：");
    JLabel difficultLabel = new JLabel("难度系数：");
    JLabel questionLabel = new JLabel("题目：");
    JLabel answerLabel = new JLabel("答案：");
    ButtonGroup btg = new ButtonGroup();
    JRadioButton singleRadButton = new JRadioButton("单选题");
    JRadioButton multiRadButton = new JRadioButton("多选题");
    JRadioButton subjectiveRadButton = new JRadioButton("主观题");
    JTextField idField = new JTextField();
    JTextField difficultField = new JTextField();
    JScrollPane quesPan = new JScrollPane();
    JScrollPane ansPan = new JScrollPane();
    JTextArea questionArea = new JTextArea();
    JTextArea answerArea = new JTextArea();
    JButton addBut = new JButton("增加题目");
    JButton delBut = new JButton("删除题目");
    JButton findByTypeBut = new JButton("按类型统计题目");
    JButton findByDifBut = new JButton("按难度统计题目");
    JButton checkBut = new JButton("查询题目");
    JButton firstBut = new JButton("第一个");
    JButton lastBut = new JButton("最后一个");
    JButton nextBut = new JButton("下一个");
    JButton preBut =  new JButton("上一个");
    JButton back = new JButton("返回");

    private QuestionConn dbconn = null;
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
        typeLabel.setFont(font1);
        typeLabel.setBackground(Color.CYAN);
        difficultLabel.setFont(font1);
        difficultLabel.setBackground(Color.CYAN);
        questionLabel.setFont(font1);
        questionLabel.setBackground(Color.CYAN);
        answerLabel.setFont(font1);
        answerLabel.setBackground(Color.CYAN);

        singleRadButton.setFont(font1);
        multiRadButton.setFont(font1);
        subjectiveRadButton.setFont(font1);
        idField.setFont(font1);
        difficultField.setFont(font1);
        questionArea.setFont(font1);
        answerArea.setFont(font1);
        addBut.setFont(font1);
        addBut.setBackground(Color.YELLOW);
        delBut.setFont(font1);
        delBut.setBackground(Color.YELLOW);
        findByDifBut.setFont(font1);
        findByDifBut.setBackground(Color.CYAN);
        findByTypeBut.setFont(font1);
        findByTypeBut.setBackground(Color.CYAN);
        checkBut.setFont(font1);
        checkBut.setBackground(Color.CYAN);
        firstBut.setFont(font1);
        firstBut.setBackground(Color.GREEN);
        lastBut.setFont(font1);
        lastBut.setBackground(Color.GREEN);
        nextBut.setFont(font1);
        nextBut.setBackground(Color.GREEN);
        preBut.setFont(font1);
        preBut.setBackground(Color.GREEN);
        back.setFont(font1);
        back.setBackground(Color.GREEN);

    }

    private void init(){
        beautify();

        idLabel.setBounds(50,20,90,25);
        idField.setBounds(160,20,90,25);
        typeLabel.setBounds(50,50,90,25);
        btg.add(singleRadButton);
        btg.add(multiRadButton);
        btg.add(subjectiveRadButton);
        singleRadButton.setBounds(160,50,90,25);
        multiRadButton.setBounds(260,50,90,25);
        subjectiveRadButton.setBounds(360,50,90,25);
        difficultLabel.setBounds(50,100,90,25);
        difficultField.setBounds(160,100,300,25);
        questionLabel.setBounds(50,150,90,25);
        quesPan.setBounds(160,150,300,150);
        quesPan.setViewportView(questionArea);
        answerLabel.setBounds(50,350,90,25);
        ansPan.setBounds(160,350,300,150);
        ansPan.setViewportView(answerArea);
        addBut.setBounds(150,550,150,25);
        delBut.setBounds(370,550,150,25);
        findByTypeBut.setBounds(240,600,180,25);
        findByDifBut.setBounds(450,600,180,25);
        checkBut.setBounds(60,600,150,25);
        firstBut.setBounds(60,650,90,25);
        preBut.setBounds(210,650,90,25);
        nextBut.setBounds(360,650,90,25);
        lastBut.setBounds(510,650,120,25);
        back.setBounds(540,700,90,25);


        jPanel.add(idField);
        jPanel.add(idLabel);
        jPanel.add(typeLabel);
        jPanel.add(singleRadButton);
        jPanel.add(multiRadButton);
        jPanel.add(subjectiveRadButton);
        jPanel.add(difficultField);
        jPanel.add(difficultLabel);
        jPanel.add(quesPan);
        jPanel.add(questionLabel);
        jPanel.add(answerLabel);
        jPanel.add(ansPan);
        jPanel.add(addBut);
        jPanel.add(delBut);
        jPanel.add(findByDifBut);
        jPanel.add(findByTypeBut);
        jPanel.add(checkBut);
        jPanel.add(firstBut);
        jPanel.add(preBut);
        jPanel.add(nextBut);
        jPanel.add(lastBut);
        jPanel.add(back);
        setContentPane(jPanel);
        jPanel.updateUI();

        findByDifBut.setEnabled(false);
        findByTypeBut.setEnabled(false);
        addBut.setEnabled(false);

    }


    public void addListener(){
        checkBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(difficultField.getText().equals(""))){
                    firstQue();
                }else{
                    JOptionPane.showMessageDialog(null, "请输入题目难度","提示",0);
                }
            }
        });

        firstBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(difficultField.getText().equals(""))){
                    firstQue();
                }else{
                    JOptionPane.showMessageDialog(null, "请输入题目难度","提示",0);
                }
            }
        });

        lastBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(difficultField.getText().equals(""))){
                    last();
                }else{
                    JOptionPane.showMessageDialog(null, "请输入题目难度","提示",0);
                }
            }
        });

        nextBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(idField.getText().equals("")||difficultField.getText().equals("")||questionArea.getText().equals("")
                        ||answerArea.getText().equals(""))
                        &&(singleRadButton.isSelected()==true||multiRadButton.isSelected()==true
                        ||subjectiveRadButton.isSelected()==true)){
                    nextQue();
                }else{
                    JOptionPane.showMessageDialog(null, "当前无题目，无法进行下一题查询","提示",0);
                }
            }
        });

        preBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(idField.getText().equals("")||difficultField.getText().equals("")||questionArea.getText().equals("")
                        ||answerArea.getText().equals(""))
                        &&(singleRadButton.isSelected()==true||multiRadButton.isSelected()==true
                        ||subjectiveRadButton.isSelected()==true)){
                    preQue();
                }else{
                    JOptionPane.showMessageDialog(null, "当前无题目，无法进行上一题查询","提示",0);
                }
            }
        });

        delBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delQue();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpholdQuestion();
                dispose();
            }
        });
    }



    public void firstQue(){
        dbconn = new QuestionConn();

        String now = difficultField.getText();
        String sql = "SELECT * FROM question.questiontable WHERE difficult="+now;
        idField.setText("");
        difficultField.setText("");
        questionArea.setText("");
        answerArea.setText("");
        btg.clearSelection();
        try {
            conn = dbconn.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                int id = rs.getInt(1);
                int type  = rs.getInt(2);
                int difficult = rs.getInt(3);
                String question = rs.getString(4);
                String answer = rs.getString(5);
                idField.setText(String.valueOf(id));
                if(type==1)singleRadButton.setSelected(true);
                else if (type==2)multiRadButton.setSelected(true);
                else if(type==3)subjectiveRadButton.setSelected(true);
                difficultField.setText(String.valueOf(difficult));
                questionArea.setText(question);
                answerArea.setText(answer);
            }else{
                JOptionPane.showMessageDialog(null, "题库中没有此类型题目","提示",2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void last(){
        dbconn = new QuestionConn();

        String now = difficultField.getText();
        String sql = "SELECT * FROM question.questiontable WHERE difficult="+now;
        idField.setText("");
        difficultField.setText("");
        questionArea.setText("");
        answerArea.setText("");
        btg.clearSelection();
        try {
            conn = dbconn.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt(1);
                int type  = rs.getInt(2);
                int difficult = rs.getInt(3);
                String question = rs.getString(4);
                String answer = rs.getString(5);
                idField.setText(String.valueOf(id));
                if(type==1)singleRadButton.setSelected(true);
                else if (type==2)multiRadButton.setSelected(true);
                else if(type==3)subjectiveRadButton.setSelected(true);
                difficultField.setText(String.valueOf(difficult));
                questionArea.setText(question);
                answerArea.setText(answer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void nextQue(){
        dbconn = new QuestionConn();

        String now1 = difficultField.getText();//声明的难度系数
        String sql = "SELECT * FROM question.questiontable WHERE difficult="+now1;
        try {
            conn = dbconn.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int now = Integer.parseInt(idField.getText());//得到现存题目的id
            while(rs.next()){
                if(rs.getInt(1)==now)break;//循环，从满足条件的内容中找到now这个位置
            }
            if (rs.next()){
                int id = rs.getInt(1);
                int type  = rs.getInt(2);
                int difficult = rs.getInt(3);
                String question = rs.getString(4);
                String answer = rs.getString(5);
                idField.setText(String.valueOf(id));
                if(type==1)singleRadButton.setSelected(true);
                else if (type==2)multiRadButton.setSelected(true);
                else if(type==3)subjectiveRadButton.setSelected(true);
                difficultField.setText(String.valueOf(difficult));
                questionArea.setText(question);
                answerArea.setText(answer);
            }else{
                JOptionPane.showMessageDialog(null, "已经是最后一题了","提示",2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void preQue(){
        dbconn = new QuestionConn();

        try {
            conn = dbconn.getConnection();
            stmt = conn.createStatement();
            int diff = Integer.parseInt(difficultField.getText());
            int pre = Integer.parseInt(idField.getText());
            pre--;
            String now = String.valueOf(pre);
            String sql1 = "select * from question.questiontable where id="+now;
            ResultSet rs = stmt.executeQuery(sql1);

            while (rs.next()) {
                    if (rs.getInt(3) == diff) break;

                    pre--;
                    now = String.valueOf(pre);
                    sql1 = "select * from question.questiontable where id=" + now;
                    rs = stmt.executeQuery(sql1);
            }
            ResultSet rs1 = stmt.executeQuery(sql1);

            btg.clearSelection();
            if (rs1.next()){
                int id = rs1.getInt(1);
                int type  = rs1.getInt(2);
                int difficult = rs1.getInt(3);
                String question = rs1.getString(4);
                String answer = rs1.getString(5);
                idField.setText(String.valueOf(id));
                if(type==1)singleRadButton.setSelected(true);
                else if (type==2)multiRadButton.setSelected(true);
                else if(type==3)subjectiveRadButton.setSelected(true);
                difficultField.setText(String.valueOf(difficult));
                questionArea.setText(question);
                answerArea.setText(answer);
            }else{
                JOptionPane.showMessageDialog(null, "已经是第一道题目了","提示",2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
    public void delQue(){
        dbconn = new QuestionConn();
        try {
            conn = dbconn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM question.questiontable WHERE ID ="+idField.getText();
            int n = JOptionPane.showConfirmDialog(null, "确认删除吗?", "确认删除框", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                stmt.executeUpdate(sql);
                idField.setText("");
                difficultField.setText("");
                questionArea.setText("");
                answerArea.setText("");
                btg.clearSelection();
                JOptionPane.showMessageDialog(null, "删除成功！","提示",1);
                //updateId();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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


}
