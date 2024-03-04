package Student;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class timer{

    public static JLabel jl0 = new JLabel();
    public boolean testend=true;
    private ScheduledThreadPoolExecutor scheduled;

    public void run(){
        String a = new gettime().gettime();
        scheduled = new ScheduledThreadPoolExecutor(2);
        timer(a,jl0);
    }

    private Date String2Date(String dateStr,JLabel jLabel) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            jLabel.setText("");
            throw new IllegalArgumentException("");
        }
    }

    /*倒计时*/
    public void timer(String dateStr,JLabel jLabel) {
        Date end = String2Date(dateStr,jLabel);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long time = (end.getTime() - 1 - System.currentTimeMillis()) / 1000;
                if (time <= 0) {
                    stopTimer();
                    {
                        jLabel.setText("考试结束，请等待老师批改");
                        testend=false;//时间结束，testend=false
                    }return;
                }
                long hour = time / 3600;
                long minute = (time - hour * 3600) / 60;
                long seconds = time - hour * 3600 - minute * 60;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("距离考试结束还有").append(hour).append("时 ").append(minute).append("分 ").
                        append(seconds).append("秒 ");

                jLabel.setText(stringBuilder.toString());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /*停止定时器*/
    public void stopTimer() {
        if (scheduled != null) {
            scheduled.shutdownNow();
            scheduled = null;
        }
    }
    /* 构造 实现界面的开发 GUI */
    public timer() {
        run();
    }
    /* 组件的装配 */

    public static JLabel getLabel(){
        return jl0;
    }
}

