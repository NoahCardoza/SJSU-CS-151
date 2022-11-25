# Assignment 05

Adding a GUI to assignment 01's calendar.

## Structure

There are two entry points one (`SimpleCalendarTester`) to run the GUI version and the other (`MyCalendarTester`) to run the CLI version.
This is a perfect example of separations of concerns. The `MyCalendar` class is totally
unconcerned with any type of visual representation to the point that we can use the same backend
calendar and even logic with two totally different user experiences.