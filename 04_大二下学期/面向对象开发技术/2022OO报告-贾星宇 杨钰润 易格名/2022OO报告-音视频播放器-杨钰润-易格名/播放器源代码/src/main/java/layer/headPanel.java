package layer;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import service.AudioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static layer.LocalAudio.*;
import static layer.LocalAudio.indic;

public class headPanel extends gPanel {
    String searchContent;
    JButton mnFile;

    public headPanel(Image pho) {
        super(pho);
        this.setLayout(null);
        ImageIcon icon3 = new ImageIcon("D:\\UIresources\\file.png");
        icon3.setImage(icon3.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        JLabel file = new JLabel(icon3);
        file.setBounds(370, 50, 30, 30);
        mnFile = new JButton("选择文件");
        mnFile.setBounds(400, 50, 140, 35);
        mnFile.setContentAreaFilled(false);
        mnFile.setBorderPainted(false);
        mnFile.setFont(new Font("FZFW ZhenZhu Tis L", 1, 24));
        mnFile.setForeground(Color.white);
        mnFile.addActionListener(e -> {
            JLabel label = new JLabel();
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int option = chooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file1 = chooser.getSelectedFile();
                String ml = file1.getAbsolutePath();
                System.out.println(ml);
                ListPanel.addAudioItem(ml);
                label.setText("选择文件或目录: " + file1.getName());
            } else {
                label.setText("打开命令取消");
            }
        });
        this.add(file);
        this.add(mnFile);


        RTextField t = new RTextField();
        t.textSet(t);
        t.setBounds(940, 50, 500, 40);
        RButton search = new RButton("");
        search.setBounds(1450, 50, 40, 40);
        search.setIcon("D:\\UIresources\\search.png", search);
        this.add(t);
        this.add(search);
        search.addActionListener(new ActionListener() {//获取文本框输入的信息
            @Override
            public void actionPerformed(ActionEvent e) {
                searchContent = t.getText();
                int d = AudioService.search(vector, searchContent);
                if (d!=-1){
                    indic = d;
                    audioPlayerComponent.mediaPlayer().media().prepare(filePath.get(d));
                    audioPlayerComponent.mediaPlayer().controls().play();
                    try {
                        reset(filePath.get(d));
                    } catch (ReadOnlyFileException readOnlyFileException) {
                        readOnlyFileException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (TagException tagException) {
                        tagException.printStackTrace();
                    } catch (InvalidAudioFrameException invalidAudioFrameException) {
                        invalidAudioFrameException.printStackTrace();
                    } catch (CannotReadException cannotReadException) {
                        cannotReadException.printStackTrace();
                    }
                }
                else{
                    System.out.println(indic);
                }
            }
        });
        //回车监听和空格监听有冲突，待解决。
    }


}
