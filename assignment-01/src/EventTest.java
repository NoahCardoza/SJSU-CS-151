import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @org.junit.jupiter.api.Test
    void repeatedDaysParsing() {
        Event event;

        /**
         * Recurring
         */
        event = Event.fromScanner(new Scanner(
                "R:CS157C Lecture\n" +
                       "SA 08/22/2022 12/06/2022 11:45 13:15"
        ));
        assertEquals(0b1000001, event.getRepeatedDays());
        assertEquals(event.repeatedDaysToStringPadded(), "S     A");

        event = Event.fromScanner(new Scanner(
                "R:CS157C Lecture\n" +
                        "SMTWRFA 08/22/2022 12/06/2022 11:45 13:15"
        ));
        assertEquals(0b1111111, event.getRepeatedDays());
        assertEquals(event.repeatedDaysToStringPadded(), "SMTWRFA");

        event = Event.fromScanner(new Scanner(
                "R:CS157C Lecture\n" +
                        "MWF 08/22/2022 12/06/2022 11:45 13:15"
        ));
        assertEquals(0b0101010, event.getRepeatedDays());
        assertEquals(event.repeatedDaysToStringPadded(), " M W F ");

        /**
         * One-time
         */
        event = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 07:00 07:30"
        ));
        assertEquals(0b0000001, event.getRepeatedDays());


        // event on monday
        event = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/05/2022 07:00 07:30"
        ));
        assertEquals(0b0000010, event.getRepeatedDays());

        // event on wednesday
        event = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/07/2022 07:00 07:30"
        ));
        assertEquals(0b0001000, event.getRepeatedDays());

        event = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/09/2022 07:00 07:30"
        ));

        assertEquals(0b0100000, event.getRepeatedDays());
    }

    /**
     * Test one time events on the same date with time intervals differing
     */
    @org.junit.jupiter.api.Test
    void overlappingOneTimeEvents() {
        Event event1, event2;
        /**
         * Test exact duplicates
         */
        event1 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 17:45"
        ));
        event2 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 17:45"
        ));
        assertEquals(true, event1.conflicts(event2));

        /**
         * Test event2 inside event1
         */
        event1 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 18:00"
        ));
        event2 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:15 17:45"
        ));
        assertEquals(true, event1.conflicts(event2));

        /**
         * Test event1 inside event2
         */
        event1 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:15 17:45"
        ));
        event2 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 18:00"
        ));

        assertEquals(true, event1.conflicts(event2));


        /**
         * Test event2 ends after event2 starts
         */
        event1 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 17:45"
        ));
        event2 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 14:30 17:15"
        ));
        assertEquals(true, event1.conflicts(event2));

        /**
         * Test event2 starts before event1 finishes
         */
        event1 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 17:45"
        ));
        event2 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:30 18:00"
        ));
        assertEquals(true, event1.conflicts(event2));

        /**
         * Test event1 ends before event2 starts
         */
        event1 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 17:45"
        ));
        event2 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 18:00 18:30"
        ));
        assertEquals(false, event1.conflicts(event2));

        /**
         * Test event2 ends before event1 starts
         */
        event1 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 17:45"
        ));
        event2 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 16:00 16:30"
        ));
        assertEquals(false, event1.conflicts(event2));

        /**
         * Test event1 ends the same minute event2 starts and vice versa
         *
         * This should not conflict
         */
        event1 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 16:00 17:00"
        ));
        event2 = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 17:00 17:30"
        ));
        assertEquals(false, event1.conflicts(event2));
        assertEquals(false, event2.conflicts(event1));
    }

    /**
     * Test repeating events conflicting with one-time events
     */
    @org.junit.jupiter.api.Test
    void overlappingOneTimeEventsWithRecurringEvents() {
        Event recurring, oneTime;
        recurring = Event.fromScanner(new Scanner(
                "R:Piano Lesson\n" +
                        "MWF 09/05/2022 09/09/2022 07:00 08:00"
        ));
        // event before the range starts
        oneTime = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 07:00 07:30"
        ));
        assertEquals(false, recurring.conflicts(oneTime));

        // event on monday
        oneTime = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/05/2022 07:00 07:30"
        ));
        assertEquals(true, recurring.conflicts(oneTime));

        // event on wednesday
        oneTime = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/07/2022 07:00 07:30"
        ));
        assertEquals(true, recurring.conflicts(oneTime));

        // event on friday
        oneTime = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/09/2022 07:00 07:30"
        ));
        assertEquals(true, recurring.conflicts(oneTime));

        /**
         * Test repeating events conflicting with one-time events
         */
        recurring = Event.fromScanner(new Scanner(
                "R:Piano Lesson\n" +
                        "MWF 09/05/2022 09/09/2022 07:00 08:00"
        ));
        // event before the range starts
        oneTime = Event.fromScanner(new Scanner(
                "O:Piano Lesson\n" +
                        "09/04/2022 07:00 07:30"
        ));
        assertEquals(false, recurring.conflicts(oneTime));

        // test one time events
//        event1 = Event.fromScanner(new Scanner(
//                "R:CS157C Lecture\n" +
//                        "SA 09/01/2022 12/07/2022 11:45 13:15"
//        ));
//
//        assertEquals(0b1000001, event.getRepeatedDays());
//        assertEquals(event.repeatedDaysToStringPadded(), "S     A");
//
//        event2 = Event.fromScanner(new Scanner(
//                "R:CS157C Lecture\n" +
//                        "SMTWRFA 08/22/2022 12/06/2022 11:45 13:15"
//        ));
//        assertEquals(0b1111111, event.getRepeatedDays());
//        assertEquals(event.repeatedDaysToStringPadded(), "SMTWRFA");
    }

    /**
     * Test repeating events conflicting with other repeating events
     */
    @org.junit.jupiter.api.Test
    void overlappingRecurringEvents() {
        Event event1, event2;

        /**
         * event1's date range ends when event2's starts
         */
        event1 = Event.fromScanner(new Scanner(
                "R:Piano Lesson\n" +
                        "MWF 09/05/2022 09/09/2022 07:00 08:00"
        ));
        event2 = Event.fromScanner(new Scanner(
                "R:Piano Lesson\n" +
                        "MWF 09/09/2022 09/16/2022 07:00 08:00"
        ));
        assertEquals(true, event1.conflicts(event2));
    }
}