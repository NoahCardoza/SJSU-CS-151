package gui.view;

import gui.model.MainModel;
import gui.view.component.DayView;
import gui.view.component.MonthView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MainView extends JFrame {
    private final DayView dayView;
    private final MonthView monthView;

    private final JButton quitButton;
    private final JButton createEventButton;
    private final JButton lastMonthButton;
    private final JButton todayButton;
    private final JButton nextMonthButton;
    public MainView(MainModel mainModel) {
        super();

        setTitle("Calendar");

        monthView = new MonthView(mainModel);
        dayView = new DayView();
        dayView.setPreferredSize(new Dimension(500, 300));

        dayView.setBorder(new EmptyBorder(10, 10, 10, 10));

        createEventButton = new JButton("Create");
        quitButton = new JButton("Quit");

        lastMonthButton = new BasicArrowButton(BasicArrowButton.WEST);

        todayButton = new JButton("Today");
        nextMonthButton = new BasicArrowButton(BasicArrowButton.EAST);

        setLayout(new BorderLayout());

        JPanel aside = new JPanel(new BorderLayout());
        JPanel asideHeader = new JPanel(new BorderLayout());
        asideHeader.add(createEventButton, BorderLayout.NORTH);
        asideHeader.add(lastMonthButton, BorderLayout.LINE_START);
        asideHeader.add(todayButton, BorderLayout.CENTER);
        asideHeader.add(nextMonthButton, BorderLayout.LINE_END);
        asideHeader.add(quitButton, BorderLayout.SOUTH);

        aside.setBorder(new EmptyBorder(10, 10, 10, 10));
        aside.add(asideHeader, BorderLayout.NORTH);
        aside.add(monthView, BorderLayout.SOUTH);

        add(aside, BorderLayout.LINE_START);
        add(dayView, BorderLayout.CENTER);

        quitButton.addActionListener(event -> {
            processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
    }

    public DayView getDayView() {
        return dayView;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public MonthView getMonthView() {
        return monthView;
    }

    public JButton getCreateEventButton() {
        return createEventButton;
    }

    public JButton getLastMonthButton() {
        return lastMonthButton;
    }

    public JButton getNextMonthButton() {
        return nextMonthButton;
    }

    public JButton getTodayButton() {
        return todayButton;
    }
}
