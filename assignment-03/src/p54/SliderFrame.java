package p54;

import javax.swing.*;
import java.awt.*;

public class SliderFrame extends JFrame {
    SliderFrame() {
        super("Slider");

        setLayout(new GridLayout(2,1,5,5));
        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new FlowLayout(FlowLayout.CENTER));
        JSlider slider = new JSlider();
        slider.setMinimum(0);
        slider.setValue(100);
        slider.setMaximum(200);
        buttonGroup.add(slider);

        // TODO: how to center the label?
        RescalableShape shape = new CarShape(0, 0, 1, Color.BLACK);
        Icon icon = new ShapeIcon(shape, ICON_WIDTH, ICON_HEIGHT);
        JLabel label = new JLabel(icon);
        JPanel drawingPanel = new JPanel();
        drawingPanel.add(Box.createHorizontalGlue());
        drawingPanel.add(label);
        drawingPanel.add(Box.createHorizontalGlue());

        add(buttonGroup, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.SOUTH);


        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        slider.addChangeListener((e) -> {
            shape.rescale(((float) slider.getValue()) / 100);
            label.repaint();
        });
    }

    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;
}
