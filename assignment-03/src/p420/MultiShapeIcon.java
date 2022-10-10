package p420;

import javax.swing.*;
import java.awt.*;

/**
 An icon that contains a moveable shape.
 */
public class MultiShapeIcon implements Icon
{
    private final int width;
    private final int height;
    private final java.util.List<Shape> shapes;

    public MultiShapeIcon(java.util.List<Shape> shapes, int width, int height)
    {
        this.shapes = shapes;
        this.width = width;
        this.height = height;
    }

    public int getIconWidth()
    {
        return width;
    }

    public int getIconHeight()
    {
        return height;
    }

    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g;
        for (Shape shape : shapes) {
            shape.draw(g2);
        }
    }
}