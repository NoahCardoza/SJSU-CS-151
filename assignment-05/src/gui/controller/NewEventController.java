package gui.controller;

import calender.Event;
import calender.MyCalender;
import calender.TimeInterval;
import gui.model.MainModel;
import gui.model.NewEventModel;
import gui.window.NewEventWindow;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class NewEventController {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private final MyCalender calender;
    private final NewEventWindow window;
    private final NewEventModel newEventModel;
    private final MainModel mainModel;

    public NewEventController(MyCalender calender, NewEventWindow window, NewEventModel newEventModel, MainModel mainModel) {
        this.calender = calender;
        this.window = window;
        this.newEventModel = newEventModel;
        this.mainModel = mainModel;
    }

    private void addEventListeners() {
        window.getNewEventView().getSaveButton().addActionListener(e -> {

            try {
                LocalDate day = LocalDate.parse(window.getNewEventView().getDateTextField().getText(), dateFormat);
                Event event = new Event(
                        window.getNewEventView().getNameTextField().getText(),
                        day,
                        new TimeInterval(
                                LocalTime.parse(window.getNewEventView().getStartTimeTextField().getText(), timeFormat),
                                LocalTime.parse(window.getNewEventView().getEndTimeTextField().getText(), timeFormat)
                        )
                );

                if (!calender.checkConflict(event)) {
                    calender.addEvent(event);
                    window.getNewEventView().dispose();
                    mainModel.setCurrentDay(day);
                } else {
                    JOptionPane.showMessageDialog(
                            window.getNewEventView(),
                            "This event conflicts with another. Please update the information and try again.",
                            "Conflict",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (DateTimeParseException error) {
                JOptionPane.showMessageDialog(
                        window.getNewEventView(),
                        error.getMessage(),
                        "Parsing Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public void setup() {
        addEventListeners();

        window.getNewEventView().getNameTextField().setText(newEventModel.getName());
        window.getNewEventView().getDateTextField().setText(newEventModel.getDate());
        window.getNewEventView().getStartTimeTextField().setText(newEventModel.getStartTime());
        window.getNewEventView().getEndTimeTextField().setText(newEventModel.getEndTime());
    }
}
