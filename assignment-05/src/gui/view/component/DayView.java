package gui.view.component;

import gui.model.MainModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class DayView extends JPanel {
    private final JButton lastDayButton;
    private final JButton nextDayButton;
    private final JLabel headerLabel;
    private final DayViewEventsCanvas dayViewEventsCanvas;
    public DayView(MainModel mainModel) {
        super();

        lastDayButton = new BasicArrowButton(BasicArrowButton.WEST);//new JButton("<");
        nextDayButton = new BasicArrowButton(BasicArrowButton.EAST);//JButton(">");

        headerLabel = new JLabel();
        mainModel.addEventListener("update:currentDay", event -> {
            headerLabel.setText(mainModel.getDayViewTitle());
        });

        dayViewEventsCanvas = new DayViewEventsCanvas(mainModel);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        JPanel rightAside = new JPanel(new FlowLayout());
        rightAside.add(lastDayButton);
        rightAside.add(nextDayButton);

        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(headerLabel, BorderLayout.LINE_START);
        panel.add(rightAside, BorderLayout.LINE_END);


        add(panel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(dayViewEventsCanvas);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JButton getLastDayButton() {
        return lastDayButton;
    }

    public JButton getNextDayButton() {
        return nextDayButton;
    }
}
