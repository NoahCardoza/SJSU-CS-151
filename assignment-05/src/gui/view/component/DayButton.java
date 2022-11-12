package gui.view.component;

import javax.swing.*;
import java.awt.*;

public class DayButton extends JButton {
    private final DayButtonIcon icon;

    public DayButton(String text, int size) {
        super();

        icon = new DayButtonIcon(text, size);
        setPreferredSize(new Dimension(size, size));
        setBorder(null);

        setIcon(icon);
    }

    public void setText(String text) {
        if (icon.getText().equals(text)) {
            return;
        }
        icon.setText(text);
        repaint();
    }

    public void setToday(boolean today) {
        if (icon.isToday() == today) {
            return;
        }

        icon.setToday(today);
        repaint();
    }

    public void setSelected(boolean selected) {
         if (icon.isSelected() == selected) {
            return;
        }

        icon.setSelected(selected);
        repaint();
    }

    public void setHasEvents(boolean hasEvents) {
        if (icon.isHasEvents() == hasEvents) {
            return;
        }

        icon.setHasEvents(hasEvents);
    }
}
