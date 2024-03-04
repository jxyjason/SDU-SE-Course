package GuiTest;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TextfieldDemo
{
    public static void main(String args[])
    {
        loginWindow pw = new loginWindow();
    }
}

class loginWindow extends JFrame implements ActionListener
{
    JTextField titleText;
    JPasswordField passwordText;
    JLabel title;

    loginWindow()
    {
        setSize(200, 150);
        title = new JLabel("姓名");
        titleText = new JTextField(10);
        passwordText = new JPasswordField(10);
        passwordText.setEchoChar('*');
        titleText.addActionListener(this);
        passwordText.addActionListener(this);
        setLayout(new FlowLayout());
        add(title);
        add(titleText);
        add(passwordText);
        setVisible(true);
        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e)
    {
        JTextField textSource = (JTextField) e.getSource();
        if (textSource == titleText)
            System.out.println("用户名：" + titleText.getText());
        else if (textSource == passwordText)
        {
            char c[] = passwordText.getPassword();
            System.out.println("密码" + new String(c));
        }
    }
}