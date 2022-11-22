package gui.view.component;

import gui.model.MainModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MonthView extends JPanel {
    private static final int rows = 7;
    private static final int cols = 7;
    private static final int bodyCells = (rows - 1) * cols;
    private static final int tableCellSize = 30;
    private static final String monthTableHeader = "SMTWTFS";
    private static final Dimension tableCellDimensions = new Dimension(tableCellSize, tableCellSize);
    private final JLabel headerLabel;
    private final ArrayList<DayButton> dayButtons;
    public MonthView(MainModel mainModel) {
        super();

        dayButtons = new ArrayList<>();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel header = new JPanel();
        JPanel body = new JPanel(new GridLayout(rows, cols));
        body.setPreferredSize(new Dimension(cols * tableCellSize, rows * tableCellSize));

        headerLabel = new JLabel();
        header.add(headerLabel);
        mainModel.addEventListener("update:currentMonth", event -> {
            headerLabel.setText(mainModel.getMonthViewTitle());
        });

        monthTableHeader.chars().forEachOrdered((chr) -> {
            JLabel label = new JLabel(Character.toString(chr), SwingConstants.CENTER);
            label.setPreferredSize(tableCellDimensions);
            body.add(label);
        });

        for (int i = 0; i < bodyCells; i++) {
            DayButton btn = new DayButton(mainModel.getDayButtonModel(i), tableCellSize);
            btn.setBorder(null);
            body.add(btn);
            dayButtons.add(btn);
        }

        add(header);
        add(body);
        add(Box.createVerticalGlue());
    }

    public ArrayList<DayButton> getDayButtons() {
        return dayButtons;
    }
}
