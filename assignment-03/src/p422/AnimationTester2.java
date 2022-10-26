package p422;

import javax.swing.*;
import java.awt.*;

public class AnimationTester2 {
    private static final int CAR_WIDTH = 100;
    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;
    public static void main(String[] args) {
        CarShape shape = new CarShape(0, 0, 1, Color.BLACK);

        Icon icon = new ShapeIcon(shape, ICON_WIDTH, ICON_HEIGHT);
        JLabel label = new JLabel(icon);
        JFrame frame = new JFrame("Animation 2");
        frame.add(label);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // hooking up the timer/event loop
        Timer timer = new Timer(10, e -> {
            ((MovableShape) shape).move();

            if (shape.getX() > ICON_WIDTH) {
                shape.setX(-CAR_WIDTH);
            }

            label.repaint();
        });
        timer.start();
    }
}
