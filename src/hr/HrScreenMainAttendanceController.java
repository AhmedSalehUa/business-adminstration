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
public class HrScreenMainAttendanceController implements Initializable {

    Preferences prefs;
    @FXML
    private Button manualPush;
    @FXML
    private Button showAttend;
    @FXML
    private Button leaveMaster;

    BorderPane parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        new ZoomInRight(manualPush).play();
        new ZoomInLeft(showAttend).play();
        new ZoomInRight(leaveMaster).play();
    }

    @FXML
    private void openManualPush(ActionEvent event) {
        try {
            Parent mainMember = FXMLLoader.load(getClass().getResource(HrScreenPushAttendance));
            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
            parent.setCenter(mainMember);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void showAttend(ActionEvent event) {
        try {
            Parent mainMember = FXMLLoader.load(getClass().getResource(HrScreenAttendanceView));
            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
           parent.setCenter(mainMember);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void openleaveMaster(ActionEvent event) {
        try {
            Parent mainMember = FXMLLoader.load(getClass().getResource(HrScreenEmployeeLeaveMaster));
            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
            parent.setCenter(mainMember);
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

}
