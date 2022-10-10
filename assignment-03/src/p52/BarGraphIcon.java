package p52;

import javax.swing.*;
import java.awt.*;

public class BarGraphIcon implements Icon {
    private int width;
    private int height;
    private double progress;

    public BarGraphIcon(int width, int height, double progress) {
        this.width = width;
        this.height = height;
        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawRect(0,0, (int) (progress * width) - 1, height - 2);
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
