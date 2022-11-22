package gui.controller;

import calender.DateInterval;
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

public class NewEventViewController {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public NewEventViewController(MyCalender calender, NewEventWindow window, NewEventModel newEventModel, MainModel mainModel) {
        window.getNewEventView().addEventTypeComboBoxActionListener((event) -> {
            newEventModel.setEventType(NewEventModel.eventTypeOptionsToEnumValues.get(((JComboBox<?>) event.getSource()).getSelectedIndex()));
        });

        window.getNewEventView().addSaveButtonActionListener(e -> {
            try {
                LocalDate day = LocalDate.parse(window.getNewEventView().getStartDate(), dateFormat);
                Event event;

                if (newEventModel.getEventType() == NewEventModel.EventType.ONE_TIME) {
                    event = new Event(
                        window.getNewEventView().getEventName(),
                        day,
                        new TimeInterval(
                                LocalTime.parse(window.getNewEventView().getStartTime(), timeFormat),
                                LocalTime.parse(window.getNewEventView().getEndTime(), timeFormat)
                        )
                    );
                } else {
                    int repeatedDays = 0;
                    for (int bitIndex : window.getNewEventView().getSelectedDays()) {
                        repeatedDays |= 1 << bitIndex;
                    }

                    event = new Event(
                            window.getNewEventView().getEventName(),
                            new DateInterval(day, LocalDate.parse(window.getNewEventView().getEndDate(), dateFormat)),
                            repeatedDays,
                            new TimeInterval(
                                    LocalTime.parse(window.getNewEventView().getStartTime(), timeFormat),
                                    LocalTime.parse(window.getNewEventView().getEndTime(), timeFormat)
                            )
                    );
                }

                if (!calender.checkConflict(event)) {
                    window.getNewEventView().dispose();
                    mainModel.addCalendarEvent(event);
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
}
