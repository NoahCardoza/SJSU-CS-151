/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/10/2022
 * @assignment Calendar GUI
 */

package gui.controller;

import calendar.DateInterval;
import calendar.Event;
import calendar.TimeInterval;
import gui.model.MainModel;
import gui.model.NewEventModel;
import gui.window.NewEventWindow;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A controller for the new event window created when the
 * user wants to add a new event to the calendar.
 */
public class NewEventViewController {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructs a new instance and binds to all the submit
     * button, check boxes, and combo box.
     *
     * @param window a reference to the main window
     * @param newEventModel a reference to the new event model
     * @param mainModel a reference to the main model
     */
    public NewEventViewController(NewEventWindow window, NewEventModel newEventModel, MainModel mainModel) {
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

                if (!mainModel.checkConflict(event)) {
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
