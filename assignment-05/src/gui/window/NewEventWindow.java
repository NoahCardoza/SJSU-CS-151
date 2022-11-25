/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/10/2022
 * @assignment Calendar GUI
 */

package gui.window;

import gui.model.NewEventModel;
import gui.view.NewEventView;

import javax.swing.*;

/**
 * Configures a new "new event" window.
 */
public class NewEventWindow {
    private final NewEventView newEventView;

    /**
     * Creates a new instance.
     *
     * @param newEventModel a reference to the new event data model
     */
    public NewEventWindow(NewEventModel newEventModel) {
        newEventView = new NewEventView(newEventModel);
        newEventView.pack();
        newEventView.setResizable(false);
        newEventView.setVisible(true);
        newEventView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public NewEventView getNewEventView() {
        return newEventView;
    }
}
