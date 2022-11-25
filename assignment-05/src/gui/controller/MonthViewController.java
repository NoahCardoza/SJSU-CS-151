/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui.controller;

import gui.model.MainModel;
import gui.view.component.DayButton;
import gui.window.MainWindow;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A controller for the month view of the GUI. Handles all clicks
 * to the day buttons.
 */
public class MonthViewController {
    /**
     * Constructs a new instance and binds to all the buttons in
     * the month view.
     *
     * @param mainModel a reference to the main data model
     * @param mainWindow a reference to the main view
     */
    public MonthViewController(MainModel mainModel, MainWindow mainWindow) {
        ArrayList<DayButton> dayButtons = mainWindow.getMainView().getMonthView().getDayButtons();

        for (int i = 0; i < dayButtons.size(); i++) {
            int index = i + 1;
            dayButtons.get(i).addActionListener(event -> {
                mainModel.setCurrentDay(
                        LocalDate.of(
                                mainModel.getCurrentMonth().getYear(),
                                mainModel.getCurrentMonth().getMonth(),
                                index - mainModel.getFirstDayOfCurrentMonthWeekOffset()
                        )
                );
            });
        }
    }
}
