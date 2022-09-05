import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateInterval extends Interval {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final LocalDate start;
    private final LocalDate end;
    DateInterval(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public static DateInterval fromScanner(Scanner scanner) {
        LocalDate start = LocalDate.parse(scanner.next(), formatter);
        LocalDate end = LocalDate.parse(scanner.next(), formatter);
        return new DateInterval(start, end);
    }

//    public boolean overlaps(DateInterval dateInterval) {
//        // TODO: does this work LOL?
//        int startStart = this.start.compareTo(dateInterval.getStart());
//        int endStart = this.end.compareTo(dateInterval.getStart());
//        int startEnd = this.start.compareTo(dateInterval.getEnd());
//        int endEnd = this.end.compareTo(dateInterval.getEnd());
//        return startStart == 0 && endEnd == 0 ? false : (startStart < 0 && 0 < endStart) || (startEnd < 0 && 0 < endEnd);
////        return (
////                (this.start.compareTo(dateInterval.getStart()) < 0 && 0 < this.end.compareTo(dateInterval.getStart())) ||
////                (this.start.compareTo(dateInterval.getEnd()) < 0 && 0 < this.end.compareTo(dateInterval.getEnd()))
////        );
//    }

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
