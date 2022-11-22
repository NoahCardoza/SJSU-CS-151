package gui.controller;

import calender.MyCalender;
import gui.model.MainModel;
import gui.model.NewEventModel;
import gui.window.MainWindow;
import gui.window.NewEventWindow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

@SuppressWarnings("InstantiationOfUtilityClass")
public class MainViewController {
    private final MyCalender calender;
    private final MainWindow mainWindow;
    private final MainModel mainModel;

    public MainViewController(MyCalender calender, MainWindow mainWindow, MainModel mainModel) {
        this.calender = calender;
        this.mainWindow = mainWindow;
        this.mainModel = mainModel;
    }

    private void addEventListeners() {
        // create event buttons
        mainWindow.getMainView().getCreateEventButton().addActionListener(event -> {
            LocalTime now = LocalTime.now();
            LocalTime nextHour = now
                    .plusMinutes(60 - now.getMinute())
                    .plusHours(1);
            NewEventModel newEventModel = new NewEventModel(
                    "",
                    mainModel.getCurrentDay().format(NewEventViewController.dateFormat),
                    mainModel.getCurrentDay().plusMonths(1).format(NewEventViewController.dateFormat),
                    nextHour.format(NewEventViewController.timeFormat),
                    nextHour.plusHours(1).format(NewEventViewController.timeFormat),
                    NewEventModel.EventType.ONE_TIME
            );
            NewEventWindow window = new NewEventWindow(newEventModel);
            new NewEventViewController(calender, window, newEventModel, mainModel);
        });

        // on today button clicked
        mainWindow.getMainView().addTodayButtonActionListener(event -> {
            mainModel.setCurrentDay(LocalDate.now());
        });

        // month navigation buttons
        mainWindow.getMainView().getLastMonthButton().addActionListener(event -> {
            mainModel.setCurrentMonth(mainModel.getCurrentMonth().minusMonths(1));
        });

        // on next month button clicked
        mainWindow.getMainView().getNextMonthButton().addActionListener(event -> {
            mainModel.setCurrentMonth(mainModel.getCurrentMonth().plusMonths(1));
        });

        // day navigation buttons
        mainWindow.getMainView().getDayView().getLastDayButton().addActionListener(event -> {
            mainModel.setCurrentDay(mainModel.getCurrentDay().minusDays(1));
        });

        mainWindow.getMainView().getDayView().getNextDayButton().addActionListener(event -> {
            mainModel.setCurrentDay(mainModel.getCurrentDay().plusDays(1));
        });
    }


    public void setup() {
        addEventListeners();

        LocalDate today = LocalDate.now();
        mainModel.setCurrentDay(today);
        mainModel.setCurrentMonth(YearMonth.of(today.getYear(), today.getMonth()));
    }
}
