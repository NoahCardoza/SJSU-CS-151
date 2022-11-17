package gui.window;

import gui.model.MainModel;
import gui.view.MainView;

import java.awt.*;

public class MainWindow {
    private final MainView mainView;

    public MainWindow(MainModel mainModel) {
        mainView = new MainView(mainModel);
        mainView.setMinimumSize(new Dimension(600, 400));
        mainView.setVisible(true);
    }

    public MainView getMainView() {
        return mainView;
    }
}
