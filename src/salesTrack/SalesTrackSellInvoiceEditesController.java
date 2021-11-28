/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesTrack;

import assets.classes.AlertDialogs;
import static assets.classes.statics.USER_ID;
import businessadministration.BusinessAdministration;
import clients.assets.Clients;
import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
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
import javafx.scene.control.CheckBox;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import salesTrack.assets.TrackLine;
import salesTrack.assets.TrackSell;
import salesTrack.assets.TrackSellDetails;
import store.assets.StoreProducts;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesTrackSellInvoiceEditesController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private JFXDatePicker date;
    @FXML
    private ComboBox<Clients> clients;
    @FXML
    private TextField filesPath;
    @FXML
    private ComboBox<TrackLine> line;
    @FXML
    private ComboBox<TrackSell> id;
    @FXML
    private CheckBox noTaxes;
    @FXML
    private CheckBox hasTaxes;
    @FXML
    private CheckBox addtionalCost;
    @FXML
    private CheckBox onNote;
    @FXML
    private Button show;
    @FXML
    private TableView<TrackSellDetails> invoiceTable;
    @FXML
    private MenuItem deleteRow1;
    @FXML
    private TableColumn<TrackSellDetails, String> tabId1;
    @FXML
    private TableColumn<TrackSellDetails, String> tabProduct1;
    @FXML
    private TableColumn<TrackSellDetails, String> tabAmount1;
    @FXML
    private TableColumn<TrackSellDetails, String> tabCost1;
    @FXML
    private Button invoiveDelete;
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

    /**
     * Initializes the controller class.
     */
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

    public void setTotal() {
        try {

            ObservableList<TrackSellDetails> items1 = invoiceTable.getItems();
            double total = 0;
            for (TrackSellDetails a : items1) {
                total += Double.parseDouble(a.getAmountField().getText()) * Double.parseDouble(a.getCostField().getText());
            }
            invoiceTotal.setText(Double.toString(total));
//            if (addtionalCost.isSelected()) {
//                invoiceTotal.setText(Double.toString(total + ((14 * total) / 100)));
//               
//            }
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
            if (addtionalCost.isSelected()) {
                invoiceLastTotal.setText(Double.toString(Double.parseDouble(invoiceLastTotal.getText()) + ((14 * Double.parseDouble(invoiceLastTotal.getText())) / 100)));
            }
        } catch (Exception e) {
            AlertDialogs.showErrors(e);
        }
    }
    ObservableList<Clients> clientData;
    ObservableList<Clients> clientDataSearch = FXCollections.observableArrayList();

    ObservableList<TrackLine> trackData;
    ObservableList<TrackLine> trackDataSearch = FXCollections.observableArrayList();

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<TrackSell> invoices;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            trackData = TrackLine.getDataWithId();
                            clientData = Clients.getData();
                            invoices = TrackSell.getData();
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
                id.setItems(invoices);

                id.setConverter(new StringConverter<TrackSell>() {
                    @Override
                    public String toString(TrackSell object) {
                        return Integer.toString(object.getId());
                    }

                    @Override
                    public TrackSell fromString(String string) {
                        return null;
                    }
                });
                id.setCellFactory(cell -> new ListCell<TrackSell>() {

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
                    protected void updateItem(TrackSell person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblOrg.setText("التاريخ: " + person.getDate());
                            lblName.setText("العميل: " + person.getClient());
                            setGraphic(gridPane);
                        } else {
                            setGraphic(null);
                        }
                    }
                });
                line.setItems(trackData);
                line.setEditable(true);
                line.setOnKeyReleased((event) -> {

                    if (line.getEditor().getText().length() == 0) {
                        line.setItems(trackData);
                    } else {
                        trackDataSearch = FXCollections.observableArrayList();

                        for (TrackLine a : trackData) {
                            if (a.getDate().contains(line.getEditor().getText()) || a.getLocation().contains(line.getEditor().getText())) {
                                trackDataSearch.add(a);
                            }
                        }
                        line.setItems(trackDataSearch);
                        line.show();
                    }
                });
                line.setConverter(new StringConverter<TrackLine>() {
                    @Override
                    public String toString(TrackLine object) {
                        return object.getLocation();
                    }

                    @Override
                    public TrackLine fromString(String string) {
                        return null;
                    }
                });
                line.setCellFactory(cell -> new ListCell<TrackLine>() {

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
                            lblOrg.setText("التاريخ: " + person.getDate());
                            lblName.setText("المكان: " + person.getLocation());
                            setGraphic(gridPane);
                        } else {
                            setGraphic(null);
                        }
                    }
                });
                clients.setItems(clientData);
                clients.setEditable(true);
                clients.setOnKeyReleased((event) -> {

                    if (clients.getEditor().getText().length() == 0) {
                        clients.setItems(clientData);
                    } else {
                        clientDataSearch = FXCollections.observableArrayList();

                        for (Clients a : clientData) {
                            if (a.getName().contains(clients.getEditor().getText()) || a.getOrganization().contains(clients.getEditor().getText())) {
                                clientDataSearch.add(a);
                            }
                        }
                        clients.setItems(clientDataSearch);
                        clients.show();
                    }
                });
                clients.setConverter(new StringConverter<Clients>() {
                    @Override
                    public String toString(Clients object) {
                        return object.getOrganization();
                    }

                    @Override
                    public Clients fromString(String string) {
                        return null;
                    }
                });
                clients.setCellFactory(cell -> new ListCell<Clients>() {

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
                    protected void updateItem(Clients person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblOrg.setText("المؤسسة: " + person.getOrganization());
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

    private void getData(int id) {
        try {
            TrackSell in = TrackSell.getDataById(id).get(0);
            notes.setText(in.getNotes());
            invoiceTotal.setText(in.getOriginalCost());
            onNote.setSelected(in.getPayType() == null ? false : in.getPayType().equals("تقسيط"));
            addtionalCost.setSelected(Boolean.parseBoolean(in.getHasTaxs()));
            noTaxes.setSelected(!Boolean.parseBoolean(in.getHasTaxs()));
            invoiceLastTotal.setText(in.getTotal());
            invoicedisc.setText(in.getDiscount());
            invoiceDiscPercent.setText(in.getDiscountPercent());
            date.setValue(LocalDate.parse(in.getDate()));

            ObservableList<Clients> items1 = clients.getItems();
            for (Clients a : items1) {

                if (a.getId() == in.getClientId()) {
                    clients.getSelectionModel().select(a);
                }
            }
            ObservableList<TrackLine> items3 = line.getItems();
            for (TrackLine a : items3) {

                if (a.getId() == in.getLineId()) {
                    line.getSelectionModel().select(a);
                }
            }
            ObservableList<StoreProducts> data = StoreProducts.gettDataOfClientsInvoiceOfTrack(id);
            invoiceTable.setItems(TrackSellDetails.getData(id, data));
            invoiceTable.setOnKeyReleased((event) -> {

                if (event.getCode() == KeyCode.ENTER) {
                    ObservableList<TrackSellDetails> items = invoiceTable.getItems();
                    boolean found = false;
                    for (TrackSellDetails item : items) {
                        StoreProducts a = (StoreProducts) item.getProductsCombo().getSelectionModel().getSelectedItem();
                        if (Double.parseDouble(item.getAmountField().getText()) > Double.parseDouble(a.getUnitAmount())) {
                            found = true;

                        }
                    }
                    if (found) {
                        AlertDialogs.showError("الكمية المباعة اكبر من الموجودة بالمخزن");
                    } else {
                        setTotal();
                    }
                }
                if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getProductsCombo().getSelectionModel().getSelectedIndex() != -1
                        && !invoiceTable.getSelectionModel().getSelectedItem().getAmountField().getText().equals("0")
                        && !invoiceTable.getSelectionModel().getSelectedItem().getCostField().getText().equals("0")) {
                    setTotal();
//                    invoiceTable.getItems().add(new TrackSellDetails(invoiceTable.getItems().size() + 1, data, "0", "0", "0", "0"));
//                    invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
                }

            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
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
    private void setLineProucts(ActionEvent event) {
    }

    @FXML
    private void removeSelectNoTax(ActionEvent event) {
        hasTaxes.setSelected(false);
        addtionalCost.setSelected(false);
    }

    @FXML
    private void removeSelect(ActionEvent event) {
        noTaxes.setSelected(false);
        addtionalCost.setSelected(false);
    }

    @FXML
    private void addDariba(ActionEvent event) {
        hasTaxes.setSelected(false);
        noTaxes.setSelected(false);
        setTotal();
    }

    @FXML
    private void show(ActionEvent event) {
        getData(id.getItems().get(id.getSelectionModel().getSelectedIndex()).getId());
    }

    @FXML
    private void deleteRow(ActionEvent event) {
        if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الصف اولا");
        } else {
            if (invoiceTable.getSelectionModel().getSelectedItem().getProductsCombo().getSelectionModel().getSelectedIndex() != -1
                    && !invoiceTable.getSelectionModel().getSelectedItem().getAmountField().getText().equals("0")
                    && !invoiceTable.getSelectionModel().getSelectedItem().getCostField().getText().equals("0")) {
                invoiceTable.getItems().remove(invoiceTable.getSelectionModel().getSelectedIndex());
            } else if (invoiceTable.getSelectionModel().getSelectedIndex() <= invoiceTable.getItems().size() - 1) {
                invoiceTable.getItems().remove(invoiceTable.getSelectionModel().getSelectedIndex());
            }
        }
    }

    @FXML
    private void invoiveDelete(ActionEvent event) {
       
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                TrackSell in;
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
                                        ObservableList<TrackSellDetails> items = invoiceTable.getItems();
                                        
                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                            alert.setTitle("Deleting Invoice  ");
                                            alert.setHeaderText("سيتم حذف  الفاتورة ");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                in = new TrackSell();
                                                in.setId(id.getItems().get(id.getSelectionModel().getSelectedIndex()).getId());
                                                in.Delete();
                                                ok = true;
                                            }
                                       
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
                        AlertDialogs.success();
                    }
                    super.succeeded();
                }
            };
            service.start();
       
    }

    @FXML
    private void invoiveAdd(ActionEvent event) {
        if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                TrackSell in;
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
                                        ObservableList<TrackSellDetails> items = invoiceTable.getItems();
                                        
                                            in = new TrackSell();
                                            in.setId(id.getItems().get(id.getSelectionModel().getSelectedIndex()).getId());
                                            in.setClientId(clients.getItems().get(clients.getSelectionModel().getSelectedIndex()).getId());
                                            in.setLineId(line.getItems().get(line.getSelectionModel().getSelectedIndex()).getId());
                                            in.setSalesId(line.getItems().get(line.getSelectionModel().getSelectedIndex()).getSalesId());
                                            in.setDate(date.getValue().format(format));
                                            in.setOriginalCost(invoiceTotal.getText());
                                            in.setDiscount(invoicedisc.getText());
                                            in.setDiscountPercent(invoiceDiscPercent.getText());
                                            in.setTotal(invoiceLastTotal.getText());
                                            in.setHasTaxs(Boolean.toString(addtionalCost.isSelected() || hasTaxes.isSelected()));
                                            in.setPayType(onNote.isSelected() ? "تقسيط" : "كاش");
                                            in.setPriceType(onNote.isSelected() ? "تقسيط" : "كاش");
                                            in.setNotes(notes.getText());
                                            items.remove(items.size() - 1);
                                            in.setDetails(items);
                                            in.setUserId(Integer.parseInt(prefs.get(USER_ID, "0")));
                                            if (filesPath.getText().isEmpty() || filesPath.getText().length() == 0) {
                                                in.EditeWithoutPhoto();
                                            } else {
                                                InputStream input = new FileInputStream(new File(filesPath.getText()));
                                                in.setDoc(input);

                                                String[] st = filesPath.getText().split(Pattern.quote("."));
                                                in.setDocExt(st[st.length - 1]);
                                                in.Edite();
                                            }
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
                        AlertDialogs.success();
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void setDisc(KeyEvent event) {setTotal();
    }

    private boolean Validate() {
        if (date.getValue() == null) {
            AlertDialogs.showError("برجاء ادخال تاريخ الفاتورة");
            return false;
        } else if (clients.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("برجاء اختيار العميل");
            return false;
        }  else if (line.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("برجاء اختيار خط السير");
            return false;
        } else if (filesPath.getText().length() > 0) {
            File a = new File(filesPath.getText());
            boolean file = a.isFile();
            if (!file) {
                AlertDialogs.showError("الملف غير صحيح");
                return false;
            }
        }   else if (invoiceTotal.getText().equals("0")) {
            setTotal();
        } 
        return true;
    }
}
