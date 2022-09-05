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
        return dateInterval.contains(day);
    }

    @Override
    public ArrayList<Integer> daysOfTheMonth(YearMonth month) {
        return dateInterval.daysOfTheMonth(month);
    }

    public String toString() {
        return String.format("%s%n%s %s %s", this.name, this.dateInterval.daysOfTheWeekToString(), this.timeInterval.toString(), this.dateInterval.datesToString());
    }

//    public LocalDate getDate() {
//        // TODO: correct this hack to get it to compile for now
//        return date.start;
//    }
}
