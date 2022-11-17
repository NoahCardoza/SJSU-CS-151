package gui.controller;

import gui.model.MainModel;
import gui.view.component.DayButton;
import gui.window.MainWindow;

import java.time.LocalDate;
import java.util.ArrayList;

public class MonthViewController {
    public MonthViewController(MainModel mainModel, MainWindow mainWindow) {
        ArrayList<DayButton> dayButtons = mainWindow.getMainView().getMonthView().getDayButtons();
        int firstDayOfMonthWeekOffset = mainModel.getFirstDayOfCurrentMonthWeekOffset();

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
}
