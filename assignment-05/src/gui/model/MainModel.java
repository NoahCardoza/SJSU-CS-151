package gui.model;

import calender.MyCalender;

import java.time.LocalDate;
import java.time.YearMonth;

public class MainModel extends BaseModel {
    private final MyCalender calender;
    private String monthViewTitle;
    private String dayViewTitle;
    private YearMonth currentMonth;
    private LocalDate currentDay;

    public MainModel(MyCalender calender, String monthViewTitle, String dayViewTitle, YearMonth currentMonth, LocalDate currentDay) {
        super();
        this.calender = calender;
        this.monthViewTitle = monthViewTitle;
        this.dayViewTitle = dayViewTitle;
        this.currentMonth = currentMonth;
        this.currentDay = currentDay;
    }

    public String getMonthViewTitle() {
        return monthViewTitle;
    }

    public void setMonthViewTitle(String monthViewTitle) {
        this.monthViewTitle = monthViewTitle;
    }

    public String getDayViewTitle() {
        return dayViewTitle;
    }

    public void setDayViewTitle(String dayViewTitle) {
        this.dayViewTitle = dayViewTitle;
    }

    public YearMonth getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(YearMonth currentMonth) {
        this.currentMonth = currentMonth;
        dispatchEvent("update:currentMonth");
    }

    public LocalDate getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(LocalDate currentDay) {
        this.currentDay = currentDay;
        setCurrentMonth(YearMonth.of(currentDay.getYear(), currentDay.getMonth()));
        dispatchEvent("update:currentDay");
    }

    public MyCalender getCalender() {
        return calender;
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

}
