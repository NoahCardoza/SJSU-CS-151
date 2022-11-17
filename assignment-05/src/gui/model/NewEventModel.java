package gui.model;

import java.util.List;

public class NewEventModel extends BaseModel {
    // TODO: is this really needed since most of these are never update past the first render?
    private String name;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private EventType eventType;

    public enum EventType { ONE_TIME, RECURRING }

    public static final List<String> eventTypeOptions = List.of(new String[]{"One-time", "Recurring"});
    public static final List<EventType> eventTypeOptionsToEnumValues = List.of(new EventType[]{EventType.ONE_TIME, EventType.RECURRING});

    public NewEventModel(String name, String startDate, String endDate, String startTime, String endTime, EventType eventType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
        dispatchEvent("update:eventType");
    }
}
