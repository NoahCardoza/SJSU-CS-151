package p422;

import javax.swing.*;
import java.awt.*;

/**
 An icon that contains a moveable shape.
 */
public class ShapeIcon implements Icon
{
    private final int width;
    private final int height;
    private final Shape shape;

    public ShapeIcon(Shape shape, int width, int height)
    {
        this.shape = shape;
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
        shape.draw(g2);
    }
}