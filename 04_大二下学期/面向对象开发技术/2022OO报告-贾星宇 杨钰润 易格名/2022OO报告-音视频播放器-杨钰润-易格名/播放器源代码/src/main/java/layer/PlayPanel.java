package layer;


import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import service.AudioService;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import utils.MusicUtil;
import utils.Time;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static layer.LocalAudio.*;
import static layer.LocalAudio.indic;

public class PlayPanel extends gPanel {
    MediaPlayer mediaPlayer;
    static RButton pause;
    RButton next, last, model, speed;
    static JProgressBar progress;

    static JLabel timeLabel;
    static JLabel durationLabel;
    JSlider positionSlider;

    final AtomicBoolean sliderChanging = new AtomicBoolean();
    final AtomicBoolean positionChanging = new AtomicBoolean();
    final static AtomicBoolean end = new AtomicBoolean();
    static JLabel labelImage, labelName, voiceControl;
    JSlider slider;
    static int ckPlay = 1;
    static int ckModel = 1;
    static int ckSpeed = 1;
    static ImageIcon i1 = new ImageIcon("D:\\UIresources\\AudioPlayerBkg.png");
    static Image pho1 = i1.getImage();


    public PlayPanel(MediaPlayer mediaPlayer) {
        super(pho1);
        this.setLayout(null);
        this.mediaPlayer = mediaPlayer;
        pause = new RButton("");
        next = new RButton("");
        last = new RButton("");
        model = new RButton("");
        speed = new RButton("");
        pause.setBounds(760, 30, 70, 70);
        last.setBounds(670, 35, 60, 60);
        next.setBounds(860, 35, 60, 60);
        model.setBounds(600, 45, 50, 50);
        speed.setBounds(945, 50, 45, 35);
        pause.setIcon("D:\\UIresources\\pause.png", pause);
        last.setIcon("D:\\UIresources\\last.png", last);
        next.setIcon("D:\\UIresources\\next.png", next);
        model.setIcon("D:\\UIresources\\list.png", model);
        speed.setIcon("D:\\UIresources\\1s.png", speed);
        this.add(pause);
        this.add(last);
        this.add(next);//3个按钮要加监听
        this.add(model);
        this.add(speed);


        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ckPlay = ButtonControl.iconChange(ckPlay, pause, "D:\\UIresources\\pause.png",
                        "D:\\UIresources\\play.png");
                if (ckPlay % 2 == 0) {
                    mediaPlayer.controls().play();
                    if (vector.size() >= 1) {
                        labelName.setText(String.valueOf(vector.get(indic)));
                    }
                } else if (ckPlay % 2 == 1) {
                    mediaPlayer.controls().pause();
                }
            }
        });

        model.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ckModel = ButtonControl.iconChange(ckModel, model, "D:\\UIresources\\list.png",
                        "D:\\UIresources\\single.png", "D:\\UIresources\\random.png");
                AudioService.mode(ckModel, filePath);
            }
        });

        speed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ckSpeed = ButtonControl.iconChange(ckSpeed, speed, "D:\\UIresources\\1s.png",
                        "D:\\UIresources\\1.5s.png", "D:\\UIresources\\2s.png", "D:\\UIresources\\0.5s.png");
                if (ckSpeed % 4 == 2) {
                    mediaPlayer.controls().setRate(1.5F);
                } else if (ckSpeed % 4 == 3) {
                    audioPlayerComponent.mediaPlayer().controls().setRate(2);
                } else if (ckSpeed % 4 == 0) {
                    audioPlayerComponent.mediaPlayer().controls().setRate(0.5F);
                } else {
                    audioPlayerComponent.mediaPlayer().controls().setRate(1);
                }
            }
        });

        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indic == 0) {
                    indic = filePath.size() - 1;

                } else {
                    indic--;
                }
                try {
                    MusicUtil.getSong(filePath.get(indic));
                } catch (TagException tagException) {
                    tagException.printStackTrace();
                } catch (ReadOnlyFileException readOnlyFileException) {
                    readOnlyFileException.printStackTrace();
                } catch (CannotReadException cannotReadException) {
                    cannotReadException.printStackTrace();
                } catch (InvalidAudioFrameException invalidAudioFrameException) {
                    invalidAudioFrameException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                pic = MusicUtil.song.getPic();
                setImg(pic);
                labelName.setText(MusicUtil.song.getTitle());
                LyricPanel.lrcArea.setText(MusicUtil.song.getLyrics());
                mediaPlayer.media().prepare(filePath.get(indic));
                mediaPlayer.controls().play();
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indic == filePath.size() - 1) {
                    indic = 0;
                } else {
                    indic++;
                }
                try {
                    MusicUtil.getSong(filePath.get(indic));
                } catch (TagException tagException) {
                    tagException.printStackTrace();
                } catch (ReadOnlyFileException readOnlyFileException) {
                    readOnlyFileException.printStackTrace();
                } catch (CannotReadException cannotReadException) {
                    cannotReadException.printStackTrace();
                } catch (InvalidAudioFrameException invalidAudioFrameException) {
                    invalidAudioFrameException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                pic = MusicUtil.song.getPic();
                setImg(pic);
                labelName.setText(MusicUtil.song.getTitle());
                LyricPanel.lrcArea.setText(MusicUtil.song.getLyrics());
                audioPlayerComponent.mediaPlayer().media().prepare(filePath.get(indic));
                audioPlayerComponent.mediaPlayer().controls().play();
            }
        });
        //进度条
        progress = new JProgressBar();
        progress.setFocusable(false);
        progress.setBounds(0, 0, 1600, 10);
        progress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                float per = (float) x / progress.getWidth();
                audioPlayerComponent.mediaPlayer().controls().setTime((long) (per * audioPlayerComponent.mediaPlayer().status().length()));
                System.out.println(audioPlayerComponent.mediaPlayer().media().info().duration());
            }
        });
        this.add(progress);


        timeLabel = new StandardLabel("9:99:99");
        timeLabel.setBounds(30, 20, 100, 10);
        UIManager.put("Slider.paintValue", true);
        positionSlider = new JSlider();
        positionSlider.setMinimum(0);
        positionSlider.setMaximum(1000);
        positionSlider.setValue(0);

        positionSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!positionChanging.get()) {
                    JSlider source = (JSlider) e.getSource();
                    if (source.getValueIsAdjusting()) {
                        sliderChanging.set(true);
                    } else {
                        sliderChanging.set(false);
                    }
                    audioPlayerComponent.mediaPlayer().controls().setPosition(source.getValue() / 1000.0f);
                }
            }
        });

        durationLabel = new StandardLabel("9:99:99");
        durationLabel.setBounds(1500, 20, 100, 10);
        timeLabel.setText("-:--:--");
        durationLabel.setText("-:--:--");

        this.add(timeLabel);
        this.add(positionSlider, "grow");
        this.add(durationLabel, "shrink");


        // JLabel(歌曲信息)


        labelImage = new JLabel();

        labelImage.setBounds(50, 30, 100, 100);

        labelName = new JLabel("welcome");

        labelName.setBounds(250, 50, 300, 50);
        labelName.setFont(new Font("FZFW ZhenZhu Tis L", 0, 24));
        labelName.setForeground(Color.white);
        this.add(labelImage);
        this.add(labelName);

        //音量调节
        ImageIcon icon2 = new ImageIcon("D:\\UIresources\\voice.png");
        icon2.setImage(icon2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        voiceControl = new JLabel(icon2);
        voiceControl.setBounds(1100, 45, 50, 50);
        slider = new JSlider();
        slider.setFocusable(false);
        //关于默认音量的设定要在 stateChanged 事件之前
        // 如果要设定最大音量和最小音量也是如此
        slider.setValue(100);
        slider.setBounds(1190, 60, 290, 18);
        //为音量调节 slider 添加 stateChanged 事件
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                audioPlayerComponent.mediaPlayer().audio().setVolume(slider.getValue());
            }
        });//操作提示：系统音量提前调好，开始播放后不要调系统音了。
        this.add(voiceControl);
        this.add(slider);


        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {//键盘监听，已实现空格键暂停
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:

                    case KeyEvent.VK_LEFT:

                    case KeyEvent.VK_UP:

                    case KeyEvent.VK_DOWN:

                    case KeyEvent.VK_SPACE:
                        ckPlay = ButtonControl.iconChange(ckPlay, pause, "D:\\UIresources\\pause.png",
                                "D:\\UIresources\\play.png");
                        if (ckPlay % 2 == 0) {
                            audioPlayerComponent.mediaPlayer().controls().play();
                        } else if (ckPlay % 2 == 1) {
                            audioPlayerComponent.mediaPlayer().controls().pause();
                        }
                    default:

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    public void refresh(long time2) {
        timeLabel.setText(Time.formatTime(time2));
        if (!sliderChanging.get()) {
            int value = (int) (audioPlayerComponent.mediaPlayer().status().position() * 1000.0f);
            positionChanging.set(true);
            positionSlider.setValue(value);
            positionChanging.set(false);
        }
    }


    static void setDuration(long duration) {
        durationLabel.setText(Time.formatTime(duration));
        progress.setString(Time.formatTime(duration));
    }

    static void setImg(byte[] pic) {
        ImageIcon icon;
        if (pic != null) {
            icon = new ImageIcon(pic);
        } else {
            icon = new ImageIcon("D:\\UIresources\\pic.png");
        }
        icon.setImage(icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        labelImage.setIcon(icon);
    }

}
