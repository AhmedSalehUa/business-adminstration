/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigator;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesDialogController extends Dialog<String> {

    @FXML
    private Pane out;
    @FXML
    private Pane in;
    @FXML
    private Pane out1;

    /**
     * Initializes the controller class.
     */
    public SalesDialogController(Window owner) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Navigator/SalesDialog.fxml"));
            loader.setController(this);

            DialogPane dialogPane = loader.load();

            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);

            setResizable(true);
            setTitle("Choose Sales");
            setDialogPane(dialogPane);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void choosedOut(MouseEvent event) {
        Stage st = (Stage) in.getScene().getWindow();
        setResult("choosedOut");
        st.close();
    }

    @FXML
    private void choosedIn(MouseEvent event) {

        Stage st = (Stage) in.getScene().getWindow();
        setResult("choosedIn");
        st.close();
    }

    @FXML
    private void close(ActionEvent event) {
        Stage st = (Stage) in.getScene().getWindow();
        setResult("noChoose");
        st.close();
    }

    @FXML
    private void choosedOutTrack(MouseEvent event) {
        Stage st = (Stage) in.getScene().getWindow();
        setResult("choosedOutTrack");
        st.close();
    }
}
