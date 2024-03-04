package layer;


import javax.swing.*;
import java.awt.*;

public class LyricPanel extends gPanel {
    static ImageIcon i3 = new ImageIcon("D:\\UIresources\\AudioLyricsBkg.png");
    static Image pho3 = i3.getImage();
    static JTextArea lrcArea;


    public LyricPanel() {
        super(pho3);
        this.setLayout(null);
        this.setVisible(true);
        lrcArea = new JTextArea();
        lrcArea.setBounds(0, 0, 1060, 580);
        lrcArea.setEditable(false);
        lrcArea.setFont(new Font("FZFW ZhenZhu Tis L", 0, 25));

        JScrollPane jsp = new JScrollPane(lrcArea);
        jsp.setBounds(0, 0, 1060, 580);
        jsp.setOpaque(false);
        jsp.getViewport().setOpaque(false);
        this.add(jsp);
        lrcArea.setVisible(true);
    }

}



