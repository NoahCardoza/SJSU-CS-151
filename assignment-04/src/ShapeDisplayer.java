/**
 * @author Noah Cardoza
 * @version 0.0.1
 * @date 10/24/2022
 * @assignment Icon Stamper
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A GUI program that allows the user to select shapes and
 * stamps them where the user clicks in the window.
 */
public class ShapeDisplayer {
    /**
     * The main method of the program.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        // define the shapes to display
        ArrayList<CompositeShapeFactory> shapes = new ArrayList<>();
        shapes.add((x, y) -> new CarShape(x, y, 40));
        shapes.add((x, y) -> new SnowMan(x, y, 40));
        shapes.add((x, y) -> new IceCreamCone(x, y, 40));

        // create the header and canvas
        JPanel header = new JPanel(new FlowLayout());
        CanvasComponent canvas = new CanvasComponent(shapes.get(0));

        // create the header buttons and attach the event listeners
        for (CompositeShapeFactory shapeFactory : shapes) {
            // create a new icon with the button
            JButton btn = new JButton(
                    new ShapeIcon(
                            shapeFactory.factory(0 , 0),
                            40,
                            40
                    )
            );
            // add a listener to the button
            btn.addActionListener((event) -> {
                // when clicked update the selected shape with the shape in the button that was just clicked
                canvas.setSelectedShape(shapeFactory);
            });
            // append the button to the header
            header.add(btn);
        }

        // create the main frame
        JFrame frame = new JFrame("Shape Displayer");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // define the size of all the components
        header.setPreferredSize(new Dimension(400, 60));
        canvas.setPreferredSize(new Dimension(400,400));
        frame.setSize(400, 460);

        frame.add(header);
        frame.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
