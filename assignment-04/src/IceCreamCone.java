/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 10/24/2022
 * @assignment Icon Stamper
 */

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Draws an ice cream cone onto a 2d graphics context
 */
public class IceCreamCone implements CompositeShape {
    private final int x;
    private final int y;
    private final int height;

    /**
     * Constructs an ice cream cone instance
     * @param x x offset
     * @param y y offset
     * @param height the height of the ice cream cone (draws inside a square)
     */
    public IceCreamCone(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g2) {
        int coneHeight = (int)(height * ((float)2 / 3));
        int coneWidth = height / 2;
        int coneCenter = coneWidth / 2;
        int creamHeight = height / 3;
        int translateX = coneWidth / 2;

        double hype = Math.sqrt(Math.pow(coneHeight, 2) + Math.pow(coneCenter, 2));
        double theta =  Math.asin(coneCenter / hype);


        Path2D conePath = new Path2D.Double();
        conePath.moveTo(0, creamHeight);
        conePath.lineTo(coneCenter, creamHeight + coneHeight);
        conePath.lineTo(coneWidth, creamHeight);
        conePath.closePath();

        Line2D.Double waffleLine1 = new Line2D.Double(
                (double)((float)1/3) * hype * Math.sin(theta),
                creamHeight + (double)((float)1/3) * hype * Math.cos(theta),
                (double)((float)1/3) * coneWidth,
                creamHeight
        );
        Line2D.Double waffleLine2 = new Line2D.Double(
                (double)((float)2/3) * hype * Math.sin(theta),
                creamHeight + (double)((float)2/3) * hype * Math.cos(theta),
                (double)((float)2/3) * coneWidth,
                creamHeight
        );

        Line2D.Double waffleLine3 = new Line2D.Double(
                coneWidth - (double)((float)1/3) * hype * Math.sin(theta),
                creamHeight + (double)((float)1/3) * hype * Math.cos(theta),
                (double)((float)2/3) * coneWidth,
                creamHeight
        );
        Line2D.Double waffleLine4 = new Line2D.Double(
                coneWidth - (double)((float)2/3) * hype * Math.sin(theta),
                creamHeight + (double)((float)2/3) * hype * Math.cos(theta),
                (double)((float)1/3) * coneWidth,
                creamHeight
        );

        Arc2D.Double creamArc = new Arc2D.Double(0, 0, coneWidth, creamHeight * 2, 0, 180, Arc2D.PIE);

        g2.translate(translateX, 0);
        g2.translate(x, y);
        g2.setColor(new Color(252, 191, 91));
        g2.fill(conePath);
        g2.setColor(new Color(219, 144, 0));
        g2.draw(conePath);
        g2.draw(waffleLine1);
        g2.draw(waffleLine2);
        g2.draw(waffleLine3);
        g2.draw(waffleLine4);
        g2.setColor(Color.pink);
        g2.fill(creamArc);
        g2.translate(-x, -y);
        g2.translate(-translateX, 0);
    }
}
