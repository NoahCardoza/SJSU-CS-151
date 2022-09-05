import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class OneTimeEvent extends Event {
    private LocalDate date;

    OneTimeEvent(String name, LocalDate date, TimeInterval timeInterval) {
        this.name = name;
        this.date = date;
        this.timeInterval = timeInterval;
    }

    public boolean isRecurring () {
        return false;
    }

    @Override
    public boolean conflicts(Event event) {
        return event.isOnDay(date) && event.getTimeInterval().overlaps(timeInterval);
    }
//    public boolean conflicts(Event event) {
//        return event.isOnDay(date) && event.getTimeInterval().overlaps(timeInterval);
//        if (event.isRecurring()) {
//            if () {
//
//            }
//            event.get
//        } else {
//
//        }
//    }

    @Override
    public boolean isOnDay(LocalDate day) {
        return this.date.equals(day);
    }

    @Override
    public ArrayList<Integer> daysOfTheMonth(YearMonth month) {
        ArrayList<Integer> days = new ArrayList<>();

        if (month.equals(YearMonth.from(date))) {
            days.add(date.getDayOfMonth());
        }

        return days;
    }

    public String toString() {
        return String.format("%s%n%s %s", this.name, this.date.format(OneTimeEvent.formatter), this.timeInterval.toString());
    }

//    @Override
//    public int compareTo(Object o) {
//        return 0;
//    }

    public LocalDate getDate() {
        return date;
    }
}
