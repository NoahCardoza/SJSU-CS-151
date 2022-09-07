/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Prompt {
    private final Scanner scanner;

    Prompt (Scanner scanner) {
        this.scanner = scanner;
    }
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
            System.out.printf("Error: Invalid input. Must be an integer.%n", start, end);
        }

        System.out.printf("Error: Invalid selection. Must be an integer between %d and %d.%n", start, end);
        return range(prompt, start, end);
    }

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

    public String line(String prompt) {
        System.out.printf("%s: ", prompt);
        return scanner.nextLine();
    }

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
}
