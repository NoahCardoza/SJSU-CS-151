/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 11/08/2022
 * @assignment Calendar GUI
 */

package gui.window;

import gui.model.MainModel;
import gui.view.MainView;

import java.awt.*;

/**
 * Configures the main window.
 */
public class MainWindow {
    private final MainView mainView;

    /**
     * Constructs a new instance.
     *
     * @param mainModel a reference to the main model
     */
    public MainWindow(MainModel mainModel) {
        mainView = new MainView(mainModel);
        mainView.setMinimumSize(new Dimension(600, 400));
        mainView.setVisible(true);
    }

    public MainView getMainView() {
        return mainView;
    }
}
