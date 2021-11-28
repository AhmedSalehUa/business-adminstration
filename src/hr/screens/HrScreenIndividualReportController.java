/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.screens;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import hr.assets.Employee;
import hr.assets.LeaveMaster;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenIndividualReportController implements Initializable {

    @FXML
    private ComboBox<Employee> emp;
    @FXML
    private JFXDatePicker dateFrom;
    @FXML
    private Button show;
    @FXML
    private JFXDatePicker dateTo;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private ProgressIndicator progress;

    /**
     * Initializes the controller class.
     */
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
progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    private void fillCombo() {
        try {

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
    private void show(ActionEvent event) {
        if (emp.getSelectionModel().getSelectedIndex() == -1 || dateFrom.getValue() == null || dateTo.getValue() == null) {

            AlertDialogs.showError("اختار الفترة والموظف اولا");
        } else {progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                try {
                                    HashMap hash = new HashMap();
                                    Employee selectedItem = emp.getSelectionModel().getSelectedItem();
                                    String allSalary = db.get.getTableData("SELECT  IFNULL( sum(convert(`salary_calc` , decimal(4,2))) ,'0') from `att_report` WHERE `emp_id`='" + selectedItem.getId() + "'  and `date`>='" + dateFrom.getValue().format(format) + "'  and `date`<= '" + dateTo.getValue().format(format) + "'").getValueAt(0, 0).toString();
                                    if (selectedItem.getSalary() == null) {
                                        hash.put("salarys", "0");
                                        hash.put("solfs", "0");
                                        hash.put("remains", "0");

                                    } else {
                                        if (Integer.parseInt(selectedItem.getSalary()) != 0) {
                                            double oneDaye = Integer.parseInt(selectedItem.getSalary()) / 30;
                                            double salary = Math.round((double)(oneDaye * Double.parseDouble(allSalary))*100)/100;
                                            System.out.println(salary);
                                            String solfa = db.get.getTableData("SELECT IFNULL( sum(convert(`amount` , decimal(4,2))) ,'0') from `att_employee_solfa` WHERE `emp_id`='" + selectedItem.getId() + "'  and `date`>='" + dateFrom.getValue().format(format) + "'  and `date`<= '" + dateTo.getValue().format(format) + "'").getValueAt(0, 0).toString();
                                            double remaining = salary - Double.parseDouble(solfa);
                                            System.out.println(solfa);
                                            System.out.println(remaining);
                                            hash.put("salarys", Double.toString(salary));
                                            hash.put("solfs", solfa);
                                            hash.put("remains", Double.toString(remaining));
                                        }
                                    }

                                    hash.put("emp", selectedItem.getId());
                                    hash.put("date_from", dateFrom.getValue().format(format));
                                    hash.put("date_to", dateTo.getValue().format(format));
                                    hash.put("emp_name", selectedItem.getName());
                                    System.out.println(hash.get("emp"));
                                    System.out.println(hash.get("date_from"));
                                    System.out.println(hash.get("date_to"));
                                    System.out.println(hash.get("salarys"));
                                    System.out.println(hash.get("solfs"));
                                    System.out.println(hash.get("remains"));
                                    System.out.println(hash.get("emp_name"));
                                    InputStream a = getClass().getResourceAsStream("/screens/hr/reports/oneEmployeeReport.jrxml");
                                    JasperDesign design = JRXmlLoader.load(a);
                                    JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                    JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                    JasperViewer.viewReport(jasperprint, false);
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    System.out.println(ex.getMessage());
                                }
                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            } finally {

                            }

                            return null;
                        }
                    };

                }

                @Override
                protected void succeeded() { progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();

        }
    }

}
