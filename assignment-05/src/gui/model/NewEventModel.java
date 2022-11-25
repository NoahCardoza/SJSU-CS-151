/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/10/2022
 * @assignment Calendar GUI
 */

package gui.model;

import java.util.List;

/**
 * A data model to manage the state of the new event view.
 */
public class NewEventModel extends BaseModel {
    private String name;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private EventType eventType;

    public enum EventType { ONE_TIME, RECURRING }

    public static final List<String> eventTypeOptions = List.of(new String[]{"One-time", "Recurring"});
    public static final List<EventType> eventTypeOptionsToEnumValues = List.of(new EventType[]{EventType.ONE_TIME, EventType.RECURRING});

    /**
     * Constructs a new instance.
     *
     * @param name the default name of the event
     * @param startDate the default start date
     * @param endDate the default end date (for a recurring event)
     * @param startTime the default start time
     * @param endTime the default end time
     * @param eventType the event type to display
     */
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
        emit("update:eventType");
    }
}
