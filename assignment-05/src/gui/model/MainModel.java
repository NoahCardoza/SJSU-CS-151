/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui.model;

import calendar.Event;
import calendar.MyCalendar;
import gui.EventManager;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The main model for the whole GUI. Manages the current month, day and
 * state of the day and month view.
 */
public class MainModel extends BaseModel {
    private static final DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
    private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EE MM/dd/yyyy");
    private final MyCalendar calendar;
    private String monthViewTitle;
    private String dayViewTitle;
    private YearMonth currentMonth;
    private LocalDate currentDay;
    private final ArrayList<DayButtonModel> dayButtonModels;

    /**
     * Constructs a new instance.
     *
     * @param calendar the calendar instance to use
     * @param currentMonth the current month to display in the gui
     * @param currentDay the current day to select in the day viewer
     */
    public MainModel(MyCalendar calendar, YearMonth currentMonth, LocalDate currentDay) {
        super();

        this.calendar = calendar;

        this.dayButtonModels = new ArrayList<>();

        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                dayButtonModels.add(new DayButtonModel("", false, false, false));
            }
        }

        // explicitly disable warnings inside the constructor
        EventManager.setRaiseErrorOnNoListener(false);
        setCurrentDay(currentDay);
        setCurrentMonth(currentMonth);
        EventManager.setRaiseErrorOnNoListener(true);
    }

    public String getMonthViewTitle() {
        return monthViewTitle;
    }

    public String getDayViewTitle() {
        return dayViewTitle;
    }

    public YearMonth getCurrentMonth() {
        return currentMonth;
    }

    /**
     * Set the current month and update related values.
     *
     * @param currentMonth the new current month
     */
    public void setCurrentMonth(YearMonth currentMonth) {
        this.currentMonth = currentMonth;
        monthViewTitle = currentMonth.format(monthFormat);

        LocalDate today = LocalDate.now();
        LocalDate currentCalendarDay = getFirstDayOfCurrentMonth();

        int firstDayOfMonthWeekOffset = getFirstDayOfCurrentMonthWeekOffset();
        int daysInTheMonth = getNumberOfDaysInCurrentMonth();

        for (int i = 0; i < firstDayOfMonthWeekOffset; i++) {
            dayButtonModels.get(i).setVisible(false);
        }

        for (int i = 0; i < daysInTheMonth; i++) {
            DayButtonModel btn = dayButtonModels.get(firstDayOfMonthWeekOffset + i);
            btn.setVisible(true);
            btn.setText(Integer.toString(i + 1));
            btn.setSelected(false);
            btn.setToday(false);
            btn.setHasEvents(calendar.getAllEventsOnDate(currentCalendarDay).size() > 0);
            currentCalendarDay = currentCalendarDay.plusDays(1);
        }

        for (int i = daysInTheMonth + firstDayOfMonthWeekOffset; i < dayButtonModels.size(); i++) {
            dayButtonModels.get(i).setVisible(false);
        }

        if (currentDay.getMonth().getValue() == currentMonth.getMonthValue() && currentDay.getYear() == currentMonth.getYear()) {
            dayButtonModels.get(currentDay.getDayOfMonth() + firstDayOfMonthWeekOffset - 1).setSelected(true);
        }

        if (today.getMonthValue() == currentMonth.getMonthValue() && today.getYear() == currentMonth.getYear()) {
            dayButtonModels.get(today.getDayOfMonth() + firstDayOfMonthWeekOffset - 1).setToday(true);
        }

        emit("update:currentMonth");
    }

    public LocalDate getCurrentDay() {
        return currentDay;
    }

    /**
     * Set a new current day to display and update related values.
     *
     * @param currentDay the new current day value
     */
    public void setCurrentDay(LocalDate currentDay) {
        this.currentDay = currentDay;
        dayViewTitle = currentDay.format(dayFormat);
        setCurrentMonth(YearMonth.of(currentDay.getYear(), currentDay.getMonth()));
        emit("update:currentDay");
    }

    public LocalDate getFirstDayOfCurrentMonth() {
        return LocalDate.of(currentMonth.getYear(), currentMonth.getMonth(), 1);
    }

    public int getFirstDayOfCurrentMonthWeekOffset() {
        return getFirstDayOfCurrentMonth().getDayOfWeek().getValue() % 7;
    }

    public int getNumberOfDaysInCurrentMonth() {
        return YearMonth.of(currentMonth.getYear(), currentMonth.getMonth()).lengthOfMonth();
    }

    public ArrayList<Event> getAllEventsOnCurrentDay() {
        return calendar.getAllEventsOnDate(currentDay);
    }

    public void addCalendarEvent(Event event) {
        calendar.addEvent(event);
        setCurrentDay(event.getStartDate());
    }

    public DayButtonModel getDayButtonModel(int index) {
        return dayButtonModels.get(index);
    }

    public boolean checkConflict(Event event) {
        return calendar.checkConflict(event);
    }
}
