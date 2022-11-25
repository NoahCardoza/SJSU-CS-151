/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages event listeners. Used by the <code>BaseModel</code> class
 */
public class EventManager {
    private final HashMap<String, ArrayList<ChangeListener>> listeners;
    private static boolean raiseErrorOnNoListener;

    /**
     * Constructs a new instance.
     */
    public EventManager() {
        this.listeners = new HashMap<>();
        raiseErrorOnNoListener = true;
    }

    /**
     * Adds a new <code>ChangeListener</code> waiting on
     * the "event" channel.
     *
     * @param event the event to await for
     * @param listener the listener to call when the event is fired
     *
     * @post the listeners will be tracked within the event manager
     */
    public void add(String event, ChangeListener listener) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new ArrayList<>());
        }

        listeners.get(event).add(listener);
    }

    /**
     * Dispatches the given even to any listeners waiting for it.
     *
     * @param event
     *
     * @pre there must be events awaiting the passed event or warnings mush be disabled
     */
    public void dispatch(String event) {
        if (!listeners.containsKey(event)) {
            // by default, throw errors if there are no listeners
            // this most likely means something was not configured
            // properly
            if (raiseErrorOnNoListener) {
                throw new RuntimeException("No listeners for '" + event + "'.");
            }
            return;
        }
        for (ChangeListener listener : listeners.get(event)) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Determine if warning are enabled or not.
     *
     * @return whether warning are enabled or not
     */
    static public boolean isRaiseErrorOnNoListener() {
        return raiseErrorOnNoListener;
    }

    /**
     * Allow the warnings to be disabled or enabled.
     *
     * @param newRaiseErrorOnNoListener the new value
     */
    static public void setRaiseErrorOnNoListener(boolean newRaiseErrorOnNoListener) {
        raiseErrorOnNoListener = newRaiseErrorOnNoListener;
    }
}
