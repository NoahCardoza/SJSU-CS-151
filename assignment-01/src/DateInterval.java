import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DateInterval {
    private final LocalDate start;
    private final LocalDate end;
    private final int daysOfTheWeek;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    DateInterval(LocalDate start, LocalDate end, int daysOfTheWeek) {
        this.start = start;
        this.end = end;
        this.daysOfTheWeek = daysOfTheWeek;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public ArrayList<Integer> daysOfTheMonth(YearMonth month) {
        ArrayList<Integer> days = new ArrayList<>();

        if (contains(month)) {
            LocalDate day = start.getMonth() == month.getMonth() ? start : LocalDate.of(month.getYear(), month.getMonthValue(), 1);
            int lastDayOfTheMonth = end.getMonth() == month.getMonth() ? end.getDayOfMonth() : YearMonth.of(month.getYear(), month.getMonth()).lengthOfMonth();

            while (true) {
                if ((daysOfTheWeek & (1 << (day.getDayOfWeek().getValue() % 7))) > 0) {
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

    public String daysOfTheWeekToString() {
        StringBuilder serializedBitmask = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            if ((daysOfTheWeek & (1 << i)) > 0) {
                serializedBitmask.append("SMTWRFA".charAt(i));
            }
        }

        return serializedBitmask.toString();
    }
    public String datesToString() {
        return String.format("%s %s", this.start, this.end);
    }

    public boolean contains(YearMonth month) {
        return YearMonth.from(start).compareTo(month) <= 0 && 0 <= YearMonth.from(end).compareTo(month);
    }

    public boolean contains(LocalDate day) {
        return start.compareTo(day) <= 0 && 0 <= end.compareTo(day);
    }

    public boolean occursOn(LocalDate day) {
        // TODO: make sure this works LOL
        return (daysOfTheWeek & (1 << (day.getDayOfWeek().getValue() % 7))) > 0;
    }
}
