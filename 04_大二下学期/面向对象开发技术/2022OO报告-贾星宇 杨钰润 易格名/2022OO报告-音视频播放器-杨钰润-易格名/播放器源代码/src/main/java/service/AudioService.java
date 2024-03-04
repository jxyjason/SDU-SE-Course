package service;


import layer.LocalAudio;
import models.Song;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import utils.MusicUtil;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static layer.LocalAudio.indic;

public class AudioService {
    //让MP3文件的内容获得
    public static Song getSong(String ml) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        if (ml.contains(".mp3")) {
            MusicUtil.getSong(ml);
            Song song = MusicUtil.song;
            if (song != null) {
                return song;
            }
        }
        return null;
    }

    //三种模式播放，通过改变list中的count,在播放结束前三秒进行prepare,play操作.单曲循环模式，可以设置音乐为repeat
    //调用时机：一首歌结束，切换模式；自己选择歌曲和从文件夹中选择歌曲比这个的优先级高
    public static void mode(int clickCount, List filePath) {

        if (clickCount % 3 == 0) {
            indic = new Random().nextInt(filePath.size());

        } else if (clickCount % 3 == 1) {
            if (indic+1==filePath.size())
            {
                indic = 0;
            }
            else {
                indic++;
            }
        }
    }

    //在文件夹中选择后，首先改变prepare，然后再改变图标，然后play
    public static void choose(String ml) {
        LocalAudio.getInstance().mediaPlayer().media().prepare(ml);

    }

    //
    public static int search(Vector<String> vector,String search){
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i).contains(search)){
                return i;
            }
        }
        return -1;
    }

}
