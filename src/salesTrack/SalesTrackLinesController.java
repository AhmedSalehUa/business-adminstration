/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesTrack;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
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
import sales.assets.SalesMembers;
import salesTrack.assets.TrackLine;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesTrackLinesController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<TrackLine> tab;
    @FXML
    private TableColumn<TrackLine, String> tabDate;
    @FXML
    private TableColumn<TrackLine, String> tabLocation;
    @FXML
    private TableColumn<TrackLine, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField location;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<SalesMembers> sales;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdite;
    @FXML
    private Button btnAdd;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(true);
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
        tab.setOnMouseClicked((e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                btnNew.setDisable(false);

                btnDelete.setDisable(false);

                btnEdite.setDisable(false);

                btnAdd.setDisable(true);

                TrackLine selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                location.setText(selected.getLocation());
                date.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<SalesMembers> items1 = sales.getItems();
                for (SalesMembers a : items1) {
                    if (a.getName().equals(selected.getSales())) {
                        sales.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tabLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();
        btnNew.setDisable(true);

        btnDelete.setDisable(true);

        btnEdite.setDisable(true);

        btnAdd.setDisable(false);
        location.setText("");
        date.setValue(null);
        sales.getSelectionModel().clearSelection();
        sales.getEditor().setText("");
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

                            autoNum = TrackLine.getAutoNum();
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
    ObservableList<TrackLine> items;

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<TrackLine> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = TrackLine.getData();

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
                tab.setItems(data);
                items = data;
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<SalesMembers> salesData;
    ObservableList<SalesMembers> salesDataSearched = FXCollections.observableArrayList();

    private void fillCombo() throws Exception {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            salesData = SalesMembers.getData();
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

                sales.setItems(salesData);
                sales.setEditable(true);
                sales.setOnKeyReleased((event) -> {

                    if (sales.getEditor().getText().length() == 0) {
                        sales.setItems(salesData);
                    } else {
                        salesDataSearched = FXCollections.observableArrayList();

                        for (SalesMembers a : salesData) {
                            if (a.getName().contains(sales.getEditor().getText())) {
                                salesDataSearched.add(a);
                            }
                        }
                        sales.setItems(salesDataSearched);
                        sales.show();
                    }
                });
                sales.setConverter(new StringConverter<SalesMembers>() {
                    @Override
                    public String toString(SalesMembers patient) {
                        return patient.getName();
                    }

                    @Override
                    public SalesMembers fromString(String string) {
                        return null;
                    }
                });
                sales.setCellFactory(cell -> new ListCell<SalesMembers>() {

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
                    protected void updateItem(SalesMembers person, boolean empty) {
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
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void search(KeyEvent event) {

        FilteredList<TrackLine> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getLocation().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)
                    || pa.getSales().contains(lowerCaseFilter));

        });

        SortedList<TrackLine> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);
    }

    @FXML
    private void New(ActionEvent event) {
        clear();
    }

    @FXML
    private void Delete(ActionEvent event) { progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                TrackLine tr;
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
                                         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Out Line  ");
                                        alert.setHeaderText("سيتم حذف  خط السير ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                        tr = new TrackLine();
                                        tr.setId(Integer.parseInt(id.getText())); 
                                        tr.Delete();
                                        ok = true;}
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
                        getData();
                    }
                    super.succeeded();
                }
            };
            service.start();
    }

    @FXML
    private void Edite(ActionEvent event) {
         if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                TrackLine tr;
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
                                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editing Out Line  ");
                                        alert.setHeaderText("سيتم تعديل  خط السير ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {    tr = new TrackLine();
                                        tr.setId(Integer.parseInt(id.getText()));
                                        tr.setLocation(location.getText());
                                        tr.setDate(date.getValue().format(format));
                                        tr.setSalesId(sales.getItems().get(sales.getSelectionModel().getSelectedIndex()).getId());
                                        tr.Edite();
                                        ok = true;}
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
                        getData();
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                TrackLine tr;
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
                                        tr = new TrackLine();
                                        tr.setLocation(location.getText());
                                        tr.setDate(date.getValue().format(format));
                                        tr.setSalesId(sales.getItems().get(sales.getSelectionModel().getSelectedIndex()).getId());
                                        tr.Add();
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
                        getData();
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    private boolean Validate() {
        if (location.getText().isEmpty() || location.getText().length() == 0) {
            AlertDialogs.validateError("المكان لا يجب ان يكون فارغ");
            return false;
        }

        if (date.getValue() == null) {
            AlertDialogs.validateError(" التاريخ لا يجب ان يكون فارغ");
            return false;
        }
        if (sales.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.validateError("الموظف لا يجب ان يكون فارغ");
            return false;
        }
        return true;
    }

}
