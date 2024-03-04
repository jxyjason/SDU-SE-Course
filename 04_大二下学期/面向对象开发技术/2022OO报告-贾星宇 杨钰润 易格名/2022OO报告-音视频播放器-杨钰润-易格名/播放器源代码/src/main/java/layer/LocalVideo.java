package layer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;

import models.Video;
import sun.plugin.javascript.navig.LinkArray;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
//

public class LocalVideo extends JFrame {
    private static final long serialVersionUID = 1L;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private static RButton playButton;
    private RButton rewindButton;
    private RButton skipButton;
    private RButton speed;
    private RButton findFile;
    private JProgressBar progress;
    static Vector video = new Vector();
    //对应的文件路径
    static List<String> path = new ArrayList<>();
    static int ckPlay = 1;
    int ckSpeed = 1;
    static Video v = new Video();
    private ButtonControl manager = new ButtonControl();
    ImageIcon i4 = new ImageIcon("D:\\UIresources\\VideoHeadBkg.png");
    Image pho4 = i4.getImage();

    ImageIcon i2 = new ImageIcon("D:\\UIresources\\VideoListBkg.png");
    Image pho2 = i2.getImage();

    ListPanel panel = new ListPanel(pho2);


    public LocalVideo(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mediaPlayerComponent = v.getInstance();
    }

    public void initialize() {
        this.setBounds(new Rectangle(2000, 1100));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                v.stop();
                ListPanel.kind=-1;
            }
        });


        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);//
        contentPane.add(mediaPlayerComponent);
        contentPane.add(panel);
        panel.clear();
        mediaPlayerComponent.setBounds(0, 0, 1500, 900);
        panel.setBounds(1500, 0, 500, 1200);
        JPanel videoControlsPane = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(pho4,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        videoControlsPane.setLayout(null);
        videoControlsPane.setBounds(0, 900, 1500, 200);
        findFile = new RButton("");
        findFile.setBounds(500, 50, 40, 40);
        findFile.setIcon("D:\\UIresources\\file.png", findFile);
        videoControlsPane.add(findFile);
        playButton = new RButton("");
        playButton.setBounds(760, 50, 40, 40);
        playButton.setIcon("D:\\UIresources\\pause.png", playButton);
        videoControlsPane.add(playButton);

        rewindButton = new RButton("Rewind");
        rewindButton.setBounds(670, 50, 40, 40);
        rewindButton.setIcon("D:\\UIresources\\back.png", rewindButton);
        videoControlsPane.add(rewindButton);
        skipButton = new RButton("Skip");
        skipButton.setBounds(960, 50, 40, 40);
        skipButton.setIcon("D:\\UIresources\\forward.png", skipButton);
        videoControlsPane.add(skipButton);
        speed = new RButton("Speed");
        speed.setBounds(1090, 50, 40, 40);
        speed.setIcon("D:\\UIresources\\1s.png", speed);

        progress = new JProgressBar();
        progress.setFocusable(false);
        progress.setBounds(0, 0, 1600, 10);

        //为进度条 progress 添加 mouseClicked 事件
        progress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //获取鼠标点击在进度条上的位置
                int x = e.getX();
                //计算点击位置占进度条总长的百分比
                float per = (float) x / progress.getWidth();
                mediaPlayerComponent.mediaPlayer().controls().setTime((long) (per * mediaPlayerComponent.mediaPlayer().status().length()));
                System.out.println(mediaPlayerComponent.mediaPlayer().media().info().duration());
            }
        });
        videoControlsPane.add(progress);
        videoControlsPane.setBackground(Color.BLUE);
        videoControlsPane.add(speed);
        contentPane.add(videoControlsPane, BorderLayout.SOUTH);


        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mediaPlayerComponent.mediaPlayer().media().info().mrl() != null) {
                    if (ckPlay % 2 == 1 && ckPlay != 1) {
                        mediaPlayerComponent.mediaPlayer().controls().play();
                    } else if (ckPlay % 2 == 0) {
                        mediaPlayerComponent.mediaPlayer().controls().pause();
                    } else if (ckPlay == 1) {
                        mediaPlayerComponent.mediaPlayer().controls().play();
                    }
                    ckPlay = manager.iconChange(ckPlay, playButton, "D:\\UIresources\\pause.png",
                            "D:\\UIresources\\play.png");
                }

            }
        });
        rewindButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().skipTime(-14000);
            }
        });
        skipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().skipTime(4000);
            }
        });
        speed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ckSpeed = manager.iconChange(ckSpeed, speed, "D:\\UIresources\\1s.png",
                        "D:\\UIresources\\1.5s.png", "D:\\UIresources\\2s.png", "D:\\UIresources\\0.5s.png");
                if (ckSpeed % 4 == 2) {
                    mediaPlayerComponent.mediaPlayer().controls().setRate(1.5F);
                } else if (ckSpeed % 4 == 3) {
                    mediaPlayerComponent.mediaPlayer().controls().setRate(2);
                } else if (ckSpeed % 4 == 0) {
                    mediaPlayerComponent.mediaPlayer().controls().setRate(0.5F);
                } else {
                    mediaPlayerComponent.mediaPlayer().controls().setRate(1);
                }
            }
        });
//
        findFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int option = chooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file1 = chooser.getSelectedFile();
                String ml = file1.getAbsolutePath();
                ListPanel.addVideoItem(ml);
                System.out.println(ml);

            }

        });
        this.setContentPane(contentPane);

    }

    public static void loadVideo(String path) {
        ButtonControl.iconChange(ckPlay, playButton, "D:\\UIresources\\pause.png",
                "D:\\UIresources\\play.png");
        v.getInstance().mediaPlayer().media().prepare(path);
        v.getInstance().mediaPlayer().controls().play();
    }


}

