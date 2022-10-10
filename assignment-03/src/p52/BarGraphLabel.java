package p52;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.*;

public class BarGraphLabel extends JLabel implements DocumentListener {
    private BarGraphIcon icon;
    private JTextField textField;

    public BarGraphLabel(JTextField textField, int width, int height, double progress) {
        this(new BarGraphIcon(width, height, progress));

        this.textField = textField;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e);
                textField.setText(Integer.toString((int) (100 * (((double) e.getX()) / width))));
            }
        });
    }

    private BarGraphLabel(BarGraphIcon icon) {
        super(icon);
        this.icon = icon;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        Document doc = e.getDocument();

        onChange(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onChange(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onChange(e);
    }

    private void onChange(DocumentEvent e) {
        Document doc = e.getDocument();
        try {
            String value = doc.getText(0, e.getDocument().getLength()).trim();

            if (value.isEmpty()) {
//                textField.setText("0");
                value = "0";
            }

            double percentile = Double.parseDouble(value);

            if (percentile > 100) {
//                textField.setText("100");
                percentile = 100;
            } else if (percentile < 0) {
//                textField.setText("0");
                percentile = 0;
            }

            this.icon.setProgress(percentile / 100);
            this.repaint();
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
