/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/21/2022
 * @assignment Airline Reservation System
 */

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A utility class to handle and ensure user input and validity.
 */
public class Prompt {
    private final PrintStream output;
    private final Scanner scanner;

    /**
     * Constructs a Prompt instance.
     *
     * @param scanner the Scanner to read input from
     * @param output the PrintStream to write to
     */
    public Prompt (PrintStream output, Scanner scanner) {
        this.output = output;
        this.scanner = scanner;
    }

    /**
     * Reads a single character of user input and validates it. If
     * the character is valid its returned, otherwise, it prompts
     * the user to try again.
     *
     * @param prompt the text to prompt the user with
     * @param options a list of valid characters
     *
     * @return one of the valid characters
     */
    public char choice(String prompt, String options) {
        String input;
        char userChoice;

        output.println(prompt);

        input = scanner.next().toUpperCase();
        input = input.toUpperCase();
        userChoice = input.charAt(0);

        // flush the newline from the buffer
        scanner.nextLine();

        if (options.indexOf(userChoice) == -1) {
            output.printf("Error: Invalid selection. Must be one of the following characters \"%s\".%n", options);
            return choice(prompt, options);
        }

        return userChoice;
    }

    /**
     * Prompts the user and reads a whole line. No validation is provided.
     *
     * @param prompt the text to prompt the user with
     *
     * @return a line of user input
     */
    public String line(String prompt) {
        output.printf("%s: ", prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts multiple lines until an empty line is detected.
     *
     * @param prompt the text to prompt the user with
     *
     * @return a list of the lines entered before the empty line
     */
    public List<String> lines(String prompt) {
        output.printf("%s:%n", prompt);
        ArrayList<String> lines = new ArrayList<>();
        String line;
        int lineNumber = 1;

        while (true) {
            output.printf("%2s] ", lineNumber);
            line = scanner.nextLine();
            if (line.length() == 0) {
                return lines;
            }
            lines.add(line);
            lineNumber++;
        }
    }
}
