/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.screens;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import hr.assets.Employee;
import hr.assets.LeaveMaster;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenAppendLeaveMasterController implements Initializable {

    @FXML
    private ComboBox<Employee> emp;
    @FXML
    private JFXDatePicker date;
    @FXML
    private Button save;
    @FXML
    private ComboBox<LeaveMaster> leaveMaster;
    @FXML
    private Label available;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            fillCombo();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        } finally {

                        }

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

    private void fillCombo() {
        try {
            leaveMaster.setItems(LeaveMaster.getData());
            leaveMaster.setConverter(new StringConverter<LeaveMaster>() {
                @Override
                public String toString(LeaveMaster patient) {
                    return patient.getName();
                }

                @Override
                public LeaveMaster fromString(String string) {
                    return null;
                }
            });
            leaveMaster.setCellFactory(cell -> new ListCell<LeaveMaster>() {

                // Create our layout here to be reused for each ListCell
                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();
                Label lblAva = new Label();

                // Static block to configure our layout
                {
                    // Ensure all our column widths are constant
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                            new ColumnConstraints(100, 100, 100)
                    );

                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);
                    gridPane.add(lblAva, 2, 1);

                }

                // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
                @Override
                protected void updateItem(LeaveMaster person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {

                        // Update our Labels
                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());
                        lblAva.setText("المتاح خلال الشهر: " + person.getMaxNum());

                        // Set this ListCell's graphicProperty to display our GridPane
                        setGraphic(gridPane);
                    } else {
                        // Nothing to display here
                        setGraphic(null);
                    }
                }
            });
            emp.setItems(Employee.getData());
            emp.setConverter(new StringConverter<Employee>() {
                @Override
                public String toString(Employee patient) {
                    return patient.getName();
                }

                @Override
                public Employee fromString(String string) {
                    return null;
                }
            });
            emp.setCellFactory(cell -> new ListCell<Employee>() {

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
    private void save(ActionEvent event)   {
        if (emp.getSelectionModel().getSelectedIndex() == -1 || date.getValue() == null || leaveMaster.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار التاريخ والموظف والغياب اولا");
        } else {
            calcLeave(event);
            if(Integer.parseInt(available.getText())>0){
                try {
                    PreparedStatement Prepare = db.get.Prepare("INSERT INTO `att_early_leave`(  `emp_id`, `leave_id`, `date`) VALUES (?,?,?)");
                    Prepare.setInt(1, emp.getSelectionModel().getSelectedItem().getId());
                    Prepare.setInt(2, leaveMaster.getSelectionModel().getSelectedItem().getId());
                    Prepare.setString(3, date.getValue().format(format));
                    Prepare.execute();
                    AlertDialogs.showmessage("تم");
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            
            }
        }
    }

    @FXML
    private void calcLeave(ActionEvent event) {
        if (emp.getSelectionModel().getSelectedIndex() == -1 || date.getValue() == null) {
            AlertDialogs.showError("اختار التاريخ والموظف اولا");
        } else {
            LocalDate value = date.getValue();
            String frist = value.getYear() + "-" + value.getMonthValue() + "-01";
            String last = value.getYear() + "-" + value.getMonthValue() + "-" + value.getMonth().maxLength();
            try {
                int used = Integer.parseInt(db.get.getTableData("SELECT  IFNULL(Count(`leave_id`),'0') FROM `att_early_leave` WHERE `emp_id`='" + emp.getSelectionModel().getSelectedItem().getId() + "' and `date`>='" + frist + "' and `date` <= '" + last + "'").getValueAt(0, 0).toString());
                int avalable = Integer.parseInt(leaveMaster.getSelectionModel().getSelectedItem().getMaxNum());
                available.setText(Integer.toString(avalable - used));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
