Fall 2022 CS151 Programming Assignment 1  
My First Calendar  
Instructor: Dr. Suneuy Kim  

* * *

[Zoom lecture on this assignment](https://sjsu.zoom.us/rec/share/oumc7dKhtQVDPk9YT3XTDjbAtr1qoqVEFzloXMOZ65s0oygIbMFZJPeS07ar5jgx.CPo8adCPGD_0CP67)

* * *

*   The outcome of this assignment will be used later in the assignmen 4. In the assignment 4, you will add a GUI interface to the result of the assignment 1.
*   Java 8 introduced new APIs for Date and Time to address the shortcomings of the older java.util.Date and java.util.Calendar. You have to use these new features of Java 8 to manipulate time information in your program. Study the API of classes in java.time package to learn how to manipulate day, week and month of calendar. Here is [an example code](./DateTimeExample.java) to learn how to advance a calendar by month, to print a date in a specified format, and to figure out the day of the week for the 1st day of the given month.
*   In your implementation, ignore cases to recognize a user request and make sure to follow the given example format to enter data.
*   I expect at least 4 classes in your design:
    *   MyCalendarTester class with a main method
    *   MyCalendar class that defines an underlying data structure to hold events
    *   TimeInterval class that represents an interval of time, suitable for events (such as a meeting on a given date from 10:00 - 11:00). Provide a method to check whether two intervals overlap.
    *   Event class that represent an event. An event consists of its name and a TimeInterval of the event.
*   The format of the output from your program can be different from that of the given example outputs.

* * *

In this assignment, you will design and implement a calendar similar to the one you can find on your phone. The calendar is going to be implemented as a console application.

The initial screen shows the current month looking like this. It also highlights today's date using a pair of brackets. (It is not straightforward to highlight on the console. It is fine to use a pair of brackets for this purpose.)

 August 2022  
Su Mo Tu We Th Fr Sa  
    1  \[2\]  3  4  5  6  <--   It is ok if this line is sticking out.
 7  8  9 10 11 12 13  
14 15 16 17 18 19 20  
21 22 23 24 25 26 27  
28   

When the program starts, it loads events from events.txt and populates the calendar with them. The events.txt is a text file of an event you need to prepare **ahead of** the program execution.

There are two different types of events the program manages: recurring and one-time.

*   A recurring event is one that is scheduled every week on the same day or days, such as a lecture that meets every Monday and Wednesday.
*   A one-time event is scheduled on a particular date, such as 10/15/22, and doesn't repeat.

In the events.txt file, each event takes up two lines.

*   Recurring event  
    The first line contains the name of the event which may contain spaces. The second line consists of a sequence of day abbreviations (SMTWRFA, upper or lower case) followed by a starting date and an ending date of the recurring event.
*   One time event  
    The first line contains the name of the event which may contain spaces. The second line consists of a date in the format mm/dd/yy, e.g. 3/22/22. There cannot be any spaces within a date description. The date description is followed by a starting time and an ending time. For the starting and ending times, only military 24-hour time will be used for simplicity. For example, 18:15 instead of 6:15 pm The minutes cannot be left off in which case zeros should be specified for the minutes, e.g. 14:00.

Here is a sample events.txt:

```
CS151 Lecture
TR 10:30 11:45 8/23/22 12/6/22
CS157C Lecture
MW 11:45 13:15 8/22/22 12/6/22
Interview at BigCorp 
9/28/22 9:30 11:30
Dentist appt
10/3/22 16:15 17:00
Course Committee Meeting
F 18:30 20:30  9/20/22 12/9/22 
```

After loading the events, the program prompts "Loading is done!".  
The program now displays a **MAIN MENU** with following options: View by, Create, Go to, Event list, Delete, and Quit. After the function of an option is done, the main menu is displayed again for the user to choose the next option.

```
Select one of the following main menu options:
[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit
```

The user may enter one of the letters highlighted with a pair of the bracket to choose an option. For example,

```
V
```

will choose the View by option.*   \[V\]iew by  
The user can choose a Day or a Month view. If a Day view is chosen, the program prints today's date. If there is an event(s) scheduled on that day, display them **in the order of start time** of the event. With a Month view, it displays the **current** month and **highlights** day(s) with a pair of brackets {} if any event scheduled on that day. After the selected view is displayed, the calendar gives the user three options: P, N, and G, where P, N, and M stand for Previous, Next and Go back to the main menu, respectively. The previous and next options allow the user to navigate **the current view** back and forth. If the day view was selected, the view goes back (P) and forth (N) by day. If the month view was chosen, the view goes back (P) and forth (N) by month. Here are sample runs:
    
```
[D]ay view or [M]view ? 

If the user selects D, then today's date is displayed along with scheduled events.

Thu, August 25, 2022 
CS151 Lecture : 10:30 - 11:45 

[P]revious or [N]ext or [G]o back to the main menu ?  <-- The option menu allows the user to choose navigating the Day view or going back to the main menu

If the user selects M, then

    August 2022      
Su Mo Tu We Th Fr Sa  
    1  2  3  4  5  6  
    7  8  9 10 11 12 13  
14 15 16 17 18 19 20  
21 22 23 24 {25} 26 27  
28  

[P]revious or [N]ext or [G]o back to main menu ? 
```

    Help: The following example code segment shows how to print the given date in a specified format.
    
    LocalDate c = ....
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
    System.out.println(" " + formatter.format(c));
    
*   \[C\]reate  
    This option allows the user to schedule an event. The calendar asks the user to enter the name, date, starting time, and ending time of an event. For simplicity, we consider **one-time event only** for the Create function. Your program should check if a new event is a conflict with existing events. Please stick to the following format to enter data:
    *   Name: a string (doesn't have to be one word)
    *   Date: MM/DD/YYYY
    *   Starting time and ending time: 24 hour clock such as 06:00 for 6 AM and 15:30 for 3:30 PM.
*   \[G\]o to  
    With this option, the user is asked to enter a date in the form of MM/DD/YYYY and then the calendar displays the Day view of the requested date including an event scheduled on that day in the order of starting time.
*   \[E\]vent list  
    The user can browse scheduled events. The scheduled events are presented in two categories: One time events and Recurring events. Any canceled events will not show. The calendar displays one-time events **in the order** of starting date and starting time. Recurring events are presented in the order of starting date. An example presentation of events is as follows: (The format of event strings does not have to be exactly like this.)
    
    ONE TIME EVENTS
    
    2021
      Friday March 15 13:15 - 14:00 Dentist 
      Thursday April 25 15:00 - 16:00 Job Interview 
    2022 
      ... 
    
    RECURRING EVENTS
    CS157C Lecture
    MW 10:30 11:45 8/22/22 12/6/22 
    CS151 Lecture
    TR 9:00 10:15 8/22/22 12/6/22 
    
*   \[D\]elete  
    The user can delete an event from the Calendar. There are **three** different ways to delete an event: Selected, All and DeleteRecurring. Other types of deletion will not be considered for simplicity.
    
    *   \[S\]elected: the user specifies the date and name of an **ONE TIME** event. The specific one time event will be deleted.
    *   \[A\]ll: the user specifies a date and then all **ONE TIME** events scheduled on the date will be deleted.
    *   \[DR\]: the user specifies the name of a **RECURRING** event. The specified recurring event will be deleted. This will delete the recurring event throughout the calendar.
    
    \[S\]elected  \[A\]ll   \[DR\] 
    
    Here is a scenario of \[S\]elected as an example. You may implement \[A\]ll and \[DR\] according to the above description. If the user enters S, then the calendar asks for the date and displays all the events scheduled on that date. The program then asks the name of the event to be deleted and deletes the specified event. If there is no such event, the program promotes an error message.
    
    Enter the date \[dd/mm/yyyy\]
    09/15/2022
    
      13:15 - 14:00 Dentist 
      17:00 - 17:45 Piano Lesson
    
    Enter the name of the event to delete: Dentist
    
*   \[Q\]uit The program prompts "Good Bye", saves the current events in a file called output.txt, and terminates. The main menu will be displayed after each option is done. It is crucial to have a user friendly interface for the user to enter input. For example, if the calendar needs a date from the user, suggest a specific format of the date for the user to use.
    
    ### Deliverable
    
    **Due: Saturday, September 10 11:59 PM**
    
    ONLY Softcopy submission is required: Submit hw1.zip through the course web site. The file hw1.zip should contain the followings:
    
    1.  All source programs you wrote (.java) required to run the application.
        *   Name the class with main method as **MyCalendarTester**
        *   Put javadoc comments in the source codes.
        *   Include .java files only. For plagiarism check purposes, do not include any sub-directories in the zip file. Zip the source files without any subdirectory layers. (There will be point deductions if this requirement is not met.)
    2.  events.txt with at least 5 recurring events and 5 one time events scheduled on different dates
    3.  output.txt (This file is created when you select the \[Q\]uit option.)
    4.  googledoc.txt: In this text file, specify a link to your google doc, titled as CS151\_HW1\_YOUR ID. Be sure to make this document available to ANYONE WHO HAS THE LINK (There will be point deductions if this requirement is not met.) **The google doc should not be modified after the due date** . The followings are required to present in the google document.
        *   All use cases
        *   A set of CRC cards
        *   UML class diagram (simple version)
        *   One sequence diagram for the "to create an event" use case.
        *   14 screenshots (clear screenshots are required for credit)
            *   Screen 1: Show the initial calendar after starting the application
            *   Screen 2: Show \[E\]vent list. The list is supposed to show events loaded from events.txt
            *   Screen 3: Select \[V\]iew by and choose the Day view and show the current Day view.
            *   Screen 4: From the Day view, press \[P\]revious and show the previous day.
            *   Screen 5: From the previous view, press \[N\]ext THREE times and show the next day.
            *   Screen 6: Move the Day view to show a day with an event.
            *   Screen 7: Select \[G\]o back to the main menu and select \[V\]iew by and \[M\]onth view and show the current Month view.
            *   Screen 8: Move the Month view to show a month with an event.
            *   Screen 9: Select \[C\]reate and create an event. Show \[E\]vent list to confirm that the event is created.
            *   Screen 10: Select \[C\]reate and create an event with time conflict. Show you handled the time conflict.
            *   Screen 11: \[G\]o to a specific date with multiple events and show the events are listed in order.
            *   Screen 12: Select \[D\]elete and \[S\]elected to delete a selected event scheduled on a specified date.
            *   Screen 13: Select \[D\]elete and \[A\]ll to delete all events on a specified date. Show all the events are deleted on the specified date by \[E\]vent list.
            *   Screen 14: Select \[Q\]. Show all events persisted in output.txt.