/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/21/2022
 * @assignment Airline Reservation System
 */

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains reservation information
 */
public class Reservation {
    private String name;
    private final List<Seat> seats;
    private boolean group;
    boolean firstClass;

    /**
     * A simple constructor used when deserializing.
     */
    private Reservation() {
        this.seats = new ArrayList<>();
    }

    /**
     * Constructs a new Reservation from one person.
     *
     * @param name the name of the reservation
     * @param seat the one seat occupied
     * @param firstClass whether the seat is in first class
     */
    public Reservation(String name, Seat seat, boolean firstClass) {
        this.name = name;
        this.seats = new ArrayList<>();
        this.seats.add(seat);
        this.firstClass = firstClass;
        this.group = false;
    }

    /**
     * Constructs a new Reservation for a group.
     *
     * @param name the name of the reservation
     * @param seats a collection of seats
     * @param firstClass whether the seat is in first class
     */
    public Reservation(String name, List<Seat> seats, boolean firstClass) {
        this.name = name;
        this.seats = seats;

        // ensure seats are sorted
        this.seats.sort(Comparator.comparing(Seat::getRow).thenComparing(Seat::getSeat));
        this.firstClass = firstClass;
        this.group = true;
    }

    /**
     * Getter for the private seats attribute
     *
     * @return the private seats attribute
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Getter for the private seats attribute as a stream
     *
     * @return the private seats attribute as a stream
     */
    public Stream<Seat> getSeatsStream() {
        return seats.stream();
    }

    /**
     * Getter for the private name attribute
     *
     * @return the private name attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the private firstClass attribute
     *
     * @return the private firstClass attribute
     */
    public boolean isFirstClass() {
        return firstClass;
    }

    /**
     * Getter for the private group attribute
     *
     * @return the private group attribute
     */
    public boolean isGroup() {
        return group;
    }

    /**
     * Creates a receipt detailing which seats were acquired
     * and to whom they were assigned.
     *
     * @return a String based receipt
     */
    public String getReceipt() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());

        joiner.add("Seating Chart:");
        joiner.add(seats.stream()
                .collect(
                        Collectors.groupingBy(
                                Seat::getRow,
                                TreeMap::new,
                                Collectors.reducing(
                                        (byte)0,
                                        (seat) -> Manifest.columnToBitmask(seat.getSeat()),
                                        (a, b) -> (byte)(a | b))))
                .entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey() + 1, Manifest.getAvailableColumns(entry.getValue())))
                .collect(Collectors.joining(System.lineSeparator())));

        joiner.add("Itinerary:");
        joiner.add(
                seats.stream()
                        .sorted(
                                Comparator.comparing(Seat::getRow)
                                        .thenComparing(Seat::getSeat))
                        .map(Seat::formatForManifest)
                        .collect(Collectors.joining(System.lineSeparator())));

        return joiner.toString();
    }

    /**
     * Deserializes a Reservation instance from a scanner.
     *
     * @param scanner the scanner to read
     *
     * @return a new Reservation instance
     */
    public static Reservation deserialize(Scanner scanner) {
        String line;
        int size = 0;
        Reservation reservation = new Reservation();

        line = scanner.nextLine();
        char magicNumber = line.charAt(0);

        if ((magicNumber & 0b01) > 0) {
            reservation.group = true;
        }

        if ((magicNumber & 0b10) > 0) {
            reservation.firstClass = true;
        }


        if (reservation.group) {
            size |= (line.charAt(1) << 8);
            size |= (line.charAt(2));
            line = line.substring(3);
            reservation.name = line;
            for (int i = 0; i < size; i++) {
                line = scanner.nextLine();
                reservation.seats.add(Seat.parse(line));
            }
        } else {
            line = line.substring(1);
            Seat seat = Seat.parse(line);
            reservation.name = seat.getName();
            reservation.seats.add(seat);
        }


        return reservation;
    }

    /**
     * Serialized a Reservation instance as a String.
     *
     * @return a String representation of a Reservation
     */
    public String serialize() {
        StringBuilder builder = new StringBuilder();

        char magicNumber = 0;

        if (group) {
            magicNumber |= 0b01;
        }
        if (firstClass) {
            magicNumber |= 0b10;
        }

        builder.append(magicNumber);

        // save space when there is only one person in the reservation
        if (group) {
            int numberOfPeople = seats.size();
            ByteBuffer sizeBytes = ByteBuffer.allocate(2)
                    .put((byte)((numberOfPeople >> 8) & 0xFF))
                    .put((byte)(numberOfPeople & 0xFF));

            builder.append(new String(sizeBytes.array()));
            builder.append(name);
            StringJoiner joiner = new StringJoiner(System.lineSeparator());
            builder.append(System.lineSeparator());
            for (Seat seat : seats) {
                joiner.add(seat.serialize());
            }
            builder.append(joiner);
        } else {
            builder.append(seats.get(0).serialize());
        }

        return builder.toString();
    }
}
