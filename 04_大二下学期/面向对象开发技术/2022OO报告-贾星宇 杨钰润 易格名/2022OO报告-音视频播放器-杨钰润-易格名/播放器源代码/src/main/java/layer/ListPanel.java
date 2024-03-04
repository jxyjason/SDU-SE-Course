package layer;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import utils.GetPclFile;
import utils.MusicUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static layer.LocalAudio.*;
import static layer.LocalAudio.vector;

public class ListPanel extends gPanel {
   static JList<String> list = new JList<>();
    public static int kind;

    public ListPanel(Image pho) {
        super(pho);
        this.setLayout(null);
        list.setBounds(0, 0, 500, 650);
        list.setBackground(new Color(255, 255, 255, 0));
        list.setOpaque(false);
        list.setCellRenderer(new MyRenderer());
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 获取所有被选中的选项索引
               if (kind==1){
                   indic = list.getSelectedIndex();
                   try {
                       LocalAudio.reset(filePath.get(indic));
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
              else if (kind==2){

                   indic = list.getSelectedIndex();
                   String location = LocalVideo.path.get(indic);
                   LocalVideo.loadVideo(location);
               }

            }
        });
        this.add(list);
    }

    public static void addAudioItem(String path) {
        kind=1;
        GetPclFile.GetPclLib(new File(path));
        LocalAudio.vector = GetPclFile.musicName;
        LocalAudio.filePath = GetPclFile.musicPath;
        list.setListData(LocalAudio.vector);
    }

    public static void addVideoItem(String path) {
        kind=2;
        GetPclFile.GetPclLib(new File(path));
        LocalVideo.video = GetPclFile.videoName;
        LocalVideo.path = GetPclFile.videoPath;
        list.setListData(LocalVideo.video);
    }
    public void clear(){
        list.setListData(new String[]{});
    }

}
