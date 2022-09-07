/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

public abstract class Interval {
    abstract Comparable getStart();
    abstract Comparable getEnd();

    public boolean overlaps(Interval interval) {
        int startStart = this.getStart().compareTo(interval.getStart());
        int endStart = this.getEnd().compareTo(interval.getStart());
        int startEnd = this.getStart().compareTo(interval.getEnd());
        int endEnd = this.getEnd().compareTo(interval.getEnd());
        return (startStart == 0 && endEnd == 0) || (startStart < 0 && 0 < endStart) || (startEnd < 0 && 0 < endEnd);
    }
}
