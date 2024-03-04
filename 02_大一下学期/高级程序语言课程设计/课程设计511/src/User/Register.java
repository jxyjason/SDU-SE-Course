package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;


public class Register extends JFrame {
    DBConnection dbConn = new DBConnection();
    Connection conn = dbConn.getConnection();
//    Socket socket;
    public Register() throws IOException {
        setVisible(true);
        setTitle("注册");
        setSize(500,400);
        setLocation(450,320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.init();
        this.addListener();

//        RegSer ser = new RegSer();
//        ser.start();
//        InetAddress ia = InetAddress.getLocalHost();
//        socket = new Socket(ia.getHostAddress(),7777);
    }

    Font font = new Font("仿宋",Font.BOLD,27);
    Font font1 = new Font("仿宋",Font.BOLD,17);
    Font font2 = new Font("仿宋",Font.BOLD,14);


    JPanel jPanel = new JPanel(null);
    JLabel title = new JLabel("注册");
    JLabel nameLabel = new JLabel("姓名：");
    JLabel idLabel = new JLabel("账号：");
    JLabel passLabel = new JLabel("密码：");
    JTextField nameField = new JTextField();
    JTextField idField = new JTextField();
    JTextField passField = new JTextField();
    JRadioButton stuButton = new JRadioButton("我是学生");
    JRadioButton teaButton = new JRadioButton("我是老师");
    ButtonGroup btGroup = new ButtonGroup();
    JButton register = new JButton("注册");
    JButton returnButton = new JButton("返回登录");

    public void beautify() {
        jPanel.setOpaque(false);
        ImageIcon ig = new ImageIcon("/IDEA/课程设计/src/images/login.png");
        JLabel imgLabel = new JLabel(ig);
        this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        imgLabel.setBounds(0,0,ig.getIconWidth(),ig.getIconHeight());

        title.setForeground(Color.BLACK);
        title.setFont(font);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(font1);
        idLabel.setForeground(Color.BLACK);
        idLabel.setFont(font1);
        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(font1);
        stuButton.setFont(font2);
        stuButton.setBackground(Color.CYAN);
        teaButton.setFont(font2);
        teaButton.setBackground(Color.CYAN);
        register.setFont(font1);
        register.setBackground(Color.CYAN);
        returnButton.setFont(font1);
        returnButton.setBackground(Color.CYAN);
        nameField.setFont(font1);
        idField.setFont(font1);
        passField.setFont(font1);

    }

    public void init(){
        beautify();
        title.setBounds(220,50,90,25);
        nameLabel.setBounds(50, 100, 90, 25);
        nameField.setBounds(130,100,300,25);
        idLabel.setBounds(50, 130, 90, 25);
        idField.setBounds(130,130,300,25);
        passLabel.setBounds(50, 160, 90, 25);
        passField.setBounds(130,160,300,25);
        teaButton.setBounds(140,205,90,25);
        stuButton.setBounds(240,205,90,25);

        btGroup.add(teaButton);
        btGroup.add(stuButton);
        register.setBounds(140,300,90,25);
        returnButton.setBounds(240,300,150,25);


        jPanel.add(title);
        jPanel.add(nameLabel);
        jPanel.add(idLabel);
        jPanel.add(passLabel);
        jPanel.add(idField);
        jPanel.add(nameField);
        jPanel.add(passField);
        jPanel.add(teaButton);
        jPanel.add(stuButton);
        jPanel.add(register);
        jPanel.add(returnButton);

        setContentPane(jPanel);
        jPanel.updateUI();
    }

    private void addListener(){
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
                nameField.setText("");
                idField.setText("");
                passField.setText("");
                btGroup.clearSelection();
            }
        });
    }

//    public void send() throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        PrintWriter pw = new PrintWriter(bw,true);
//
//
//        if ((nameField.getText().equals("")||idField.getText().equals("")||passField.getText().equals(""))
//                ||(teaButton.isSelected()==false&&stuButton.isSelected()==false)){
//            JOptionPane.showMessageDialog(null, "请将登录信息填写完整！",
//                    "消息", 0);
//        }else {
//            int isTea = teaButton.isSelected()?1:0;
//            String send = nameField.getText() + " " + idField.getText() + " " + passField.getText() + " " +isTea;
//            pw.println(send);
//            int judge = Integer.parseInt(br.readLine());
//            if (judge == 1) {
//                JOptionPane.showMessageDialog(this, "添加成功");
//            } else if (judge == 2) {
//                JOptionPane.showMessageDialog(this, "用户已经注册");
//            } else {
//                JOptionPane.showMessageDialog(null, "注册失败",
//                        "消息", 0);
//            }
//        }
//
//
//    }


    private void add(){
        if(!(nameField.getText().equals("")||idField.getText().equals("")||passField.getText().equals(""))
                &&(teaButton.isSelected()==true||stuButton.isSelected()==true)) {

            try {
                String sql1 = "SELECT * FROM user.new_table WHERE id =" + idField.getText();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql1);
                if (!rs.next()) {//rs.next判断rs是否有第一条数据


                    String sql = "insert into user.new_table (id,name,password,grade,isTeacher) values(?,?,?,?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, Integer.parseInt(idField.getText()));
                    pstmt.setString(2, nameField.getText());
                    pstmt.setString(3, passField.getText());
                    pstmt.setInt(4, 0);
                    if (teaButton.isSelected()) pstmt.setInt(5, 1);
                    if (stuButton.isSelected()) pstmt.setInt(5, 0);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "注册成功!");
                } else {
                    JOptionPane.showMessageDialog(this, "用户已经注册");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else{
            JOptionPane.showMessageDialog(null, "注册信息填写不完整！","提示",0);
        }
    }



    public static void main(String[] args) throws IOException {
        Register register = new Register();
    }

}
