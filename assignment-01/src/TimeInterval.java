import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringJoiner;

public class TimeInterval {
    private LocalTime start;
    private LocalTime end;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    TimeInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public static TimeInterval fromScanner(Scanner scanner) {
        LocalTime startTime = LocalTime.parse(scanner.next(), TimeInterval.formatter);
        LocalTime endTime = LocalTime.parse(scanner.next(), TimeInterval.formatter);
        return new TimeInterval(startTime, endTime);
    }

    public String toString() {
        return String.format("%s %s", this.start.format(TimeInterval.formatter), this.end.format(TimeInterval.formatter));
    }

    public int compareTo(TimeInterval timeInterval) {
        return start.compareTo(timeInterval.start);
    }

    public String toStringInline() {
        return String.format("%s - %s", this.start.format(TimeInterval.formatter), this.end.format(TimeInterval.formatter));
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
