package calender; /**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contains all event specific information and (de)serialization logic.
 */
public class Event {
    private final String name;
    private final TimeInterval timeInterval;
    private final DateInterval dateInterval;
    private final boolean recurring;

    // TODO: use a class to wrap this int
    private final int repeatedDays;
    private static final String repeatedDaysString = "SMTWRFA";

    static private String repeatedDaysToString(int unserialized) {
        StringBuilder serialized = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            if ((unserialized & (1 << i)) > 0) {
                serialized.append(repeatedDaysString.charAt(i));
            }
        }
        return serialized.toString();
    }

    static private int repeatedDaysFromString(String serialized) {
        int unserialized = 0;

        for (int i = 0; i < serialized.length(); i++) {
            int shift = repeatedDaysString.indexOf(serialized.charAt(i));
            int bitmask = 1 << shift;
            unserialized |= bitmask;
        }

        return unserialized;
    }

    /**
     * Constructs a one time event.
     *
     * @param name the name of the event
     * @param date the date the event occurs on
     * @param timeInterval the time of day the event spans
     */
    public Event(String name, LocalDate date, TimeInterval timeInterval) {
        this.recurring = false;
        this.name = name;
        this.repeatedDays = 1 << (date.getDayOfWeek().getValue() % 7);
        this.dateInterval = new DateInterval(date, date);
        this.timeInterval = timeInterval;
    }

    /**
     * Constructs a repeating event.
     *
     * @param name the name of the event
     * @param dateInterval the interval to repeat the event throughout
     * @param repeatedDays the days of the week the event repeats on
     * @param timeInterval the time of day the event spans
     */
    public Event(String name, DateInterval dateInterval, int repeatedDays, TimeInterval timeInterval) {
        this.recurring = true;
        this.name = name;
        this.repeatedDays = repeatedDays;
        this.dateInterval = dateInterval;
        this.timeInterval = timeInterval;
    }

    /**
     * Generates a padded string showing the days the event repeats on.
     *
     * @return A string with characters "SMTWRFA" denoting which days
     * the event repeats on
     */
    public String repeatedDaysToStringPadded() {
        StringBuilder serialized = new StringBuilder("       ");
        for (int i = 0; i < 7; i++) {
            if ((repeatedDays & (1 << i)) > 0) {
                serialized.setCharAt(i, repeatedDaysString.charAt(i));
            }
        }
        return serialized.toString();
    }

    /**
     * Deserialized an event from a scanner object.
     *
     * --- calender.Event format specs --------------------------
     * [O|R (one-time|recurring)]:[name (with spaces)]$
     * [SMTWRFA (days to repeat on)] [MM/dd/yyyy (start date)] [MM/dd/yyyy (end date)] [HH:mm (start time)] [HH:mm (end time)]$
     *
     * E.G.
     *
     * R:CS151 Lecture$
     * TR 08/23/2022 12/06/2022 10:30 11:45$
     * -------------------------------------------------
     *
     * @param scanner the scanner instance to read from
     *
     * @return a new calendar.Event instance created from the parsed information
     */
    public static Event fromScanner(Scanner scanner) {
        String header;
        String name;
        LocalDate date;
        TimeInterval time;

        header = scanner.nextLine();
        name = header.substring(2);
        if (header.charAt(0) == 'R') {
            // Parse recurring events
            int daysOfTheWeek = repeatedDaysFromString(scanner.next());
            DateInterval dateInterval = DateInterval.fromScanner(scanner);
            time = TimeInterval.fromScanner(scanner);
            return new Event(name, dateInterval, daysOfTheWeek, time);
        } else {
            // Parse one time events
            date = LocalDate.parse(scanner.next(), DateInterval.formatter);
            time = TimeInterval.fromScanner(scanner);
            return new Event(name, date, time);
        }
    }

    /**
     * Determines if there is a conflict between another event.
     *
     * @param event the other event to compare against
     *
     * @return Weather the two events conflict at the time interval level
     */
    public boolean conflicts(Event event) {
        // do the date intervals intersect?
        if (this.dateInterval.overlaps(event.dateInterval)) {
            // do the events share the same day(s) of the week?
            if ((this.repeatedDays & event.repeatedDays) > 0) {
                // do their time intervals intersect?
                return this.timeInterval.overlaps(event.timeInterval);
            }
        }
        return false;
    }

    /**
     * Determines if the event occurs on the provided date.
     *
     * @param date the date to test
     *
     * @return Whether the event occurs on the date
     */
    public boolean isOnDay(LocalDate date) {
        if (dateInterval.contains(date)) {
            return (repeatedDays & (1 << (date.getDayOfWeek().getValue() % 7))) > 0;
        }
        return false;
    }

    /**
     * Calculates the days of the month the event occurs on.
     *
     * @param month the month to calculate the days of
     *
     * @return the days of the month that the event occurs on
     */
    public ArrayList<Integer> daysOfTheMonth(YearMonth month) {
        ArrayList<Integer> days = new ArrayList<>();

        if (dateInterval.contains(month)) {
            LocalDate start = dateInterval.getStart();
            LocalDate end = dateInterval.getEnd();

            LocalDate day = start.getMonth() == month.getMonth() ? start : LocalDate.of(month.getYear(), month.getMonthValue(), 1);
            int lastDayOfTheMonth = end.getMonth() == month.getMonth() ? end.getDayOfMonth() : YearMonth.of(month.getYear(), month.getMonth()).lengthOfMonth();

            while (true) {
                if ((repeatedDays & (1 << (day.getDayOfWeek().getValue() % 7))) > 0) {
                    days.add(day.getDayOfMonth());
                }
                // This break is required because plusDays won't let you past the last
                // day and thus the end of the loop isn't detectable in the while test
                // without not processing the last day.
                if (day.getDayOfMonth() == lastDayOfTheMonth) {
                    break;
                }
                day = day.plusDays(1);
            }
        }

        return days;
    }

    /**
     * Represents the instance as a string. Uses the same format as
     * fromStream expects when deserializing.
     *
     * @return The object as a string.
     */
    public String toString() {
        if (recurring) {
            return String.format(
                    "%c:%s%n%s %s %s",
                    'R',
                    this.name,
                    repeatedDaysToString(repeatedDays),
                    this.dateInterval,
                    this.timeInterval
            );
        } else {
            return String.format(
                    "%c:%s%n%s %s",
                    'O',
                    this.name,
                    this.dateInterval.getStart().format(DateInterval.formatter),
                    this.timeInterval
            );
        }
    }

    /**
     * Access the date interval's start date.
     *
     * @return the start date of the event
     */
    public LocalDate getStartDate() {
        // hack to display the events in the list view
        return dateInterval.getStart();
    }

    /**
     * Access the date interval's end date.
     *
     * @return the end date of the event
     */
    public LocalDate getEndDate() {
        // hack to display the events in the list view
        return dateInterval.getStart();
    }

    /**
     * Access the time interval's start time.
     *
     * @return the start time of the event
     */
    public LocalTime getStartTime() {
        return timeInterval.getStart();
    }

    /**
     * Access the time interval's end date.
     *
     * @return the end time of the event
     */
    public LocalTime getEndTime() {
        return timeInterval.getEnd();
    }

    /**
     * Access the event's name.
     *
     * @return the name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Access the event's recurring flag.
     *
     * @return whether the event is recurring
     */
    public boolean isRecurring() {
        return recurring;
    }

    /**
     * Access the event's repeating days bitmask.
     *
     * @return the event's repeating days bitmask
     */
    public int getRepeatedDays() {
        return repeatedDays;
    }
}
