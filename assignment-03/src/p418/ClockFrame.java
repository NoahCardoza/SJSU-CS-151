package p418;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

public class ClockFrame extends JFrame {

    static String calendarToString(GregorianCalendar calendar) {
        return String.format(
                "%02d:%02d:%02d",
                calendar.get(GregorianCalendar.HOUR_OF_DAY),
                calendar.get(GregorianCalendar.MINUTE),
                calendar.get(GregorianCalendar.SECOND)
        );
    }
    ClockFrame() {
        super("Clock");

        GregorianCalendar calendar = new GregorianCalendar();

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        JPanel contentPanel = new JPanel();

        contentPanel.setBorder(padding);

        setContentPane(contentPanel);

        
        ClockIcon clockIcon = new ClockIcon(500);
        Label timeLabel = new Label(calendarToString(calendar));
        JLabel clockLabel = new JLabel(clockIcon);


        add(timeLabel);
        add(clockLabel);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Timer time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GregorianCalendar calendar = new GregorianCalendar();
                clockIcon.setCalendar(calendar);
                clockLabel.repaint();
                timeLabel.setText(calendarToString(calendar));
            }
        });
        time.start();
    }
}
