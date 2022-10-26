package p54;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 A car that can be resized.

 @author Textbook, modified by Noah Cadoza
 */
public class CarShape implements RescalableShape
{

    private final Color color;
    private final int x;
    private final int y;
    private float scale;
    private final int SCALE = 100;

    /**
     * Constructs a car item.
     *
     * @param x     the left of the bounding rectangle
     * @param y     the top of the bounding rectangle
     * @param scale the width of the bounding rectangle
     * @param color the color of the car
     */
    public CarShape(int x, int y, float scale, Color color)
    {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2)
    {
        g2.setColor(this.color);
        Rectangle2D.Double body = new Rectangle2D.Double(x, y + scale * SCALE / 6, scale * SCALE - 1, scale * SCALE / 6);
        Ellipse2D.Double frontTire = new Ellipse2D.Double(x + scale * SCALE / 6, y + scale * SCALE / 3, scale * SCALE / 6, scale * SCALE / 6);
        Ellipse2D.Double rearTire = new Ellipse2D.Double(x + scale * SCALE * 2 / 3, y + scale * SCALE / 3, scale * SCALE / 6, scale * SCALE / 6);

        // The bottom of the front windshield
        Point2D.Double r1 = new Point2D.Double(x + scale * SCALE / 6, y + scale * SCALE / 6);
        // The front of the roof
        Point2D.Double r2 = new Point2D.Double(x + scale * SCALE / 3, y);
        // The rear of the roof
        Point2D.Double r3 = new Point2D.Double(x + scale * SCALE * 2 / 3, y);
        // The bottom of the rear windshield
        Point2D.Double r4 = new Point2D.Double(x + scale * SCALE * 5 / 6, y + scale * SCALE / 6);
        Line2D.Double frontWindshield = new Line2D.Double(r1, r2);
        Line2D.Double roofTop = new Line2D.Double(r2, r3);
        Line2D.Double rearWindshield = new Line2D.Double(r3, r4);

        g2.draw(body);
        g2.draw(frontTire);
        g2.draw(rearTire);
        g2.draw(frontWindshield);
        g2.draw(roofTop);
        g2.draw(rearWindshield);
    }

    @Override
    public void rescale(float scale) {
        this.scale = scale;
    }
}