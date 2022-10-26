package p420;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A program that shows 3 cars moving at different speeds.
 */
public class AnimationTester1 {
    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;

    /**
     * The main method.
     * @param args unused
     */
    public static void main(String[] args) {
        // create the array of moving cars
        java.util.List<Shape> shapes = new ArrayList<>();
        shapes.add(new CarShape(35, 50, 1, 1, Color.PINK));
        shapes.add(new CarShape(10, 20, 1, 2, Color.MAGENTA));
        shapes.add(new CarShape(0, 0, 1, 3, Color.ORANGE));

        Icon icon = new MultiShapeIcon(shapes, ICON_WIDTH, ICON_HEIGHT);
        JLabel label = new JLabel(icon);

        // creating the main frame
        JFrame frame = new JFrame("Animation 1");
        frame.add(label);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // hooking up the timer/event loop
        Timer timer = new Timer(100, e -> {
            for (Shape shape1 : shapes) {
                ((MovableShape) shape1).move();
            }
            label.repaint();
        });
        timer.start();
    }
}
