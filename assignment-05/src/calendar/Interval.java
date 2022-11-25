/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

package calendar;

public abstract class Interval<T extends Comparable<? super T>> {
    // TODO: should these get java docs?
    protected final T start;
    protected final T end;

    /**
     * Constructs calendar.Interval subclass and ensures the
     * arguments are stored in the correct order.
     *
     * @param start the lower bound
     * @param end the upper bound
     */
    public Interval(T start, T end) {
        // ensure the lower limit always is stored in this.start
        if (start.compareTo(end) > 0) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
    }

    abstract T getStart();
    abstract T getEnd();

    /**
     * Determines if two intervals of the same type overlap.
     * The comparison is not inclusive.
     * <p>
     * E.G. an event ends at
     * 1400 and the next event starts at 1400, they are not
     * considered overlapping.
     * <p>
     * TODO: verify that non inclusive checks correctly identify overlapping
     *       recurring events when the end date is the other events start date
     *
     * @param interval the interval to compare with
     *
     * @return whether an overlap occurs
     */
    public boolean overlaps(Interval<T> interval) {
        int startStart = this.getStart().compareTo(interval.getStart());
        int endStart = this.getEnd().compareTo(interval.getStart());
        int startEnd = this.getStart().compareTo(interval.getEnd());
        int endEnd = this.getEnd().compareTo(interval.getEnd());
        return (
                // if the intervals are directly on top of each other
                (startStart == 0 && endEnd == 0) ||
                // if the one interval is completely inside another
                (startStart > 0 && endEnd < 0) ||
                (startStart < 0 && 0 < endStart) || (startEnd < 0 && 0 < endEnd)
        );
    }
}
