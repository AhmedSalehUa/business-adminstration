/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;
 
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.THEME;
import static assets.classes.statics.USER_ROLE;
import businessadministration.BusinessAdministration;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sales.assets.Calls;
import sales.assets.SalesClient;
import sales.assets.SalesMembers;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenCallsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Calls> tab;
    @FXML
    private TableColumn<Calls, String> tabSales;
    @FXML
    private TableColumn<Calls, String> tabDetails;
    @FXML
    private TableColumn<Calls, String> tabTime;
    @FXML
    private TableColumn<Calls, String> tabDate;
    @FXML
    private TableColumn<Calls, String> tabClient;
    @FXML
    private TableColumn<Calls, String> tabId;
    @FXML
    private Label id;
    @FXML
    private ComboBox<SalesMembers> sales;
    @FXML
    private ComboBox<SalesClient> client;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker time;
    @FXML
    private TextArea details;
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

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Preferences prefs;
    @FXML
    private Button addEvent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        date.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
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
        tab.setOnMouseClicked((e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                Calls selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                details.setText(selected.getDetails());
                date.setValue(LocalDate.parse(selected.getDate()));
                time.setValue(LocalTime.parse(selected.getTime()));
                ObservableList<SalesClient> items1 = client.getItems();
                for (SalesClient a : items1) {
                    if (a.getName().equals(selected.getClient())) {
                        client.getSelectionModel().select(a);
                    }
                }
                ObservableList<SalesMembers> items = sales.getItems();
                for (SalesMembers a : items) {
                    if (a.getName().equals(selected.getSales())) {
                        sales.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tabSales.setCellValueFactory(new PropertyValueFactory<>("sales"));

        tabDetails.setCellValueFactory(new PropertyValueFactory<>("details"));

        tabTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tabClient.setCellValueFactory(new PropertyValueFactory<>("client"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);
        client.getSelectionModel().clearSelection();
        sales.getSelectionModel().clearSelection();
        date.setValue(null);
        time.setValue(null);
        details.setText("");
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
                            autoNum = Calls.getAutoNum();
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

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Calls> data;

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
                                        if (prefs.get(USER_ROLE, "user").equals("super_admin")) {
                                            data = Calls.getData();
                                        } else {
                                            data = Calls.getData(prefs.get(statics.USER_ID, "0"));
                                        }

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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
                tab.setItems(data);
                items = data;
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Calls> items;

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<SalesClient> data;ObservableList<SalesMembers> salesData;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = SalesClient.getData();
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
              
                    client.setItems(data);

                    client.setConverter(new StringConverter<SalesClient>() {
                        @Override
                        public String toString(SalesClient patient) {
                            return patient.getName();
                        }

                        @Override
                        public SalesClient fromString(String string) {
                            return null;
                        }
                    });
                    client.setCellFactory(cell -> new ListCell<SalesClient>() {

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
                        protected void updateItem(SalesClient person, boolean empty) {
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
                    sales.setItems(salesData);
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

        FilteredList<Calls> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getClient().contains(lowerCaseFilter)
                    || pa.getSales().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)
                    || pa.getTime().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< Calls> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);
    }

    @FXML
    private void New(ActionEvent event) {
        clear();
    }

    @FXML
    private void Delete(ActionEvent event) {
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
                                    alert.setTitle("Deleting Call ");
                                    alert.setHeaderText("سيتم حذف المكالمة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Calls sl = new Calls();
                                        sl.setId(Integer.parseInt(id.getText()));
                                        sl.Delete();
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
    private void Edite(ActionEvent event) {
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
                                    alert.setTitle("Editing Call ");
                                    alert.setHeaderText("سيتم تعديل المكالمة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Calls sl = new Calls();
                                        sl.setId(Integer.parseInt(id.getText()));
                                        sl.setClient_id(client.getSelectionModel().getSelectedItem().getId());
                                        sl.setSales_id(sales.getSelectionModel().getSelectedItem().getId());
                                        sl.setDate(date.getValue().format(format));
                                        sl.setTime(time.getValue().format(DateTimeFormatter.ISO_LOCAL_TIME));
                                        sl.setDetails(details.getText());
                                        sl.Edite();
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
    private void Add(ActionEvent event) {
        if (client.getSelectionModel().getSelectedIndex() == -1 || sales.getSelectionModel().getSelectedIndex() == -1 || date.getValue() == null || time.getValue() == null) {
            AlertDialogs.showError("يوجد بيانات فارغة");
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
                                        Calls sl = new Calls();
                                        sl.setId(Integer.parseInt(id.getText()));
                                        sl.setClient_id(client.getSelectionModel().getSelectedItem().getId());
                                        sl.setSales_id(sales.getSelectionModel().getSelectedItem().getId());
                                        sl.setDate(date.getValue().format(format));
                                        sl.setTime(time.getValue().format(DateTimeFormatter.ISO_LOCAL_TIME));
                                        sl.setDetails(details.getText());
                                        sl.Add();
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
    private void addEvent(ActionEvent event) {
        try {
            FXMLLoader eventView = new FXMLLoader(getClass().getResource("SalesScreenAddEvent.fxml"));
            Parent eventParent = eventView.load();
             
            eventParent.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
            
            Stage st = new Stage();
            st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
            st.setTitle("Acapy Trade");
            
            Scene scene = new Scene(eventParent, 458, 529);
            st.setScene(scene);
            st.show(); 
        } catch (IOException ex) {
           AlertDialogs.showErrors(ex);
        }
        
    }

}
