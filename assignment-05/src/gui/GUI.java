package gui; /**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 09/12/2022
 * @assignment My First Calendar
 */

import calender.MyCalender;
import gui.controller.MainController;
import gui.model.MainModel;
import gui.window.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Contains all the methods needed to interact with the stdin/out.
 */
public class GUI {
    private final MyCalender calender;
    private final MainWindow mainWindow;

    public GUI() {
        calender = new MyCalender();

        if (loadFromFile()) {
            System.out.println("Success: Events loaded from events.txt!");
        } else {
            System.out.println("Error: events.txt could not be found.");
        }

        mainWindow = new MainWindow();
        MainModel mainModel = new MainModel("", "", null, null);

        MainController mainController = new MainController(calender, mainWindow, mainModel);
        mainController.setup();

        mainWindow.getMainView().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainWindow.getMainView().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

    }

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
            calender.load(iFile);
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
            calender.dump(oFile);
            oFile.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

}
