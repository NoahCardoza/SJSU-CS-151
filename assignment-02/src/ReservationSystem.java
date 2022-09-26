/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/21/2022
 * @assignment Airline Reservation System
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Contains all the logic that dictates how reservations
 * are reserved, stored, and queried.
 */
public class ReservationSystem {
    private final byte numberFirstClassRows = 2;
//  private byte numberEconomyClassRows = 30;

    private final byte numberFirstClassColumns = 4;
    private final byte numberEconomyClassColumns = 6;

    private final byte[] firstClassRows;
    private final byte[] economyClassRows;

    private final byte firstClassWindowMask = 0b1001;
    private final byte economyClassWindowMask = 0b100001;

    private final byte economyClassCenterMask = 0b010010;

    private final byte firstClassAisleMask = 0b0110;
    private final byte economyClassAisleMask = 0b001100;

    private final byte firstClassEmptyRow = 0b1111;
    private final byte economyClassEmptyRow = 0b111111;

    private final HashMap<String, Reservation> reservations;

    /**
     * Used to pass lambda functions to the reserveWrapper method.
     */
    interface ReserveLoader {
        // TODO: do I have to jdoc this?
        Reservation operation();
    }

    private void addReservation(Reservation reservation) {
        byte[] rows = reservation.isFirstClass() ? firstClassRows : economyClassRows;
        int rowOffset = reservation.isFirstClass() ? 0 : numberFirstClassRows;

        reservation.getSeatsStream().forEach(
                seat -> {
                    rows[seat.getRow() - rowOffset] &= columnToBitmaskInverted(seat.getSeat());
                }
        );

        reservations.put(reservation.getName(), reservation);
    }

    private static char getFirstColumn(byte rowMask) {
        byte search = 1;
        char column = 'A';
        while (column <= 'F') {
            if ((search & rowMask) > 0) {
                return column;
            }
            column++;
            search <<= 1;
        }

        // TODO: maybe we should throw an error?
        return 'Z'; // this should never happen, but at least it will stand out now.
    }

    /**
     * Formats a string showing which columns are empty
     * in a row using its mask.
     *
     * @param rowMask the mask describing the row
     *
     * @return a string representation of the row
     */
    public static String getAvailableColumns(byte rowMask) {
        byte search = 1;
        char column = 'A';
        StringJoiner output = new StringJoiner(" ");

        while (column <= 'F') {
            if ((search & rowMask) > 0) {
                output.add(String.valueOf(column));
            } else {
                output.add(" ");
            }
            column++;
            search <<= 1;
        }

        return output.toString();
    }

    /**
     * Converts a column represented by a char to
     * a bitmask of a row with said column occupied.
     *
     * @param column the column to occupy
     *
     * @return a row bitmask with said column occupied
     */
    public static byte columnToBitmask(char column) {
        return (byte) (1 << (column - 'A'));
    }

    private static byte columnToBitmaskInverted(char column) {
        return (byte) ~columnToBitmask(column);
    }

    /**
     * Constructs a new ReservationSystem
     */
    public ReservationSystem() {
        reservations = new HashMap<>();
        firstClassRows = new byte[]{
              //  DCBA
                0b1111,
                0b1111
        };
        economyClassRows = new byte[]{
               // FEDCBA
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111,
                0b111111
        };
    }

    /**
     * Makes a reservation for a group of people.
     *
     * @param reservationName the name of the reservation
     * @param people a list of the names of the people in the reservation
     * @param firstClass Whether the reservation is for first class
     *
     * @pre there must be enough empty space to seat all people
     * @post a new Reservation instance will be created and tracked internally
     *
     * @return a Reservation instance or null if there wasn't enough space
     */
    public Reservation reserveGroup(String reservationName, List<String> people, boolean firstClass) {
        Reservation reservation;

        if (reservations.containsKey(reservationName)) {
            // TODO: report — error group name already used
            return null;
        }

        reservation = firstClass
                ? reserveGroup(reservationName, people, firstClassRows, 0, firstClassEmptyRow, numberFirstClassColumns, true)
                : reserveGroup(reservationName, people, economyClassRows, numberFirstClassRows, economyClassEmptyRow, numberEconomyClassColumns, false);

        // TODO: proper error reporting
        if (reservation != null) {
            reservations.put(reservationName, reservation);
        }


        return reservation;
    }

    private Reservation reserveGroup(String reservationName, List<String> people, byte[] rows, int rowOffset, byte emptyRowMask, int rowLength, boolean firstClass) {
        Iterator<String> peopleIter = people.iterator();
        AtomicInteger peopleLeftToSeat = new AtomicInteger(people.size());
        List<Seat> seats = new ArrayList<>();
        // mask needs to be inverted before using
        List<AbstractMap.SimpleEntry<Integer, Byte>> seatingMasks = new ArrayList<>();
        int j;
        byte seatingMask;
        char column;
        int i = 0;

        List<SeatReservationMask> submasks = new ArrayList<>();
        int size;
        byte mask, indexMask;
        SeatReservationMask seatReservationMask;

        while (i < rows.length && peopleIter.hasNext()) {
            // if the whole row is empty, we can place rowLength for sure
            // and we know everything past this row is empty
            if ((emptyRowMask & rows[i]) == emptyRowMask) {
                j = 0;
                seatingMask = 0;
                column = 'A';
                while (peopleIter.hasNext() && j < rowLength) {
                    seatingMask |= (1 << j);
                    j++;
                    seats.add(new Seat(rowOffset + i, column++, peopleIter.next()));
                    peopleLeftToSeat.getAndDecrement();
                }
                // save these and only apply them if we can fit everyone.
                seatingMasks.add(new AbstractMap.SimpleEntry<>(i, seatingMask));
            } else {
                size = 0;
                mask = 0;
                for (j = 0; j < rowLength; j++) {
                    indexMask = (byte)(1 << j);
                    // MOVED BACK (-2 lines) size++;
                    if ((rows[i] & indexMask) > 0) {
                        size++;
                        mask |= indexMask;
                    } else {
                        if (size > 0) {
                            // if we find a slot that can hold all the people, skip to the end
                            if (size >= people.size()) {
                                submasks.clear();
                                submasks.add(new SeatReservationMask(mask, size, 0));
                                break;
                            }
                            submasks.add(new SeatReservationMask(mask, size, 0));
                            size = 0;
                            mask = 0;
                        }
                    }
                }
                if (size > 0) {
                    // if we find a slot that can hold all the people, skip to the end
                    if (size >= people.size()) {
                        submasks.clear();
                        submasks.add(new SeatReservationMask(mask, size, 0));
                        break;
                    }
                    submasks.add(new SeatReservationMask(mask, size, 0));
                }
            }

            if (!peopleIter.hasNext()) {
                for (AbstractMap.SimpleEntry<Integer, Byte> seatingMaskPair : seatingMasks) {
                    rows[seatingMaskPair.getKey()] &= ~seatingMaskPair.getValue();
                }
                return new Reservation(reservationName, seats, firstClass);
            }
            i++;
        }
        Iterator<SeatReservationMask> submaskIter;
        // make sure we actually need to sort before we do
        if (peopleIter.hasNext()) {

            submasks.sort(Comparator.comparing(SeatReservationMask::getSize).thenComparing(SeatReservationMask::getRow).reversed());
            submaskIter = submasks.iterator();

            while (peopleIter.hasNext() && submaskIter.hasNext()) {
                // TODO: optimize for filling in the largest row that seats the rest of the whole group if possible
                // submaskIter = submasks.stream().filter(group -> peopleLeftToSeat.get() >= group.getSize()).iterator();
                seatReservationMask = submaskIter.next();

                i = 0;
                seatingMask = 0;
                column = getFirstColumn(seatReservationMask.getMask());

                while ((i < seatReservationMask.getSize()) && peopleIter.hasNext()) {
                    seatingMask |= columnToBitmask(column);
                    seats.add(new Seat(rowOffset + seatReservationMask.getRow(), column++, peopleIter.next()));
                    peopleLeftToSeat.getAndDecrement();
                    i++;
                }

                // clip the mask if not the total amount of people could fit
                seatingMasks.add(new AbstractMap.SimpleEntry<>(seatReservationMask.getRow(), seatingMask));
            }
        }

        if (!peopleIter.hasNext()) {
            for (AbstractMap.SimpleEntry<Integer, Byte> seatingMaskPair : seatingMasks) {
                rows[seatingMaskPair.getKey()] &= ~seatingMaskPair.getValue();
            }

            return new Reservation(reservationName, seats, firstClass);
        }

        // if we couldn't place all the people
        return null;
    }

    private Reservation reserve(String reservationName, byte[] rows, byte seatingPreferenceMask, int rowOffset, boolean firstClass) {
        for (int i = 0; i < rows.length; i++) {
            byte emptySeatsMatchingPreferenceMask = (byte)(rows[i] & seatingPreferenceMask);
            if (emptySeatsMatchingPreferenceMask > 0) {
                char column = getFirstColumn(emptySeatsMatchingPreferenceMask);
                byte setMask = columnToBitmaskInverted(column);
                rows[i] &= setMask;
                return new Reservation(reservationName, new Seat(rowOffset + i, column, reservationName), firstClass);
            }
        }

        return null;
    }

    private Reservation reserveWrapper(String reservationName, ReserveLoader loader) {
        Reservation reservation;

        if (reservations.containsKey(reservationName)) {
            // TODO: report — error group name already used
            return null;
        }

        reservation = loader.operation();

        if (reservation != null) {
            reservations.put(reservationName, reservation);
        }

        reservations.put(reservationName, reservation);

        return reservation;
    }

    /**
     * Makes a single seat reservation for a window seat.
     *
     * @param reservationName the name of the reservation
     * @param firstClass Whether the reservation is for first class
     *
     * @pre there must be an empty window seat
     * @post a new Reservation instance will be created and tracked internally
     *
     * @return a Reservation instance or null if there wasn't an empty  window seat
     */
    public Reservation reserveWindowSeat(String reservationName, boolean firstClass) {
        return reserveWrapper(
                reservationName,
                () -> firstClass
                        ? reserve(reservationName, firstClassRows, firstClassWindowMask, 0, true)
                        : reserve(reservationName, economyClassRows, economyClassWindowMask, numberFirstClassRows, false)
        );
    }

    /**
     * Makes a single seat reservation for an aisle seat.
     *
     * @param reservationName the name of the reservation
     * @param firstClass Whether the reservation is for first class
     *
     * @pre there must be an empty aisle seat
     * @post a new Reservation instance will be created and tracked internally
     *
     * @return a Reservation instance or null if there wasn't an empty aisle seat
     */
    public Reservation reserveAisleSeat(String reservationName, boolean firstClass) {
        return reserveWrapper(
                reservationName,
                () -> firstClass
                        ? reserve(reservationName, firstClassRows, firstClassAisleMask, 0, true)
                        : reserve(reservationName, economyClassRows, economyClassAisleMask, numberFirstClassRows, false)
        );
    }

    /**
     * Makes a single seat reservation for a center seat only applicable in economy seats.
     *
     * @param reservationName the name of the reservation
     *
     * @pre there must be an empty center seat in economy
     * @post a new Reservation instance will be created and tracked internally
     *
     * @return a Reservation instance or null if there wasn't an empty center seat
     */
    public Reservation reserveEconomyCenterSeat(String reservationName) {
        return reserveWrapper(
                reservationName,
                () -> reserve(reservationName, economyClassRows, economyClassCenterMask, numberFirstClassRows, false)
        );
    }

    /**
     * Finds a reservation by name.
     *
     * @param reservationName the name of the reservation
     *
     * @return a Reservation instance or null if not found
     */
    public Reservation getReservationByName(String reservationName) {
        return reservations.get(reservationName);
    }

    /**
     * Cancels a reservation and empties the seats.
     *
     * @pre there must be a reservation under the specified name
     * @post the seating will be freed and the reservation untracked
     *
     * @param reservation the reservation to cancel
     */
    public void cancelReservation(Reservation reservation) {
        byte[] rows = reservation.isFirstClass() ? firstClassRows : economyClassRows;
        int rowOffset = reservation.isFirstClass() ? 0 : numberFirstClassRows;

        reservation.getSeatsStream().forEach(
                seat -> {
                    rows[seat.getRow() - rowOffset] |= columnToBitmask(seat.getSeat());
                }
        );

        reservations.remove(reservation.getName());
    }

    /**
     * Returns a map of empty seats in first class.
     *
     * @return a string showing seat availability in first class
     */
    public String getFirstClassAvailabilityList() {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("First Class:");
        stringJoiner.add(getAvailabilityList(firstClassRows, "  "));
        return stringJoiner.toString();
    }

    /**
     * Returns a map of empty seats in economy.
     *
     * @return a string showing seat availability in economy
     */
    public String getEconomyClassAvailabilityList() {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("Economy:");
        stringJoiner.add(getAvailabilityList(economyClassRows, ""));
        return stringJoiner.toString();
    }

    private String getAvailabilityList(byte[] rowMasks, String padding) {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        for (int i = 0; i < rowMasks.length; i++) {
            stringJoiner.add(String.format("%2s: %s%s", i + 1, padding, getAvailableColumns(rowMasks[i])));
        }
        return stringJoiner.toString();
    }

    /**
     * Creates the flight manifest.
     *
     * @return a string showing which seats are occupied and who occupies them
     */
    public String getManifest() {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        stringJoiner.add("Manifest:");
        stringJoiner.add(
                reservations.entrySet()
                        .stream()
                        .map(Map.Entry::getValue)
                        .flatMap(Reservation::getSeatsStream)
                        .sorted(
                                Comparator.comparing(Seat::getRow)
                                        .thenComparing(Seat::getSeat)
                        )
                        .map(Seat::formatForManifest)
                        .collect(Collectors.joining(System.lineSeparator()))
        );
        return stringJoiner.toString();
    }

    /**
     * Deserializes a ReservationSystem instance from a file.
     *
     * @param file the file to read from
     *
     * @return a new ReservationSystem instance
     *
     * @throws FileNotFoundException if the file doesn't exist
     */
    static public ReservationSystem load(File file) throws FileNotFoundException {
        return deserialize(new Scanner(file));
    }

    /**
     * Deserializes a ReservationSystem instance from a string.
     *
     * @param serialized the string to interpret
     *
     * @return a new ReservationSystem instance
     */
    static public ReservationSystem loads(String serialized) {
        return deserialize(new Scanner(serialized));
    }


    static private ReservationSystem deserialize(Scanner scanner) {
        ReservationSystem reservationSystem = new ReservationSystem();

        while (scanner.hasNext()) {
            reservationSystem.addReservation(Reservation.deserialize(scanner));
        }

        return reservationSystem;
    }

    /**
     * Serializes a ReservationSystem to a file.
     *
     * @param file the file to write to
     *
     * @throws FileNotFoundException if the file doesn't exist
     */
    public void dump(File file) throws FileNotFoundException {
        serialize(new PrintStream(file));
    }

    /**
     * Serializes a ReservationSystem to a string.
     *
     * @return a String containing a serialized ReservationSystem instance
     */
    public String dumps() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        serialize(new PrintStream(output));
        return output.toString();
    }

    private void serialize(PrintStream stream) {
        stream.println(reservations.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(Reservation::serialize)
                .collect(Collectors.joining(System.lineSeparator())));
    }
}
