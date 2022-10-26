package mvc;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
  The data model
*/
public class DataModel
{
   ArrayList<String> lines;
   ArrayList<ChangeListener> listeners;
   /**
      Constructs a DataModel object
   */
   public DataModel()
   {
      lines = new ArrayList<>();
      listeners = new ArrayList<>();
   }

   /**
      Constructs a DataModel object
      @return the data in an ArrayList
   */
   public ArrayList<String> getLines()
   {
      return (ArrayList<String>) lines.clone();
   }

   /**
      Attach a listener to the Model
      @param c the listener
   */
   public void attach(ChangeListener c)
   {
      listeners.add(c);
   }

   /**
    * Adds a line of text to the data model
    * @param line the line of text to add
    */
   public void add(String line)
   {
      lines.add(line);
      notifyListeners();
   }

   private void notifyListeners() {
      for (ChangeListener l : listeners)
      {
         l.stateChanged(new ChangeEvent(this));
      }
   }
}
