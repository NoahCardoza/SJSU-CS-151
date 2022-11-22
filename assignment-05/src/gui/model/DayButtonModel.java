package gui.model;

public class DayButtonModel extends BaseModel {
    private boolean isVisible;
    private String text;
    private boolean hasEvents;
    private boolean isToday;
    private boolean isSelected;

    public DayButtonModel(String text, boolean hasEvents, boolean isToday, boolean isSelected) {
        this.text = text;
        this.hasEvents = hasEvents;
        this.isToday = isToday;
        this.isSelected = isSelected;
    }

    public boolean getVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
        emit("update:isVisible");
    }

    public void setText(String text) {
        this.text = text;
        emit("update:text");
    }

    public String getText() {
        return text;
    }

    public boolean isHasEvents() {
        return hasEvents;
    }

    public void setHasEvents(boolean hasEvents) {
        this.hasEvents = hasEvents;
        emit("update:hasEvents");
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
        emit("update:isToday");
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        emit("update:isSelected");
    }
}
