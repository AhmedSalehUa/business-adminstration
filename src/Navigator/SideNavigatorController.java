/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigator;

import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.*;
import businessadministration.BusinessAdministration;
import db.User;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class SideNavigatorController implements Initializable {

    @FXML
    private Label mainLabel;
    @FXML
    private ImageView imgview;

    Preferences prefs;
    @FXML
    private Button storeButton;
    @FXML
    private Button salesButton;
    @FXML
    private Button accountsButton;
    @FXML
    private Button hrButton;
    @FXML
    private Button clientBtn;
    @FXML
    private Button providersBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        mainLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {

                Parent login = FXMLLoader.load(getClass().getResource("/Navigator/Home.fxml"));login.getStylesheets().clear();
                login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());

                Scene current = (Scene) mainLabel.getScene();
                Stage st = (Stage) mainLabel.getScene().getWindow();
                Scene sc = new Scene(login, current.getWidth(), current.getHeight());
                st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                st.setTitle("Acapy Trade Business Administration ");
                st.setScene(sc);
                st.show();
            } catch (IOException ex) {
                AlertDialogs.showErrors(ex);
            }
        });
        clientBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("ClientScreen")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(ClientData));login.getStylesheets().clear();
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene current = (Scene) mainLabel.getScene();
                    Scene sc = new Scene(login, current.getWidth(), current.getHeight());
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Business Administration (العملاء)");
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        salesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Sales")) {
                try {
                    SalesDialogController dialog = new SalesDialogController(salesButton.getScene().getWindow());

                    Window window = dialog.getDialogPane().getScene().getWindow();
                    window.setOnCloseRequest(event -> window.hide());
                    Optional<String> a = dialog.showAndWait();
                    if (a.toString().equals("Optional[choosedIn]")) {
                        Parent mainData = FXMLLoader.load(getClass().getResource(SalesScreen));mainData.getStylesheets().clear();
                        mainData.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                        Scene current = ((Node) e.getSource()).getScene();
                        Scene sc = new Scene(mainData, current.getWidth(), current.getHeight());
                        Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                        st.setTitle("Business Administration (المبيعات)");
                        st.setScene(sc);
                    } else if (a.toString().equals("Optional[choosedOut]")) {
                        Parent mainData = FXMLLoader.load(getClass().getResource(SalesOutScreen));mainData.getStylesheets().clear();
                        mainData.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                        Scene current = ((Node) e.getSource()).getScene();
                        Scene sc = new Scene(mainData, current.getWidth(), current.getHeight());
                        Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                        st.setTitle("Business Administration (مبيعات خارجي)");
                        st.setScene(sc);
                    } else if (a.toString().equals("Optional[choosedOutTrack]")) {
                        Parent mainData = FXMLLoader.load(getClass().getResource(SalesOutTrackScreen));mainData.getStylesheets().clear();
                        mainData.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                        Scene current = ((Node) e.getSource()).getScene();
                        Scene sc = new Scene(mainData, current.getWidth(), current.getHeight());
                        Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                        st.setTitle("Business Administration (خطوط سير)");
                        st.setScene(sc);
                    }
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        storeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Store")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(StoreScreen));login.getStylesheets().clear();
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene current = (Scene) mainLabel.getScene();
                    Scene sc = new Scene(login, current.getWidth(), current.getHeight());
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Business Administration (المخازن)");
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        accountsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Accounts")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(AccountsScreen));login.getStylesheets().clear();
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene current = (Scene) mainLabel.getScene();
                    Scene sc = new Scene(login, current.getWidth(), current.getHeight());
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Business Administration (الحسابات)");
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        hrButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Hr")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(HrScreen));login.getStylesheets().clear();
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene current = (Scene) mainLabel.getScene();
                    Scene sc = new Scene(login, current.getWidth(), current.getHeight());
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Business Administration (شؤون الموظفين)");
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }
        });
        providersBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("provider")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(ProviderScreen));login.getStylesheets().clear();
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene current = (Scene) mainLabel.getScene();
                    Scene sc = new Scene(login, current.getWidth(), current.getHeight());
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Business Administration (الموردين)");
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }
        });

    }

}
