/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/21/2022
 * @assignment Airline Reservation System
 */

/**
 * A glorified tuple used when searching for the next
 * largest group of seats when seating a group.
 */
public class SeatReservationMask {
    private final byte mask;
    private final int size;
    private final int row;

    /**
     * Constructs a new SeatReservationMask instance.
     *
     * @param mask the mask of the seating group
     * @param size the number of contiguously empty seats
     * @param row the row index the seats were found on
     */
    public SeatReservationMask(byte mask, int size, int row) {
        this.mask = mask;
        this.size = size;
        this.row = row;
    }

    /**
     * Getter for the private mask attribute.
     *
     * @return the mask associated with this instance
     */
    public byte getMask() {
        return mask;
    }

    /**
     * Getter for the private size attribute.
     *
     * @return the size associated with this instance
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter for the private row attribute.
     *
     * @return the row associated with this instance
     */
    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "SeatReservationMask{" +
                "mask=" + Integer.toBinaryString(mask) +
                ", size=" + size +
                ", row=" + row +
                '}';
    }
}
