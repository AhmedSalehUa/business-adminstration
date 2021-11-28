/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr;
 
import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import static assets.classes.statics.*;  
import businessadministration.BusinessAdministration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage; 

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenMainHolidaysAndLeaveController implements Initializable {
 Preferences prefs;
    @FXML
    private Button earlyLeave; 
    @FXML
    private Button holidays;
 BorderPane parent;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
       new ZoomInRight(earlyLeave).play();
          new ZoomInLeft(holidays).play();
       
    }    
    
    public void setParent(BorderPane parent) {
        this.parent = parent;
    }


    @FXML
    private void earlyLeave(ActionEvent event) {
        try {
            Parent mainMember = FXMLLoader.load(getClass().getResource(HrScreenLeaveMaster));
            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
           parent.setCenter(mainMember);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }
 

    @FXML
    private void holidays(ActionEvent event) {
          try {
            Parent mainMember = FXMLLoader.load(getClass().getResource(HrScreenHolidays));
            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
            parent.setCenter(mainMember);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    
}
