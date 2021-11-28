/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import assets.animation.BounceInRight;
import assets.animation.BounceInUp;
import assets.animation.FadeInRight;
import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.NoPermission;
import static assets.classes.statics.THEME;
import businessadministration.BusinessAdministration;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import db.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import providers.assets.ProviderStatics;
import store.assets.StoreStatics;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreMainScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox statisticsPane;
    @FXML
    private Label allTrashed;
    @FXML
    private Label shorts;
    @FXML
    private Label pendedPer;
    @FXML
    private Label allProducts;
    @FXML
    private HBox chart;
    @FXML
    private VBox panel;
    @FXML
    private Button products;
    @FXML
    private Button stores;
    @FXML
    private Button permissions;
    @FXML
    private Button trashed;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;

    Preferences prefs;
    @FXML
    private Button statistic;
    @FXML
    private HBox Static;
    @FXML
    private HBox Static2;
    @FXML
    private HBox Static3;
    @FXML
    private HBox Static4;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    try {
                                        setStatics();
                                        configDrawer();
                                        products.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                products.setId("selectedNavBtn");
                                                products.setText("");
                                                stores.setId("navBtn");
                                                stores.setText("");
                                                permissions.setId("navBtn");
                                                permissions.setText("");
                                                trashed.setId("navBtn");
                                                trashed.setText("");
                                                statistic.setId("navBtn");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("StoreScreenProducts")) {
                                                    node = FXMLLoader.load(getClass().getResource("/store/StoreProducts.fxml"));
 node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);

                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });

                                        stores.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                stores.setId("selectedNavBtn");
                                                stores.setText("");
                                                products.setId("navBtn");
                                                products.setText("");
                                                permissions.setId("navBtn");
                                                permissions.setText("");
                                                trashed.setId("navBtn");
                                                trashed.setText("");
                                                statistic.setId("navBtn");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("StoreScreenStores")) {
                                                    node = FXMLLoader.load(getClass().getResource("/store/Stores.fxml")); node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        permissions.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                permissions.setId("selectedNavBtn");
                                                permissions.setText("");
                                                products.setId("navBtn");
                                                products.setText("");
                                                stores.setId("navBtn");
                                                stores.setText("");
                                                trashed.setId("navBtn");
                                                trashed.setText("");
                                                statistic.setId("navBtn");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("StoreScreenPermissions")) {
                                                    node = FXMLLoader.load(getClass().getResource("/store/StorePermissions.fxml")); node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        trashed.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                trashed.setId("selectedNavBtn");
                                                trashed.setText("");
                                                products.setId("navBtn");
                                                products.setText("");
                                                stores.setId("navBtn");
                                                stores.setText("");
                                                permissions.setId("navBtn");
                                                permissions.setText("");
                                                statistic.setId("navBtn");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("ClientScreenInvoicesReturn")) {
                                                    node = FXMLLoader.load(getClass().getResource("ClientsInvoicesReturn.fxml")); node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        statistic.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                statistic.setId("selectedNavBtn");
                                                statistic.setText("");
                                                products.setId("navBtn");
                                                products.setText("");
                                                stores.setId("navBtn");
                                                stores.setText("");
                                                trashed.setId("navBtn");
                                                trashed.setText("");
                                                permissions.setId("navBtn");
                                                permissions.setText("");
                                                borderpane.setCenter(statisticsPane);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        AlertDialogs.showErrors(ex);

                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
                                }
                            }

                            private void configDrawer() {
                                try {

                                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/Navigator/SideNavigator.fxml"));
anchorPane.getStylesheets().clear();
                                    anchorPane.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

                                    drawer.setSidePane(anchorPane);

                                    double drawerx = drawer.getLayoutX();
                                    double drawery = drawer.getLayoutY();
                                    drawer.setLayoutX(drawerx + 250);
                                    drawer.setLayoutY(drawery);
                                    drawer.setVisible(false);
                                    drawer.setMaxWidth(0);

                                    drawer.setOnDrawerOpening(event
                                            -> {
                                        drawer.setLayoutX(drawerx);
                                        drawer.setLayoutY(drawery);
                                        drawer.setMaxWidth(250);
                                    });

                                    drawer.setOnDrawerClosed(event
                                            -> {
                                        drawer.setLayoutX(drawerx + 250);
                                        drawer.setLayoutY(drawery);
                                        drawer.setVisible(false);
                                        drawer.setMaxWidth(0);
                                    });
                                    for (Node node : anchorPane.getChildren()) {

                                        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

                                        });

                                    }
                                    HamburgerBackArrowBasicTransition nav = new HamburgerBackArrowBasicTransition(hamburg);
                                    nav.setRate(nav.getRate() * -1);
                                    nav.play();
                                    hamburg.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                        nav.setRate(nav.getRate() * -1);
                                        nav.play();
                                        drawer.setVisible(true);
                                        if (drawer.isOpened()) {
                                            drawer.close();
                                        } else {
                                            drawer.open();
                                        }
                                    });
                                } catch (Exception e) {
                                    AlertDialogs.showErrors(e);
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
        new ZoomInLeft(products).play();
        new ZoomInRight(stores).play();
        new ZoomInRight(permissions).play();
        new ZoomInRight(trashed).play();
        new ZoomInRight(statistic).play();
    }

    private void setStatics() throws Exception {
        allTrashed.setText(StoreStatics.getAllTrashed());
        shorts.setText(StoreStatics.getAllShorts());
        pendedPer.setText(StoreStatics.getAllPendedPermission());
        allProducts.setText(StoreStatics.getAllProducts());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String, Number> lineChart
                = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("حركات المخزن خلال  " + LocalDate.now().format(format));

        XYChart.Series series1 = StoreStatics.getEntrances();
        series1.setName("دخول");

        XYChart.Series series2 = StoreStatics.getExit();
        series2.setName("خروج");

        XYChart.Series series3 = StoreStatics.getProviderReturns();
        series3.setName("مرتجع مورد");

        XYChart.Series series4 = StoreStatics.getClientReturns();
        series4.setName("مرتجع عميل");

        XYChart.Series series5 = StoreStatics.getTrashed();
        series5.setName("هالك");

        lineChart.getData().addAll(series5, series3, series4, series1, series2);
        chart.getChildren().clear();
        chart.getChildren().add(lineChart);
        chart.setHgrow(lineChart, Priority.ALWAYS);
        new BounceInUp(Static).play();
        new BounceInUp(Static2).play();
        new BounceInUp(Static3).play();
        new BounceInUp(Static4).play();
    }
}
