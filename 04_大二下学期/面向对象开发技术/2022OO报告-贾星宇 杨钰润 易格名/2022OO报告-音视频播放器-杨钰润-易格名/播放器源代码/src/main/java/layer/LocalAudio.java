package layer;

import models.Audio;
import models.Song;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import service.AudioService;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import utils.MusicUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

//获得歌曲列表
public class LocalAudio extends Main {

    ImageIcon i2 = new ImageIcon("D:\\UIresources\\AudioListBkg.png");
    Image pho2 = i2.getImage();
    ImageIcon i4 = new ImageIcon("D:\\UIresources\\AudioHeadBkg.png");
    Image pho4 = i4.getImage();
    static byte[] pic;
    //这里预留的vector是因为它自己有存一个预留的歌曲列表
    static Vector vector = new Vector();
    //对应的文件路径
    static List<String> filePath = new ArrayList<>();
    public static int indic = 0;
    static AudioPlayerComponent audioPlayerComponent;
    PlayPanel p1;
    ListPanel p2 = new ListPanel(pho2);
    LyricPanel p3 = new LyricPanel();
    headPanel p4 = new headPanel(pho4);

    MediaPlayerEventListener mediaPlayerEventListener = (new MediaPlayerEventAdapter() {


        @Override
        public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    p1.setDuration(newLength);
                }
            });
        }

        @Override
        public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    p1.refresh(newTime);
                }

            });
        }

        @Override
        public void mediaPlayerReady(MediaPlayer mediaPlayer) {
        }

        @Override
        public void mediaChanged(MediaPlayer mediaPlayer, MediaRef media) {
            super.mediaChanged(mediaPlayer, media);

        }

        public void finished(MediaPlayer mediaPlayer) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("结束" + indic);
                    try {
                        AudioService.mode(PlayPanel.ckModel,filePath);
                        reset(filePath.get(indic));
                        mediaPlayer.media().prepare(filePath.get(indic));
                        mediaPlayer.controls().play();
                        return;
                    } catch (ReadOnlyFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TagException e) {
                        e.printStackTrace();
                    } catch (InvalidAudioFrameException e) {
                        e.printStackTrace();
                    } catch (CannotReadException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    });

    public LocalAudio(Audio audio) {
        super();
        p1 = new PlayPanel(audio.getInstance().mediaPlayer());
        this.setVisible(true);
        audioPlayerComponent = audio.getInstance();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JScrollPane jsp = new JScrollPane(p2);
        p2.clear();
        jsp.setBounds(0, 120, 540, 580);
        p1.setBounds(0, 700, 1600, 150);
        p2.setBounds(0, 0, 500, 650);
        p3.setBounds(540, 120, 1060, 580);
        p3.setVisible(true);
        p4.setBounds(0, 0, 1600, 120);
        this.add(p1);
        this.add(jsp);
        this.add(p3);
        this.add(p4);
        audioPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(mediaPlayerEventListener);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                audio.stop();
                ListPanel.kind = -1;
            }

        });
    }

    public static AudioPlayerComponent getInstance() {
        return audioPlayerComponent;
    }

    public static void reset(String ml) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        audioPlayerComponent.mediaPlayer().media().prepare(ml);
        MusicUtil.getSong(ml);
        Song song = MusicUtil.song;
        LyricPanel.lrcArea.setText(song.getLyrics());
        ButtonControl.refresh(PlayPanel.pause);
        PlayPanel.ckPlay = 2;
        audioPlayerComponent.mediaPlayer().controls().play();

        PlayPanel.labelName.setText(song.getTitle() + "--" + song.getSinger());
        if (ml.contains("mp3")){
            pic = song.getPic();
        }else{
            pic = null;
        }

        PlayPanel.setImg(pic);

    }


}





