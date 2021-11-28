/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.permissions;

import assets.classes.AlertDialogs;
import static assets.classes.statics.USER_ID;
import businessadministration.BusinessAdministration;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import salesTrack.assets.TrackLine;
import store.assets.StoreProducts;
import store.permissions.assets.TrackIn;
import store.permissions.assets.TrackInDetails; 

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class PermissionLineReturnedController implements Initializable {

    @FXML
    private Label id;
    @FXML
    private JFXDatePicker date;
    @FXML
    private ComboBox<TrackLine> lines;
    @FXML
    private TableView<TrackInDetails> invoiceTable;
    @FXML
    private MenuItem deleteRow1;
    @FXML
    private TableColumn<TrackInDetails,String> tabId1;
    @FXML
    private TableColumn<TrackInDetails,String> tabProduct1;
    @FXML
    private TableColumn<TrackInDetails,String> tabAmount1;
    @FXML
    private TableColumn<TrackInDetails,String> tabCost1;
    @FXML
    private Button invoiveAdd;
    @FXML
    private ProgressIndicator progress;
 Preferences prefs;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        date.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return format.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, format);
            }
        });
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    clear();
                                    intialColumn(); 
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
    }    

    private void intialColumn() {
        tabCost1.setCellValueFactory(new PropertyValueFactory<>("costField"));

        tabAmount1.setCellValueFactory(new PropertyValueFactory<>("amountField"));

        tabProduct1.setCellValueFactory(new PropertyValueFactory<>("productsCombo"));

        tabId1.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    private void clear() {
        getAutoNum();
    }

    private void getAutoNum() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            autoNum = TrackIn.getAutoNum();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
                id.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();
    }

    private void getData(int id) {
        try {
            ObservableList<StoreProducts> data = StoreProducts.getDataOfTrack(id);

            ObservableList<TrackInDetails> list = TrackInDetails.getData(id, data); 
            invoiceTable.setItems(list);
             
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<TrackLine> trackData;
    ObservableList<TrackLine> trackDataSearch = FXCollections.observableArrayList();

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            trackData = TrackLine.getData();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
                lines.setItems(trackData);
                lines.setEditable(true);
                lines.setOnKeyReleased((event) -> {

                    if (lines.getEditor().getText().length() == 0) {
                        lines.setItems(trackData);
                    } else {
                        trackDataSearch = FXCollections.observableArrayList();

                        for (TrackLine a : trackData) {
                            if (a.getLocation().contains(lines.getEditor().getText()) || a.getDate().contains(lines.getEditor().getText())) {
                                trackDataSearch.add(a);
                            }
                        }
                        lines.setItems(trackDataSearch);
                        lines.show();
                    }
                });
                lines.setConverter(new StringConverter<TrackLine>() {
                    @Override
                    public String toString(TrackLine object) {
                        return object.getLocation() + "  " + object.getDate();
                    }

                    @Override
                    public TrackLine fromString(String string) {
                        return null;
                    }
                });
                lines.setCellFactory(cell -> new ListCell<TrackLine>() {

                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();
                    Label lblOrg = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(50, 50, 50),
                                new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblOrg, 1, 1);
                        gridPane.add(lblName, 2, 1);
                    }

                    @Override
                    protected void updateItem(TrackLine person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblOrg.setText("المكان: " + person.getLocation());
                            lblName.setText("التاريح: " + person.getDate());
                            setGraphic(gridPane);
                        } else {
                            setGraphic(null);
                        }
                    }
                });
                super.succeeded();
            }
        };
        service.start();
    }
    @FXML
    private void deleteRow(ActionEvent event) {
    }

    @FXML
    private void invoiveAdd(ActionEvent event) {
     if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                TrackIn in;
                boolean ok = false;

                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        ObservableList<TrackInDetails> items = invoiceTable.getItems();
                                         
                                            in = new TrackIn();
                                            in.setId(Integer.parseInt(id.getText()));
                                            in.setLineId(lines.getItems().get(lines.getSelectionModel().getSelectedIndex()).getId());
                                            in.setDate(date.getValue().format(format)); 
                                            in.setDetails(items);
                                            in.setUserId(Integer.parseInt(prefs.get(USER_ID, "0"))); 
                                            in.Add(); 
                                            ok = true;
                                         
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                        ok = false;
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
                    if (ok) {
                        clear(); 
                        AlertDialogs.success();
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    private boolean Validate() {
        if (date.getValue() == null) {
            AlertDialogs.showError("برجاء ادخال تاريخ الفاتورة");
            return false;
        } else if (lines.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("برجاء اختيار خط السير");
            return false;
        } else if (invoiceTable.getItems().isEmpty()) {
            AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
            return false;
        } 
        return true;
    }

    @FXML
    private void getTrackData(ActionEvent event) {
         getData(lines.getItems().get(lines.getSelectionModel().getSelectedIndex()).getId());
    }
    
}
