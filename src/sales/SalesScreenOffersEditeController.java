/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import  sales.assets.Offers;
import  sales.assets.OffersConditions;
import  sales.assets.OffersDetails;
import  sales.assets.SalesClient;
import  sales.assets.SalesMembers;
import  store.assets.Products;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenOffersEditeController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private ComboBox<SalesClient> client;
    @FXML
    private JFXDatePicker date;
    @FXML
    private TextField filesPath;
    @FXML
    private ComboBox<SalesMembers> sales;
    @FXML
    private Button showInvoice;
    @FXML
    private ComboBox<Offers> id;
    @FXML
    private TableView<OffersDetails> invoiceTable;
    @FXML
    private MenuItem deleteRow;
    @FXML
    private TableColumn<OffersDetails, String> tabCost;
    @FXML
    private TableColumn<OffersDetails, String> tabAmount;
    @FXML
    private TableColumn<OffersDetails, String> tabProduct;
    @FXML
    private TableColumn<OffersDetails, String> tabId;
    @FXML
    private TableView<OffersConditions> CondTab;
    @FXML
    private TableColumn<OffersConditions, String> CondTabValue;
    @FXML
    private TableColumn<OffersConditions, String> CondTabAttribute;
    @FXML
    private TableColumn<OffersConditions, String> CondTabId;
    @FXML
    private Label condId;
    @FXML
    private TextField condAtrribute;
    @FXML
    private TextField condValue;
    @FXML
    private ProgressIndicator progressCond;
    @FXML
    private Button New;
    @FXML
    private Button Delete;
    @FXML
    private Button Edite;
    @FXML
    private Button Add;
    @FXML
    private Button deleteInvoive;
    @FXML
    private Button invoiveAdd;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TextField invoiceTotal;
    @FXML
    private TextField invoicedisc;
    @FXML
    private TextField invoiceDiscPercent;
    @FXML
    private TextField invoiceLastTotal;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(true);
        progressCond.setVisible(true);
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
                                    fillCombo();
                                    getCondAuto();
                                    clearCond();
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
                progressCond.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
        CondTab.setOnMouseClicked((e) -> {
            if (CondTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                New.setDisable(false);

                Delete.setDisable(false);

                Edite.setDisable(false);

                Add.setDisable(true);

                OffersConditions selected = CondTab.getSelectionModel().getSelectedItem();
                condId.setText(Integer.toString(selected.getId()));
                condAtrribute.setText(selected.getAttribute());
                condValue.setText(selected.getValue());

            }
        });
        invoiceDiscPercent.setOnKeyPressed((w) -> {
            setTotal("");
        });
        invoicedisc.setOnKeyPressed((w) -> {
            setTotal("");
        });
    }

    private void intialColumn() {

        tabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        tabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tabProduct.setCellValueFactory(new PropertyValueFactory<>("products"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        CondTabValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        CondTabAttribute.setCellValueFactory(new PropertyValueFactory<>("attribute"));

        CondTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clearCond() {
        getCondAuto();
        condAtrribute.setText("");
        condValue.setText("");

        New.setDisable(true);
        Delete.setDisable(true);
        Edite.setDisable(true);
        Add.setDisable(false);
    }

    private void clear() {
        date.setValue(null);
        client.getSelectionModel().clearSelection();
        sales.getSelectionModel().clearSelection();
        filesPath.setText("");
        notes.setText("");
        invoiceTotal.setText("");
        invoicedisc.setText("");
        invoiceDiscPercent.setText("");
        invoiceLastTotal.setText("");
        CondTab.setItems(null);
        getCondAuto();

    }

    private void clear(Offers in) {
        ObservableList<Offers> IdItems = id.getItems();
        for (Offers a : IdItems) {
            if (a.getId() == in.getId()) {
                id.getSelectionModel().select(a);
            }
        }
        date.setValue(LocalDate.parse(in.getDate()));

        ObservableList<SalesClient> items1 = client.getItems();
        for (SalesClient a : items1) {
            if (a.getName().equals(in.getClient())) {
                client.getSelectionModel().select(a);
            }
        }

        ObservableList<SalesMembers> items = sales.getItems();
        for (SalesMembers a : items) {
            if (a.getName().equals(in.getSales())) {
                sales.getSelectionModel().select(a);
            }
        }

        notes.setText(in.getNotes());
        invoiceTotal.setText(in.getCost());
        invoicedisc.setText(in.getDicount());
        invoiceDiscPercent.setText(in.getDiscount_percent());
        invoiceLastTotal.setText(in.getTotal_cost());
    }

    private void getCondAuto() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            autoNum = OffersConditions.getAutoNum();

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
                int size = CondTab.getItems() != null ? CondTab.getItems().size() : 0;
                int name = Integer.parseInt(autoNum) + size;
                condId.setText(Integer.toString(name));
                super.succeeded();
            }
        };
        service.start();

    }

    private void getData(int id) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Products> data;
            ObservableList<OffersConditions> conditions;
            Offers offer;
            ObservableList<OffersDetails> details;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            offer = Offers.getData(id).get(0);
                            details = OffersDetails.getDataById(id);
                            conditions = OffersConditions.getData(id);

                            data = Products.getData();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                clear(offer);
                invoiceTable.setItems(details);
                CondTab.setItems(conditions);
                progress.setVisible(false);

                invoiceTable.getItems().add(new OffersDetails(invoiceTable.getItems().size() + 1, data, "0", "0", "0", null));
                invoiceTable.setOnKeyReleased((event) -> {

                    if (event.getCode() == KeyCode.ENTER) {
                        setTotal("");
                    }
                    if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getProducts().getSelectionModel().getSelectedIndex() != -1
                            && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                            && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                        setTotal("");
                        invoiceTable.getItems().add(new OffersDetails(invoiceTable.getItems().size() + 1, data, "0", "0", "0", null));
                        invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
                    }

                });

                super.succeeded();
            }
        };
        service.start();

    }

    public void setTotal(String toString) {
        try {

            ObservableList<OffersDetails> items1 = invoiceTable.getItems();
            double total = 0;
            for (OffersDetails a : items1) {
                total += Double.parseDouble(a.getAmount().getText()) * Double.parseDouble(a.getCost().getText());
            }
            invoiceTotal.setText(Double.toString(total));

            double discount = 0;
            double discountPercent = 0;
            if (invoicedisc.getText().isEmpty()) {
            } else {

                discount = Double.parseDouble(invoicedisc.getText().isEmpty() ? "0" : invoicedisc.getText());

            }
            if (invoiceDiscPercent.getText().isEmpty()) {
            } else {
                String a = invoiceDiscPercent.getText().isEmpty() ? "0" : invoiceDiscPercent.getText();
                discountPercent = ((Double.parseDouble(a) * total) / 100);

            }

            invoiceLastTotal.setText(Double.toString(total - discount - discountPercent));
        } catch (Exception e) {
            AlertDialogs.showErrors(e);
        }
    }

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<SalesMembers> Salesdata;
            ObservableList<SalesClient> clientData;
            ObservableList<Offers> offersData;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            offersData = Offers.getData();
                            Salesdata = SalesMembers.getData();
                            clientData = SalesClient.getData();
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
                id.setItems(offersData);
                id.setConverter(new StringConverter<Offers>() {
                    @Override
                    public String toString(Offers patient) {
                        return Integer.toString(patient.getId());
                    }

                    @Override
                    public Offers fromString(String string) {
                        return null;
                    }
                });
                id.setCellFactory(cell -> new ListCell<Offers>() {

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
                    protected void updateItem(Offers person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("العميل: " + person.getClient());

                            setGraphic(gridPane);
                        } else {
                            setGraphic(null);
                        }
                    }
                });
                sales.setItems(Salesdata);
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
                client.setItems(clientData);
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

                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void attachFile(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog(st);

        if (file != null) {
            filesPath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void showInvoice(ActionEvent event) {

        getData(id.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    private void deleteRow(ActionEvent event) {
        if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الصف اولا");
        } else {
            if (invoiceTable.getSelectionModel().getSelectedItem().getProducts().getSelectionModel().getSelectedIndex() != -1
                    && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                    && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                invoiceTable.getItems().remove(invoiceTable.getSelectionModel().getSelectedIndex());
            }
        }
    }

    @FXML
    private void NewCond(ActionEvent event) {
        clearCond();
    }

    @FXML
    private void DeleteCond(ActionEvent event) {
        progressCond.setVisible(true);
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

                                    int i = CondTab.getSelectionModel().getSelectedIndex();
                                    CondTab.getItems().remove(i);
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
                progressCond.setVisible(false);
                clearCond();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void EditeCond(ActionEvent event) {
        progressCond.setVisible(true);
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

                                    OffersConditions of = new OffersConditions();
                                    of.setId(Integer.parseInt(condId.getText()));
                                    of.setAttribute(condAtrribute.getText());
                                    of.setValue(condValue.getText());
                                    of.setInvoice_id(id.getSelectionModel().getSelectedItem().getId());
                                    int i = CondTab.getSelectionModel().getSelectedIndex();
                                    CondTab.getItems().remove(i);
                                    CondTab.getItems().add(i, of);
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
                progressCond.setVisible(false);
                clearCond();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void AddCond(ActionEvent event) {
        progressCond.setVisible(true);
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

                                    OffersConditions of = new OffersConditions();
                                    of.setId(Integer.parseInt(condId.getText()));
                                    of.setAttribute(condAtrribute.getText());
                                    of.setValue(condValue.getText());
                                    of.setInvoice_id(id.getSelectionModel().getSelectedItem().getId());
                                    if (CondTab.getItems() == null) {
                                        ObservableList<OffersConditions> items = FXCollections.observableArrayList();
                                        items.add(of);
                                        CondTab.setItems(items);
                                    } else {
                                        CondTab.getItems().add(of);
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
                progressCond.setVisible(false);
                clearCond();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void deleteInvoice(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            boolean ok = true;
            Offers in = new Offers();

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
                                    setTotal("");
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting Offer  ");
                                    alert.setHeaderText("سيتم حذف  العرض ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        in = new Offers();
                                        in.setId(id.getSelectionModel().getSelectedItem().getId());

                                        in.Delete();
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok = false;
                                    try {

                                        in.Delete();
                                    } catch (Exception ex1) {
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
                progress.setVisible(false);
                if (ok) {
                    clear();
                    AlertDialogs.showmessage("تم");
                } else {
                    clear(in);
                }

                super.succeeded();
            }

        };
        service.start();
    }

    @FXML
    private void invoiveAdd(ActionEvent event) {

        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            boolean ok = true;
            Offers in = new Offers();

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
                                    setTotal("");
                                    if (date.getValue() == null) {
                                        AlertDialogs.showError("برجاء ادخال تاريخ الفاتورة");
                                    } else if (client.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("برجاء اختيار العميل");
                                    } else if (sales.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("برجاء اختيار مسؤول المبيعات");
                                    } else if (invoiceTable.getItems().isEmpty()) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else if (invoiceTable.getItems().size() == 1 && invoiceTotal.getText().equals("0") || invoiceTotal.getText().equals("0.0")) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else if (invoiceTotal.getText().equals("0")) {
                                        setTotal("");
                                    } else if (invoiceTable.getItems().size() == 1) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else {
                                        ObservableList<OffersDetails> items = invoiceTable.getItems();

                                        if (items.size() - 1 == 0) {
                                            AlertDialogs.showError("اضغط Enter اذا كان الجدول غير فارغ على اخر خانة");
                                        } else {
                                            in = new Offers();
                                            in.setId(id.getSelectionModel().getSelectedItem().getId());
                                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                            in.setDate(date.getValue().format(format));
                                            in.setClient_id(client.getSelectionModel().getSelectedItem().getId());
                                            in.setCost(invoiceTotal.getText());
                                            in.setDicount(invoicedisc.getText().isEmpty() ? "0" : invoicedisc.getText());
                                            in.setDiscount_percent(invoiceDiscPercent.getText().isEmpty() ? "0" : invoiceDiscPercent.getText());
                                            in.setTotal_cost(invoiceLastTotal.getText());
                                            in.setNotes(notes.getText().isEmpty() ? "لايوجد" : notes.getText());

                                            items.remove(items.size() - 1);
                                            in.setDetails(items);

                                            ObservableList<OffersConditions> conditions = CondTab.getItems();
                                            in.setConditions(conditions);

                                            in.EditeWithouPhoto();
                                        }
                                    }
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                    ok = false;
                                    try {

                                        in.Delete();
                                    } catch (Exception ex1) {
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
                progress.setVisible(false);
                if (ok) {
                    clear();
                    AlertDialogs.showmessage("تم");
                } else {
                    clear(in);
                }

                super.succeeded();
            }

        };
        service.start();
    }

}
