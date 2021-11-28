/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessadministration;

import assets.classes.AlertDialogs;
import static assets.classes.statics.DATABASE_IP;
import static assets.classes.statics.DEFAULT_DATABASE_IP;
import java.util.Optional;
import java.util.prefs.Preferences;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author AHMED
 */
public class SplashScreenLoader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;
    @FXML
    private ProgressBar prog;
    @FXML
    private Label num;
    Preferences prefs;

    public SplashScreenLoader() {

    }

    @Override
    public void init() throws Exception {

        Parent root1 = FXMLLoader.load(getClass().getResource("splash.fxml"));
        scene = new Scene(root1);
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.preloaderStage = primaryStage;

        // Set preloader scene and show stage.
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {

        if (info instanceof ProgressNotification) {
            String massege = "";
            double progress = ((ProgressNotification) info).getProgress();
            if (progress == 0) {
                massege = "connecting to local db";
            } else if (progress == 1) {
                massege = "successful";
            } else if (progress == 11) {
                massege = "faild";
                AlertDialogs.showError("failed to connect to local db");

            } else if (progress == 2) {
                massege = "connecting to local serever db";

            } else if (progress == 3) {
                massege = "successful";
            } else if (progress == 13) {
                massege = "faild";
                AlertDialogs.showError("failed to connect to serever db");
                TextInputDialog dialog = new TextInputDialog(prefs.get(DATABASE_IP, DEFAULT_DATABASE_IP));
                dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/assets/icons/dns.png"))));
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/assets/icons/logo.png").toString()));
                dialog.setTitle("Set Database Ip");
                dialog.setContentText("Please Enter Database IP:");
                dialog.setHeaderText("Database IP");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(ip -> prefs.put(DATABASE_IP, ip));
            } else if (progress == 4) {
                massege = "check for update";
            } else if (progress == 5) {
                massege = "successful";
            }
//            SplashScreenController.label.setText(massege);
        }

    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

        StateChangeNotification.Type type = info.getType();
        switch (type) {

            case BEFORE_START:

                System.out.println("BEFORE_START");
                preloaderStage.hide();
                break;
        }

    }

}
