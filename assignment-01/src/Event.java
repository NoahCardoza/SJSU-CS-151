import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

abstract public class Event implements Comparable<Event> {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");

    String name;
    TimeInterval timeInterval;

    public String getName() {
        return name;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public abstract boolean isRecurring();

    public abstract ArrayList<Integer> daysOfTheMonth(YearMonth month);

//    public abstract boolean conflictsWith(Event event);
    @Override
    public int compareTo(Event event) {
        return timeInterval.compareTo(event.timeInterval);
    }
    public static Event fromScanner(Scanner scanner) {
        // TODO: ask if this is legal...

        String name;
        LocalDate date;
        TimeInterval time;

        name = scanner.nextLine();
        Pattern pattern = Pattern.compile("[SMTWRFA]{1,6}");
        if (scanner.hasNext(pattern)) {
            // Parse recurring events
            int daysOfTheWeek = 0;
            String repeatingDays = scanner.next();

            for (int i = 0; i < repeatingDays.length(); i++) {
                int shift = "SMTWRFA".indexOf(repeatingDays.charAt(i));
                int bitmask = 1 << shift;
                daysOfTheWeek |= bitmask;
            }

            time = TimeInterval.fromScanner(scanner);
            LocalDate startDate = LocalDate.parse(scanner.next(), RecurringEvent.formatter);
            LocalDate endDate = LocalDate.parse(scanner.next(), RecurringEvent.formatter);
            DateInterval dateInterval = new DateInterval(startDate, endDate, daysOfTheWeek);
            return new RecurringEvent(name, dateInterval, time);
        } else {
            // Parse one time events
            date = LocalDate.parse(scanner.next(), RecurringEvent.formatter);
            time = TimeInterval.fromScanner(scanner);
            return new OneTimeEvent(name, date, time);
        }
    }

    public abstract boolean isOnDay(LocalDate date);
}
