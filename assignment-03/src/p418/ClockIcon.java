package p418;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ClockIcon implements Icon {
    private int size;
    private GregorianCalendar calendar;

    ClockIcon(int size) {
        this.size = size;
        this.calendar = new GregorianCalendar();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D)g;

        double secondDelta = ((double) calendar.get(Calendar.SECOND)) / 60;
        double minuteDelta = (((double) calendar.get(Calendar.MINUTE)) / 60) + secondDelta / 60;
        double hourDelta = (((double) calendar.get(Calendar.HOUR_OF_DAY)) / 24) + minuteDelta / 60;
        double midpoint = ((double) size) / 2;

        g2d.setStroke(new BasicStroke(5));

        g2d.drawOval(0, 0, size, size);

        g2d.drawLine(
                (int) midpoint,
                (int) midpoint,
                (int) (midpoint + midpoint * Math.cos(2 * Math.PI * secondDelta - Math.PI * 0.5)),
                (int) (midpoint + midpoint * Math.sin(2 * Math.PI * secondDelta - Math.PI * 0.5))
        );

        g2d.drawLine(
                (int) midpoint,
                (int) midpoint,
                (int) (midpoint + midpoint / 2 * Math.cos(2 * Math.PI * minuteDelta - Math.PI * 0.5)),
                (int) (midpoint + midpoint / 2 * Math.sin(2 * Math.PI * minuteDelta - Math.PI * 0.5))
        );

        g2d.drawLine(
                (int) midpoint,
                (int) midpoint,
                (int) (midpoint + midpoint / 4 * Math.cos(2 * Math.PI * hourDelta - Math.PI * 0.5)),
                (int) (midpoint + midpoint / 4 * Math.sin(2 * Math.PI * hourDelta - Math.PI * 0.5))
        );
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    public void setCalendar(GregorianCalendar calendar) {
        this.calendar = calendar;
    }
}
