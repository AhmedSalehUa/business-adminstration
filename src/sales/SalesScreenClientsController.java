/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;
 
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.USER_ROLE;
import businessadministration.BusinessAdministration;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import  clients.assets.Clients;
import  sales.assets.Calls;
import  sales.assets.SalesClient;
import  sales.assets.SalesMembers;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenClientsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<SalesClient> tab;
    @FXML
    private TableColumn<SalesClient, String> tabTele2;
    @FXML
    private TableColumn<SalesClient, String> tabTele1;
    @FXML
    private TableColumn<SalesClient, String> tabEmail;
    @FXML
    private TableColumn<SalesClient, String> tabAdress;
    @FXML
    private TableColumn<SalesClient, String> tabRel;
    @FXML
    private TableColumn<SalesClient, String> tabOrg;
    @FXML
    private TableColumn<SalesClient, String> tabName;
    @FXML
    private TableColumn<SalesClient, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField name;
    @FXML
    private TextField organization;
    @FXML
    private TextField adress;
    @FXML
    private TextField email;
    @FXML
    private TextField relation;
    @FXML
    private TextField tele1;
    @FXML
    private TextField tele2;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button New;
    @FXML
    private Button Delete;
    @FXML
    private Button Edite;
    @FXML
    private Button Add;
    @FXML
    private TableColumn<SalesClient, String> tabSales;
    @FXML
    private ComboBox<SalesMembers> sales;
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
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
                                    setTableColumns();
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

        tab.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {

            } else {
                Add.setDisable(true);
                Edite.setDisable(false);
                Delete.setDisable(false);
                New.setDisable(false);

                SalesClient pa = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(pa.getId()));
                name.setText(pa.getName());
                organization.setText(pa.getOrganization());
                adress.setText(pa.getLocation());
                email.setText(pa.getEmail());
                relation.setText(pa.getRelation());
                tele1.setText(pa.getTele1());
                tele2.setText(pa.getTele2());
                ObservableList<SalesMembers> items1 = sales.getItems();
                for (SalesMembers a : items1) {
                    if (a.getName().equals(pa.getSales())) {
                        sales.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void fillCombo() throws Exception {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<SalesMembers> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = SalesMembers.getData();
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
                sales.setItems(data);
                sales.setConverter(new StringConverter<SalesMembers>() {
                    @Override
                    public String toString(SalesMembers sales) {
                        return sales.getName();
                    }

                    @Override
                    public SalesMembers fromString(String string) {
                        return null;
                    }
                });
                sales.setCellFactory(cell -> new ListCell<SalesMembers>() {

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
                    protected void updateItem(SalesMembers person, boolean empty) {
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
                super.succeeded();
            }
        };
        service.start();

    }

    private void setAutoNumber() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            autoNum = SalesClient.getAutoNum();
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

    private void clear() {
        setAutoNumber();
        name.setText("");
        organization.setText("");
        adress.setText("");
        relation.setText("");
        email.setText("");
        tele1.setText("");
        tele2.setText("");
        sales.getSelectionModel().clearSelection();
    }

    private void setTableColumns() {
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabOrg.setCellValueFactory(new PropertyValueFactory<>("organization"));
        tabAdress.setCellValueFactory(new PropertyValueFactory<>("location"));
        tabEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tabRel.setCellValueFactory(new PropertyValueFactory<>("relation"));
        tabTele1.setCellValueFactory(new PropertyValueFactory<>("tele1"));
        tabTele2.setCellValueFactory(new PropertyValueFactory<>("tele2"));
        tabSales.setCellValueFactory(new PropertyValueFactory<>("sales"));
    }

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<SalesClient> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {
                            if (prefs.get(USER_ROLE, "user").equals("super_admin") || prefs.get(USER_ROLE, "user").equals("admin")) {

                                data = SalesClient.getData();
                            } else {
                                data = SalesClient.getData(prefs.get(statics.USER_ID, "0"));
                            }
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
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
    ObservableList<SalesClient> items;

    @FXML
    private void search(KeyEvent event) {

        FilteredList<SalesClient> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getName().contains(lowerCaseFilter)
                    || pa.getOrganization().contains(lowerCaseFilter)
                    || pa.getLocation().contains(lowerCaseFilter)
                    || pa.getRelation().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getSales().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< SalesClient> sortedData = new SortedList<>(filteredData);
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
                                    alert.setTitle("Deleting  Client");
                                    alert.setHeaderText("سيتم حذف العميل ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        SalesClient sl = new SalesClient();
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
        if (name.getText().isEmpty()) {
            AlertDialogs.showError("اسم العميل لا يمكن ان يكون فارغ");
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
                                        alert.setTitle("Editing  Client");
                                        alert.setHeaderText("سيتم تعديل العميل ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            SalesClient sl = new SalesClient();
                                            sl.setId(Integer.parseInt(id.getText()));
                                            sl.setName(name.getText());
                                            sl.setOrganization(organization.getText());
                                            sl.setRelation(relation.getText());
                                            sl.setLocation(adress.getText());
                                            sl.setEmail(email.getText());
                                            sl.setTele1(tele1.getText());
                                            sl.setTele2(tele2.getText());
                                            sl.setSales_id(sales.getSelectionModel().getSelectedItem().getId());
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
    }

    @FXML
    private void Add(ActionEvent event) {
        if (name.getText().isEmpty()) {
            AlertDialogs.showError("اسم العميل لا يمكن ان يكون فارغ");
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
                                        SalesClient sl = new SalesClient();
                                        sl.setId(Integer.parseInt(id.getText()));
                                        sl.setName(name.getText());
                                        sl.setOrganization(organization.getText());
                                        sl.setRelation(relation.getText());
                                        sl.setLocation(adress.getText());
                                        sl.setEmail(email.getText());
                                        sl.setTele1(tele1.getText());
                                        sl.setTele2(tele2.getText());
                                        sl.setSales_id(sales.getSelectionModel().getSelectedItem().getId());
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

}
