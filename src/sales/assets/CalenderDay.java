package sales.assets;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;

/**
 * Create an anchor pane that can store additional data.
 */
public class CalenderDay extends AnchorPane {

    private LocalDate date;
    
  

    public CalenderDay(Node... children) {
        super(children);
         
    }

    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren(); //To change body of generated methods, choose Tools | Templates.
    }
  
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
 
    
}
