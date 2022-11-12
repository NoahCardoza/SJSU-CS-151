package gui.window;

import gui.view.MainView;

import java.awt.*;

public class MainWindow {
    private final MainView mainView;

    public MainWindow() {
        mainView = new MainView();
        mainView.setMinimumSize(new Dimension(600, 400));
        mainView.setVisible(true);
    }

    public MainView getMainView() {
        return mainView;
    }
}
