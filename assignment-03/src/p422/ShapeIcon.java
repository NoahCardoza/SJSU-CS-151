package p422;

import javax.swing.*;
import java.awt.*;

/**
 An icon that contains a shape.
 */
public class ShapeIcon implements Icon
{
    private final int width;
    private final int height;
    private final Shape shape;

    /**
     * Constructs a ShapeICon
     * @param shape the shape to render
     * @param width the width of the icon
     * @param height the height of the icon
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