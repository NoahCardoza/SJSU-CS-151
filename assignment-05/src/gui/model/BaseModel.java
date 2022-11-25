/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui.model;

import gui.EventManager;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The BaseModel to be inherited by all other model classes. It abstracts away
 * attaching event listeners and the code needed to update listeners.
 */
public abstract class BaseModel {
    private final EventManager eventManager;

    /**
     * Constructs a new instance
     */
    public BaseModel() {
        this.eventManager = new EventManager();
    }

    /**
     * Binds a new listener for a specific event.
     *
     * @param event the event to bind to
     * @param listener the callback
     */
    public void addEventListener(String event, ChangeListener listener) {
        this.eventManager.add(event, listener);
    }

    /**
     * Binds a new listener for a specific event and executes it immediately.
     *
     * @param event the event to bind to
     * @param listener the callback
     * @param immediate flag to execute the listener once immediately
     */
    public void addEventListener(String event, ChangeListener listener, boolean immediate) {
        addEventListener(event, listener);
        if (immediate) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Used by concrete classes to notify listeners of state changes.
     *
     * @param event the event to broadcast to the listeners
     */
    protected void emit(String event) {
        this.eventManager.dispatch(event);
    }
}
