package gui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    private final HashMap<String, ArrayList<ChangeListener>> listeners;

    public EventManager() {
        this.listeners = new HashMap<>();
    }

    public void add(String event, ChangeListener listener) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new ArrayList<>());
        }

        listeners.get(event).add(listener);
    }

    public void dispatch(String event) {
        if (!listeners.containsKey(event)) {
            throw new RuntimeException("No listeners for '" + event + "'.");
        }
        for (ChangeListener listener : listeners.get(event)) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

}
