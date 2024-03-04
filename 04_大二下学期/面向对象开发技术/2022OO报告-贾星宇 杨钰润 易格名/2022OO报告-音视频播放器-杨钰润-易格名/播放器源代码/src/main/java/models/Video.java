package models;


import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;

public class Video extends Player{
    File url;
    Double time;
    private static EmbeddedMediaPlayerComponent embeddedMediaPlayer = new EmbeddedMediaPlayerComponent();
    private static final Video video = new Video();

    public Video() {
        super(embeddedMediaPlayer.mediaPlayer());
    }
    public EmbeddedMediaPlayerComponent getInstance(){
        return embeddedMediaPlayer;
    }
}
