/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui.view.component;

import gui.model.MainModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The view portion that displays information about the
 * events on the selected day.
 */
public class DayView extends JPanel {
    private final JButton lastDayButton;
    private final JButton nextDayButton;
    private final JLabel headerLabel;

    /**
     * Constructs a new day view.
     *
     * @param mainModel a reference to the main model
     */
    public DayView(MainModel mainModel) {
        super();

        lastDayButton = new BasicArrowButton(BasicArrowButton.WEST);
        nextDayButton = new BasicArrowButton(BasicArrowButton.EAST);

        headerLabel = new JLabel();
        mainModel.addEventListener("update:currentDay", (event) -> {
            headerLabel.setText(mainModel.getDayViewTitle());
        }, true);

        DayViewEventsCanvas dayViewEventsCanvas = new DayViewEventsCanvas(mainModel);

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

    /**
     * Binds a listener to the next day button.
     *
     * @param listener the listener to bind
     */
    public void onNextDayButtonClicked(ActionListener listener) {
        nextDayButton.addActionListener(listener);
    }

    /**
     * Binds a listener to the previous day button.
     *
     * @param listener the listener to bind
     */
    public void  onPrevDayButtonClicked(ActionListener listener) {
        lastDayButton.addActionListener(listener);
    }
}
