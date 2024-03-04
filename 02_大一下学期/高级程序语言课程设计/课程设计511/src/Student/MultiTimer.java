package Student;

import javax.swing.*;

public class MultiTimer extends Thread{
    JLabel jLabel = new JLabel();

    public MultiTimer(){
        start();
    }


    public void run(){
        String a = new gettime().gettime();
        timer cTimer = new timer();
        cTimer.timer(a,jLabel);
    }

    public JLabel getLabel(){
        return jLabel;
    }
}
