/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr;

import assets.animation.BounceInRight;
import assets.animation.BounceInUp;
import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import static assets.classes.statics.*;
import businessadministration.BusinessAdministration;
import clients.assets.ClientsStatics;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import db.User;
import hr.assets.hrStatics;
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

public class HrMainScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox statisticsPane;
    @FXML
    private HBox Static;
    @FXML
    private Label allHolidays;
    @FXML
    private HBox Static2;
    @FXML
    private Label allLeaves;
    @FXML
    private HBox Static3;
    @FXML
    private Label allAttends;
    @FXML
    private HBox Static4;
    @FXML
    private Label allEmploye;
    @FXML
    private HBox chart;
    @FXML
    private VBox panel;
    @FXML
    private Button employee;
    @FXML
    private Button shifts;
    @FXML
    private Button attend;
    @FXML
    private Button leaveMaster;
    @FXML
    private Button machine;
    @FXML
    private Button statistic;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;

    Preferences prefs;
    @FXML
    private Button salary;

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
                                        employee.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();

                                                employee.setId("selectedNavBtn");
                                                shifts.setId("navBtn");
                                                attend.setId("navBtn");
                                                leaveMaster.setId("navBtn");
                                                machine.setId("navBtn");
                                                statistic.setId("navBtn");
                                                salary.setId("navBtn");

                                                salary.setText("");
                                                employee.setText("");
                                                shifts.setText("");
                                                attend.setText("");
                                                leaveMaster.setText("");
                                                machine.setText("");
                                                statistic.setText("");

                                                FXMLLoader node = new FXMLLoader(getClass().getResource(NoPermission));
                                                if (User.canAccess("HrScreenEmployee")) {
                                                    node = new FXMLLoader(getClass().getResource(HrScreenEmployes));
                                                    borderpane.setCenter(node.load());
                                                    HrScreenEmployeeScreenController a = node.getController(); 
                                                    a.setParent(borderpane);
                                                } else {
                                                    borderpane.setCenter(node.load());
                                                }
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });

                                        shifts.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();

                                                shifts.setId("selectedNavBtn");
                                                employee.setId("navBtn");
                                                attend.setId("navBtn");
                                                leaveMaster.setId("navBtn");
                                                machine.setId("navBtn");
                                                statistic.setId("navBtn");
                                                salary.setId("navBtn");

                                                salary.setText("");
                                                shifts.setText("");
                                                employee.setText("");
                                                attend.setText("");
                                                leaveMaster.setText("");
                                                machine.setText("");
                                                statistic.setText("");
                                                FXMLLoader node = new FXMLLoader(getClass().getResource(NoPermission));
                                                if (User.canAccess("HrScreenShifts")) {
                                                    node = new FXMLLoader(getClass().getResource(HrShifts));
                                                    borderpane.setCenter(node.load());
                                                    HrScreenMainShiftsController a = node.getController();
                                                    a.setParent(borderpane);
                                                } else {
                                                    borderpane.setCenter(node.load());
                                                }
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        attend.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();

                                                attend.setId("selectedNavBtn");
                                                employee.setId("navBtn");
                                                shifts.setId("navBtn");
                                                leaveMaster.setId("navBtn");
                                                machine.setId("navBtn");
                                                statistic.setId("navBtn");
                                                salary.setId("navBtn");

                                                salary.setText("");
                                                attend.setText("");
                                                employee.setText("");
                                                shifts.setText("");
                                                leaveMaster.setText("");
                                                machine.setText("");
                                                statistic.setText("");
                                                FXMLLoader node = new FXMLLoader(getClass().getResource(NoPermission));
                                                if (User.canAccess("HrScreenAttend")) {
                                                    node = new FXMLLoader(getClass().getResource(HrScreenAttends));
                                                    borderpane.setCenter(node.load());
                                                    HrScreenMainAttendanceController a = node.getController();
                                                    a.setParent(borderpane);
                                                } else {
                                                    borderpane.setCenter(node.load());
                                                }
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        leaveMaster.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                leaveMaster.setId("selectedNavBtn");
                                                employee.setId("navBtn");
                                                shifts.setId("navBtn");
                                                attend.setId("navBtn");
                                                machine.setId("navBtn");
                                                statistic.setId("navBtn");
                                                salary.setId("navBtn");

                                                salary.setText("");
                                                leaveMaster.setText("");
                                                employee.setText("");
                                                shifts.setText("");
                                                attend.setText("");
                                                machine.setText("");
                                                statistic.setText("");
                                                FXMLLoader node = new FXMLLoader(getClass().getResource(NoPermission));
                                                if (User.canAccess("HrScreenLeaveMaster")) {
                                                    node = new FXMLLoader(getClass().getResource(HrScreenLeaveMastering));
                                                    borderpane.setCenter(node.load());
                                                    HrScreenMainHolidaysAndLeaveController a = node.getController();
                                                    a.setParent(borderpane);
                                                } else {
                                                    borderpane.setCenter(node.load());
                                                }
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        machine.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                machine.setId("selectedNavBtn");
                                                employee.setId("navBtn");
                                                shifts.setId("navBtn");
                                                attend.setId("navBtn");
                                                leaveMaster.setId("navBtn");
                                                statistic.setId("navBtn");
                                                salary.setId("navBtn");

                                                salary.setText("");
                                                machine.setText("");
                                                employee.setText("");
                                                shifts.setText("");
                                                attend.setText("");
                                                leaveMaster.setText("");
                                                statistic.setText("");
                                                FXMLLoader node = new FXMLLoader(getClass().getResource(NoPermission));
                                                if (User.canAccess("HrScreenMachine")) {
                                                    node = new FXMLLoader(getClass().getResource(HrScreenMachines));
                                                    borderpane.setCenter(node.load());
                                                    HrScreenMainDevicesController a = node.getController();
                                                    a.setParent(borderpane);
                                                } else {
                                                    borderpane.setCenter(node.load());
                                                }
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        salary.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                salary.setId("selectedNavBtn");
                                                machine.setId("navBtn");
                                                employee.setId("navBtn");
                                                shifts.setId("navBtn");
                                                attend.setId("navBtn");
                                                leaveMaster.setId("navBtn");
                                                statistic.setId("navBtn");

                                                salary.setText("");
                                                machine.setText("");
                                                employee.setText("");
                                                shifts.setText("");
                                                attend.setText("");
                                                leaveMaster.setText("");
                                                statistic.setText("");
                                                FXMLLoader node = new FXMLLoader(getClass().getResource(NoPermission));
                                                if (User.canAccess("HrScreenSalary")) {
                                                    node = new FXMLLoader(getClass().getResource(HrScreenSalary));
                                                    borderpane.setCenter(node.load());
                                                    HrScreenMainSalaryController a = node.getController();
                                                    a.setParent(borderpane);
                                                } else {
                                                    borderpane.setCenter(node.load());
                                                }
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                                AlertDialogs.showErrors(ex);
                                            }
                                        });
                                        statistic.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                                            try {
                                                panel.setPrefWidth(55);
                                                new BounceInRight(panel).play();
                                                statistic.setId("selectedNavBtn");
                                                machine.setId("navBtn");
                                                employee.setId("navBtn");
                                                shifts.setId("navBtn");
                                                attend.setId("navBtn");
                                                leaveMaster.setId("navBtn");
                                                salary.setId("navBtn");

                                                salary.setText("");
                                                machine.setText("");
                                                employee.setText("");
                                                shifts.setText("");
                                                attend.setText("");
                                                leaveMaster.setText("");
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
        new ZoomInRight(employee).play();
        new ZoomInRight(shifts).play();
        new ZoomInRight(attend).play();
        new ZoomInRight(leaveMaster).play();
        new ZoomInRight(machine).play();
        new ZoomInRight(salary).play();
        new ZoomInRight(statistic).play();

    }

    private void setStatics() throws Exception {
        allEmploye.setText(hrStatics.getAllEmploye());
        allAttends.setText(hrStatics.getNewAttend());
        allLeaves.setText(hrStatics.getNewLeave());
        allHolidays.setText(hrStatics.getNewHoliday());
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String, Number> lineChart
                = new LineChart<String, Number>(xAxis, yAxis);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
        lineChart.setTitle("الحضور و الانصراف خلال " + LocalDate.now().format(format));

        XYChart.Series series1 = hrStatics.getAttendChart();
        series1.setName("الحضور");

        XYChart.Series series2 = hrStatics.getLeaveChart();
        series2.setName("الغياب");

        lineChart.getData().addAll(series1, series2);
        chart.getChildren().clear();
        chart.getChildren().add(lineChart);
        chart.setHgrow(lineChart, Priority.ALWAYS);

        new BounceInUp(Static).play();
        new BounceInUp(Static2).play();
        new BounceInUp(Static3).play();
        new BounceInUp(Static4).play();
    }
}
