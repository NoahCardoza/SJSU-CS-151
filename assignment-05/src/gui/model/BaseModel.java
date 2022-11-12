package gui.model;

import gui.EventManager;

import javax.swing.event.ChangeListener;

public abstract class BaseModel {
    private final EventManager eventManager;

    public BaseModel() {
        this.eventManager = new EventManager();
    }

    public void addEventListener(String event, ChangeListener listener) {
        this.eventManager.add(event, listener);
    }

    protected void dispatchEvent (String event) {
        this.eventManager.dispatch(event);
    }
}
