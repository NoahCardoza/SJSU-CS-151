/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TimeInterval extends Interval {
    private final LocalTime start;
    private final LocalTime end;

    TimeInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public static TimeInterval fromScanner(Scanner scanner) {
        LocalTime startTime = LocalTime.parse(scanner.next());
        LocalTime endTime = LocalTime.parse(scanner.next());
        return new TimeInterval(startTime, endTime);
    }

    public String toString() {
        return String.format("%s %s", this.start, this.end);
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
