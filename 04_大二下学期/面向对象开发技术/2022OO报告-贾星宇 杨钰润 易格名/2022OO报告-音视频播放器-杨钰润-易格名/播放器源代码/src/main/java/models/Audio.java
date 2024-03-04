package models;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

public class Audio extends Player {
    private static final AudioPlayerComponent audioPlayerComponent = new AudioPlayerComponent();

    public Audio() {
        super(audioPlayerComponent.mediaPlayer());
        audioPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.out.println("Audio Playback Finished.");
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                System.out.println("Failed to load Audio.");
            }

            @Override
            public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
                super.lengthChanged(mediaPlayer, newLength);

            }
        });
    }


    public AudioPlayerComponent getInstance() {
        return audioPlayerComponent;
    }
}
