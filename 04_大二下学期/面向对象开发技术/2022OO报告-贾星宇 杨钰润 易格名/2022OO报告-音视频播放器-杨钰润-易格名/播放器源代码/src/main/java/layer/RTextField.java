package layer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RTextField extends JTextField {
        //private static final long serialVersionUID = -1946802815417758252L;

        void textSet(JTextField t) {
                t.setFont(new java.awt.Font("FZFW ZhenZhu Tis L", 0, 24));
                t.addFocusListener(new JTextFieldHintListener(t, "请输入搜索内容..."));
                t.setBorder(null);
        }
}

class JTextFieldHintListener implements FocusListener {
        private String hintText;
        private JTextField textField;
        public JTextFieldHintListener(JTextField jTextField,String hintText) {
                this.textField = jTextField;
                this.hintText = hintText;
                jTextField.setText(hintText);  //默认直接显示
                jTextField.setForeground(Color.GRAY);
        }

        @Override
        public void focusGained(FocusEvent e) {
                //获取焦点时，清空提示内容
                String temp = textField.getText();
                if(temp.equals(hintText)) {
                        textField.setText("");
                        textField.setForeground(new Color(103, 103, 103));
                }

        }

        @Override
        public void focusLost(FocusEvent e) {
                //失去焦点时，没有输入内容，显示提示内容
                String temp = textField.getText();
                if(temp.equals("")) {
                        textField.setForeground(new Color(192, 192, 192));
                        textField.setText(hintText);
                }

        }

}

