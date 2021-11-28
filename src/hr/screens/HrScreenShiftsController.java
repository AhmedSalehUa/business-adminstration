/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.screens;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import  hr.assets.Overtime;
import  hr.assets.Shifts;
import  hr.assets.Workdays;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenShiftsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TableView<Shifts> shiftTable;
    @FXML
    private TableColumn<Shifts, String> shiftTabWorkDays;
    @FXML
    private TableColumn<Shifts, String> shiftTabOvertme;
    @FXML
    private TableColumn<Shifts, String> shiftTabIdDaily;
    @FXML
    private TableColumn<Shifts, String> shiftTabEarlyLeave;
    @FXML
    private TableColumn<Shifts, String> shiftTabLateTime;
    @FXML
    private TableColumn<Shifts, String> shiftTabEndTime;
    @FXML
    private TableColumn<Shifts, String> shiftTabStartTime;
    @FXML
    private TableColumn<Shifts, String> shiftTabName;
    @FXML
    private TableColumn<Shifts, String> shiftTabId;
    @FXML
    private Label shiftId;
    @FXML
    private TextField shiftName;
    @FXML
    private TextField shiftLateTime;
    @FXML
    private TextField shiftEarlyLeave;
    @FXML
    private ComboBox<Workdays> shiftWorkdays;
    @FXML
    private ComboBox<Overtime> shiftOvertime;
    @FXML
    private CheckBox shiftIsDaily;
    @FXML
    private JFXTimePicker shiftEndTime;
    @FXML
    private JFXTimePicker shiftStartTime;
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
        shiftEndTime.set24HourView(true);
        shiftStartTime.set24HourView(true);
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
                                    fillCombo();

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
        shiftTable.setOnMouseClicked((e) -> {
            if (shiftTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                Shifts selected = shiftTable.getSelectionModel().getSelectedItem();
                shiftId.setText(Integer.toString(selected.getId()));
                shiftName.setText(selected.getName());

                shiftLateTime.setText(selected.getLateTime());

                shiftEarlyLeave.setText(selected.getEarlyLeave());

                shiftIsDaily.setSelected(Boolean.parseBoolean(selected.getIsDaily()));

                shiftEndTime.setValue(LocalTime.parse(selected.getEndTime()));

                shiftStartTime.setValue(LocalTime.parse(selected.getStartTime()));

                ObservableList<Workdays> items1 = shiftWorkdays.getItems();
                for (Workdays a : items1) {
                    if (a.getName().equals(selected.getWorkdays())) {
                        shiftWorkdays.getSelectionModel().select(a);
                    }
                }
                ObservableList<Overtime> items2 = shiftOvertime.getItems();
                for (Overtime a : items2) {
                    if (a.getName().equals(selected.getOvertime())) {
                        shiftOvertime.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        shiftTabWorkDays.setCellValueFactory(new PropertyValueFactory<>("workdays"));

        shiftTabOvertme.setCellValueFactory(new PropertyValueFactory<>("overtime"));

        shiftTabIdDaily.setCellValueFactory(new PropertyValueFactory<>("isDaily"));

        shiftTabEarlyLeave.setCellValueFactory(new PropertyValueFactory<>("earlyLeave"));

        shiftTabLateTime.setCellValueFactory(new PropertyValueFactory<>("lateTime"));

        shiftTabEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        shiftTabStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        shiftTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        shiftTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        try {
            getAutoNum();
            formNew.setDisable(true);

            formDelete.setDisable(true);

            formEdite.setDisable(true);

            formAdd.setDisable(false);

            shiftName.setText("");

            shiftLateTime.setText("");

            shiftEarlyLeave.setText("");

            shiftWorkdays.getSelectionModel().clearSelection();

            shiftOvertime.getSelectionModel().clearSelection();

            shiftIsDaily.setSelected(false);

            shiftEndTime.setValue(null);

            shiftStartTime.setValue(null);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getAutoNum() throws Exception {
        shiftId.setText(Shifts.getAutoNum());
    }

    private void getData() {
        try {
            shiftTable.setItems(Shifts.getData());
            items = shiftTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Shifts> items;

    private void fillCombo() throws Exception {

        shiftOvertime.setItems(Overtime.getData());
        shiftOvertime.setConverter(new StringConverter<Overtime>() {
            @Override
            public String toString(Overtime patient) {
                return patient.getName();
            }

            @Override
            public Overtime fromString(String string) {
                return null;
            }
        });
        shiftOvertime.setCellFactory(cell -> new ListCell<Overtime>() {

            // Create our layout here to be reused for each ListCell
            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();

            // Static block to configure our layout
            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);

            }

            // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
            @Override
            protected void updateItem(Overtime person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());

                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        shiftWorkdays.setItems(Workdays.getData());
        shiftWorkdays.setConverter(new StringConverter<Workdays>() {
            @Override
            public String toString(Workdays patient) {
                return patient.getName();
            }

            @Override
            public Workdays fromString(String string) {
                return null;
            }
        });
        shiftWorkdays.setCellFactory(cell -> new ListCell<Workdays>() {

            // Create our layout here to be reused for each ListCell
            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();

            // Static block to configure our layout
            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);

            }

            // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
            @Override
            protected void updateItem(Workdays person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());

                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Shifts> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter));

        });

        SortedList<Shifts> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(shiftTable.comparatorProperty());
        shiftTable.setItems(sortedData);
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
                                    alert.setTitle("Deleting Shift");
                                    alert.setHeaderText("سيتم حذف الشيفت ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Shifts sh = new Shifts();
                                        sh.setId(Integer.parseInt(shiftId.getText()));
                                        sh.Delete();
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
                                    alert.setTitle("Editing Shift");
                                    alert.setHeaderText("سيتم تعديل الشيفت ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Shifts sh = new Shifts();
                                        sh.setId(Integer.parseInt(shiftId.getText()));
                                        sh.setName(shiftName.getText());
                                        sh.setOvertime_id(shiftOvertime.getSelectionModel().getSelectedItem().getId());
                                        sh.setWorkdays_id(shiftWorkdays.getSelectionModel().getSelectedItem().getId());
                                        sh.setStartTime(shiftStartTime.getValue().toString());
                                        sh.setEndTime(shiftEndTime.getValue().toString());
                                        sh.setIsDaily(Boolean.toString(shiftIsDaily.isSelected()));
                                        sh.setLateTime(shiftLateTime.getText());
                                        sh.setEarlyLeave(shiftEarlyLeave.getText());
                                        sh.Edite();
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
                                    Shifts sh = new Shifts();
                                    sh.setId(Integer.parseInt(shiftId.getText()));
                                    sh.setName(shiftName.getText());
                                    sh.setOvertime_id(shiftOvertime.getSelectionModel().getSelectedItem().getId());
                                    sh.setWorkdays_id(shiftWorkdays.getSelectionModel().getSelectedItem().getId());
                                    sh.setStartTime(shiftStartTime.getValue().toString());
                                    sh.setEndTime(shiftEndTime.getValue().toString());
                                    sh.setIsDaily(Boolean.toString(shiftIsDaily.isSelected()));
                                    sh.setLateTime(shiftLateTime.getText());
                                    sh.setEarlyLeave(shiftEarlyLeave.getText());
                                    sh.Add();
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

}
