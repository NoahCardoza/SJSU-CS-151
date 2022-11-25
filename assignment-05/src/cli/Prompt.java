/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

package cli;

import calendar.TimeInterval;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A utility class to handle and ensure user input and validity.
 */
public class Prompt {
    private final Scanner scanner;

    /**
     * Constructs a Prompt instance.
     *
     * @param scanner the scanner to read input from
     */
    public Prompt (Scanner scanner) {
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

        System.out.println(prompt);

        input = scanner.next().toUpperCase();
        input = input.toUpperCase();
        userChoice = input.charAt(0);

        // flush the newline from the buffer
        scanner.nextLine();

        if (options.indexOf(userChoice) == -1) {
            System.out.printf("Error: Invalid selection. Must be one of the following characters \"%s\".%n", options);
            return choice(prompt, options);
        }

        return userChoice;
    }

    /**
     * Reads an integer of user input and validates that its inclusively
     * between the provided start and end numbers. If not, it asks the
     * user to try again.
     *
     * @param prompt the text to prompt the user with
     * @param start the inclusive lower bound of the range
     * @param end the inclusive upper bound of the range
     *
     * @return a number inclusively between the start and end numbers.
     */
    public int range(String prompt, int start, int end) {
        int input;

        System.out.printf("%s [%d - %d]: ", prompt, start, end);
        try {
            input = scanner.nextInt();

            // flush the newline from the buffer
            scanner.nextLine();

            if (start <= input && input <= end) {
                return input;
            }

            System.out.printf("Error: Invalid selection. Must be between %d and %d.%n", start, end);
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Must be an integer.");
        }

        System.out.printf("Error: Invalid selection. Must be an integer between %d and %d.%n", start, end);
        return range(prompt, start, end);
    }

    /**
     * Reads a date from the user in the form "MM/dd/yyyy". If
     * the date cannot be parsed, it asks the user to retry.
     *
     * @param prompt the text to prompt the user with
     *
     * @return a valid date entered by the user
     */
    public LocalDate date(String prompt) {
        System.out.printf("%s [mm/dd/yyyy]: ", prompt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String rawDate = scanner.next();

        // flush the newline from the buffer
        scanner.nextLine();

        try {
            return LocalDate.parse(rawDate, formatter);
        } catch (DateTimeException exception) {
            System.out.printf("Error: Could not parse \"%s\". Please try again.%n", rawDate);
            return date(prompt);
        }
    }

    /**
     * Reads a timestamp from the user in the form "HH:mm". If
     * the timestamp cannot be parsed, it asks the user to retry.
     *
     * @param prompt the text to prompt the user with
     *
     * @return a valid timestamp entered by the user
     */
    public LocalTime time(String prompt) {
        System.out.printf("%s: ", prompt);

        String rawTime = scanner.next();
        try {
            return LocalTime.parse(rawTime);
        } catch (DateTimeParseException e) {
            System.out.printf("Error: Could not parse \"%s\". Please try again.%n", rawTime);
            return time(prompt);
        }
    }

    /**
     * Reads a time interval from the user. The end time must be
     * greater that the start time.
     *
     * @param startPrompt the text to prompt the user with for the start time
     * @param endPrompt the text to prompt the user with for the end time
     *
     * @return a time interval object created from user input
     */
    public TimeInterval timeInterval(String startPrompt, String endPrompt) {
        LocalTime start = time(startPrompt);
        LocalTime end = time(endPrompt);

        // allow events with no time profile, e.g. as a reminder
        if (start.compareTo(end) <= 0) {
            return new TimeInterval(start, end);
        }

        System.out.println("Error: The first time stamp must come chronologically before the second. Please try again.");
        return timeInterval(startPrompt, endPrompt);
    }

    /**
     * Prompts the user and reads a whole line. No validation is provided.
     *
     * @param prompt the text to prompt the user with
     *
     * @return a line of user input
     */
    public String line(String prompt) {
        System.out.printf("%s: ", prompt);
        return scanner.nextLine();
    }
}
