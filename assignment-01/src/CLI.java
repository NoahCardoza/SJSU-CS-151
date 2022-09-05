import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

public class CLI {
    private MyCalender calender;
    private Scanner scanner;
    private Prompt prompt;

    CLI(Scanner scanner) {
        this.scanner = scanner;
        prompt = new Prompt(scanner);
        calender = new MyCalender();
    }

    private boolean loadFromFile() {
        File iFile = new File("events.txt");

        try {
            calender.load(iFile);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

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



    public void mainLoop() {
        calender.printTodayCalendar();

        if (loadFromFile()) {
            System.out.println("Success: Events loaded from events.txt!");
        } else {
            System.out.println("Error: events.txt could not be found.");
            return;
        }

        calender.printEventCalendar();

        screenMainMenuLoop();

        if (dumpToFile()) {
            System.out.println("Success: Events saved to output.txt!");
        } else {
            System.out.println("Error: output.txt could not be written to.");
        }
    }

    private void screenMainMenuLoop() {
        char choice;

        do {
            System.out.println("Select one of the following main menu options:");
            choice = prompt.choice(
                    "[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit",
                    "VCGEDQ"
            );

            if (choice == 'V') { screenViewBy(); }
            else if (choice == 'C') { screenCreate(); }
            else if (choice == 'G') { screenGoTo(); }
            else if (choice == 'E') { screenEventList(); }
            else if (choice == 'D') { screenDelete(); }
        } while (choice != 'Q');
    }


    private void screenViewBy() {
        char choice;

        choice = prompt.choice("[D]ay view or [M]onth iew", "DM");

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

        char choice = prompt.choice("[O]ne-time  [R]ecurring", "OR");

        if (choice == 'R') {
            System.out.println("Error: Not implemented yet.");
            return;
        }

        String name = prompt.line("Name");
        LocalDate date = prompt.date("Date");

        LocalTime startTime = prompt.time("Start");
        LocalTime endTime = prompt.time("End");
        TimeInterval timeInterval = new TimeInterval(startTime, endTime);

        OneTimeEvent event = new OneTimeEvent(name, date, timeInterval);

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
        List<OneTimeEvent> oneTimeEvents = calender.getEventsOneTime().stream().sorted((a, b) -> {
            int cmp = a.getDate().compareTo(b.getDate());
            if (cmp == 0) {
                return a.getTimeInterval().compareTo(b.getTimeInterval());
            }
            return cmp;
        }).toList();

        String yearFormat  = "| %s%n";
        String monthFormat = "@--| %s%n";
        String dayFormat   = "#----| %02d%n";
        String eventFormat = "+--------| [%s - %s] %s%n";

        int currentYear = oneTimeEvents.get(0).getDate().getYear();
        Month currentMonth = oneTimeEvents.get(0).getDate().getMonth();
        int currentDay = oneTimeEvents.get(0).getDate().getDayOfMonth();

        System.out.println("ONE-TIME EVENTS:");
        System.out.println();
        System.out.printf(yearFormat, currentYear);
        System.out.printf(monthFormat, currentMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        System.out.printf(dayFormat, currentDay);

        for (OneTimeEvent event : oneTimeEvents) {
            if (currentYear != event.getDate().getYear()) {
                currentYear = event.getDate().getYear();
                currentMonth = event.getDate().getMonth();
                currentDay = event.getDate().getDayOfMonth();
                System.out.printf(yearFormat, currentYear);
                System.out.printf(monthFormat, currentMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                System.out.printf(dayFormat, currentDay);
            } else if (!currentMonth.equals(event.getDate().getMonth())) {
                currentMonth = event.getDate().getMonth();
                currentDay = event.getDate().getDayOfMonth();
                System.out.printf(monthFormat, currentMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                System.out.printf(dayFormat, currentDay);
            } else if (currentDay != event.getDate().getDayOfMonth()) {
                currentDay = event.getDate().getDayOfMonth();
                System.out.printf(dayFormat, currentDay);
            }

            System.out.printf(eventFormat, event.getTimeInterval().getStart(), event.getTimeInterval().getEnd(), event.getName());
        }

        System.out.println();
        System.out.println("RECURRING EVENTS:");
        System.out.println();

        calender
            .getEventsRecurring()
            .stream()
            .sorted(Comparator.comparing(a -> a.getDateInterval().getStart()))
                .forEachOrdered(System.out::println);

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
