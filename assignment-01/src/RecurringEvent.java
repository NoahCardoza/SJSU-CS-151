import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RecurringEvent extends Event {
    private DateInterval dateInterval;

    RecurringEvent(String name, DateInterval dateInterval, TimeInterval timeInterval) {
        this.name = name;
        this.dateInterval = dateInterval;
        this.timeInterval = timeInterval;
    }

    public boolean isRecurring () {
        return true;
    }

    @Override
    public boolean isOnDay(LocalDate day) {
        // TODO: maybe split days from date interval?
        if (dateInterval.contains(day)) {
            return dateInterval.occursOn(day);
        }
        return false;
    }

    @Override
    public ArrayList<Integer> daysOfTheMonth(YearMonth month) {
        return dateInterval.daysOfTheMonth(month);
    }

    @Override
    public boolean conflicts(Event event) {
        return event.isOnDay(dateInterval) && event.getTimeInterval().overlaps(timeInterval);
    }

    public String toString() {
        return String.format("%s%n%s %s %s", this.name, this.dateInterval.daysOfTheWeekToString(), this.timeInterval.toString(), this.dateInterval.datesToString());
    }

    public DateInterval getDateInterval() {
        return dateInterval;
    }
}
