package gui.view;

import gui.model.NewEventModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NewEventView extends JFrame {
    private final JTextField nameTextField;
    private final JTextField startDateTextField;
    private final JTextField endDateTextField;
    private final JTextField startTimeTextField;
    private final JTextField endTimeTextField;
    private final JButton saveButton;
    private final JComboBox<String> eventTypeSelect;
    private final JCheckBox[] daysOfTheWeekCheckboxes;

    public NewEventView(NewEventModel newEventModel) {
        super();

        JPanel pane = new JPanel();
        pane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(pane);

        String[] daysOfTheWeek = new String[] { "Sun", "Mon", "Tus", "Wed", "Thr", "Fri", "Sat" };
        daysOfTheWeekCheckboxes = new JCheckBox[daysOfTheWeek.length];
        JPanel daySelectPanel = new JPanel(new FlowLayout());
        for (int i = 0; i < daysOfTheWeek.length; i++) {
            daysOfTheWeekCheckboxes[i] = new JCheckBox(daysOfTheWeek[i]);
            daySelectPanel.add(daysOfTheWeekCheckboxes[i]);
        }

        nameTextField = new JTextField();
        eventTypeSelect = new JComboBox<>(NewEventModel.eventTypeOptions.toArray(new String[0]));
        startDateTextField = new JTextField(8);
        JLabel dateToLabel = new JLabel(" to ");
        endDateTextField = new JTextField(8);
        startTimeTextField  = new JTextField(5);
        endTimeTextField = new JTextField(5);
        saveButton = new JButton("Save");

        nameTextField.setText(newEventModel.getName());

        newEventModel.addEventListener("update:eventType", (event) -> {
            NewEventModel.EventType eventType = newEventModel.getEventType();
            eventTypeSelect.setSelectedIndex(NewEventModel.eventTypeOptionsToEnumValues.indexOf(eventType));

            boolean isRecurring = eventType.equals(NewEventModel.EventType.RECURRING);
            daySelectPanel.setVisible(isRecurring);
            dateToLabel.setVisible(isRecurring);
            endDateTextField.setVisible(isRecurring);
            pack();
        }, true);

        startDateTextField.setText(newEventModel.getStartDate());
        endDateTextField.setText(newEventModel.getEndDate());
        startTimeTextField.setText(newEventModel.getStartTime());
        endTimeTextField.setText(newEventModel.getEndTime());

        setTitle("New Event");
        setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(new JLabel("Name: "), BorderLayout.LINE_START);
        northPanel.add(nameTextField, BorderLayout.CENTER);
        northPanel.add(eventTypeSelect, BorderLayout.LINE_END);
        add(northPanel, BorderLayout.NORTH);

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(startDateTextField);
        bottom.add(dateToLabel);
        bottom.add(endDateTextField);
        bottom.add(startTimeTextField);
        bottom.add(new JLabel(" to "));
        bottom.add(endTimeTextField);
        bottom.add(saveButton);
        add(daySelectPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    public void addEventTypeComboBoxActionListener(ActionListener listener) {
        eventTypeSelect.addActionListener(listener);
    }

    public void addSaveButtonActionListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public List<Integer> getSelectedDays() {
        ArrayList<Integer> selected = new ArrayList<>();

        for (int i = 0; i < daysOfTheWeekCheckboxes.length; i++) {
            if (daysOfTheWeekCheckboxes[i].isSelected()) {
                selected.add(i);
            }
        }

        return selected;
    }

    public String getEventName() {
        return nameTextField.getText();
    }

    public String getStartDate() {
        return startDateTextField.getText();
    }

    public String getStartTime() {
        return startTimeTextField.getText();
    }

    public String getEndTime() {
        return endTimeTextField.getText();
    }

    public String getEndDate() {
        return endDateTextField.getText();
    }
}
