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
import hr.assets.LateRules;
import hr.assets.Shifts;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenLateRulesController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<LateRules> table;
    @FXML
    private TableColumn<LateRules, String> tabShift;
    @FXML
    private TableColumn<LateRules, String> tabAction;
    @FXML
    private TableColumn<LateRules, String> tabRule;
    @FXML
    private TableColumn<LateRules, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField rule;
    @FXML
    private ComboBox<Shifts> shift;
    @FXML
    private ComboBox<String> action;
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
        table.setOnMouseClicked((e) -> {
            if (table.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                LateRules selected = table.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                rule.setText(selected.getRule());
                action.getSelectionModel().select(selected.getAction());
                ObservableList<Shifts> items1 = shift.getItems();
                for (Shifts a : items1) {
                    if (a.getId() == selected.getShift()) {
                        shift.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tabShift.setCellValueFactory(new PropertyValueFactory<>("shift"));

        tabAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        tabRule.setCellValueFactory(new PropertyValueFactory<>("rule"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);
        rule.setText("");
        action.getSelectionModel().clearSelection();
        shift.getSelectionModel().clearSelection();

    }

    private void getAutoNum() {
        try {
            id.setText(LateRules.getAutoNum());
        } catch (Exception ex) {
              AlertDialogs.showErrors(ex);
        }
    }

    private void getData() {
        try {
            table.setItems(LateRules.getData());
            items = table.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<LateRules> items;

    private void fillCombo() {
        try {
            action.getItems().add("خصم نصف يوم");
            action.getItems().add("خصم يوم");
            action.getItems().add("خصم يومين");
            shift.setItems(Shifts.getData());
            shift.setConverter(new StringConverter<Shifts>() {
                @Override
                public String toString(Shifts patient) {
                    return patient.getName();
                }
                
                @Override
                public Shifts fromString(String string) {
                    return null;
                }
            });
            shift.setCellFactory(cell -> new ListCell<Shifts>() {
                
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
                protected void updateItem(Shifts person, boolean empty) {
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
    private void search(KeyEvent event) {
        FilteredList<LateRules> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getRule().contains(lowerCaseFilter));

        });

        SortedList<LateRules> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @FXML
    private void formNew(ActionEvent event) {clear();
    }

    @FXML
    private void formDelete(ActionEvent event) {  progress.setVisible(true); 
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
                                try {  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting  Rule");
                                        alert.setHeaderText("سيتم الحذف ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                    LateRules lt=new LateRules();
                                    lt.setId(Integer.parseInt(id.getText()));
                                     lt.Delete();
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
               clear();getData();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void formEdite(ActionEvent event) {  progress.setVisible(true); 
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
                                try {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editing  Rule");
                                        alert.setHeaderText("سيتم التعديل ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                    LateRules lt=new LateRules();
                                    lt.setId(Integer.parseInt(id.getText()));
                                    lt.setRule(rule.getText());
                                    lt.setAction(action.getSelectionModel().getSelectedItem()); 
                                    lt.setShift(shift.getSelectionModel().getSelectedItem().getId());
                                    lt.Edite();}

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
               clear();getData();
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
                                    LateRules lt=new LateRules();
                                    lt.setId(Integer.parseInt(id.getText()));
                                    lt.setRule(rule.getText());
                                    lt.setAction(action.getSelectionModel().getSelectedItem()); 
                                    lt.setShift(shift.getSelectionModel().getSelectedItem().getId());
                                    lt.Add();

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
               clear();getData();
                super.succeeded();
            }
        };
        service.start();
    }

}
