/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import calender.Event;
import calender.MyCalender;
import calender.TimeInterval;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Contains all the methods needed to interact with the stdin/out.
 */
public class CLI {
    private final MyCalender calender;
    private final Scanner scanner;
    private final Prompt prompt;

    /**
     * Constructs a new CLI instance.
     * @param scanner the scanner to read user input from.
     */
    public CLI(Scanner scanner) {
        this.scanner = scanner;
        prompt = new Prompt(scanner);
        calender = new MyCalender();
    }

    /**
     * Loads the events serialized in the events.txt file.
     * @return Whether the events were loaded successfully.
     */
    private boolean loadFromFile() {
        File iFile = new File("events.txt");

        try {
            calender.load(iFile);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Serializes the current events to the output.txt file.
     * @return Whether the events were serialized successfully.
     */
    private boolean dumpToFile() {
        FileWriter oFile;

        try {
            oFile = new FileWriter("output.txt");
            calender.dump(oFile);
            oFile.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Executes the main CLI loop. Handles loading the events.txt
     * and serializing the data to output.txt after the program is
     * quit.
     */
    public void mainLoop() {
        calender.printTodayCalendar();

        if (loadFromFile()) {
            System.out.println("Success: Events loaded from events.txt!");
        } else {
            System.out.println("Error: events.txt could not be found.");
            return;
        }

        calender.printMonthView();

        screenMainMenuLoop();

        System.out.println("Good Bye!");

        if (dumpToFile()) {
            System.out.println("Success: Events saved to output.txt!");
        } else {
            System.out.println("Error: output.txt could not be written to.");
        }
    }

    /**
     * Contains all the logic for the main menu loop.
     */
    private void screenMainMenuLoop() {
        char choice;

        do {
            System.out.println("Select one of the following main menu options:");
            choice = prompt.choice(
                    "[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit",
                    "VCGEDQ"
            );

            switch (choice) {
                case 'V' -> screenViewBy();
                case 'C' -> screenCreate();
                case 'G' -> screenGoTo();
                case 'E' -> screenEventList();
                case 'D' -> screenDelete();
            }
        } while (choice != 'Q');
    }

    private void screenViewBy() {
        char choice;

        choice = prompt.choice("[D]ay view or [M]onth view", "DM");
        switch (choice) {
            case 'D' -> screenViewByDay();
            case 'M' -> screenViewByMonth();
        }
    }

    private void screenViewByDay() {
        char choice = 0;

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
    }

    private void screenViewByMonth() {
        char choice = 0;

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

    private void screenCreate() {
        System.out.println("Enter the details for a one-time event below:");

        char choice = prompt.choice("[O]ne-time  [R]ecurring", "OR");

        if (choice == 'R') {
            System.out.println("Error: Not implemented yet.");
            return;
        }

        String name = prompt.line("Name");
        LocalDate date = prompt.date("Date");
        TimeInterval timeInterval = prompt.timeInterval("Start", "End");

        Event event = new Event(name, date, timeInterval);

        List<Event> conflicts = calender.findConflicts(event);

        if (conflicts.size() >= 1) {
            System.out.println("Error: Cannot event as it conflicts with the following events:");
            for (Event e : conflicts) {
                System.out.printf(
                        "  [%s - %s] %s%n",
                        e.getStartTime(),
                        e.getEndTime(),
                        e.getName()
                );
            }
        } else {
            calender.addEvent(event);
            System.out.println("Success: The new event has been added to the calendar.");
        }
    }

    private void screenGoTo() {
        LocalDate day = prompt.date("Enter a date to go to");
        calender.printDayView(day);
    }

    private void screenEventList() {
        List<Event> oneTimeEvents =
                calender.getEventsOneTime()
                        .stream()
                        .sorted(
                                Comparator.comparing(Event::getStartDate)
                                .thenComparing(Event::getStartTime)
                        )

                        .toList();

        String yearFormat  = "┃ ▣ %s%n";
        String monthFormat = "┃    ◉ %s%n";
        String dayFormat   = "┃      ▶ %02d%n";
        String eventFormat = "┃        ◈ [%s - %s] %s%n";

        int currentYear = oneTimeEvents.get(0).getStartDate().getYear();
        Month currentMonth = oneTimeEvents.get(0).getStartDate().getMonth();
        int currentDay = oneTimeEvents.get(0).getStartDate().getDayOfMonth();

        // TODO: should this be contained by the MyCalendar
        System.out.println("┏ ONE-TIME EVENTS ▷");
        System.out.printf(yearFormat, currentYear);
        System.out.printf(monthFormat, currentMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        System.out.printf(dayFormat, currentDay);

        for (Event event : oneTimeEvents) {
            if (currentYear != event.getStartDate().getYear()) {
                currentYear = event.getStartDate().getYear();
                currentMonth = event.getStartDate().getMonth();
                currentDay = event.getStartDate().getDayOfMonth();
                System.out.printf(yearFormat, currentYear);
                System.out.printf(monthFormat, currentMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                System.out.printf(dayFormat, currentDay);
            } else if (!currentMonth.equals(event.getStartDate().getMonth())) {
                currentMonth = event.getStartDate().getMonth();
                currentDay = event.getStartDate().getDayOfMonth();
                System.out.printf(monthFormat, currentMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                System.out.printf(dayFormat, currentDay);
            } else if (currentDay != event.getStartDate().getDayOfMonth()) {
                currentDay = event.getStartDate().getDayOfMonth();
                System.out.printf(dayFormat, currentDay);
            }

            System.out.printf(eventFormat, event.getStartTime(), event.getEndTime(), event.getName());
        }
        System.out.println("┗");

        System.out.println();

        // TODO: should this be contained by the MyCalendar
        System.out.println("┏ RECURRING EVENTS ▷");
        calender
            .getEventsRecurring()
            .stream()
            .sorted(
                    Comparator.comparing(Event::getStartDate)
            ).forEachOrdered(event -> {
                System.out.printf(
                        "┣ %s%n┃ [%s]  (%s - %s)%n┃ %s  --  %s%n",
                        event.getName(),
                        event.repeatedDaysToStringPadded(),
                        event.getStartTime(),
                        event.getEndTime(),
                        event.getStartDate(),
                        event.getEndDate()
                );
                });
            System.out.println("┗");
    }

    private void screenDelete() {
        char choice = prompt.choice("[S]elect   [A]ll on date   [R]ecurring event", "SAR");
        switch (choice) {
            case 'S' -> screenDeleteInteractive();
            case 'A' -> screenDeleteAllOnDay();
            case 'R' -> screenDeleteRecurringEvent();
        }
    }

    private void screenDeleteInteractive() {
        LocalDate date = prompt.date("Enter a date to list the events on that day");
        ArrayList<Event> events = calender.getOneTimeEventsOnDate(date);

        if (events.isEmpty()) {
            System.out.println("There are no one-time events scheduled that day.");
            return;
        }

        calender.printDayView(date, events);
        int eventIndex = prompt.range("Choose an even to delete", 1, events.size()) - 1;
        calender.removeEvent(events.get(eventIndex));
        System.out.println("Success: The event was removed!");
    }

    private void screenDeleteAllOnDay() {
        LocalDate date = prompt.date("Enter a date to clear the events from");
        ArrayList<Event> events = calender.getOneTimeEventsOnDate(date);
        for (Event event : events) {
            calender.removeEvent(event);
        }
        System.out.printf("Success: All the one-time events on %1$tm/%1$td/%1$tY removed!%n", date);
    }

    private void screenDeleteRecurringEvent() {
        System.out.print("calender.Event name: ");
        String name = scanner.nextLine();

        Event event = calender.findRecurringEventByName(name);

        if (event != null) {
            calender.removeEvent(event);
            System.out.println("Success: The recurring event was removed.");
        } else {
            System.out.println("Error: Could not find a recurring event matching that name. Please try again.");
            screenDeleteRecurringEvent();
        }
    }
}
