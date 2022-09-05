import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringJoiner;

public class TimeInterval {
    private LocalTime start;
    private LocalTime end;

    // TODO: uses the default format so we can remove this
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

    public boolean overlaps(TimeInterval timeInterval) {
        return (
                (this.start.compareTo(timeInterval.getStart()) < 0 && 0 < this.end.compareTo(timeInterval.getStart())) ||
                (this.start.compareTo(timeInterval.getEnd()) < 0 && 0 < this.end.compareTo(timeInterval.getEnd()))
        );
    }
}
