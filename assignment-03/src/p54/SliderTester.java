package p54;

import javax.swing.*;
import java.awt.*;

/**
 * An example program to show how a slider works
 */
public class SliderTester {
    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;

    /**
     * The main method of the program
     * @param args unused
     */
    public static void main(String[] args) {
        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new FlowLayout(FlowLayout.CENTER));
        JSlider slider = new JSlider();
        slider.setMinimum(0);
        slider.setValue(100);
        slider.setMaximum(200);
        buttonGroup.add(slider);

        RescalableShape shape = new CarShape(0, 0, 1, Color.BLACK);
        Icon icon = new ShapeIcon(shape, ICON_WIDTH, ICON_HEIGHT);
        JLabel label = new JLabel(icon);
        JPanel drawingPanel = new JPanel();
        drawingPanel.add(Box.createHorizontalGlue());
        drawingPanel.add(label);
        drawingPanel.add(Box.createHorizontalGlue());

        // create frame
        JFrame frame = new JFrame("Slider Example");
        frame.setLayout(new GridLayout(2,1,5,5));
        frame.add(buttonGroup, BorderLayout.NORTH);
        frame.add(drawingPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // add event listeners
        slider.addChangeListener((e) -> {
            shape.rescale(((float) slider.getValue()) / 100);
            label.repaint();
        });
    }
}
