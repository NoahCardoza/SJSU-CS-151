/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui.view.component;

import gui.model.DayButtonModel;

import javax.swing.*;
import java.awt.*;

/**
 * A button that represents each day of a calendar month.
 */
public class DayButton extends JButton {
    private final DayButtonIcon icon;

    /**
     * Constructs a new day button.
     *
     * @param model the data model to control the look of the button
     * @param size the size of the button
     */
    public DayButton(DayButtonModel model, int size) {
        super();

        icon = new DayButtonIcon(model.getText(), size);
        setPreferredSize(new Dimension(size, size));
        setBorder(null);

        model.addEventListener("update:isVisible", (event) -> {
            setVisible(model.getVisible());
        }, true);

        model.addEventListener("update:text", (event) -> {
            setIconText(model.getText());
        }, true);

        model.addEventListener("update:hasEvents", (event) -> {
            setHasEvents(model.isHasEvents());
        }, true);

        model.addEventListener("update:isToday", (event) -> {
            setToday(model.isToday());
        }, true);

        model.addEventListener("update:isSelected", (event) -> {
            setButtonSelected(model.isSelected());
        }, true);

        setIcon(icon);
    }


    private void setIconText(String text) {
        if (icon.getText().equals(text)) {
            return;
        }
        icon.setText(text);
        repaint();
    }

    private void setToday(boolean today) {
        if (icon.isToday() == today) {
            return;
        }

        icon.setToday(today);
        repaint();
    }

    private void setButtonSelected(boolean selected) {
         if (icon.isSelected() == selected) {
            return;
        }

        icon.setSelected(selected);
        repaint();
    }

    private void setHasEvents(boolean hasEvents) {
        if (icon.isHasEvents() == hasEvents) {
            return;
        }

        icon.setHasEvents(hasEvents);
        repaint();
    }
}
