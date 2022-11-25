/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui;

import calendar.MyCalendar;
import gui.controller.DayViewController;
import gui.controller.MainViewController;
import gui.controller.MonthViewController;
import gui.model.MainModel;
import gui.window.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Sets up the GUI, creates the models, controllers, and views.
 */
public class GUI {
    private final MyCalendar calendar;
    private final MainWindow mainWindow;

    /**
     * Creates a new instance.
     */
    public GUI() {
        calendar = new MyCalendar();

        if (loadFromFile()) {
            System.out.println("Success: Events loaded from events.txt!");
        } else {
            System.out.println("Error: events.txt could not be found.");
        }

        MainModel mainModel = new MainModel(calendar, YearMonth.now(), LocalDate.now());
        mainWindow = new MainWindow(mainModel);

        new MainViewController(mainWindow, mainModel);
        new MonthViewController(mainModel, mainWindow);
        new DayViewController(mainModel, mainWindow);

        mainWindow.getMainView().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainWindow.getMainView().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
    }

    /**
     * Saves the calendar to a file when the window closes.
     */
    private void onClose() {
        mainWindow.getMainView().dispose();
        dumpToFile();
        System.exit(0);
    }

    /**
     * Loads the events serialized in the events.txt file.
     * @return Whether the events were loaded successfully.
     */
    private boolean loadFromFile() {
        File iFile = new File("events.txt");

        try {
            calendar.load(iFile);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Serializes the current events to the output.txt file.
     * @return Whether the events were serialized successfully.
     */
    private boolean dumpToFile() {
        FileWriter oFile;

        try {
            oFile = new FileWriter("events.txt");
            calendar.dump(oFile);
            oFile.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

}
