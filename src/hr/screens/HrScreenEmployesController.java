/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.screens;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.controlsfx.control.CheckListView;
//import screens.accounts.assets.Accounts;
import  hr.assets.Employee;
import  hr.assets.EmployeeSolfa;
import  hr.assets.Sections;
import  hr.assets.Shifts;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenEmployesController implements Initializable {
    
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Employee> empTable;
    @FXML
    private TableColumn<Employee, String> empTabSection;
    @FXML
    private TableColumn<Employee, String> empTabSalary;
    @FXML
    private TableColumn<Employee, String> empTabAge;
    @FXML
    private TableColumn<Employee, String> empTabTele;
    @FXML
    private TableColumn<Employee, String> empTabAdress;
    @FXML
    private TableColumn<Employee, String> empTabName;
    @FXML
    private TableColumn<Employee, String> empTabId;
    @FXML
    private Label empId;
    @FXML
    private TextField empName;
    @FXML
    private TextField contractDiscount;
    @FXML
    private TextField empTele;
    @FXML
    private TextField empAge;
    @FXML
    private TextField empAdress;
    @FXML
    private TextField empSalary;
    @FXML
    private ComboBox<Sections> empSection;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button empNew;
    @FXML
    private Button empDelete;
    @FXML
    private Button empEdite;
    @FXML
    private Button empAdd;
    @FXML
    private CheckListView<String> shifts;
    @FXML
    private TableView<EmployeeSolfa> solfaTable; 
    @FXML
    private TableColumn<EmployeeSolfa, String> solfaTabDate;
    @FXML
    private TableColumn<EmployeeSolfa, String> solfaTabAmount;
    @FXML
    private TableColumn<EmployeeSolfa, String> solfaTabId;
    @FXML
    private Label solfaId;
    @FXML
    private TextField solfaAmount; 
    @FXML
    private JFXDatePicker solfaDate;
    @FXML
    private Button solfaNew;
    @FXML
    private Button solfaDelete;
    @FXML
    private Button solfaEdite;
    @FXML
    private Button solfaAdd;
    
    Employee selectedOne;

    /**
     * Initializes the controller class.
     */
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
        empTable.setOnMouseClicked((e) -> {
            if (empTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                empNew.setDisable(false);
                
                empDelete.setDisable(false);
                
                empEdite.setDisable(false);
                
                empAdd.setDisable(true);
                
                Employee selected = empTable.getSelectionModel().getSelectedItem();
                empId.setText(Integer.toString(selected.getId()));
                
                empName.setText(selected.getName());
                
                empTele.setText(selected.getTele());
                
                empAge.setText(selected.getAge());
                
                empAdress.setText(selected.getAdress());
                
                empSalary.setText(selected.getSalary());
                
                ObservableList<Sections> items1 = empSection.getItems();
                for (Sections a : items1) {
                    if (a.getName().equals(selected.getSection())) {
                        empSection.getSelectionModel().select(a);
                    }
                }
                try {
                    shifts.getCheckModel().clearChecks();
                    ObservableList<Employee> data = Employee.getData(selected.getId());
                    ObservableList<String> items2 = shifts.getItems();
                    for (Employee a : data) {
                        for (String b : items2) {
                            if (b.split("-")[0].split(":")[1].replaceAll(" ", "").contains(a.getShifts())) {
                                shifts.getCheckModel().check(b);
                            }
                        }
                        
                    }
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                selectedOne = selected;
                getSolfaFor(selected.getId());
            }
        });
        solfaTable.setOnMouseClicked((e) -> {
            if (solfaTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                EmployeeSolfa sel = solfaTable.getSelectionModel().getSelectedItem();
                solfaId.setText(Integer.toString(sel.getId()));
                solfaAmount.setText(sel.getAmount());
//                ObservableList<Accounts> items2 = solfaAcc.getItems();
//                for (Accounts a : items2) {
//                    if (a.getName().equals(sel.getAcc())) {
//                        solfaAcc.getSelectionModel().select(a);
//                    }
//                    
//                }
                solfaDate.setValue(LocalDate.parse(sel.getDate()));
            }
        });
    }
    
    private void clear() {
        try {
            getAutoNum();
            empNew.setDisable(true);
            
            empDelete.setDisable(true);
            
            empEdite.setDisable(true);
            
            empAdd.setDisable(false);
            
            empSection.getSelectionModel().clearSelection();
            empName.setText("");
            
            empTele.setText("");
            
            empAge.setText("");
            
            empAdress.setText("");
            
            empSalary.setText("");
            shifts.getCheckModel().clearChecks();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        
    }
    
    private void intialColumn() {
        empTabSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        
        empTabSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        
        empTabAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        empTabTele.setCellValueFactory(new PropertyValueFactory<>("tele"));
        
        empTabAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        
        empTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        empTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
          
        solfaTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        solfaTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        
        solfaTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
    }
    
    private void getAutoNum() throws Exception {
        empId.setText(Employee.getAutoNum());
    }
    
    private void getData() {
        try {
            empTable.setItems(Employee.getData());
            items = empTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Employee> items;
    
    private void fillCombo() throws Exception {
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<Shifts> data = Shifts.getData();
        for (Shifts a : data) {
            items.add("id: " + a.getId() + " - name: " + a.getName());
        }
        shifts.setItems(items);
        empSection.setItems(Sections.getData());
        empSection.setConverter(new StringConverter<Sections>() {
            @Override
            public String toString(Sections patient) {
                return patient.getName();
            }
            
            @Override
            public Sections fromString(String string) {
                return null;
            }
        });
        empSection.setCellFactory(cell -> new ListCell<Sections>() {
            
            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            
            {
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );
                
                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                
            }
            
            @Override
            protected void updateItem(Sections person, boolean empty) {
                super.updateItem(person, empty);
                
                if (!empty && person != null) {
                    
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    
                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
        
    }
    
    @FXML
    private void search(KeyEvent event) {
        FilteredList<Employee> filteredData = new FilteredList<>(items, p -> true);
        
        filteredData.setPredicate(pa -> {
            
            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }
            
            String lowerCaseFilter = search.getText().toLowerCase();
            
            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getSection().contains(lowerCaseFilter));
            
        });
        
        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(empTable.comparatorProperty());
        empTable.setItems(sortedData);
    }
    
    @FXML
    private void empNew(ActionEvent event) {
        clear();
    }
    
    @FXML
    private void empDelete(ActionEvent event) {
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
                                    alert.setTitle("Deleting Employee");
                                    alert.setHeaderText("سيتم حذف الموظف ");
                                    alert.setContentText("هل انت متاكد؟");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Employee em = new Employee();
                                        em.setId(Integer.parseInt(empId.getText()));
                                        em.Delete();
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
    private void empEdite(ActionEvent event) {
        ObservableList<String> selectedItems = shifts.getCheckModel().getCheckedItems();
        if (selectedItems.isEmpty()) {
            AlertDialogs.showError("يجب اختيار شيفت على الاقل");
        } else {
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
                                        alert.setTitle("Editing Employee");
                                        alert.setHeaderText("سيتم تعديل الموظف ");
                                        alert.setContentText("هل انت متاكد؟");
                                        
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            
                                            String shifts = "";
                                            for (String a : selectedItems) {
                                                shifts += a.split("-")[0].split(":")[1].replaceAll(" ", "") + "-";
                                            }
                                            shifts = shifts.substring(0, shifts.length() - 1);
                                            Employee em = new Employee();
                                            em.setId(Integer.parseInt(empId.getText()));
                                            em.setName(empName.getText());
                                            em.setAdress(empAdress.getText());
                                            em.setAge(empAge.getText());
                                            em.setTele(empTele.getText());
                                            em.setSalary(empSalary.getText());
                                            em.setSection_id(empSection.getSelectionModel().getSelectedItem().getId());
                                            em.setShifts(shifts);
                                            em.Edite();
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
    }
    
    @FXML
    private void empAdd(ActionEvent event) {
        ObservableList<String> selectedItems = shifts.getCheckModel().getCheckedItems();
        if (selectedItems.isEmpty()) {
            AlertDialogs.showError("يجب اختيار شيفت على الاقل");
        } else {
            
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
                                        String shifts = "";
                                        for (String a : selectedItems) {
                                            shifts += a.split("-")[0].split(":")[1].replaceAll(" ", "") + "-";
                                        }
                                        shifts = shifts.substring(0, shifts.length() - 1);
                                        Employee em = new Employee();
                                        em.setId(Integer.parseInt(empId.getText()));
                                        em.setName(empName.getText());
                                        em.setAdress(empAdress.getText());
                                        em.setAge(empAge.getText());
                                        em.setSalary(empSalary.getText());
                                        em.setTele(empTele.getText());
                                        em.setSection_id(empSection.getSelectionModel().getSelectedItem().getId());
                                        em.setShifts(shifts);
                                        em.Add();
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
    
    @FXML
    private void solfaNew(ActionEvent event) {
        clearSolfa();
    }
    
    private void clearSolfa() {
        try {
            solfaId.setText(EmployeeSolfa.getAutoNum());
            solfaAmount.setText("");
            solfaDate.setValue(null);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    
    @FXML
    private void solfaDelete(ActionEvent event) {
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
                                    alert.setTitle("Deleting  Solfa");
                                    alert.setHeaderText("سيتم حذف السلفة ");
                                    alert.setContentText("هل انت متاكد؟");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        EmployeeSolfa emps = new EmployeeSolfa();
                                        emps.setId(Integer.parseInt(solfaId.getText())); 
                                        emps.setAmount(solfaAmount.getText());
                                        emps.setEmployee_id(selectedOne.getId());
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        emps.setDate(solfaDate.getValue().format(format));
                                        emps.Delete();
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
                
                clearSolfa();
                try {
                    solfaTable.setItems(EmployeeSolfa.getData(selectedOne.getId()));
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }
    
    @FXML
    private void solfaEdite(ActionEvent event) {
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
                                    alert.setTitle("Editing  Solfa");
                                    alert.setHeaderText("سيتم تعديل السلفة ");
                                    alert.setContentText("هل انت متاكد؟");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        EmployeeSolfa emps = new EmployeeSolfa();
                                        emps.setId(Integer.parseInt(solfaId.getText()));
//                                        emps.setAccId(solfaAcc.getSelectionModel().getSelectedItem().getId());
                                        emps.setAmount(solfaAmount.getText());
                                        emps.setEmployee_id(selectedOne.getId());
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        emps.setDate(solfaDate.getValue().format(format));
                                        emps.Edite();
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
                
                clearSolfa();
                try {
                    solfaTable.setItems(EmployeeSolfa.getData(selectedOne.getId()));
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }
    
    @FXML
    private void solfaAdd(ActionEvent event) {
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
                                    EmployeeSolfa emps = new EmployeeSolfa();
                                    emps.setId(Integer.parseInt(solfaId.getText()));
//                                    emps.setAccId(solfaAcc.getSelectionModel().getSelectedItem().getId());
                                    emps.setAmount(solfaAmount.getText());
                                    emps.setEmployee_id(selectedOne.getId());
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    emps.setDate(solfaDate.getValue().format(format));
                                    emps.Add();
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
                
                clearSolfa();
                try {
                    solfaTable.setItems(EmployeeSolfa.getData(selectedOne.getId()));
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }
    
    private void getSolfaFor(int id) {
//        try {
//            solfaAcc.setItems(Accounts.getData());
//            solfaAcc.setConverter(new StringConverter<Accounts>() {
//                @Override
//                public String toString(Accounts patient) {
//                    return patient.getName();
//                }
//                
//                @Override
//                public Accounts fromString(String string) {
//                    return null;
//                }
//            });
//            solfaAcc.setCellFactory(cell -> new ListCell<Accounts>() {
//                
//                GridPane gridPane = new GridPane();
//                Label lblid = new Label();
//                Label lblName = new Label();
//                
//                {
//                    gridPane.getColumnConstraints().addAll(
//                            new ColumnConstraints(100, 100, 100),
//                            new ColumnConstraints(100, 100, 100)
//                    );
//                    
//                    gridPane.add(lblid, 0, 1);
//                    gridPane.add(lblName, 1, 1);
//                    
//                }
//                
//                @Override
//                protected void updateItem(Accounts person, boolean empty) {
//                    super.updateItem(person, empty);
//                    
//                    if (!empty && person != null) {
//                        
//                        lblid.setText("م: " + Integer.toString(person.getId()));
//                        lblName.setText("الاسم: " + person.getName());
//                        
//                        setGraphic(gridPane);
//                    } else {
//                        setGraphic(null);
//                    }
//                }
//            });
//            solfaTable.setItems(EmployeeSolfa.getData(id));
//            solfaId.setText(EmployeeSolfa.getAutoNum());
//        } catch (Exception ex) {
//            AlertDialogs.showErrors(ex);
//        }
    }
    
}
