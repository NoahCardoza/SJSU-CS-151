package gui.controller;

import calender.Event;
import calender.MyCalender;
import gui.model.MainModel;
import gui.window.MainWindow;

import java.time.LocalDate;
import java.util.ArrayList;

public class DayViewController {
    public DayViewController(MyCalender calender, MainModel mainModel, MainWindow mainWindow) {
        mainModel.addEventListener("update:currentDay", e -> {
            LocalDate day = mainModel.getCurrentDay();

            ArrayList<Event> events = calender.getAllEventsOnDate(day);

            mainWindow.getMainView().getDayView().getDayViewEventsCanvas().setEvents(events);

            if (events.size() > 0) {
                mainWindow.getMainView().getDayView().getDayViewEventsCanvas().scrollToEvent(events.get(0));
            }
        });
    }
}
