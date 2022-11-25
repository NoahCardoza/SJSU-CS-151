/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

import gui.GUI;

import javax.swing.*;

/**
 * The entry point of the application.
 */
public class SimpleCalendarTester {
    /**
     * The main method called when the program starts.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}