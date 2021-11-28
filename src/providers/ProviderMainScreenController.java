/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers;

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

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ProviderMainScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox statisticsPane;
    @FXML
    private Label newReturned;
    @FXML
    private Label newInvoices;
    @FXML
    private Label allProvider;
    @FXML
    private HBox chart;
    @FXML
    private VBox panel;
    @FXML
    private Button provider;
    @FXML
    private Button invoice;
    @FXML
    private Button returned;
    @FXML
    private Button statistic;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;
    Preferences prefs;
    @FXML
    private HBox Static;
    @FXML
    private HBox Static2;
    @FXML
    private HBox Static3;

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
                                        provider.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                provider.setId("selectedNavBtn");
                                                invoice.setId("navBtn");
                                                returned.setId("navBtn");
                                                statistic.setId("navBtn"); 
                                                provider.setText("");
                                                invoice.setText("");
                                                returned.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("ProviderScreenProviders")) {
                                                    node = FXMLLoader.load(getClass().getResource("/providers/Providers.fxml"));
 node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);

                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });

                                        invoice.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                invoice.setId("selectedNavBtn");
                                                provider.setId("navBtn");
                                                returned.setId("navBtn");
                                                statistic.setId("navBtn"); 
                                                provider.setText("");
                                                invoice.setText("");
                                                returned.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("ProviderScreenInvoices")) {
                                                    node = FXMLLoader.load(getClass().getResource("/providers/ProviderInvoiceBuy.fxml")); node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        returned.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                returned.setId("selectedNavBtn");
                                                provider.setId("navBtn");
                                                invoice.setId("navBtn");
                                                statistic.setId("navBtn"); 
                                                provider.setText("");
                                                invoice.setText("");
                                                statistic.setText("");
                                                returned.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("ProviderScreenInvoicesReturn")) {
                                                    node = FXMLLoader.load(getClass().getResource("/providers/ProviderInvoiceReturn.fxml")); node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        statistic.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                statistic.setId("selectedNavBtn");
                                                invoice.setId("navBtn");
                                                returned.setId("navBtn");
                                                provider.setId("navBtn"); 
                                                provider.setText("");
                                                invoice.setText("");
                                                returned.setText("");
                                                statistic.setText("");
                                                borderpane.setCenter(statisticsPane);setStatics();
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

        new ZoomInLeft(provider).play();
        new ZoomInRight(invoice).play();
        new ZoomInRight(returned).play();
        new ZoomInRight(statistic).play();
    }

    private void setStatics() throws Exception{
        allProvider.setText(ProviderStatics.getAllProviders());
        newInvoices.setText(ProviderStatics.getAllInvoices()); 
        newReturned.setText(ProviderStatics.getAllReturnedInvoices()); 
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month"); 
        final LineChart<String, Number> lineChart
                = new LineChart<String, Number>(xAxis, yAxis);
         DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
        lineChart.setTitle("الاوردرات خلال  "+ LocalDate.now().format(format));

        XYChart.Series series1 =ProviderStatics.getInvoicesByMonth();
        series1.setName("الاوردرات");
 

        XYChart.Series series2 = ProviderStatics.getReturnedInvoicesByMonth();
        series2.setName("المرتجعات"); 

        lineChart.getData().addAll(series1, series2);chart.getChildren().clear();
        chart.getChildren().add(lineChart);
        chart.setHgrow(lineChart, Priority.ALWAYS);
        new BounceInUp(Static).play();
        new BounceInUp(Static2).play();
        new BounceInUp(Static3).play(); 
    }
}
