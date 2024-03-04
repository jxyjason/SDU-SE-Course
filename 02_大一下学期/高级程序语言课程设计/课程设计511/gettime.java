import java.util.Calendar;

public class gettime {
    String result="";
    public gettime(){};
    public String gettime() {
        Calendar now = Calendar.getInstance();
        int hours, minutes, seconds;
        hours = 0;
        minutes = 1;
        seconds = 0;
        int nowyear = now.get(Calendar.YEAR);
        int nowmonth = now.get(Calendar.MONTH)+1;
        int nowday = now.get(Calendar.DATE);
        int nowhour = now.get(Calendar.HOUR_OF_DAY);
        int nowminute = now.get(Calendar.MINUTE);
        int nowsecond = now.get(Calendar.SECOND);

        if (nowsecond + seconds < 60) {
            if (nowminute + minutes < 60) {
                if (nowhour + hours < 24) {
                    int second = nowsecond + seconds;
                    int minute = nowminute + minutes;
                    int hour = nowhour + hours;
                    result = nowyear + "-" + nowmonth + "-" + nowday + " " + hour + ":" + minute + ":" + second;
                } else {
                    int second = nowsecond + seconds;
                    int minute = nowminute + minutes;
                    int d1 = nowday+ 1;
                    int h1 = (nowhour + hours) % 24;
                    if (nowmonth == 1 && nowmonth == 3 && nowmonth == 5 && nowmonth == 7 && nowmonth == 8 && nowmonth == 10 && nowmonth == 12) {
                        if (d1 <= 31) {
                            result = nowyear + "-" + nowmonth + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                        else {
                            int d=1;
                            int month=nowmonth+1;
                            if(month<=12){
                                result = nowyear + "-" + month + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                            }
                            else{
                                int m=1;int year=nowyear+1;
                                result = year + "-" + m + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                            }
                        }
                    }
                    else if (nowmonth == 2 && nowmonth == 4 && nowmonth == 6 && nowmonth == 9 && nowmonth == 11) {
                        if (d1 <= 30) {
                            result = nowyear + "-" + nowmonth + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                        else {
                            int d=1;
                            int month=nowmonth+1;
                            result = nowyear + "-" + month + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                    }
                }
            }
            else{
                int minute=(nowminute+minutes)%60;
                int second=nowsecond+seconds;
                if (nowhour + hours +1< 24) {
                    int hour = nowhour + hours+1;
                    result = nowyear + "-" + nowmonth + "-" + nowday + " " + hour + ":" + minute + ":" + second;
                } else {
                    int d1 = nowday + 1;
                    int h1 = (nowhour + 1+hours) % 24;
                    if (nowmonth == 1 && nowmonth == 3 && nowmonth == 5 && nowmonth == 7 && nowmonth == 8 && nowmonth == 10 && nowmonth == 12) {
                        if (d1 <= 31) {
                            result = nowyear + "-" + nowmonth + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                        else {
                            int d=1;
                            int month=nowmonth+1;
                            if(month<=12){
                                result = nowyear + "-" + month + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                            }
                            else{
                                int m=1;int year=nowyear+1;
                                result = year + "-" + m + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                            }
                        }
                    }
                    else if (nowmonth == 2 && nowmonth == 4 && nowmonth == 6 && nowmonth == 9 && nowmonth == 11) {
                        if (d1 <= 30) {
                            result = nowyear + "-" + nowmonth + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                        else {
                            int d=1;
                            int month=nowmonth+1;
                            result = nowyear + "-" + month + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                    }
                }
            }
        }
        else{minutes++;
            if (nowminute + minutes < 60) {
                if (nowhour + hours < 24) {
                    int second = nowsecond + seconds;
                    int minute = nowminute + minutes;
                    int hour = nowhour + hours;
                    result = nowyear + "-" + nowmonth + "-" + nowday + " " + hour + ":" + minute + ":" + second;
                } else {
                    int second = nowsecond + seconds;
                    int minute = nowminute + minutes;
                    int d1 = nowday+ 1;
                    int h1 = (nowhour + hours) % 24;
                    if (nowmonth == 1 && nowmonth == 3 && nowmonth == 5 && nowmonth == 7 && nowmonth == 8 && nowmonth == 10 && nowmonth == 12) {
                        if (d1 <= 31) {
                            result = nowyear + "-" + nowmonth + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                        else {
                            int d=1;
                            int month=nowmonth+1;
                            if(month<=12){
                                result = nowyear + "-" + month + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                            }
                            else{
                                int m=1;int year=nowyear+1;
                                result = year + "-" + m + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                            }
                        }
                    }
                    else if (nowmonth == 2 && nowmonth == 4 && nowmonth == 6 && nowmonth == 9 && nowmonth == 11) {
                        if (d1 <= 30) {
                            result = nowyear + "-" + nowmonth + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                        else {
                            int d=1;
                            int month=nowmonth+1;
                            result = nowyear + "-" + month + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                    }
                }
            }
            else{
                int minute=(nowminute+minutes)%60;
                int second=nowsecond+seconds;
                if (nowhour + hours +1< 24) {
                    int hour = nowhour + hours+1;
                    result = nowyear + "-" + nowmonth + "-" + nowday + " " + hour + ":" + minute + ":" + second;
                } else {
                    int d1 = nowday + 1;
                    int h1 = (nowhour + 1+hours) % 24;
                    if (nowmonth == 1 && nowmonth == 3 && nowmonth == 5 && nowmonth == 7 && nowmonth == 8 && nowmonth == 10 && nowmonth == 12) {
                        if (d1 <= 31) {
                            result = nowyear + "-" + nowmonth + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                        else {
                            int d=1;
                            int month=nowmonth+1;
                            if(month<=12){
                                result = nowyear + "-" + month + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                            }
                            else{
                                int m=1;int year=nowyear+1;
                                result = year + "-" + m + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                            }
                        }
                    }
                    else if (nowmonth == 2 && nowmonth == 4 && nowmonth == 6 && nowmonth == 9 && nowmonth == 11) {
                        if (d1 <= 30) {
                            result = nowyear + "-" + nowmonth + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                        else {
                            int d=1;
                            int month=nowmonth+1;
                            result = nowyear + "-" + month + "-" + d1 + " " + h1 + ":" + minute + ":" + second;
                        }
                    }
                }
            }
        }
        return result;
    }
}
