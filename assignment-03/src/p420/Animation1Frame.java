package p420;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Animation1Frame extends JFrame {
    Animation1Frame() {
        super("Animation 1");

        // TODO: how to center the label?
        java.util.List<Shape> shapes = new ArrayList<>();

        shapes.add(new CarShape(35, 50, 1, 1, Color.PINK));
        shapes.add(new CarShape(10, 20, 1, 2, Color.MAGENTA));
        shapes.add(new CarShape(0, 0, 1, 3, Color.ORANGE));

        Icon icon = new MultiShapeIcon(shapes, ICON_WIDTH, ICON_HEIGHT);
        JLabel label = new JLabel(icon);
        add(label);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Timer timer = new Timer(100, e -> {
            for (Shape shape1 : shapes) {
                ((MovableShape) shape1).move();
            }
            label.repaint();
        });
        timer.start();
    }

    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;
}
