package Student;

import Teacher.PaperConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BeginExam extends JFrame{
    public BeginExam() throws SQLException {
        setTitle("考试进行中");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(350,50);
        init();
        addListener();
        newTable(StudentGuiChoose.welcomeLabel.getText().split(" ")[1]);
    }

    Font font = new Font("仿宋",Font.BOLD,27);
    Font font1 = new Font("仿宋",Font.BOLD,17);
    Font font2 = new Font("仿宋",Font.BOLD,14);

    JPanel jPanel = new JPanel(null);
    JLabel choseLabel = new JLabel("点击按钮切换题目类型：");
    JButton singleBut = new JButton("单选题");
    JButton multiBut = new JButton("多选题");
    JButton subBut = new JButton("主观题");

    static JLabel timerLabel;

    private AnswerConn answerConn = null;
    private Connection conn = null;
    private Statement stmt = null;

    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/1.jpg");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());

        choseLabel.setForeground(Color.BLACK);
        choseLabel.setFont(font1);
        singleBut.setFont(font1);
        singleBut.setBackground(Color.CYAN);
        multiBut.setFont(font1);
        multiBut.setBackground(Color.CYAN);
        subBut.setFont(font1);
        subBut.setBackground(Color.CYAN);



    }

    public void init(){
        beautify();

        timerLabel = new MultiTimer().getLabel();
        timerLabel.setFont(font1);
        timerLabel.setBackground(Color.RED);

        choseLabel.setBounds(50,30,300,25);
        singleBut.setBounds(50,70,90,25);
        multiBut.setBounds(200,70,90,25);
        subBut.setBounds(350,70,90,25);
        timerLabel.setBounds(50,680,400,25);

        jPanel.add(timerLabel);
        jPanel.add(choseLabel);
        jPanel.add(singleBut);
        jPanel.add(multiBut);
        jPanel.add(subBut);
        setContentPane(jPanel);
        jPanel.updateUI();
    }

    public void addListener(){
        singleBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Thread(new SingleQue()).start();
                new Thread(new MultiQue()).start();
                new Thread(new SubQue()).start();
                new SingleQue().setVisible(true);


            }
        });

        multiBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Thread(new SingleQue()).start();
                new Thread(new MultiQue()).start();
                new Thread(new SubQue()).start();
                new MultiQue().setVisible(true);
            }
        });

        subBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Thread(new SingleQue()).start();
                new Thread(new MultiQue()).start();
                new Thread(new SubQue()).start();
                new SubQue().setVisible(true);

            }
        });
    }

    public void newTable(String name) {
        answerConn = new AnswerConn();

        try {
            conn = answerConn.getConnection();
            stmt = conn.createStatement();
            String sql = "CREATE TABLE `studentanswer`.`"+name+"` (\n" +
                    "  `id` INT NOT NULL,\n" +
                    "  `type` INT NULL,\n" +
                    "  `question` TEXT(100) NULL,\n" +
                    "  `answer` TEXT(100) NULL,\n" +
                    "  `grade` INT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8\n" +
                    "COLLATE = utf8_bin";
            stmt.executeUpdate(sql);
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

    public static void main(String[] args) throws SQLException {
        new BeginExam().setVisible(true);
    }

}

class AfterExam extends StudentGuiChoose{
    public AfterExam(){
        change();
    }
    public void change(){
        exam.setText("考试已结束");
        exam.setEnabled(false);
    }

    public static void main(String[] args) {
        new AfterExam();
    }
}

