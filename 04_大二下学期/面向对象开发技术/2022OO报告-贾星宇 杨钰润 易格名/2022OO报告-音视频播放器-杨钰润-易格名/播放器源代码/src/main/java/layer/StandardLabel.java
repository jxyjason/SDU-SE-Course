package layer;

import javax.swing.*;
import java.awt.*;

public class StandardLabel extends JLabel {

    private final String template;

    public StandardLabel() {
        this(null);
    }

    public StandardLabel(String template) {
        this.template = template;
    }

    @Override
    public Dimension getMinimumSize() {
        Insets insets = getInsets();
        int w = insets.left + insets.right;
        int h = insets.top + insets.bottom;
        FontMetrics fontMetrics = getFontMetrics(getFont());
        h += fontMetrics.getHeight();
        String text = getText();
        if (template != null) {
            w += fontMetrics.stringWidth(template);
        }
        else {
            w += text != null && text.length() > 0 ? fontMetrics.stringWidth(text) : 32;
        }
        return new Dimension(w, h);
    }
}

