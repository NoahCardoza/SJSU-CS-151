/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 10/24/2022
 * @assignment Icon Stamper
 */

import java.awt.*;
import java.awt.geom.*;

/**
 Draws a car on to a 2d graphics context.

 @author Textbook; Modified by Noah Cardoza
 */
public class CarShape implements CompositeShape
{
    private final int x;
    private final int y;
    private final int width;

    /**
     * Constructs a car item.
     *
     * @param x     the left of the bounding rectangle
     * @param y     the top of the bounding rectangle
     * @param width the width of the bounding rectangle
     */
    public CarShape(int x, int y, int width)
    {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    @Override
    public void draw(Graphics2D g2)
    {
        Rectangle2D.Double body = new Rectangle2D.Double(0, (width/3) + width / 6, width, width / 6);
        Ellipse2D.Double frontTire = new Ellipse2D.Double(width / 6, (int)(width/3) +  width / 3, width / 6, width / 6);
        Ellipse2D.Double rearTire = new Ellipse2D.Double(width * 2 / 3, (int)(width/3) + width / 3, width / 6, width / 6);

        // the bottom of the front windshield
        Point2D.Double r1 = new Point2D.Double(width / 6, (int)(width/3) + width / 6);
        // the front of the roof
        Point2D.Double r2 = new Point2D.Double(width / 3, (int)(width/3));
        // the rear of the roof
        Point2D.Double r3 = new Point2D.Double(width * 2 / 3, (int)(width/3));
        // the bottom of the rear windshield
        Point2D.Double r4 = new Point2D.Double(width * 5 / 6, (int)(width/3) + width / 6);

        Path2D.Double cab = new Path2D.Double();
        cab.moveTo(r1.x, r1.y);
        cab.lineTo(r2.x, r2.y);
        cab.lineTo(r3.x, r3.y);
        cab.lineTo(r4.x, r4.y);
        cab.closePath();

        g2.translate(x, y);
        g2.setColor(Color.BLACK);
        g2.fill(frontTire);
        g2.fill(rearTire);
        g2.setColor(Color.red);
        g2.fill(body);
        g2.setColor(Color.cyan);
        g2.fill(cab);
        g2.setColor(Color.red);
        g2.draw(cab);
        g2.translate(-x, -y);
    }
}