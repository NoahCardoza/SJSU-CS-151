package p52;

import java.awt.*;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

import java.text.NumberFormat;


public class ObserverFrame extends JFrame {
    static final int GRAPH_WIDTH = 200;
    static final int GRAPH_HEIGHT = 20;

    public ObserverFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        JPanel formPane = new JPanel();
        formPane.setLayout(new GridLayout(3, 2));

        NumberFormat percentFormat = NumberFormat.getNumberInstance();
        percentFormat.setMinimumIntegerDigits(0);
        percentFormat.setMaximumIntegerDigits(3);

        JFormattedTextField tf1 = new JFormattedTextField(percentFormat);
        JFormattedTextField tf2 = new JFormattedTextField(percentFormat);
        JFormattedTextField tf3 = new JFormattedTextField(percentFormat);

        tf1.setValue(10);
        tf2.setValue(40);
        tf3.setValue(90);

        tf1.setColumns(3);
        tf2.setColumns(3);
        tf3.setColumns(3);

        formPane.add(new JLabel("Number 1:"));
        formPane.add(tf1);
        formPane.add(new JLabel("Number 2:"));
        formPane.add(tf2);
        formPane.add(new JLabel("Number 3:"));
        formPane.add(tf3);

        contentPane.add(formPane);

        BarGraphLabel lb1 = new BarGraphLabel(tf1, GRAPH_WIDTH, GRAPH_HEIGHT, .1);
        BarGraphLabel lb2 = new BarGraphLabel(tf2, GRAPH_WIDTH, GRAPH_HEIGHT, .4);
        BarGraphLabel lb3 = new BarGraphLabel(tf3, GRAPH_WIDTH, GRAPH_HEIGHT, .9);

        contentPane.add(lb1);
        contentPane.add(lb2);
        contentPane.add(lb3);

        tf1.getDocument().addDocumentListener(lb1);
        tf2.getDocument().addDocumentListener(lb2);
        tf3.getDocument().addDocumentListener(lb3);

        pack();
    }
}