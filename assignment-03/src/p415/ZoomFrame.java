package p415;

import javax.swing.*;
import java.awt.*;

public class ZoomFrame extends JFrame {
    ZoomFrame() {
        super("Zoom Tester");

        setLayout(new GridLayout(2,1,5,5));
        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnZoomIn = new JButton("Zoom In");
        JButton btnZoomOut = new JButton("Zoom Out");
        buttonGroup.add(btnZoomIn);
        buttonGroup.add(btnZoomOut);

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

        btnZoomIn.addActionListener(e -> {
            shape.rescale((float) (shape.getScale() + 0.1));
            label.repaint();
        });

        btnZoomOut.addActionListener(e -> {
            shape.rescale((float) (shape.getScale() - 0.1));
            label.repaint();
        });
    }

    private static final int ICON_WIDTH = 400;
    private static final int ICON_HEIGHT = 100;
}
