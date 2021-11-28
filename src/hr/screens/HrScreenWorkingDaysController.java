/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.screens;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import hr.assets.Overtime;
import hr.assets.Workdays;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenWorkingDaysController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Workdays> workTable;
    @FXML
    private TableColumn<Workdays, String> workTabExcept;
    @FXML
    private TableColumn<Workdays, String> workTabName;
    @FXML
    private TableColumn<Workdays, String> workTabId;
    @FXML
    private Label worId;
    @FXML
    private TextField workName;
    @FXML
    private Button sat;
    @FXML
    private Button sun;
    @FXML
    private Button mon;
    @FXML
    private Button tue;
    @FXML
    private Button wed;
    @FXML
    private Button thu;
    @FXML
    private Button fri;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button formNew;
    @FXML
    private Button formDelete;
    @FXML
    private Button formEdite;
    @FXML
    private Button formAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(true);
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
                                    clear();
                                    intialColumn();
                                    getData();

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
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
        workTable.setOnMouseClicked((e) -> {
            if (workTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                Workdays selected = workTable.getSelectionModel().getSelectedItem();
                worId.setText(Integer.toString(selected.getId()));
                workName.setText(selected.getName());
                String holidays = selected.getHolidays();
                String[] split = holidays.split(",");
                sat.setId("WorkingDaysActive");
                sun.setId("WorkingDaysActive");
                mon.setId("WorkingDaysActive");
                tue.setId("WorkingDaysActive");
                wed.setId("WorkingDaysActive");
                thu.setId("WorkingDaysActive");
                fri.setId("WorkingDaysActive");
                for (int i = 0; i < split.length; i++) {
                    switch (split[i]) {
                        case "sat":
                            sat.setId("WorkingDays");

                            break;
                        case "sun":
                            sun.setId("WorkingDays");
                            break;
                        case "mon":
                            mon.setId("WorkingDays");
                            break;
                        case "tue":
                            tue.setId("WorkingDays");
                            break;
                        case "wed":
                            wed.setId("WorkingDays");
                            break;
                        case "thu":
                            thu.setId("WorkingDays");
                            break;
                        case "fri":
                            fri.setId("WorkingDays");
                            break;
                    }
                }

            }
        });
    }

    private void intialColumn() {
        workTabExcept.setCellValueFactory(new PropertyValueFactory<>("holidays"));

        workTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        workTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        try {
            getAutoNum();
            formNew.setDisable(true);

            formDelete.setDisable(true);

            formEdite.setDisable(true);

            formAdd.setDisable(false);
            workName.setText("");
            sat.setId("WorkingDays");
            sun.setId("WorkingDays");
            mon.setId("WorkingDays");
            tue.setId("WorkingDays");
            wed.setId("WorkingDays");
            thu.setId("WorkingDays");
            fri.setId("WorkingDays");
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getAutoNum() throws Exception {
        worId.setText(Workdays.getAutoNum());
    }

    private void getData() {
        try {
            workTable.setItems(Workdays.getData());
            items = workTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Workdays> items;

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Workdays> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter));

        });

        SortedList<Workdays> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(workTable.comparatorProperty());
        workTable.setItems(sortedData);

    }

    @FXML
    private void sat(ActionEvent event) {
        String current = sat.getId();
        if (current.equals("WorkingDaysActive")) {
            sat.setId("WorkingDays");
        } else {
            sat.setId("WorkingDaysActive");
        }
    }

    @FXML
    private void sun(ActionEvent event) {
        String current = sun.getId();
        if (current.equals("WorkingDaysActive")) {
            sun.setId("WorkingDays");
        } else {
            sun.setId("WorkingDaysActive");
        }
    }

    @FXML
    private void mon(ActionEvent event) {
        String current = mon.getId();
        if (current.equals("WorkingDaysActive")) {
            mon.setId("WorkingDays");
        } else {
            mon.setId("WorkingDaysActive");
        }
    }

    @FXML
    private void tue(ActionEvent event) {
        String current = tue.getId();
        if (current.equals("WorkingDaysActive")) {
            tue.setId("WorkingDays");
        } else {
            tue.setId("WorkingDaysActive");
        }
    }

    @FXML
    private void wed(ActionEvent event) {
        String current = wed.getId();
        if (current.equals("WorkingDaysActive")) {
            wed.setId("WorkingDays");
        } else {
            wed.setId("WorkingDaysActive");
        }
    }

    @FXML
    private void thu(ActionEvent event) {
        String current = thu.getId();
        if (current.equals("WorkingDaysActive")) {
            thu.setId("WorkingDays");
        } else {
            thu.setId("WorkingDaysActive");
        }
    }

    @FXML
    private void fri(ActionEvent event) {
        String current = fri.getId();
        if (current.equals("WorkingDaysActive")) {
            fri.setId("WorkingDays");
        } else {
            fri.setId("WorkingDaysActive");
        }
    }

    @FXML
    private void formNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void formDelete(ActionEvent event) {
        progress.setVisible(true);
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting Working days");
                                    alert.setHeaderText("سيتم الحذف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Workdays wk = new Workdays();
                                        wk.setId(Integer.parseInt(worId.getText()));
                                        wk.Delete();
                                    }
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
                progress.setVisible(false);
                clear();
                getData();
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void formEdite(ActionEvent event) {
        progress.setVisible(true);
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing Working days");
                                    alert.setHeaderText("سيتم التعديل ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Workdays wk = new Workdays();
                                        wk.setId(Integer.parseInt(worId.getText()));
                                        wk.setName(workName.getText());
                                        wk.setHolidays(getDays());
                                        wk.Edite();
                                    }
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
                progress.setVisible(false);
                clear();
                getData();
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void formAdd(ActionEvent event) {
        progress.setVisible(true);
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
                                    Workdays wk = new Workdays();
                                    wk.setId(Integer.parseInt(worId.getText()));
                                    wk.setName(workName.getText());
                                    wk.setHolidays(getDays());
                                    wk.Add();
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
                progress.setVisible(false);
                clear();
                getData();
                super.succeeded();
            }
        };
        service.start();

    }

    private String getDays() {
        String a = "";
        ObservableList<String> days = FXCollections.observableArrayList();
        if (sat.getId().equals("WorkingDays")) {
            days.add("sat");
        }
        if (sun.getId().equals("WorkingDays")) {
            days.add("sun");
        }
        if (mon.getId().equals("WorkingDays")) {
            days.add("mon");
        }
        if (tue.getId().equals("WorkingDays")) {
            days.add("tue");
        }
        if (wed.getId().equals("WorkingDays")) {
            days.add("wed");
        }
        if (thu.getId().equals("WorkingDays")) {
            days.add("thu");
        }
        if (fri.getId().equals("WorkingDays")) {
            days.add("fri");
        }
        for (int i = 0; i < days.size(); i++) {
            if (i == 0) {
                a += days.get(i);
            } else {
                a += "," + days.get(i);
            }
        }
        return a;
    }

}
