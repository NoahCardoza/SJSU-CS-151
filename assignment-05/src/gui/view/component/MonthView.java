package gui.view.component;

import gui.model.MainModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class MonthView extends JPanel {
    private static final int rows = 7;
    private static final int cols = 7;
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

        mainModel.addEventListener("update:currentMonth", event -> {
            YearMonth month = mainModel.getCurrentMonth();
            LocalDate day = mainModel.getCurrentDay();
            LocalDate today = LocalDate.now();
            LocalDate currentCalendarDay = mainModel.getFirstDayOfCurrentMonth();

            int firstDayOfMonthWeekOffset = mainModel.getFirstDayOfCurrentMonthWeekOffset();
            int daysInTheMonth = mainModel.getNumberOfDaysInCurrentMonth();

            for (int i = 0; i < firstDayOfMonthWeekOffset; i++) {
                dayButtons.get(i).setVisible(false);
            }

            for (int i = 0; i < daysInTheMonth; i++) {
                DayButton btn = dayButtons.get(firstDayOfMonthWeekOffset + i);
                btn.setVisible(true);
                btn.setText(Integer.toString(i + 1));
                btn.setSelected(false);
                btn.setToday(false);
                btn.setHasEvents(mainModel.getCalender().getAllEventsOnDate(currentCalendarDay).size() > 0);
                currentCalendarDay = currentCalendarDay.plusDays(1);
            }

            for (int i = daysInTheMonth + firstDayOfMonthWeekOffset; i < dayButtons.size(); i++) {
                dayButtons.get(i).setVisible(false);
            }

            if (day.getMonth().getValue() == month.getMonthValue()) {
                dayButtons.get(day.getDayOfMonth() + firstDayOfMonthWeekOffset - 1).setSelected(true);
            }

            if (today.getMonthValue() == month.getMonthValue()) {
                dayButtons.get(today.getDayOfMonth() + firstDayOfMonthWeekOffset - 1).setToday(true);
            }

            revalidate();
        });
    }

    public JLabel getHeaderLabel() {
        return headerLabel;
    }

    public ArrayList<DayButton> getDayButtons() {
        return dayButtons;
    }
}
