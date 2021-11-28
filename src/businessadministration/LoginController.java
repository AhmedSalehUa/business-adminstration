/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessadministration;

import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.User;
import db.lite;
import de.jensd.fx.glyphs.emojione.EmojiOne;
import de.jensd.fx.glyphs.emojione.EmojiOneView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author ahmed
 */
public class LoginController implements Initializable {

    String defaultValue = "lightMode";

    Preferences prefs;

    @FXML
    private Label label;
    @FXML
    private AnchorPane root;
    @FXML
    private Button loginBtn;
//    private EmojiOneView loginIcon;
    @FXML
    private ProgressIndicator indicator;
//    private FontAwesomeIconView server;
    @FXML
    private JFXTextField userneme;
    @FXML
    private JFXPasswordField password;
    @FXML
    private ImageView server;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);

//        Font.loadFont(
//                getClass().getResource(prefs.get(FONTPATH, FONTPATH_DEFAULT)).toExternalForm(),
//                10
//        );

        root.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

//        loginIcon.setIcon(EmojiOne.UNAMUSED);
        loginBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
//            loginIcon.setIcon(EmojiOne.SMILEY);
//            loginIcon.setStyle(" -fx-fill:#000");
        });
        loginBtn.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
//            loginIcon.setIcon(EmojiOne.UNAMUSED);
//            loginIcon.setStyle(" -fx-fill:#fff");
        });
        loginBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            loggin();
        });
        password.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                loggin();

            }
        });
        server.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            TextInputDialog dialog = new TextInputDialog(prefs.get(DATABASE_IP, DEFAULT_DATABASE_IP));
            dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/assets/icons/dns.png"))));
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/assets/icons/logo.png").toString()));
            dialog.setTitle("Set Database Ip");
            dialog.setContentText("Please Enter Database IP:");
            dialog.setHeaderText("Database IP");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(ip -> prefs.put(DATABASE_IP, ip));

        });
    }

    private void loggin() {
        try {
            loginBtn.setVisible(false);
            indicator.setVisible(true);
            User us = new User();
            us.setName(userneme.getText());
            us.setPassword(password.getText());

            Service<Void> service = new Service<Void>() {
                boolean logined = false;

                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try { 
                                if (us.checkSignIn()) { 
                                    prefs.put(USER_ID, Integer.toString(us.getId()));
                                    prefs.put(USER_NAME, us.getName());
                                    prefs.put(USER_ROLE, us.getRole());
                                    logined = true;
                                }
                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            }
                            return null;
                        }
                    };

                }

                @Override
                protected void succeeded() { 
                    if (logined) {
                        try {
                            Parent mainMember = FXMLLoader.load(getClass().getResource("/Navigator/Home.fxml"));

                            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

                            Scene sc = new Scene(mainMember);
                            Stage st = (Stage) loginBtn.getScene().getWindow();
                            st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                            st.setTitle("Acapy Trade Business Administration");

                            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                            st.setX((screenBounds.getWidth() - 1360) / 2);
                            st.setY((screenBounds.getHeight() - 760) / 2);

                            st.setScene(sc);
                        } catch (Exception e) {
                            AlertDialogs.showErrors(e);
                        }
                    } else {
                        AlertDialogs.showError("اسم المستخدم او كلمةالمرور خاطئة");
                        loginBtn.setVisible(true);
                        indicator.setVisible(false);
                    }

                    super.succeeded();
                }
            };
            service.start();

        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

}
