/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui.controller;

import gui.model.MainModel;
import gui.model.NewEventModel;
import gui.window.MainWindow;
import gui.window.NewEventWindow;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A controller for main window to control the buttons on the
 * left and top right.
 */
public class MainViewController {
    private final MainWindow mainWindow;
    private final MainModel mainModel;

    /**
     * Constructs a new instance.
     *
     * @param mainWindow a reference to the main window
     * @param mainModel a reference to the main data model
     */
    public MainViewController(MainWindow mainWindow, MainModel mainModel) {
        this.mainWindow = mainWindow;
        this.mainModel = mainModel;

        addEventListeners();
    }

    private void addEventListeners() {
        // create event buttons
        mainWindow.getMainView().onCreateEventClick(event -> {
            LocalTime now = LocalTime.now();
            LocalTime nextHour = now
                    .plusMinutes(60 - now.getMinute())
                    .plusHours(1);

            // create a new data model for the new event window
            NewEventModel newEventModel = new NewEventModel(
                    "",
                    mainModel.getCurrentDay().format(NewEventViewController.dateFormat),
                    mainModel.getCurrentDay().plusMonths(1).format(NewEventViewController.dateFormat),
                    nextHour.format(NewEventViewController.timeFormat),
                    nextHour.plusHours(1).format(NewEventViewController.timeFormat),
                    NewEventModel.EventType.ONE_TIME
            );

            // bind the new model to the window
            NewEventWindow window = new NewEventWindow(newEventModel);

            // create the controller for the new event window
            new NewEventViewController(window, newEventModel, mainModel);
        });

        // on today button clicked
        mainWindow.getMainView().addTodayButtonActionListener(event -> {
            mainModel.setCurrentDay(LocalDate.now());
        });

        // month navigation buttons
        mainWindow.getMainView().onNextMonthClick(event -> {
            mainModel.setCurrentMonth(mainModel.getCurrentMonth().plusMonths(1));
        });

        mainWindow.getMainView().onPrevMonthClick(event -> {
            mainModel.setCurrentMonth(mainModel.getCurrentMonth().minusMonths(1));
        });

        // day navigation buttons
        mainWindow.getMainView().getDayView().onNextDayButtonClicked(event -> {
            mainModel.setCurrentDay(mainModel.getCurrentDay().plusDays(1));
        });

        mainWindow.getMainView().getDayView().onPrevDayButtonClicked(event -> {
            mainModel.setCurrentDay(mainModel.getCurrentDay().minusDays(1));
        });
    }
}
