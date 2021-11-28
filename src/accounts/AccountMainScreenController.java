/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts;

import accounts.assets.AccountsStatics;
import assets.animation.BounceInRight;
import assets.animation.BounceInUp;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class AccountMainScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox statisticsPane;
    @FXML
    private HBox Static;
    @FXML
    private Label uncollectedCheeks;
    @FXML
    private HBox Static2;
    @FXML
    private Label providerNotPaid;
    @FXML
    private HBox Static3;
    @FXML
    private Label clientsNotPaied;
    @FXML
    private HBox Static4;
    @FXML
    private Label currenAccount;
    @FXML
    private HBox chart;
    @FXML
    private VBox panel;
    @FXML
    private Button clients;
    @FXML
    private Button providers;
    @FXML
    private Button sales;
    @FXML
    private Button yields;
    @FXML
    private Button expenses;
    @FXML
    private Button salary;
    @FXML
    private Button statistic;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;

    Preferences prefs;
    @FXML
    private Button accounts;

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
                                                providers.setId("navBtn");
                                                sales.setId("navBtn");
                                                yields.setId("navBtn");
                                                expenses.setId("navBtn");
                                                salary.setId("navBtn");
                                                accounts.setId("navBtn");
                                                statistic.setId("navBtn");

                                                clients.setText("");
                                                providers.setText("");
                                                sales.setText("");
                                                yields.setText("");
                                                expenses.setText("");
                                                salary.setText("");
                                                accounts.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("AccountScreenClients")) {
                                                    node = FXMLLoader.load(getClass().getResource("ClientsAccounts.fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        providers.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                clients.setId("navBtn");
                                                providers.setId("selectedNavBtn");
                                                sales.setId("navBtn");
                                                yields.setId("navBtn");
                                                expenses.setId("navBtn");
                                                salary.setId("navBtn");
                                                accounts.setId("navBtn");
                                                statistic.setId("navBtn");

                                                clients.setText("");
                                                providers.setText("");
                                                sales.setText("");
                                                yields.setText("");
                                                expenses.setText("");
                                                salary.setText("");
                                                accounts.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("AccountScreenProvider")) {
                                                    node = FXMLLoader.load(getClass().getResource("ProviderAccounts.fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        sales.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                clients.setId("navBtn");
                                                providers.setId("navBtn");
                                                sales.setId("selectedNavBtn");
                                                yields.setId("navBtn");
                                                expenses.setId("navBtn");
                                                salary.setId("navBtn");
                                                accounts.setId("navBtn");
                                                statistic.setId("navBtn");

                                                clients.setText("");
                                                providers.setText("");
                                                sales.setText("");
                                                yields.setText("");
                                                expenses.setText("");
                                                salary.setText("");
                                                accounts.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("AccountScreen")) {
                                                    node = FXMLLoader.load(getClass().getResource(".fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        yields.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                clients.setId("navBtn");
                                                providers.setId("navBtn");
                                                sales.setId("navBtn");
                                                yields.setId("selectedNavBtn");
                                                expenses.setId("navBtn");
                                                salary.setId("navBtn");
                                                accounts.setId("navBtn");
                                                statistic.setId("navBtn");

                                                clients.setText("");
                                                providers.setText("");
                                                sales.setText("");
                                                yields.setText("");
                                                expenses.setText("");
                                                salary.setText("");
                                                accounts.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("AccountScreen")) {
                                                    node = FXMLLoader.load(getClass().getResource(".fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        expenses.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                clients.setId("navBtn");
                                                providers.setId("navBtn");
                                                sales.setId("navBtn");
                                                yields.setId("navBtn");
                                                expenses.setId("selectedNavBtn");
                                                salary.setId("navBtn");
                                                accounts.setId("navBtn");
                                                statistic.setId("navBtn");

                                                clients.setText("");
                                                providers.setText("");
                                                sales.setText("");
                                                yields.setText("");
                                                expenses.setText("");
                                                salary.setText("");
                                                accounts.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("AccountScreen")) {
                                                    node = FXMLLoader.load(getClass().getResource(".fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        salary.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                clients.setId("navBtn");
                                                providers.setId("navBtn");
                                                sales.setId("navBtn");
                                                yields.setId("navBtn");
                                                expenses.setId("navBtn");
                                                salary.setId("selectedNavBtn");
                                                accounts.setId("navBtn");
                                                statistic.setId("navBtn");

                                                clients.setText("");
                                                providers.setText("");
                                                sales.setText("");
                                                yields.setText("");
                                                expenses.setText("");
                                                salary.setText("");
                                                accounts.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("AccountScreen")) {
                                                    node = FXMLLoader.load(getClass().getResource(".fxml"));
                                                    node.getStylesheets().clear();
                                                    node.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                                }
                                                borderpane.setCenter(node);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        accounts.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                clients.setId("navBtn");
                                                providers.setId("navBtn");
                                                sales.setId("navBtn");
                                                yields.setId("navBtn");
                                                expenses.setId("navBtn");
                                                salary.setId("navBtn");
                                                accounts.setId("selectedNavBtn");
                                                statistic.setId("navBtn");

                                                clients.setText("");
                                                providers.setText("");
                                                sales.setText("");
                                                yields.setText("");
                                                expenses.setText("");
                                                salary.setText("");
                                                accounts.setText("");
                                                statistic.setText("");
                                                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                                                if (User.canAccess("AccountScreen")) {
                                                    node = FXMLLoader.load(getClass().getResource(".fxml"));
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
                                                clients.setId("navBtn");
                                                providers.setId("navBtn");
                                                sales.setId("navBtn");
                                                yields.setId("navBtn");
                                                expenses.setId("navBtn");
                                                salary.setId("navBtn");
                                                accounts.setId("navBtn");
                                                statistic.setId("selectedNavBtn");

                                                clients.setText("");
                                                providers.setText("");
                                                sales.setText("");
                                                yields.setText("");
                                                expenses.setText("");
                                                salary.setText("");
                                                accounts.setText("");
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
        new ZoomInRight(clients).play(); 
        new ZoomInRight(providers).play(); 
        new ZoomInRight(sales).play(); 
        new ZoomInRight(yields).play(); 
        new ZoomInRight(expenses).play(); 
        new ZoomInRight(salary).play();
        new ZoomInRight(accounts).play();
        new ZoomInRight(statistic).play();

    }

    private void setStatics() throws Exception {
        currenAccount.setText(AccountsStatics.getCurrenAccount());
        clientsNotPaied.setText(AccountsStatics.getClientsNotPaied());
        providerNotPaid.setText(AccountsStatics.getProviderNotPaid());
        uncollectedCheeks.setText(AccountsStatics.getUncollectedCheeks());
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String, Number> lineChart
                = new LineChart<String, Number>(xAxis, yAxis);
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
//         lineChart.setTitle("الحسابات خلال  " + LocalDate.now().format(format));
//
//        XYChart.Series series1 = AccountsStatics.getClientsPaysChart();
//        series1.setName("دفعات العملاء");
//
//        XYChart.Series series2 = AccountsStatics.getProviderPaysChart();
//        series2.setName("دفعات الموردين");
//
//        XYChart.Series series3 = AccountsStatics.getTransactionsChart();
//        series3.setName("الحركات على الحساب");

//        lineChart.getData().addAll(series1, series2, series3);
        chart.getChildren().clear();
        chart.getChildren().add(lineChart);
        chart.setHgrow(lineChart, Priority.ALWAYS);

        new BounceInUp(Static).play();
        new BounceInUp(Static2).play();
        new BounceInUp(Static3).play();
        new BounceInUp(Static4).play();
    }

}
