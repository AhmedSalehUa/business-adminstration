/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import assets.animation.BounceInRight;
import assets.animation.BounceInUp;
import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.NoPermission;
import static assets.classes.statics.THEME;
import businessadministration.BusinessAdministration;
import clients.assets.ClientsStatics;
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
import sales.assets.SalesStatics;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesMainScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox statisticsPane;
    @FXML
    private HBox Static;
    @FXML
    private Label newCalls;
    @FXML
    private HBox Static2;
    @FXML
    private Label newOffers;
    @FXML
    private HBox Static3;
    @FXML
    private Label newOrders;
    @FXML
    private HBox Static4;
    @FXML
    private Label allClients;
    @FXML
    private HBox chart;
    @FXML
    private VBox panel;
    @FXML
    private Button clients;
    @FXML
    private Button calls;
    @FXML
    private Button offers;
    @FXML
    private Button success;
    @FXML
    private Button calender;
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
                                        clients.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                clients.setId("selectedNavBtn");
                                                offers.setId("navBtn");
                                                calls.setId("navBtn");
                                                success.setId("navBtn");
                                                calender.setId("navBtn");
                                                statistic.setId("navBtn");
                                                clients.setText("");
                                                offers.setText("");
                                                calls.setText("");
                                                success.setText("");
                                                calender.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("SalesScreenClients")) {
                                                    node = FXMLLoader.load(getClass().getResource("SalesScreenClients.fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        calls.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                calls.setId("selectedNavBtn");
                                                offers.setId("navBtn");
                                                clients.setId("navBtn");
                                                success.setId("navBtn");
                                                calender.setId("navBtn");
                                                statistic.setId("navBtn");
                                                calls.setText("");
                                                offers.setText("");
                                                clients.setText("");
                                                success.setText("");
                                                calender.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("SalesScreenCalls")) {
                                                    node = FXMLLoader.load(getClass().getResource("SalesScreenCalls.fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        offers.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                offers.setId("selectedNavBtn");
                                                calls.setId("navBtn");
                                                clients.setId("navBtn");
                                                success.setId("navBtn");
                                                calender.setId("navBtn");
                                                statistic.setId("navBtn");
                                                offers.setText("");
                                                calls.setText("");
                                                clients.setText("");
                                                success.setText("");
                                                calender.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("SalesScreenOffers")) {
                                                    node = FXMLLoader.load(getClass().getResource("SalesScreenOffers.fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        calender.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                calender.setId("selectedNavBtn");
                                                calls.setId("navBtn");
                                                clients.setId("navBtn");
                                                success.setId("navBtn");
                                                offers.setId("navBtn");
                                                statistic.setId("navBtn");
                                                calender.setText("");
                                                calls.setText("");
                                                clients.setText("");
                                                success.setText("");
                                                offers.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("SalesScreenCalendar")) {
                                                    node = FXMLLoader.load(getClass().getResource("SalesScreenCalendar.fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/stylesheet_main.css").toExternalForm());

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
                                                calender.setId("navBtn");
                                                calls.setId("navBtn");
                                                clients.setId("navBtn");
                                                success.setId("navBtn");
                                                offers.setId("navBtn");
                                                statistic.setId("selectedNavBtn");
                                                calender.setText("");
                                                calls.setText("");
                                                clients.setText("");
                                                success.setText("");
                                                offers.setText("");
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
        new ZoomInLeft(clients).play();
        new ZoomInRight(calender).play();
        new ZoomInRight(calls).play();
        new ZoomInRight(success).play();
        new ZoomInRight(offers).play();
        new ZoomInRight(statistic).play();
    }

    private void setStatics() throws Exception {
        allClients.setText(SalesStatics.getAllClient());
        newCalls.setText(SalesStatics.getNewCalls());
        newOrders.setText(SalesStatics.getNewOrders());
        newOffers.setText(SalesStatics.getNewOffers());
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String, Number> lineChart
                = new LineChart<String, Number>(xAxis, yAxis);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
        lineChart.setTitle("احصائيات خلال  " + LocalDate.now().format(format));

//        XYChart.Series series1 = SalesStatics.getAllClientChart();
//        series1.setName("العملاء");
//
//        XYChart.Series series2 = SalesStatics.getAllCallsChart();
//        series2.setName("المكالمات");
//
//        XYChart.Series series3 = SalesStatics.getAllOrdersChart();
//        series3.setName("العمليات الناجحة");
//
//         XYChart.Series series4 = SalesStatics.getAllOffersChart();
//        series4.setName("العروض");
//        
//        lineChart.getData().addAll(series2, series4, series1, series3);
        chart.getChildren().clear();
        chart.getChildren().add(lineChart);
        chart.setHgrow(lineChart, Priority.ALWAYS);

        new BounceInUp(Static).play();
        new BounceInUp(Static2).play();
        new BounceInUp(Static3).play();
        new BounceInUp(Static4).play();
    }
}
