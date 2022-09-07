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
 * Defines an interval/range of dates and provides helpful methods to
 * detect overlaps and if a date resides between the specified dates.
 */
public class DateInterval extends Interval {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final LocalDate start;
    private final LocalDate end;

    /**
     * Constructs a new DateInterval object.
     *
     * @param start The lower bound of the range.
     * @param end The upper bound of the range.
     */
    DateInterval(LocalDate start, LocalDate end) {
        super(start, end);

        this.start = start;
        this.end = end;
    }

    /**
     * Deserializes a DateInterval instance from a scanner instance.
     *
     * @param scanner the scanner instance to deserialize the dates from.
     *
     * @pre expects scanned to contain two tokens in a row of the form "MM/dd/yyyy"
     *
     * @return a new DateInterval instance
     */
    public static DateInterval fromScanner(Scanner scanner) {
        LocalDate start = LocalDate.parse(scanner.next(), formatter);
        LocalDate end = LocalDate.parse(scanner.next(), formatter);
        return new DateInterval(start, end);
    }

    /**
     * Generate the string representation. Uses the same form that fromScanner
     * expects when serializing.
     *
     * @return A string following the format:
     * "START_DATE END_DATE" in the form "MM/dd/yyyy"
     */
    public String toString() {
        return String.format("%s %s", this.start.format(formatter), this.end.format(formatter));
    }

    /**
     * Determines if the provided month intersects with the date interval.
     *
     * @param month A YearMonth instance to test
     *
     * @return Whether the month intersects with the date range.
     */
    public boolean contains(YearMonth month) {
        return YearMonth.from(start).compareTo(month) <= 0 && 0 <= YearMonth.from(end).compareTo(month);
    }

    /**
     * Determines if the provided date lays inclusively between the start and end dates.
     *
     * @param date A LocalDate instance to test
     *
     * @return Whether the start and end date include the provided date.
     */
    public boolean contains(LocalDate date) {
        return start.compareTo(date) <= 0 && 0 <= end.compareTo(date);
    }

    /**
     * Accesses the start date
     * @return the start date
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Accesses the end date
     * @return the end date
     */
    public LocalDate getEnd() {
        return end;
    }
}
