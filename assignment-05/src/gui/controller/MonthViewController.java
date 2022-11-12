package gui.controller;

import calender.MyCalender;
import gui.model.MainModel;
import gui.view.component.DayButton;
import gui.window.MainWindow;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class MonthViewController {
    private final ArrayList<DayButton> dayButtons;
    private final MainModel mainModel;
    private final MainWindow mainWindow;


    private int firstDayOfMonthWeekOffset;
    private int daysInTheMonth;
    private final MyCalender calender;

    public MonthViewController(MyCalender calender, MainModel mainModel, ArrayList<DayButton> dayButtons, MainWindow mainWindow) {
        this.calender = calender;
        this.mainModel = mainModel;
        this.dayButtons = dayButtons;
        this.mainWindow = mainWindow;

        firstDayOfMonthWeekOffset = 0;
        daysInTheMonth = 0;
    }

    public void setup() {
        addEventListeners();
        addChangeListeners();
    }

    private void addEventListeners() {
        for (int i = 0; i < dayButtons.size(); i++) {
            int index = i;
            dayButtons.get(i).addActionListener(event -> {
                mainModel.setCurrentDay(
                        LocalDate.of(
                                mainModel.getCurrentMonth().getYear(),
                                mainModel.getCurrentMonth().getMonth(),
                                index - firstDayOfMonthWeekOffset + 1
                        )
                );
            });
        }
    }

    private void addChangeListeners() {
        mainModel.addEventListener("update:currentMonth", event -> {
            YearMonth month = mainModel.getCurrentMonth();
            LocalDate day = mainModel.getCurrentDay();
            LocalDate today = LocalDate.now();

            LocalDate currentCalendarDay = LocalDate.of(month.getYear(), month.getMonth(), 1);
            firstDayOfMonthWeekOffset = currentCalendarDay.getDayOfWeek().getValue() % 7;
            daysInTheMonth = YearMonth.of(month.getYear(), month.getMonth()).lengthOfMonth();

            for (int i = 0; i < firstDayOfMonthWeekOffset; i++) {
                dayButtons.get(i).setVisible(false);
            }

            for (int i = 0; i < daysInTheMonth; i++) {
                DayButton btn = dayButtons.get(firstDayOfMonthWeekOffset + i);
                btn.setVisible(true);
                btn.setText(Integer.toString(i + 1));
                btn.setSelected(false);
                btn.setToday(false);
                btn.setHasEvents(calender.getAllEventsOnDate(currentCalendarDay).size() > 0);
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

            mainWindow.getMainView().getDayView().revalidate();
        });
    }
}
