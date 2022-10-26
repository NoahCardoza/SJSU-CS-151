package mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
   A tester class for an example MVC based app

   The view/controller
*/
public class MVCTester
{
   /**
    * The main method of the program
    * @param args any command line arguments passed from the shell
    */
   public static void main(String[] args)
   {
      DataModel model = new DataModel();

      // create button
      JButton button = new JButton("Add");

      // create text area
      JTextArea textArea = new JTextArea();
      textArea.setPreferredSize(new Dimension(300, 300));

      // create text field
      JTextField textField = new JTextField();

      ActionListener onAddToTextBox = (event) -> {
         String line = textField.getText().trim();
         if (!line.isEmpty()) {
            model.add(line);
         }
         textField.setText("");
      };

      button.addActionListener(onAddToTextBox);
      textField.addActionListener(onAddToTextBox);

      model.attach((event) -> {
         textArea.setText(String.join(System.lineSeparator(), model.getLines()));
         textField.requestFocus();
      });

      JFrame frame = new JFrame();
      frame.setTitle("MVC Example");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Container pane = frame.getContentPane();

      pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

      // center align components
      textField.setAlignmentX(Container.CENTER_ALIGNMENT);
      button.setAlignmentX(Container.CENTER_ALIGNMENT);
      textArea.setAlignmentX(Container.CENTER_ALIGNMENT);

      // add components to panel
      pane.add(button);
      pane.add(textArea);
      pane.add(textField);

      // configure the frame
      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);

      // request the text field be focused (rather than the button by default)
      textField.requestFocus();
   }
}
