/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Encapsulates a collection of events and handles CLI representations of
 * the calendar.
 */
public class MyCalender {
    private final ArrayList<Event> eventsOneTime;
    private final ArrayList<Event> eventsRecurring;

    /**
     * Constructs a calendar instance.
     */
    public MyCalender() {
        this.eventsOneTime = new ArrayList<>();
        this.eventsRecurring = new ArrayList<>();
    }

    /**
     * Prints the calendar with the current day selected.
     */
    public void printTodayCalendar() {
        LocalDate now = LocalDate.now();
        ArrayList<Integer> selectedDays = new ArrayList<>();
        selectedDays.add(now.getDayOfMonth());
        this.printCalendar(selectedDays);
    }

    /**
     * Prints the calendar view of the specific month. Days on which
     * events occur are highlighted.
     *
     * @param month the month to display
     */
    public void printMonthView(YearMonth month) {
        ArrayList<Integer> selectedDays =
                Stream.concat(
                        this.eventsOneTime.stream(),
                        this.eventsRecurring.stream()
                )
                 // TODO: maybe pass a set to daysOfTheMonth so we don't
                 //       duplicate the days for multiple events?
                .flatMap(event -> event.daysOfTheMonth(month).stream())
                .collect(Collectors.toCollection(ArrayList::new));
        this.printCalendar(month, selectedDays);
    }

    /**
     * Print the month view for the current month.
     */
    public void printMonthView() {
        printMonthView(YearMonth.now());
    }

    private void printCalendar(ArrayList<Integer> selectedDays) {
        YearMonth now = YearMonth.now();
        printCalendar(now, selectedDays);
    }

    private void printCalendar(YearMonth now, ArrayList<Integer> selectedDays) {
        int firstDay = LocalDate.of(now.getYear(), now.getMonth(), 1).getDayOfWeek().getValue() % 7;
        int dayInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth();

        System.out.println(now.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        System.out.println("Sun   Mon   Tus   Wed   Thr   Fri   Sat");
        for (int i = 0; i < firstDay; i++) {
            System.out.print("      ");
        }

        for (int i = 1; i <= dayInMonth; i++) {
            System.out.printf(selectedDays.contains(i) ? "[%2d]  " : " %2d   ", i);
            if (((i + firstDay) % 7) == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * Display all events in the day view format on a specific date.
     *
     * @param date the date to use when searching for all events
     */
    public void printDayView(LocalDate date) {
        ArrayList<Event> events = getAllEventsOnDate(date);
        printDayView(date, events);
    }

    /**
     * Print a specific list of events in the day view format.
     *
     * @param date the date specifying the day for the day view header
     * @param events the list of events to display
     */
    public void printDayView(LocalDate date, ArrayList<Event> events) {
        Event event;

        // TODO: should I set this somewhere else?
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");

        System.out.println(formatter.format(date));

        for (int i = 0; i < events.size(); i++) {
            event = events.get(i);
            System.out.printf("%2d) [%s - %s] %s%n", i + 1, event.getStartTime(), event.getEndTime(), event.getName());
        }

    }

    /**
     * Deserialize a set of events from a file.
     *
     * @param file the file to read from
     * @throws FileNotFoundException
     *
     * @pre the file must exist in the file system and be of valid format or an exception will be thrown
     * @post the events will be loaded into the calendar
     */
    public void load(File file) throws FileNotFoundException {
        Event event;
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            event = Event.fromScanner(scanner);

            // Read the newline character
            scanner.nextLine();
            addEvent(event);
        }
    }

    /**
     * Serializes the collection of calendar events to a file.
     *
     * @param fileWriter the file to write to
     *
     * @post the events will be written to the specified file
     */
    public void dump(FileWriter fileWriter) {
        PrintWriter printWriter = new PrintWriter(fileWriter);

        Stream.concat(
                eventsOneTime.stream(),
                eventsRecurring.stream()
        ).forEach(printWriter::println);

        printWriter.close();
    }

    /**
     * Detects conflicts between the specified event and the existing collection of events.
     *
     * @param event the event to test conflicts against
     *
     * @return a list of conflicting events
     */
    public List<Event> findConflicts(Event event) {
        return Stream.concat(eventsOneTime.stream(), eventsRecurring.stream())
                .filter(e -> e.conflicts(event))
                .toList();
    }

    /**
     * Track a new event and add it to the internal collection.
     *
     * @param event the event instance to track
     *
     * @post Tracks the passed event internally.
     *
     * @return whether the event was added successfully
     */
    public boolean addEvent(Event event) {
        if (event.isRecurring()) {
            return eventsRecurring.add(event);
        }
        return eventsOneTime.add(event);
    }

    private ArrayList<Event> filterEventsOnDay(Stream<Event> stream, LocalDate date) {
        return stream.filter(event -> event.isOnDay(date))
            .sorted(Comparator.comparing(Event::getStartTime))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Filters out and returns all the one time events that occur on
     * a specific date.
     *
     * @param date the date to filter by
     *
     * @return a list of the one time events that occur on the specified date
     */
    public ArrayList<Event> getOneTimeEventsOnDate(LocalDate date) {
        return filterEventsOnDay(eventsOneTime.stream(), date);
    }

    /**
     * Find events that occur on a specific date.
     *
     * @param date the date to filter by
     *
     * @return a list of events that occur on the specified date
     */
    public ArrayList<Event> getAllEventsOnDate(LocalDate date) {
        return filterEventsOnDay(
                Stream.concat(
                    eventsOneTime.stream(),
                    eventsRecurring.stream()
                ),
                date
        );
    }

    /**
     * Remove an event from the calendar collection.
     *
     * @post Updated the internal collection of events.
     *
     * @param event the event to remove
     *
     * @return if the event was removed successfully
     */
    public boolean removeEvent(Event event) {
        if (event.isRecurring()) {
            return eventsRecurring.remove(event);
        }
        return eventsOneTime.remove(event);
    }

    /**
     * Finds the first recurring event that matches the name
     * provided.
     *
     * @param name the name to search for
     *
     * @return the event if found or null
     */
    public Event findRecurringEventByName(String name) {
        // TODO: implement fuzzy search
        return eventsRecurring.stream().filter(event -> event.getName().equals(name)).findAny().orElse(null);
    }

    /**
     * Access the calendar's collection of one time events.
     *
     * @return all the one time events
     */
    public ArrayList<Event> getEventsOneTime() {
        return eventsOneTime;
    }

    /**
     * Access the calendar's collection of recurring events.
     *
     * @return all the recurring events
     */
    public ArrayList<Event> getEventsRecurring() {
        return eventsRecurring;
    }
}
