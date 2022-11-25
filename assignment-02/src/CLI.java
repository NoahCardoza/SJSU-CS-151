/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/21/2022
 * @assignment Airline Reservation System
 */

import java.io.PrintStream;
import java.util.*;

/**
 * Wraps a Manifest to interface with user input.
 */
public class CLI {
    private final Manifest manifest;
    private final Prompt prompt;
    private final PrintStream output;

    /**
     * Constructs a new CLI instance.
     * @param manifest the reservation system to provide the interface for
     * @param output the print stream to place output
     * @param scanner the scanner to read user input from.
     */
    public CLI(Manifest manifest, PrintStream output, Scanner scanner) {
        this.output = output;
        this.manifest = manifest;

        prompt = new Prompt(output, scanner);
    }

    /**
     * Executes the main CLI loop.
     */
    public void mainLoop() {
        while (screenMainMenu());
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

        return choice != 'Q';
    }

    private void screenAddGroup() {boolean isFirstClass;
        String reservationName;
        Reservation reservation;

        reservationName = prompt.line("Group Name");
        if (manifest.getReservationByName(reservationName) != null) {
            output.println("Error: A reservation already exists for this group name.");
            return;
        }

        isFirstClass = prompt.choice("Service Class ([F]irst Class, [E]conomy)", "FE") == 'F';

        List<String> people = prompt.lines("Enter passenger names below (enter an empty line to contine)");

        reservation = manifest.reserveGroup(reservationName, people, isFirstClass);

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
        if (manifest.getReservationByName(reservationName) != null) {
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
            case 'W' -> manifest.reserveWindowSeat(reservationName, isFirstClass);
            case 'C' -> manifest.reserveEconomyCenterSeat(reservationName);
            case 'A' -> manifest.reserveAisleSeat(reservationName, isFirstClass);
            default -> null;
        };

        if (reservation == null) {
            output.println("Error: No seats of the selected service class and seating preference could be found.");
            return;
        }

        output.println(reservation.getReceipt());
    }

    private void screenPrintManifest() {
        output.println(manifest.getManifest());
    }

    private void screenAvailabilityChart() {
        output.println("Availability List:");
        output.println(manifest.getFirstClassAvailabilityList());
        output.println(manifest.getEconomyClassAvailabilityList());
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

        if (reservationName.length() == 0) {
            return;
        }

        Reservation reservation = manifest.getReservationByName(reservationName);

        if (reservation == null || reservation.isGroup()) {
            output.println("Error: Reservation not found.");
            screenCancelReservationIndividual();
            return;
        }

        manifest.cancelReservation(reservation);

        output.println("Success: Reservation cancelled:");
        output.println(reservation.getReceipt());

        output.println("Enter another name or press RETURN to return to main menu.");
        screenCancelReservationIndividual();
    }

    private void screenCancelReservationGroup() {
        String reservationName = prompt.line("Group Name");

        if (reservationName.length() == 0) {
            return;
        }

        Reservation reservation = manifest.getReservationByName(reservationName);

        if (reservation == null || !reservation.isGroup()) {
            output.println("Error: Reservation not found.");
            screenCancelReservationIndividual();
            return;
        }

        manifest.cancelReservation(reservation);

        output.println("Success: Group reservation cancelled:");
        output.println(reservation.getReceipt());

        output.println("Enter another name or press RETURN to return to main menu.");
        screenCancelReservationGroup();
    }
}
