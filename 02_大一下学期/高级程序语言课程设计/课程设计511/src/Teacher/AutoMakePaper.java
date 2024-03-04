package Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AutoMakePaper extends JFrame {
    public AutoMakePaper(){
        super("自动组卷");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,500);
        setLocation(450,320);
        init();
        addListener();
    }

    Font font = new Font("仿宋",Font.BOLD,27);
    Font font1 = new Font("仿宋",Font.BOLD,17);

    JPanel jPanel = new JPanel(null);
    JLabel warnLabel = new JLabel("请确定不同题目类型的个数以及试卷整体难度：");
    JLabel singleLabel = new JLabel("单选题个数（每题2分）：");
    JLabel multiLabel = new JLabel("多选题个数（每题4分）：");
    JLabel subLabel = new JLabel("主观题个数（每题10分）：");
    JLabel difLabel = new JLabel("试卷整体难度（0-5级）：");
    JTextField singleField = new JTextField();
    JTextField multiField = new JTextField();
    JTextField subField = new JTextField();
    JTextField difField = new JTextField();
    JButton confirmBut = new JButton("确认组卷");
    JButton viewBut = new JButton("预览试卷");
    JButton backBut = new JButton("返回");

    private QuestionConn dbconn = null;
    private Connection conn1 = null;
    private PaperConn ppConn = null;
    private Connection conn2 = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    Statement stmt2 = null;
    ResultSet rs2;

    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/login1.png");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());

        warnLabel.setForeground(Color.BLACK);
        warnLabel.setFont(font1);
        singleLabel.setForeground(Color.BLACK);
        singleLabel.setFont(font1);
        multiLabel.setForeground(Color.BLACK);
        multiLabel.setFont(font1);
        subLabel.setForeground(Color.BLACK);
        subLabel.setFont(font1);
        difLabel.setForeground(Color.BLACK);
        difLabel.setFont(font1);
        singleField.setFont(font1);
        multiField.setFont(font1);
        subField.setFont(font1);
        difField.setFont(font1);
        confirmBut.setFont(font1);
        confirmBut.setBackground(Color.CYAN);
        viewBut.setFont(font1);
        viewBut.setBackground(Color.CYAN);
        backBut.setFont(font1);
        backBut.setBackground(Color.CYAN);
    }

    public void init(){
        beautify();

        warnLabel.setBounds(30,30,700,25);
        singleLabel.setBounds(50,80,300,25);
        singleField.setBounds(270,80,100,25);
        multiLabel.setBounds(50,130,300,25);
        multiField.setBounds(270,130,100,25);
        subLabel.setBounds(50,180,300,25);
        subField.setBounds(270,180,100,25);
        difLabel.setBounds(50,230,300,25);
        difField.setBounds(270,230,100,25);
        confirmBut.setBounds(120,300,120,25);
        viewBut.setBounds(320,300,120,25);
        backBut.setBounds(420,400,90,25);

        jPanel.add(singleField);
        jPanel.add(singleLabel);
        jPanel.add(warnLabel);
        jPanel.add(multiField);
        jPanel.add(multiLabel);
        jPanel.add(subField);
        jPanel.add(subLabel);
        jPanel.add(difLabel);
        jPanel.add(difField);
        jPanel.add(confirmBut);
        jPanel.add(viewBut);
        jPanel.add(backBut);

        setContentPane(jPanel);
        jPanel.updateUI();
    }

    public void addListener(){
        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MakePapers();
                dispose();
            }
        });

        viewBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewPaperAuto();
                dispose();
            }
        });

        confirmBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (singleField.getText().equals("")||multiField.getText().equals("")||
                        subField.getText().equals("")||difField.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "请将组卷条件填写完整！","提示",2);
                }else {
                    try {
                        autoMade();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

    }
    int diff;
    public void autoMade() throws SQLException {
        //读取老师要求的不同类型题目的个数以及难度
        int singleNum = Integer.parseInt(singleField.getText());
        int multiNum = Integer.parseInt(multiField.getText());
        int subNum = Integer.parseInt(subField.getText());
        diff = Integer.parseInt(difField.getText());
        int single[];
        int multi[];
        int sub[];
        //将三种类型的题目按要求的难度给出题号
        single = giveByDif(1,singleNum,diff);
        multi = giveByDif(2,multiNum,diff);
        sub = giveByDif(3,subNum,diff);
        //按上面给出的题号将题目添加到试卷中
        for (int i=0;i<singleNum;i++)add(single[i]);
        for (int i=0;i<multiNum;i++)add(multi[i]);
        for (int i=0;i<subNum;i++)add(sub[i]);

        JOptionPane.showMessageDialog(this, "添加成功");

    }
//通过给出题目类型、题目数量以及题目难度来返回一组题库中对应题目难度的题号
    public int[] giveByDif(int type,int num,int dif)  {
        int[] result = new int[num];
        dbconn = new QuestionConn();

        try {
            conn1 = dbconn.getConnection();
            stmt = conn1.createStatement();
            if (dif!=5&&dif!=4) {
                String sql = "select * from question.questiontable where type = " + type +
                        " and (difficult=" + dif + "||difficult=" + (dif + 1)+ "||difficult=" + (dif + 2) + ")";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    for (int i = 0; i < num; i++, rs.next()) {
                        result[i] = rs.getInt(1);
                    }
                }
            }else if (dif==4){
                String sql = "select * from question.questiontable where type ="+type+" and (difficult=4||difficult=5||difficult=3)";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next())
                    for (int i = 0;i < num;i++,rs.next()) result[i] = rs.getInt(1);
            }else if (dif==5){
                String sql = "select * from question.questiontable where type ="+type+" and (difficult=5||difficult=4||difficult=3)";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next())
                    for (int i = 0;i < num;i++,rs.next()) result[i] = rs.getInt(1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                if(stmt!= null)
                    stmt.close();
                if(conn1!= null)
                    conn1.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return result;
    }
//通过给出题库中题目id来将题库中的题目添加到试卷中
    public void add(int id){
        dbconn = new QuestionConn();
        ppConn = new PaperConn();
        String sql1 = "select id from papers.paper1 where id=" + id;
        String sql2 = "select * from question.questiontable where id=" + id;
        String sql = "insert into papers.paper1 (id,type,difficult,question,answer) values(?,?,?,?,?)";

        try
        {
            conn1 = dbconn.getConnection();
            conn2 = ppConn.getConnection();
            stmt = conn1.createStatement();
            stmt2 = conn2.createStatement();
            rs = stmt.executeQuery(sql1);
            if (!rs.next())
            {
                try
                {
                    //type:1-单选；2-多选；3-主观题；
                    pstmt = conn2.prepareStatement(sql);
                    pstmt.setInt(1,id);
                    rs2 = stmt2.executeQuery(sql2);
                    if (rs2.next()) {
                        pstmt.setInt(2, Integer.parseInt(rs2.getString(2)));
                        pstmt.setInt(3,rs2.getInt(3));
                        pstmt.setString(4, rs2.getString(4));
                        pstmt.setString(5,rs2.getString(5));
                        pstmt.executeUpdate();
                    }

                }
                catch (SQLException e)
                {
                    // e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "添加失败");
                }
                finally
                {
                    try
                    {

                        if (stmt2 != null)
                            stmt2.close();
                        if (pstmt != null)
                            pstmt.close();
                        if (conn2 != null)
                            conn2.close();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                //JOptionPane.showMessageDialog(this, "本题目已存在");
            }
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        catch (HeadlessException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                if (stmt != null)
                    stmt.close();
                if (conn1 != null)
                    conn1.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new AutoMakePaper();
    }


}
class ViewPaperAuto extends ViewPaper{
    public void back(){
        new AutoMakePaper();
    }
}