/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import cli.CLI;

import java.util.Scanner;

/**
 * Contains the main method for the program.
 */
public class MyCalendarTester {
    /**
     * Beings the program's execution.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CLI cli = new CLI(scanner);

        cli.mainLoop();

        scanner.close();
    }
}