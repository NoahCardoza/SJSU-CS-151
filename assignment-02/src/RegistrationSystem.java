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
public class RegistrationSystem {
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
        Manifest manifest;
        String saveToPath = "CL34.dat";

        if (args.length == 1) {
            saveToPath = args[0];
        } else if (args.length > 1) {
            System.err.println("Error: Only expecting up to one argument");
            System.exit(1);
        }

        File file = new File(saveToPath);

        if (file.exists()) {
            System.out.println("Info: Loading previous flight manifest");
            manifest = Manifest.load(file);
        } else {
            System.out.println("Info: Creating new flight manifest database");
            file.createNewFile();
            manifest = new Manifest();
        }

        new CLI(manifest, System.out, new Scanner(System.in)).mainLoop();

        System.out.println("Info: Saving flight manifest to database");
        manifest.dump(file);
    }
}