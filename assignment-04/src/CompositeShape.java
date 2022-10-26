/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 10/24/2022
 * @assignment Icon Stamper
 */

import java.awt.*;

/**
 * A general interface to describe a class
 * with a method capable of drawing a shape to a 2d graphics context
 */
public interface CompositeShape {
    /**
     * Draws a composite shape.
     *
     * @param g2 graphics 2d context
     */
    void draw(Graphics2D g2);
}
