/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.screens;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import  hr.assets.Attendence;
import hr.assets.Employee;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenAttendanceViewController implements Initializable {

    @FXML
    private TableView<Attendence> table;
    @FXML
    private TableColumn<Attendence, String> time;
    @FXML
    private TableColumn<Attendence, String> date;
    @FXML
    private TableColumn<Attendence, String> name;
    @FXML
    private TableColumn<Attendence, String> id;
    @FXML
    private ComboBox<Employee> employee;
    @FXML
    private Button filter;
    @FXML
    private Button unFilter;
    @FXML
    private JFXDatePicker from;
    @FXML
    private JFXDatePicker to;
    @FXML
    private Button show;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
                super.succeeded();
            }
        };
        service.start();
    }

    private void intialColumn() {
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        name.setCellValueFactory(new PropertyValueFactory<>("empName"));

        id.setCellValueFactory(new PropertyValueFactory<>("empId"));

    }

    private void getData() {
        try {
            table.setItems(Attendence.getData());
            items = table.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }
    ObservableList<Attendence> items;

    private void fillCombo() {
        try {
            employee.setItems(Employee.getData());
            employee.setConverter(new StringConverter<Employee>() {
                @Override
                public String toString(Employee patient) {
                    return patient.getName();
                }

                @Override
                public Employee fromString(String string) {
                    return null;
                }
            });
            employee.setCellFactory(cell -> new ListCell<Employee>() {

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
                protected void updateItem(Employee person, boolean empty) {
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
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void filter(ActionEvent event) {
        if (employee.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الموظف اولا");
        } else {
            FilteredList<Attendence> filteredData = new FilteredList<>(items, p -> true);

            filteredData.setPredicate(pa -> {
                return (pa.getEmpId() == employee.getSelectionModel().getSelectedItem().getId());

            });

            SortedList<Attendence> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty()
            );
            table.setItems(sortedData);
        }
    }

    @FXML
    private void unFilter(ActionEvent event) {
        table.setItems(items);
    }

    @FXML
    private void show(ActionEvent event) {

        if (from.getValue() == null) {
            AlertDialogs.showError("اختار بداية المدة اولا");
        } else {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String start = from.getValue().format(format);
            String end = "";
            if (to.getValue() == null) {
                end = LocalDate.now​().format(format);
            } else {
                end = to.getValue().format(format);
            }
            try {
                table.setItems(Attendence.getDataInInterval(start, end));
                items = table.getItems();
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        }
    }

}
