/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/21/2022
 * @assignment Airline Reservation System
 */

/**
 * An object track seats in the airplane and who's sitting in them.
 */
public class Seat {
    private final int row;
    private final char seat;
    private final String name;

    /**
     * Constructs a new Seat.
     *
     * @param row the row number
     * @param seat the seat column
     * @param name the name of the person the seat is assigned to
     */
    Seat(int row, char seat, String name) {
        this.row = row;
        this.seat = seat;
        this.name = name;
    }

    /**
     * Getter for the private row attribute.
     *
     * @return the contents of the row attribute
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for the private seat attribute.
     *
     * @return the contents of the seat attribute
     */
    public char getSeat() {
        return seat;
    }

    /**
     * Getter for the private name attribute.
     *
     * @return the contents of the name attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Similar to toString but uses a special format when used
     * in a manifest list.
     *
     * @return A string in a format appropriate for a manifest
     */
    public String formatForManifest() {
        return String.format("%s%s: %s", row + 1, seat, name);
    }

    /**
     * Parses a string containing seating information.
     *
     * @param line the string containing the seating information
     *
     * @return a new Seat instance
     */
    public static Seat parse(String line) {
        return new Seat(line.charAt(0), line.charAt(1), line.substring(2));
    }

    /**
     * Serializes the Seat object as a String.
     *
     * @return A string representation of the Seat instance
     */
    public String serialize() {
        // cast to char so the number doesn't literally get written as a string
        return String.valueOf((char) row) + seat + name;
    }

    @Override
    public String toString() {
        return String.format("%s%s", row + 1, seat);
    }
}
