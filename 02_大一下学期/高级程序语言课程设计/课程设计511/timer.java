import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class timer extends  JFrame{
    public JFrame frame;
    public JLabel jl0;
    public JButton jbt=new JButton();
    public boolean testend=true;
    private ScheduledThreadPoolExecutor scheduled;
    public static void main(String[] args) {
       gettime ab=new gettime();
       String a=ab.gettime();
       new timer().timer(a);
    }
    private Date String2Date(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            jl0.setText("");
            throw new IllegalArgumentException("");
        }
    }
    /*倒计时*/
    public void timer(String dateStr) {
        Date end = String2Date(dateStr);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long time = (end.getTime() - 1 - System.currentTimeMillis()) / 1000;
                if (time <= 0) {
                    stopTimer();
                    {
                        jl0.setText("考试结束，请等待老师批改");
                        testend=false;//时间结束，testend=false
                    }return;
                }
                long hour = time / 3600;
                long minute = (time - hour * 3600) / 60;
                long seconds = time - hour * 3600 - minute * 60;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("距离考试结束还有").append(hour).append("时 ").append(minute).append("分 ").append(seconds).append("秒 ");
                jl0.setText(stringBuilder.toString());
            }
        }, 0, 1, TimeUnit.SECONDS);

    }
    /*停止定时器*/
    private void stopTimer() {
        if (scheduled != null) {
            scheduled.shutdownNow();
            scheduled = null;
        }
    }
    /* 构造 实现界面的开发 GUI */
    public timer() {
        scheduled = new ScheduledThreadPoolExecutor(2);
        init();
    }
    /* 组件的装配 */
    private void init() {
        frame = new JFrame("考试倒计时");
        jl0 = new JLabel();
        JPanel jp = new JPanel();
        jp.add(jl0);
        frame.add(jp);
        frame.setVisible(true);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jl0.setSize(200,100);
    }
}