/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

public abstract class Interval {
    // TODO: should these get java docs?

    public class IntervalRangeException extends RuntimeException {
        public IntervalRangeException(String msg) {
            super(msg);
        }
    }

    /**
     * Validate the input of every Interval subclass.
     *
     * @param start the lower bound
     * @param end the upper bound
     */
    Interval(Comparable start, Comparable end) {
        if (start.compareTo(end) > 0) {
            throw new IntervalRangeException("Lower to upper bounds should be defined left to right.");
        }
    }

    abstract Comparable getStart();
    abstract Comparable getEnd();

    /**
     * Determines if two intervals of the same type overlap.
     * The comparison is not inclusive.
     *
     * E.G. an event ends at
     * 1400 and the next event starts at 1400, they are not
     * considered overlapping.
     *
     * TODO: verify that non inclusive checks correctly identify overlapping
     *       recurring events when the end date is the other events start date
     *
     * @param interval the interval to compare with
     *
     * @return
     */
    public boolean overlaps(Interval interval) {
        int startStart = this.getStart().compareTo(interval.getStart());
        int endStart = this.getEnd().compareTo(interval.getStart());
        int startEnd = this.getStart().compareTo(interval.getEnd());
        int endEnd = this.getEnd().compareTo(interval.getEnd());
        return (startStart == 0 && endEnd == 0) || (startStart < 0 && 0 < endStart) || (startEnd < 0 && 0 < endEnd);
    }
}
