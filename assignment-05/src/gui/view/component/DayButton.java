package gui.view.component;

import gui.model.DayButtonModel;

import javax.swing.*;
import java.awt.*;

public class DayButton extends JButton {
    private final DayButtonIcon icon;

    public DayButton(DayButtonModel model, int size) {
        super();

        icon = new DayButtonIcon(model.getText(), size);
        setPreferredSize(new Dimension(size, size));
        setBorder(null);

        model.addEventListener("update:isVisible", (event) -> {
            setVisible(model.getVisible());
        });

        model.addEventListener("update:text", (event) -> {
            setText(model.getText());
        });

        model.addEventListener("update:hasEvents", (event) -> {
            setHasEvents(model.isHasEvents());
        });

        model.addEventListener("update:isToday", (event) -> {
            setToday(model.isToday());
        });

        model.addEventListener("update:isSelected", (event) -> {
            setSelected(model.isSelected());
        });

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
