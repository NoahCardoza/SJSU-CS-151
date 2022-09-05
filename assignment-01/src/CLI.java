import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private MyCalender calender;
    private Scanner scanner;
    private Prompt prompt;

    void run() {
        char choice;

        scanner = new Scanner(System.in);
        prompt = new Prompt(scanner);
        calender = new MyCalender();
        calender.printTodayCalendar();

        File file = new File("events.txt");

        try {
            calender.load(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: events.txt could not be found.");
            return;
        }

        System.out.println("Loading is done!");

        calender.printEventCalendar();

        do {
            System.out.println("Select one of the following main menu options:");
            choice = prompt.choice("[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit",
                    "VCGEDQ"
            );

            if (choice == 'V') {
                screenViewBy();
            } else if (choice == 'C') {
                screenCreate();
            } else if (choice == 'G') {
                screenGoTo();
            } else if (choice == 'E') {
                screenEventList();
            } else if (choice == 'D') {
                screenDelete();
            }
        } while (choice != 'Q');

        scanner.close();
    }


    private void screenViewBy() {
        char choice;

        choice = prompt.choice("[D]ay view or [M]view", "DM");

        if (choice == 'D') {
            LocalDate day = LocalDate.now();
            while (choice != 'G') {
                calender.printDayView(day);
                choice = prompt.choice("[P]revious or [N]ext or [G]o back to the main menu?", "PNG");
                if (choice == 'P') {
                    day = day.minusDays(1);
                } else if (choice == 'N') {
                    day = day.plusDays(1);
                }
            }
        } else {
            YearMonth month = YearMonth.now();
            while (choice != 'G') {
                calender.printMonthView(month);
                choice = prompt.choice("[P]revious or [N]ext or [G]o back to the main menu?", "PNG");
                if (choice == 'P') {
                    month = month.minusMonths(1);
                } else if (choice == 'N') {
                    month = month.plusMonths(1);
                }
            }
        }

    }

    private void screenCreate() {
        System.out.println("Enter the details for a one-time event below:");

        // OneTimeEvent event = (OneTimeEvent) Event.fromScanner(scanner, true);
        // TODO: ask about changing the format to make it work without casting
        // and duplicating this code below from Event::fromStream

        String name = scanner.nextLine();
        LocalDate date = LocalDate.parse(scanner.next(), RecurringEvent.formatter);
        TimeInterval time = TimeInterval.fromScanner(scanner);
        OneTimeEvent event = new OneTimeEvent(name, date, time);

        ArrayList<Event> conflicts = calender.findConflicts(event);

        if (conflicts.size() >= 1) {
            // TODO: list conflicting events
        }

        calender.addOneTimeEvent(event);
    }

    private void screenGoTo() {
        calender.printDayView(prompt.date("Enter a date to go to"));
    }

    private void screenEventList() {
//        ONE TIME EVENTS
//
//        2021
//        Friday March 15 13:15 - 14:00 Dentist
//        Thursday April 25 15:00 - 16:00 Job Interview
//        2022
//  ...
//
//        RECURRING EVENTS
//        CS157C Lecture
//        MW 10:30 11:45 8/22/22 12/6/22
//        CS151 Lecture
//        TR 9:00 10:15 8/22/22 12/6/22

    }

    private void screenDelete() {
        LocalDate date;
        ArrayList<Event> events;

        char choice = prompt.choice("[S]elect   [A]ll on date   [R]ecurring event", "SAR");
        switch (choice) {
            case 'S':
                date = prompt.date("Enter a date to list the events on that day");
                events = calender.getOneTimeEventsOnDate(date);

                if (events.isEmpty()) {
                    System.out.println("There are one-time events scheduled that day.");
                    return;
                }

                calender.printDayView(date, events);
                int eventIndex = prompt.range("Choose an even to delete", 1, events.size()) - 1;
                calender.removeOneTimeEvent(events.get(eventIndex));

                System.out.println("Success: The event was removed!");
                return;
            case 'A':
                date = prompt.date("Enter a date to clear the events from");
                events = calender.getOneTimeEventsOnDate(date);
                for (int i = 0; i < events.size(); i++) {
                    calender.removeOneTimeEvent(events.get(i));
                }
                System.out.printf("Success: All the one-time events on %1$tm/%1$td/%1$tY removed!%n", date);
                return;
            case 'R':
                screenDeleteRecurringEvent();
        }
    }

    private void screenDeleteRecurringEvent() {
        System.out.print("Event name: ");
        String name = scanner.nextLine();

        RecurringEvent event = calender.findRecurringEventByName(name);

        if (event != null) {
            calender.removeReoccurringEvent(event);
            System.out.println("Success: The recurring event was removed.");
        } else {
            System.out.println("Error: Could not find a recurring event matching that name. Please try again.");
            screenDeleteRecurringEvent();
        }
    }
}
