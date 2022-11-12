package gui.window;

import gui.view.NewEventView;

import javax.swing.*;

public class NewEventWindow {
    private final NewEventView newEventView;

    public NewEventWindow() {
        newEventView = new NewEventView();
        newEventView.pack();
        newEventView.setResizable(false);
        newEventView.setVisible(true);
        newEventView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public NewEventView getNewEventView() {
        return newEventView;
    }
}
