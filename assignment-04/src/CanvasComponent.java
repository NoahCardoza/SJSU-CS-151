/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 10/24/2022
 * @assignment Icon Stamper
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Canvas that draws the selected shape whereever the user clicks
 */
public class CanvasComponent extends JComponent {
    private final List<CompositeShape> shapes;
    private CompositeShapeFactory selectedShape;

    /**
     * Constructs a canvas component
     *
     * @param defaultShape the default shape to draw
     */
    public CanvasComponent (CompositeShapeFactory defaultShape) {
        super();
        shapes = new ArrayList<>();
        selectedShape = defaultShape;
        addMouseListener(new MouseListener());
    }

    private class MouseListener extends MouseAdapter
    {
        /**
         * Called when the user clicks the canvas
         *
         * @param event the event to be processed
         */
        public void mousePressed(MouseEvent event)
        {
            Point point = event.getPoint();
            shapes.add(selectedShape.factory(point.x, point.y));
            repaint();
        }
    }

    /**
     * Paints the shapes in the shapes list to the screen.
     *
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (CompositeShape s : shapes)
        {
            s.draw(g2);
        }
    }

    /**
     * Updates the selected shape.
     *
     * @param selectedShape the new shape to select
     */
    public void setSelectedShape(CompositeShapeFactory selectedShape) {
        this.selectedShape = selectedShape;
    }
}
