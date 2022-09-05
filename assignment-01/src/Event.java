import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;

public class Event {
    private final String name;
    private final TimeInterval timeInterval;
    private final DateInterval dateInterval;
    private final boolean recurring;
    private final int repeatedDays;
    private static final String repeatedDaysString = "SMTWRFA";

    static int compareByStartDateAndStartTime(Event a, Event b) {
        int cmp = a.dateInterval.getStart().compareTo(b.dateInterval.getStart());
        if (cmp == 0) {
            return a.getTimeInterval().getStart().compareTo(b.getTimeInterval().getStart());
        }
        return cmp;
    }

    static int compareByStartTime(Event a, Event b) {
        return a.getTimeInterval().getStart().compareTo(b.getTimeInterval().getStart());
    }

    static public String repeatedDaysToString(int unserialized) {
        StringBuilder serialized = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            if ((unserialized & (1 << i)) > 0) {
                serialized.append(repeatedDaysString.charAt(i));
            }
        }
        return serialized.toString();
    }

    static public int repeatedDaysFromString(String serialized) {
        int unserialized = 0;

        for (int i = 0; i < serialized.length(); i++) {
            int shift = repeatedDaysString.indexOf(serialized.charAt(i));
            int bitmask = 1 << shift;
            unserialized |= bitmask;
        }

        return unserialized;
    }

    Event(String name, LocalDate date, TimeInterval timeInterval) {
        this.recurring = false;
        this.name = name;
        this.repeatedDays = 1 << (date.getDayOfWeek().getValue() % 7);
        this.dateInterval = new DateInterval(date, date);
        this.timeInterval = timeInterval;
    }

    Event(String name, DateInterval dateInterval, int repeatedDays, TimeInterval timeInterval) {
        this.recurring = true;
        this.name = name;
        this.repeatedDays = repeatedDays;
        this.dateInterval = dateInterval;
        this.timeInterval = timeInterval;
    }

    public static Event fromScanner(Scanner scanner) {
        // TODO: ask if this is legal...

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

    public boolean conflicts(Event event) {
        if (this.dateInterval.overlaps(event.dateInterval)) {
            if ((this.repeatedDays & event.repeatedDays) > 0) {
                return this.timeInterval.overlaps(event.timeInterval);
            }
        }
        return false;
    }

    public boolean isOnDay(LocalDate day) {
        // TODO: maybe split days from date interval?
        if (dateInterval.contains(day)) {
            // TODO: make sure this works LOL
            return (repeatedDays & (1 << (day.getDayOfWeek().getValue() % 7))) > 0;
        }
        return false;
    }

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

    public DateInterval getDateInterval() {
        return dateInterval;
    }

    public LocalDate getStartDate() {
        // hack to display the events in the list view
        return dateInterval.getStart();
    }

    public String getName() {
        return name;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public boolean isRecurring() {
        return recurring;
    }
}
