package utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LRCUtil {
    JTextArea lrcArea;
    protected List<Map<Long, String>> parse(String path) {
        // 存储所有歌词信息的容器
        List<Map<Long, String>> list = new ArrayList<Map<Long, String>>();
        try {
            String encoding = "utf-8"; // 字符编码，若与歌词文件编码不符将会出现乱码
            //String encoding = "GBK";
            File file = new File(path);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String regex = "\\[(\\d{1,2}):(\\d{1,2}).(\\d{1,3})\\]"; // 正则表达式
                Pattern pattern = Pattern.compile(regex); // 创建 Pattern 对象
                String lineStr = null; // 每次读取一行字符串
                while ((lineStr = bufferedReader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(lineStr);
                    while (matcher.find()) {
                        // 用于存储当前时间和文字信息的容器
                        Map<Long, String> map = new HashMap<Long, String>();
                        // System.out.println(m.group(0)); // 例：[02:34.94]
                        // [02:34.94] ----对应---> [分钟:秒.毫秒]
                        String min = matcher.group(1); // 分钟
                        String sec = matcher.group(2); // 秒
                        String mill = matcher.group(3); // 毫秒，注意这里其实还要乘以10
                        long time = getLongTime(min, sec, mill + "0");
                        // 获取当前时间的歌词信息
                        String text = lineStr.substring(matcher.end());
                        map.put(time, text); // 添加到容器中
                        list.add(map);
                    }
                }
                read.close();
                return list;
            } else {
                System.out.println("找不到指定的文件:" + path);
            }
        } catch (Exception e) {
            System.out.println("读取文件出错!");
            e.printStackTrace();
        }
        return null;
    }

    protected long getLongTime(String min, String sec, String mill) {
        // 转成整型
        int m = Integer.parseInt(min);
        int s = Integer.parseInt(sec);
        int ms = Integer.parseInt(mill);

        if (s >= 60) {
            System.out.println("警告: 出现了一个时间不正确的项 --> [" + min + ":" + sec + "."
                    + mill.substring(0, 2) + "]");
        }
        // 组合成一个长整型表示的以毫秒为单位的时间
        long time = m * 60 * 1000 + s * 1000 + ms;
        return time;
    }

    /**
     * 打印歌词信息
     */
    protected JTextArea showLrc(List<Map<Long, String>> list) {
        String lyrics = "                       ";
        if (list == null || list.isEmpty()) {
            System.out.println("暂无歌词信息");
        } else {
            for (Map<Long, String> map : list) {
                for (Entry<Long, String> entry : map.entrySet()) {
                    lyrics += entry.getValue() + "\n" + "\t";  //歌词
                    //时间  entry.getKey()
                }
            }
            lrcArea = new JTextArea(lyrics);
        }
        return lrcArea;
    }

}