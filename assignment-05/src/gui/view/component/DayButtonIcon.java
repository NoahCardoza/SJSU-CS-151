package gui.view.component;

import javax.swing.*;
import java.awt.*;

public class DayButtonIcon implements Icon {
    private final int size;
    private String text;

    private boolean hasEvents;
    private boolean isToday;
    private boolean isSelected;

    public DayButtonIcon(String text, int size) {
        this.text = text;
        this.size = size;
        hasEvents = false;
        isToday = false;
        isSelected = false;
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *  <a href="https://stackoverflow.com/a/27740330/6169961">StackOverflow</a>
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.translate(x, y);

        if (isSelected) {
            g2.setColor(new Color(255, 175, 175, 80));
        } else if (isToday) {
            g2.setColor(Color.LIGHT_GRAY);
        }

        if (isSelected || isToday) {
            g2.fillOval(0, 0, size, size);
        }

        g2.setColor(Color.BLACK);

        drawCenteredString(g2, text, new Rectangle(0,0, size, size), g.getFont());

        if (hasEvents) {
            int diameter = 4;
            g2.setColor(Color.RED);
            g2.fillOval(size / 2 - diameter / 2, (int) (size - diameter * 1.5), diameter, diameter);
        }
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isHasEvents() {
        return hasEvents;
    }

    public void setHasEvents(boolean hasEvents) {
        this.hasEvents = hasEvents;
    }
}
