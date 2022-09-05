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

//    public boolean overlaps(TimeInterval timeInterval) {
//        // TODO: does this work LOL?
//        int startStart = this.start.compareTo(timeInterval.getStart());
//        int endStart = this.end.compareTo(timeInterval.getStart());
//        int startEnd = this.start.compareTo(timeInterval.getEnd());
//        int endEnd = this.end.compareTo(timeInterval.getEnd());
//        return startStart == 0 && endEnd == 0 ? false :(startStart < 0 && 0 < endStart) || (startEnd < 0 && 0 < endEnd);
//    }
}
