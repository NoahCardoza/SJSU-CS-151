package p422;

import javax.swing.*;
import java.awt.*;

public class Animation2Frame extends JFrame {
    Animation2Frame() {
        super("Animation 2");

        // TODO: how to center the label?
        MovableShape shape = new CarShape(0, 0, 1, Color.BLACK);

        Icon icon = new ShapeIcon(shape, ICON_WIDTH, ICON_HEIGHT);
        JLabel label = new JLabel(icon);
        add(label);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Timer timer = new Timer(10, e -> {
            ((MovableShape) shape).move();
            CarShape car = (CarShape) shape;

            if (car.getX() > ICON_WIDTH) {
                car.setX(-CAR_WIDTH);
            }

            label.repaint();
        });
        timer.start();
    }
    private static final int CAR_WIDTH = 100;
    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;
}
