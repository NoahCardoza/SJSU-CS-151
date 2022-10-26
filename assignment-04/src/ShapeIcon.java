/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 10/24/2022
 * @assignment Icon Stamper
 */

import javax.swing.*;
import java.awt.*;

/**
 * A new wrapper for composite shapes to render
 * them as icons.
 */
public class ShapeIcon implements Icon {
    private final int width;
    private final int height;
    private final CompositeShape shape;

    /**
     * Constructs a ShapeIcon instance.
     *
     * @param shape the shape to paint
     * @param width the width of the shape
     * @param height the height of the shape
     */
    public ShapeIcon(CompositeShape shape, int width, int height)
    {
        this.shape = shape;
        this.width = width;
        this.height = height;
    }


    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(x, y);
        shape.draw(g2);
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
