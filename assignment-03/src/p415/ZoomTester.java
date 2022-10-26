package p415;


import javax.swing.*;
import java.awt.*;

/**
 * A program to show how to use two buttons to rescale an icon
 */
public class ZoomTester {
    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;

    /**
     * The main method of the program
     * @param args unused
     */
    public static void main(String[] args) {
        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnZoomIn = new JButton("Zoom In");
        JButton btnZoomOut = new JButton("Zoom Out");
        buttonGroup.add(btnZoomIn);
        buttonGroup.add(btnZoomOut);

        RescalableShape shape = new CarShape(0, 0, 1, Color.BLACK);
        Icon icon = new ShapeIcon(shape, ICON_WIDTH, ICON_HEIGHT);
        JLabel label = new JLabel(icon);
        JPanel drawingPanel = new JPanel();
        drawingPanel.add(Box.createHorizontalGlue());
        drawingPanel.add(label);
        drawingPanel.add(Box.createHorizontalGlue());

        // create the frame
        JFrame frame = new JFrame("Zoom Tester");
        frame.setLayout(new GridLayout(2,1,5,5));
        frame.add(buttonGroup, BorderLayout.NORTH);
        frame.add(drawingPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // add event listeners
        btnZoomIn.addActionListener(e -> {
            shape.rescale((float) (shape.getScale() + 0.1));
            label.repaint();
        });

        btnZoomOut.addActionListener(e -> {
            shape.rescale((float) (shape.getScale() - 0.1));
            label.repaint();
        });
    }
}
