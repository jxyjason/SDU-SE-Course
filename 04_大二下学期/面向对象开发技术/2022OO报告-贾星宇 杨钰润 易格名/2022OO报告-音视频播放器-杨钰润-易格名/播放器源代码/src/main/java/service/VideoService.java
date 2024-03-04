package service;


import uk.co.caprica.vlcj.player.base.MediaApi;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import utils.GetPclFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VideoService {
    //在video中设置播放模式，分为三种：单曲循环，顺序播放（所谓顺序就是我从文件夹下获取他们的顺序）,随机播放（那就是生成随机数）
    String location;
    List<String> fileName;
    static int count;
    Random random = new Random();

    public VideoService(String location) {
        this.location = location;
        File file = new File(location);
        GetPclFile.GetPclLib(file);
//        fileName = GetPclFile.video;
    }

    public void playService(MediaPlayer mediaPlayer,int mode,int change) {
        switch (mode){
            //逻辑问题，单曲循环和随机也可以change
            case 0:playSingle(mediaPlayer);
            break;
            case 1:playOrder(mediaPlayer,change);
            break;
            case 2:playRand(mediaPlayer);
            break;
        }
    }

    public void playSingle(MediaPlayer media) {
        media.controls().setRepeat(true);
    }

    public void playOrder(MediaPlayer mediaPlayer, int count) {
        this.count = count;

        try {
            mediaPlayer.media().prepare(fileName.get(count));
            this.count++;
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public void playRand(MediaPlayer mediaPlayer) {
        this.count = random.nextInt(fileName.size());
        try {
            mediaPlayer.media().prepare(fileName.get(count));
            this.count++;
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

}
