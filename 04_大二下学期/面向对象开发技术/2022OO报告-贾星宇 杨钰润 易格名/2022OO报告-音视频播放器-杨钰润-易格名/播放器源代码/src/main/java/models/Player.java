package models;


import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.File;

public abstract class Player {
    protected MediaPlayer mediaPlayer;

    protected String location;

    private long time;

    void play() {
        if (mediaPlayer != null) {
            mediaPlayer.controls().play();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.controls().stop();
        }
    }

    void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.controls().pause();
        }
    }

    void setToTime() {
        if (mediaPlayer != null) {
            mediaPlayer.controls().setTime(5000);
        }
    }

    void getToTime() {
        if (mediaPlayer != null) {
            mediaPlayer.controls().skipTime(1000);
        }
    }

    void setTime() {
        if (mediaPlayer != null) {
            this.time = mediaPlayer.status().time();
        }
    }

    void prepare() {
        mediaPlayer.media().prepare(location);
    }

    void setMediaPlayer(MediaPlayer m) {
        this.mediaPlayer = m;
    }

    void setLocation(String url) {
        location = url;
    }

    public Player(MediaPlayer m) {
        this.mediaPlayer = m;
    }
}
