package p420;

import javax.swing.*;
import java.awt.*;

/**
 An icon that contains multiple shapes.
 */
public class MultiShapeIcon implements Icon
{
    private final int width;
    private final int height;
    private final java.util.List<Shape> shapes;

    /**
     * Constructs a MultiShapeIcon
     * @param shapes a list of shapes to render
     * @param width the width of the scene
     * @param height the height of the scene
     */
    public MultiShapeIcon(java.util.List<Shape> shapes, int width, int height)
    {
        this.shapes = shapes;
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
        for (Shape shape : shapes) {
            shape.draw(g2);
        }
    }
}