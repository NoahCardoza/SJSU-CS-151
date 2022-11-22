package gui.model;

import calender.Event;
import calender.MyCalender;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainModel extends BaseModel {
    private static final DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
    private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EE MM/dd/yyyy");
    private final MyCalender calender;
    private String monthViewTitle;
    private String dayViewTitle;
    private YearMonth currentMonth;
    private LocalDate currentDay;
    private final ArrayList<DayButtonModel> dayButtonModels;

    public MainModel(MyCalender calender, String monthViewTitle, String dayViewTitle, YearMonth currentMonth, LocalDate currentDay) {
        super();
        this.calender = calender;
        this.monthViewTitle = monthViewTitle;
        this.dayViewTitle = dayViewTitle;
        this.currentMonth = currentMonth;
        this.currentDay = currentDay;
        this.dayButtonModels = new ArrayList<>();

        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                dayButtonModels.add(new DayButtonModel("", false, false, false));
            }
        }

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
            btn.setHasEvents(calender.getAllEventsOnDate(currentCalendarDay).size() > 0);
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
        return calender.getAllEventsOnDate(currentDay);
    }

    public void addCalendarEvent(Event event) {
        calender.addEvent(event);
        setCurrentDay(event.getStartDate());
    }

    public DayButtonModel getDayButtonModel(int index) {
        return dayButtonModels.get(index);
    }
}
