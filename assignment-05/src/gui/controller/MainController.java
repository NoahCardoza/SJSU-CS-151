package gui.controller;

import calender.MyCalender;
import gui.model.MainModel;
import gui.model.NewEventModel;
import gui.window.MainWindow;
import gui.window.NewEventWindow;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class MainController {
    private static final DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
    private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EE MM/dd/yyyy");
    private final MyCalender calender;
    private final MainWindow mainWindow;
    private final MainModel mainModel;

    public MainController(MyCalender calender, MainWindow mainWindow, MainModel mainModel) {
        this.calender = calender;
        this.mainWindow = mainWindow;
        this.mainModel = mainModel;
    }

    private void addEventListeners() {
        // create event buttons
        mainWindow.getMainView().getCreateEventButton().addActionListener(event -> {
            NewEventModel model = new NewEventModel("Test", mainModel.getCurrentDay().format(NewEventController.dateFormat), "12:00", "14:00");
            NewEventWindow window = new NewEventWindow();
            NewEventController controller = new NewEventController(calender, window, model, mainModel);
            controller.setup();
        });

        // month navigation buttons
        mainWindow.getMainView().getLastMonthButton().addActionListener(event -> {
            mainModel.setCurrentMonth(mainModel.getCurrentMonth().minusMonths(1));
        });

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

    private void addChangeListeners() {
        mainModel.addEventListener("update:currentMonth", event -> {
            YearMonth month = mainModel.getCurrentMonth();

            mainWindow.getMainView().getMonthView().getHeaderLabel().setText(month.format(monthFormat));
        });

        mainModel.addEventListener("update:currentDay", event -> {
            LocalDate day = mainModel.getCurrentDay();

            mainWindow.getMainView().getDayView().getHeaderLabel().setText(day.format(dayFormat));
        });
    }

    public void setup() {
        addChangeListeners();
        addEventListeners();

        MonthViewController monthViewController = new MonthViewController(calender, mainModel, mainWindow.getMainView().getMonthView().getDayButtons(), mainWindow);
        monthViewController.setup();

        DayViewController dayViewController = new DayViewController(calender, mainModel, mainWindow);
        dayViewController.setup();

        LocalDate today = LocalDate.now();
        mainModel.setCurrentDay(today);
        mainModel.setCurrentMonth(YearMonth.of(today.getYear(), today.getMonth()));
    }
}
