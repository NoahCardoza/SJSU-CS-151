package p415;

import javax.swing.*;
import java.awt.*;

/**
 An icon that contains a movable shape.
 */
public class ShapeIcon implements Icon
{
    private final int width;
    private final int height;
    private final Shape shape;

    /**
     * Constructs a ShapeIcon
     * @param shape the shape to draw
     * @param width the width of the shape
     * @param height the height of the shape
     */
    public ShapeIcon(Shape shape, int width, int height)
    {
        this.shape = shape;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getIconWidth()
    {
        return width;
    }
    @Override
    public int getIconHeight()
    {
        return height;
    }
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D) g;
        shape.draw(g2);
    }
}