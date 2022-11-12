package gui.view;

import javax.swing.*;
import java.awt.*;

public class NewEventView extends JFrame {
    private final JTextField nameTextField;
    private final JTextField dateTextField;
    private final JTextField startTimeTextField;
    private final JTextField endTimeTextField;
    private final JButton saveButton;
    public NewEventView() {
        super();

        nameTextField = new JTextField();
        dateTextField = new JTextField(8);
        startTimeTextField  = new JTextField(5);
        endTimeTextField = new JTextField(5);
        saveButton = new JButton("Save");

        setTitle("New Event");
        setLayout(new BorderLayout());
        add(nameTextField, BorderLayout.NORTH);

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(dateTextField);
        bottom.add(startTimeTextField);
        bottom.add(new JLabel(" to "));
        bottom.add(endTimeTextField);

        add(bottom, BorderLayout.CENTER);
        add(saveButton, BorderLayout.LINE_END);

        add(bottom);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getDateTextField() {
        return dateTextField;
    }

    public JTextField getStartTimeTextField() {
        return startTimeTextField;
    }

    public JTextField getEndTimeTextField() {
        return endTimeTextField;
    }

    public JButton getSaveButton() {
        return saveButton;
    }
}
