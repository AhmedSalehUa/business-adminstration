/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.THEME;
import businessadministration.BusinessAdministration;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StorePermissionsController implements Initializable {

    @FXML
    private Tab entrance;
    @FXML
    private Tab returnProvider;
    @FXML
    private Tab exit;
    @FXML
    private Tab returnedClient;
    @FXML
    private Tab exitLine;
    @FXML
    private Tab linrReturned;
    @FXML
    private Tab transactions;
    @FXML
    private Tab trashing;
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    configPannels();

                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
                                }
                            }

                        });
                        latch.await();

                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {

                super.succeeded();
            }
        };
        service.start();
    }

    private void configPannels() throws Exception {
        Parent node1 = FXMLLoader.load(getClass().getResource("/store/permissions/PermissionEntrance.fxml"));
        entrance.setContent(node1);
        node1.getStylesheets().clear();
        node1.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
        
        Parent node2=FXMLLoader.load(getClass().getResource("/store/permissions/PermissionReturnedProvider.fxml"));
        returnProvider.setContent(node2);
        node2.getStylesheets().clear();
        node2.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
        
        Parent node3=FXMLLoader.load(getClass().getResource("/store/permissions/PermissionExit.fxml"));
        exit.setContent(node3);
        node3.getStylesheets().clear();
        node3.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
        
        Parent node4=FXMLLoader.load(getClass().getResource("/store/permissions/PermissionReturnedClient.fxml"));
        returnedClient.setContent(node4);
        node4.getStylesheets().clear();
        node4.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
        
        Parent node5=FXMLLoader.load(getClass().getResource("/store/permissions/PermissionLineExit.fxml"));
        exitLine.setContent(node5);
        node5.getStylesheets().clear();
        node5.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
        
        Parent node6=FXMLLoader.load(getClass().getResource("/store/permissions/PermissionLineReturned.fxml"));
        linrReturned.setContent(node6);
        node6.getStylesheets().clear();
        node6.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
        
        Parent node7=FXMLLoader.load(getClass().getResource("/store/permissions/PermissionTransactions.fxml"));
        transactions.setContent(node7);
        node7.getStylesheets().clear();
        node7.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
        
        Parent node8=FXMLLoader.load(getClass().getResource("/store/permissions/PermissionTrashing.fxml"));
        trashing.setContent(node8);
        node8.getStylesheets().clear();
        node8.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
    }

}
