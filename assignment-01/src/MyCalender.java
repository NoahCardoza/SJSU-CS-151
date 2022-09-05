import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyCalender {
    private final ArrayList<Event> eventsOneTime;
    private final ArrayList<Event> eventsRecurring;

    MyCalender() {
        this.eventsOneTime = new ArrayList<>();
        this.eventsRecurring = new ArrayList<>();
    }

    public void printTodayCalendar() {
        LocalDate now = LocalDate.now();
        ArrayList<Integer> selectedDays = new ArrayList<>();
        selectedDays.add(now.getDayOfMonth());
        this.printCalendar(selectedDays);
    }

    public void printEventCalendar() {
        // TODO: remove this method
        YearMonth now = YearMonth.now();
        printMonthView(now);
    }

    public void printMonthView(YearMonth month) {
        ArrayList<Integer> selectedDays =
                Stream.concat(
                        this.eventsOneTime.stream(),
                        this.eventsRecurring.stream()
                )
                .flatMap(event -> event.daysOfTheMonth(month).stream())
                .collect(Collectors.toCollection(ArrayList::new));
        this.printCalendar(month, selectedDays);
    }

    private void printCalendar(ArrayList<Integer> selectedDays) {
        YearMonth now = YearMonth.now();
        printCalendar(now, selectedDays);
    }
    // TODO: set to private
    public void printCalendar(YearMonth now, ArrayList<Integer> selectedDays) {
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

    public void printDayView(LocalDate date) {
        ArrayList<Event> events = getEventsOnDate(date);
        printDayView(date, events);
    }

    public void printDayView(LocalDate date, ArrayList<Event> events) {
        Event event;
        TimeInterval timeInterval;

        // TODO: should I set this somewhere else?
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");

        System.out.println(formatter.format(date));

        for (int i = 0; i < events.size(); i++) {
            event = events.get(i);
            timeInterval = event.getTimeInterval();
            System.out.printf("%2d) [%s - %s] %s%n", i + 1, timeInterval.getStart(), timeInterval.getEnd(), event.getName());
        }

    }


    public void load(File file) throws FileNotFoundException {
        Event event;
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            event = Event.fromScanner(scanner);

            // Read the newline character
            scanner.nextLine();

            if (event.isRecurring()) {
                addRecurringEvent(event);
            } else {
                addOneTimeEvent(event);
            }
        }
    }

    public void dump(FileWriter fileWriter) {
        PrintWriter printWriter = new PrintWriter(fileWriter);

        Stream.concat(
                eventsOneTime.stream(),
                eventsRecurring.stream()
        ).forEach(printWriter::println);

        printWriter.close();
    }

    public List<Event> findConflicts(Event event) {
        return Stream.concat(eventsOneTime.stream(), eventsRecurring.stream())
                .filter(e -> e.conflicts(event))
                .toList();
    }

    public boolean addOneTimeEvent(Event event) {
        return eventsOneTime.add(event);
    }
    public boolean addRecurringEvent(Event event) {
        return eventsRecurring.add(event);
    }
    public ArrayList<Event> getOneTimeEventsOnDate(LocalDate date) {
        return eventsOneTime.stream()
                .filter(event -> event.isOnDay(date))
                .sorted(Event::compareByStartTime)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Event> getEventsOnDate(LocalDate date) {
        return Stream.concat(
                        eventsOneTime.stream(),
                        eventsRecurring.stream()
                ).filter(event -> event.isOnDay(date))
                .sorted(Event::compareByStartTime)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean removeOneTimeEvent(Event event) {
        // TODO: split events list
        return eventsOneTime.remove(event);
    }

    // TODO: implement fuzzy search
    public Event findRecurringEventByName(String name) {
        return eventsRecurring.stream().filter(event -> event.getName().equals(name)).findAny().orElse(null);
    }

    public boolean removeReoccurringEvent(Event event) {
        // TODO: split events list
        return eventsRecurring.remove(event);
    }

    public ArrayList<Event> getEventsOneTime() {
        return eventsOneTime;
    }

    public ArrayList<Event> getEventsRecurring() {
        return eventsRecurring;
    }
}
