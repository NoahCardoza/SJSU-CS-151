package gui.view.component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MonthView extends JPanel {
    private static final int rows = 7;
    private static final int cols = 7;
    private static final int tableCellSize = 30;
    private static final String monthTableHeader = "SMTWTFS";
    private static final Dimension tableCellDimensions = new Dimension(tableCellSize, tableCellSize);
    private final JLabel headerLabel;
    private final ArrayList<DayButton> dayButtons;
    public MonthView() {
        super();

        dayButtons = new ArrayList<>();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel header = new JPanel();
        JPanel body = new JPanel(new GridLayout(rows, cols));
        body.setPreferredSize(new Dimension(cols * tableCellSize, rows * tableCellSize));

        headerLabel = new JLabel();
        header.add(headerLabel);

        monthTableHeader.chars().forEachOrdered((chr) -> {
            JLabel label = new JLabel(Character.toString(chr), SwingConstants.CENTER);
            label.setPreferredSize(tableCellDimensions);
            body.add(label);
        });

        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                DayButton btn = new DayButton("", tableCellSize);
                btn.setBorder(null);
                body.add(btn);
                dayButtons.add(btn);
            }
        }

        add(header);
        add(body);
        add(Box.createVerticalGlue());
    }

    public JLabel getHeaderLabel() {
        return headerLabel;
    }

    public ArrayList<DayButton> getDayButtons() {
        return dayButtons;
    }
}
