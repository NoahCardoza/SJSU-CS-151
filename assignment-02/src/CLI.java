/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/21/2022
 * @assignment Airline Reservation System
 */

import java.io.PrintStream;
import java.util.*;

/**
 * Wraps a ReservationSystem to interface with user input.
 */
public class CLI {
    private final ReservationSystem reservationSystem;
    private final Scanner scanner;
    private final Prompt prompt;
    private final PrintStream output;

    /**
     * Constructs a new CLI instance.
     * @param reservationSystem the reservation system to provide the interface for
     * @param output the print stream to place output
     * @param scanner the scanner to read user input from.
     */
    public CLI(ReservationSystem reservationSystem, PrintStream output, Scanner scanner) {
        this.output = output;
        this.scanner = scanner;
        this.reservationSystem = reservationSystem;

        prompt = new Prompt(output, scanner);
    }

    /**
     * Executes the main CLI loop.
     */
    public void mainLoop() {
        while(screenMainMenu());
    }

    /**
     * Displays the main menu.
     *
     * @return Whether the user opted to quit the program
     */
    public boolean screenMainMenu() {
        char choice;

        output.println("Select one of the following main menu options:");
        choice = prompt.choice(
                "Add [P]assenger, Add [G]roup, [C]ancel Reservations, Print Seating [A]vailability Chart, Print [M]anifest, [Q]uit",
                "PGCAMQ"
        );

        switch (choice) {
            case 'P' -> screenAddPassenger();
            case 'G' -> screenAddGroup();
            case 'C' -> screenCancelReservation();
            case 'A' -> screenAvailabilityChart();
            case 'M' -> screenPrintManifest();
        }

        if (choice == 'Q') {
            return false;
        }

        return true;
    }

    private void screenAddGroup() {boolean isFirstClass;
        String reservationName;
        Reservation reservation;

        reservationName = prompt.line("Group Name");
        if (reservationSystem.getReservationByName(reservationName) != null) {
            output.println("Error: A reservation already exists for this group name.");
            return;
        }

        isFirstClass = prompt.choice("Service Class ([F]irst Class, [E]conomy)", "FE") == 'F';

        List<String> people = prompt.lines("Enter passenger names below (enter an empty line to contine)");

        reservation = reservationSystem.reserveGroup(reservationName, people, isFirstClass);

        if (reservation == null) {
            output.println("Error: Could not place all group members in the selected service class.");
            return;
        }

        output.println(reservation.getReceipt());
    }

    private void screenAddPassenger() {
        boolean isFirstClass;
        char seatingPreference;
        String reservationName;
        Reservation reservation;

        reservationName = prompt.line("Name");
        if (reservationSystem.getReservationByName(reservationName) != null) {
            output.println("Error: A reservation already exists for someone by this name.");
            return;
        }

        isFirstClass = prompt.choice("Service Class ([F]irst Class, [E]conomy)", "FE") == 'F';

        if (isFirstClass) {
            seatingPreference = prompt.choice("Seating Preference ([W]indow, [A]isle)", "WA");
        } else {
            seatingPreference = prompt.choice("Seating Preference ([W]indow, [C]enter, [A]isle)", "WCA");
        }

        reservation = switch (seatingPreference) {
            case 'W' -> reservationSystem.reserveWindowSeat(reservationName, isFirstClass);
            case 'C' -> reservationSystem.reserveEconomyCenterSeat(reservationName);
            case 'A' -> reservationSystem.reserveAisleSeat(reservationName, isFirstClass);
            default -> null;
        };

        if (reservation == null) {
            output.println("Error: No seats of the selected service class and seating preference could be found.");
            return;
        }

        output.println(reservation.getReceipt());
    }

    private void screenPrintManifest() {
        output.println(reservationSystem.getManifest());
    }

    private void screenAvailabilityChart() {
        output.println("Availability List:");
        output.println(reservationSystem.getFirstClassAvailabilityList());
        output.println(reservationSystem.getEconomyClassAvailabilityList());
    }

    private void screenCancelReservation() {
        char choice = prompt.choice("Cancel [I]ndividual or [G]roup", "IG");

        switch (choice) {
            case 'I' -> screenCancelReservationIndividual();
            case 'G' -> screenCancelReservationGroup();
        }
    }

    private void screenCancelReservationIndividual() {
        String reservationName = prompt.line("Name");

        Reservation reservation = reservationSystem.getReservationByName(reservationName);

        if (reservation == null || reservation.isGroup()) {
            output.println("Error: Reservation not found.");
            screenCancelReservationIndividual();
            return;
        }

        reservationSystem.cancelReservation(reservation);

        output.println("Success: Reservation cancelled.");
    }

    private void screenCancelReservationGroup() {
        String reservationName = prompt.line("Group Name");

        Reservation reservation = reservationSystem.getReservationByName(reservationName);

        if (reservation == null || !reservation.isGroup()) {
            output.println("Error: Reservation not found.");
            screenCancelReservationIndividual();
            return;
        }

        reservationSystem.cancelReservation(reservation);

        output.println("Success: Group reservation cancelled.");
    }
}
