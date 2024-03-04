package utils;

import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.Vector;

public class GetPclFile {
    public static List<String> musicPath = new ArrayList<>();
    public static List<String> videoPath = new ArrayList<>();
    public static Vector<String> musicName = new Vector<>();
    public static Vector<String> videoName = new Vector<>();

    //获取指定格式的文件，支持的格式包括MP3，MP4
    public static void GetPclLib(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File lf : listFiles) {
                if (lf.isFile()) {
                    if (lf.getAbsolutePath().endsWith(".mp3") || lf.getAbsolutePath().endsWith(".flac")) {

                        if (!musicPath.contains(lf.getAbsolutePath())) {
                            musicPath.add(lf.getAbsolutePath());
                            musicName.add(lf.getName());
                        }


                    }
                    if (lf.getAbsolutePath().endsWith(".mp4")) {
                        System.out.println(lf);
                        if (!videoPath.contains(lf.getAbsolutePath())) {
                            videoPath.add(lf.getAbsolutePath());
                            videoName.add(lf.getName());
                        }

                    }
                } else {
                    if (lf.isDirectory()) {
                        GetPclLib(lf);
                    }
                }
            }
        }
    }
}
