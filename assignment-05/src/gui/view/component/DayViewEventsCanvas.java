package gui.view.component;

import calender.Event;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DayViewEventsCanvas extends JComponent {
    static final int ROW_HEIGHT = 80;
    static final int ROWS = 24;

    private ArrayList<Event> events;
    private int maxHourHeadingWidth;

    public DayViewEventsCanvas() {
        super();

        setPreferredSize(new Dimension(0, ROWS * ROW_HEIGHT + 1));

        this.events = new ArrayList<>();
    }

    @Override
    public int getHeight() {
        return ROWS * ROW_HEIGHT + 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int fontHeight = getFontMetrics(getFont()).getHeight();
        // TODO: move this somewhere else without causing a null ptr exception
        this.maxHourHeadingWidth = calculateMaxHourHeadingWidth();


        for (int i = 0; i < ROWS; i++) {
            int height = i * ROW_HEIGHT;
            g2.setColor(Color.lightGray);
            g2.drawLine(0, height, getWidth(), height);
            g2.drawLine(0, height + ROW_HEIGHT / 2, getWidth(), height + ROW_HEIGHT / 2);
            g2.setColor(Color.black);
            g2.drawString(formatHour(i), 0, height + fontHeight);
        }

        for (Event event : events) {
            g2.setColor(new Color(255, 175, 175, 80));

            Rectangle eventRect = rectFromEvent(event);
            eventRect.width = getWidth() - eventRect.x - 5;
            g2.fill(eventRect);

            g2.setColor(new Color(255, 175, 175));
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(eventRect.x, eventRect.y, eventRect.x, eventRect.y + eventRect.height);

            g2.setColor(Color.darkGray);
            g2.drawString(event.getStartTime().toString(), eventRect.x + 5, eventRect.y + 5 + fontHeight);

            g2.setColor(Color.black);
            drawWrappedText(g, event.getName(), new Rectangle(
                    eventRect.x + 5,
                    eventRect.y + 5 + fontHeight + 5,
                    eventRect.width - 10,
                    eventRect.height
            ));
        }
    }

    /**
     * <a href="https://stackoverflow.com/a/66041121/6169961">StackOverflow</a>
     * @param g the graphics object to draw to
     * @param text the text to draw
     * @param rect the rectangle to keep the text inside
     */
    static void drawWrappedText(Graphics g, String text, Rectangle rect) {
        JTextArea ta = new JTextArea(text);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBounds(0, 0, rect.width, rect.height);
        ta.setBackground(new Color(0,0,0,0));
        ta.setForeground(g.getColor());
        ta.setFont(g.getFont());
        Graphics g2 = g.create(rect.x, rect.y, rect.width, rect.height); // Use new graphics to leave original graphics state unchanged
        ta.paint(g2);
    }

    private static String formatHour(int n) {
        return String.format("%2s:00", n).replace(' ', '0');
    }

    private int calculateMaxHourHeadingWidth() {
        int max = 0;

        for (int i = 0; i < ROWS; i++) {
            int headingWidth = getFontMetrics(getFont()).stringWidth(formatHour(i));
            if (headingWidth > max) {
                max = headingWidth;
            }
        }

        return max;
    }

    private Rectangle rectFromEvent(Event event) {
        int x = maxHourHeadingWidth + 10;
        int y = ROW_HEIGHT * event.getStartTime().getHour() + ((int) (event.getStartTime().getMinute() / 60F * ROW_HEIGHT));

        return new Rectangle(
                x,
                y,
                100,
                ROW_HEIGHT * (event.getEndTime().getHour() - event.getStartTime().getHour()) + ((int) (event.getEndTime().getMinute() / 60F * ROW_HEIGHT))
        );
    }

    public void scrollToEvent(Event event) {
        Rectangle rect = rectFromEvent(event);

        rect.y -= ROW_HEIGHT;
        rect.height += ROW_HEIGHT;

        scrollRectToVisible(rect);
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        repaint();
    }
}
