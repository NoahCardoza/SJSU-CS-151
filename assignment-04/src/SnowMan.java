/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 10/24/2022
 * @assignment Icon Stamper
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Draws a snow man onto a 2d graphics context
 */
public class SnowMan implements CompositeShape {
    private final int x;
    private final int y;
    private final int height;

    /**
     * Constructs a snow man instance
     * @param x x offset
     * @param y y offset
     * @param height the height of the snowman (draws inside a square)
     */
    public SnowMan(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }
    @Override
    public void draw(Graphics2D g2) {
        int bodyHeight = height / 2;
        int translateX = height / 4;

        Ellipse2D.Double torso
                = new Ellipse2D.Double(x, y, bodyHeight, bodyHeight);
        Ellipse2D.Double legs
                = new Ellipse2D.Double(x, y + bodyHeight, bodyHeight, bodyHeight);

        g2.setColor(Color.BLACK);
        g2.translate(translateX, 0);
        g2.draw(torso);
        g2.draw(legs);
        g2.translate(-translateX, 0);
    }
}
