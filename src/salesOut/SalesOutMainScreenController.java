/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesOut;

import assets.animation.BounceInRight;
import assets.animation.BounceInUp;
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
import sales.assets.SalesStatics;
import salesOut.assets.SalesOutStatics;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesOutMainScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox statisticsPane;
    @FXML
    private HBox Static2;
    @FXML
    private Label newOrders;
    @FXML
    private HBox Static3;
    @FXML
    private Label newClients;
    @FXML
    private HBox Static4;
    @FXML
    private Label allOrders;
    @FXML
    private HBox chart;
    @FXML
    private VBox panel;
    @FXML
    private Button orders;
    @FXML
    private Button client; 
    @FXML
    private Button success;
    @FXML
    private Button statistic;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;

    Preferences prefs;

    /**
     * Initializes the controller class.
     */
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
                                        success.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                orders.setId("navBtn");
                                                client.setId("navBtn"); 
                                                success.setId("selectedNavBtn");
                                                statistic.setId("navBtn");
                                                orders.setText("");
                                                client.setText(""); 
                                                success.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("SalesOutScreenSuccess")) {
                                                    node = FXMLLoader.load(getClass().getResource("SalesOutSuccess.fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        client.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                orders.setId("navBtn");
                                                client.setId("selectedNavBtn"); 
                                                success.setId("navBtn");
                                                statistic.setId("navBtn");
                                                orders.setText("");
                                                client.setText(""); 
                                                success.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("SalesOutScreenClients")) {
                                                    node = FXMLLoader.load(getClass().getResource("/sales/SalesScreenClients.fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                       
                                        orders.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                orders.setId("selectedNavBtn");
                                                client.setId("navBtn"); 
                                                success.setId("navBtn");
                                                statistic.setId("navBtn");
                                                orders.setText("");
                                                client.setText(""); 
                                                success.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("SalesOutScreenOrders")) {
                                                    node = FXMLLoader.load(getClass().getResource("SalesOutOrders.fxml"));
                                                    node.getStylesheets().clear();
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
                                                orders.setId("navBtn");
                                                client.setId("navBtn"); 
                                                success.setId("navBtn");
                                                statistic.setId("selectedNavBtn");
                                                orders.setText("");
                                                client.setText(""); 
                                                success.setText("");
                                                statistic.setText("");
                                                borderpane.setCenter(statisticsPane);
                                                setStatics();
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
        new ZoomInRight(orders).play();
        new ZoomInRight(client).play();
        new ZoomInRight(success).play(); 
        new ZoomInRight(statistic).play();
    }

    private void setStatics() throws Exception {
        allOrders.setText(SalesOutStatics.getAllOrders());
        newClients.setText(SalesOutStatics.getNewClients());
        newOrders.setText(SalesOutStatics.getNewSuccessOrders());
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String, Number> lineChart
                = new LineChart<String, Number>(xAxis, yAxis);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
        lineChart.setTitle("احصائيات خلال  " + LocalDate.now().format(format));

        XYChart.Series series1 = SalesOutStatics.getAllOrdersChart();
        series1.setName("الاوردرات");

        XYChart.Series series2 = SalesOutStatics.getNewClientsChart();
        series2.setName("العملاء الجدد");

        XYChart.Series series3 = SalesOutStatics.getNewSuccessOrdersChart();
        series3.setName("العمليات الناجحة");

        lineChart.getData().addAll(series2, series1, series3);
        chart.getChildren().clear();
        chart.getChildren().add(lineChart);
        chart.setHgrow(lineChart, Priority.ALWAYS);

        new BounceInUp(Static2).play();
        new BounceInUp(Static3).play();
        new BounceInUp(Static4).play();
    }

}
