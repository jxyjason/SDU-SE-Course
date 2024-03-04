package ThreadTest;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GUIWithMultiThread extends JFrame
{
    private int count = 0;
    private JButton start = new JButton("开始"), onOff = new JButton("结束");
    private JTextField t = new JTextField(10);
    private boolean runFlag = true;

    public GUIWithMultiThread()
    {
        setSize(400, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(t);
        start.addActionListener(new StartL());
        this.add(start);
        onOff.addActionListener(new OnOffL());
        this.add(onOff);
    }

    private class SeparateSubTask extends Thread
    {
        private int count = 0;
        private boolean runFlag = true;

        SeparateSubTask()
        {
            start();
        }

        void invertFlag()
        {
            runFlag = !runFlag;
        }

        public void run()
        {
            while (true)
            {
                try
                {
                    sleep(100);
                }
                catch (InterruptedException e)
                {
                }
                if (runFlag)
                    t.setText(Integer.toString(count++));
            }
        }
    }

    private SeparateSubTask sp = null;

    class StartL implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (sp == null)
                sp = new SeparateSubTask();
        }
    }

    class OnOffL implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (sp != null)
                sp.invertFlag();
        }
    }

    public static void main(String[] args)
    {
        new GUIWithMultiThread().setVisible(true);
    }
}
