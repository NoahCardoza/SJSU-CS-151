/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Defines an interval of dates and provides helpful methods to
 * detect overlaps and if a date resides between the specified dates.
 */
public class DateInterval extends Interval {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final LocalDate start;
    private final LocalDate end;
    DateInterval(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Deserializes a DateInterval instance from a scanner instance.
     * @param scanner the scanner instance to deserialize the dates from.
     *
     * @return
     */
    public static DateInterval fromScanner(Scanner scanner) {
        LocalDate start = LocalDate.parse(scanner.next(), formatter);
        LocalDate end = LocalDate.parse(scanner.next(), formatter);
        return new DateInterval(start, end);
    }

    /**
     *
     * @return
     */
    public LocalDate getStart() {
        return start;
    }


    public LocalDate getEnd() {
        return end;
    }


    public String toString() {
        return String.format("%s %s", this.start.format(formatter), this.end.format(formatter));
    }

    public boolean contains(YearMonth month) {
        return YearMonth.from(start).compareTo(month) <= 0 && 0 <= YearMonth.from(end).compareTo(month);
    }

    public boolean contains(LocalDate day) {
        return start.compareTo(day) <= 0 && 0 <= end.compareTo(day);
    }

}
