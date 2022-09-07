/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Defines a time interval/range and provides helpful methods to
 * detect overlaps and if a date resides between the specified time
 * interval.
 */
public class TimeInterval extends Interval {
    private final LocalTime start;
    private final LocalTime end;

    /**
     * Constructs a new TimeInterval object.
     *  TODO: how to document super?
     * @param start The lower bound of the range.
     * @param end The upper bound of the range.
     */
    TimeInterval(LocalTime start, LocalTime end) {
        super(start, end);

        this.start = start;
        this.end = end;
    }

    /**
     * Deserializes a TimeInterval instance from a scanner instance.
     *
     * @param scanner the scanner instance to deserialize the timestamps from.
     *
     * @pre expects scanned to contain two tokens in a row of the form "HH:mm"
     *
     * @return a new TimeInterval instance
     */
    public static TimeInterval fromScanner(Scanner scanner) {
        LocalTime startTime = LocalTime.parse(scanner.next());
        LocalTime endTime = LocalTime.parse(scanner.next());
        return new TimeInterval(startTime, endTime);
    }

    /**
     * Generate the string representation. Uses the same form that fromScanner
     * expects when serializing.
     *
     * @return A string following the format:
     * "START_TIME END_TIME" in the form "HH:mm"
     */
    public String toString() {
        return String.format("%s %s", this.start, this.end);
    }

    /**
     * Accesses the start time
     * @return the start time
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * Accesses the end time
     * @return the end time
     */
    public LocalTime getEnd() {
        return end;
    }
}
