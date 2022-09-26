/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/21/2022
 * @assignment Airline Reservation System
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The class containing the main method.
 */
public class Main {
    /**
     * The main method of the program. Responsible for reservation
     * information from a default or specified file. Saves on exit.
     *
     * @param args a list containing up to one element used to
     *             specify a file load
     *
     * @throws IOException if for some reason a file is unable to
     *                     be written to
     */
    public static void main(String[] args) throws IOException {
        ReservationSystem reservationSystem;
        String saveToPath = "CL34.dat";

        if (args.length == 1) {
            saveToPath = args[0];
        } else if (args.length > 1) {
            System.err.println("Error: Only expecting up to one argument");
        }

        File file = new File(saveToPath);

        if (file.exists()) {
            System.err.println("Info: Loading previous flight manifest");
            reservationSystem = ReservationSystem.load(file);
        } else {
            System.err.println("Info: Creating new flight manifest database");
            file.createNewFile();
            reservationSystem = new ReservationSystem();
        }

        new CLI(reservationSystem, System.out, new Scanner(System.in)).mainLoop();

        System.err.println("Info: Saving flight manifest to database");
        reservationSystem.dump(file);
    }
}