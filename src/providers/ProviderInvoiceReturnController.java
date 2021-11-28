/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers;

import assets.classes.AlertDialogs;
import static assets.classes.statics.USER_ID;
import businessadministration.BusinessAdministration;
import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import providers.assets.InvoiceBuy;
import providers.assets.InvoiceBuyDetails;
import providers.assets.Provider;
import providers.assets.ProviderReturned;
import providers.assets.ProviderReturnedDetails;
import store.assets.Products;
import store.assets.StoreProducts;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ProviderInvoiceReturnController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private Label id;
    @FXML
    private JFXDatePicker date;
    @FXML
    private ComboBox<InvoiceBuy> invoiceID;
    @FXML
    private TableView<ProviderReturnedDetails> invoiceTable;
    @FXML
    private MenuItem deleteRow;
    @FXML
    private TableColumn<ProviderReturnedDetails, String> tabId;
    @FXML
    private TableColumn<ProviderReturnedDetails, String> tabProduct;
    @FXML
    private TableColumn<ProviderReturnedDetails, String> tabAmount;
    @FXML
    private TableColumn<ProviderReturnedDetails, String> tabCost;
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
    @FXML
    private Tab editePaneTab;
    @FXML
    private AnchorPane editePane;

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
                                    clear();
                                    intialColumn();

                                    fillCombo();
                                    ConfigPanels();
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

    private void ConfigPanels() throws Exception {
        editePaneTab.setContent(FXMLLoader.load(getClass().getResource("ProviderInvoiceReturnEdits.fxml")));
    }

    private void intialColumn() {
        tabCost.setCellValueFactory(new PropertyValueFactory<>("costField"));

        tabAmount.setCellValueFactory(new PropertyValueFactory<>("amountField"));

        tabProduct.setCellValueFactory(new PropertyValueFactory<>("productsCombo"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));

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

                            autoNum = ProviderReturned.getAutoNum();

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
            ObservableList<StoreProducts> data = StoreProducts.getDataOfInvoice(id);

            ObservableList<ProviderReturnedDetails> list = FXCollections.observableArrayList();
            list.add(new ProviderReturnedDetails(1, data, "0", "0", "0", "0"));
            invoiceTable.setItems(list);
            invoiceTable.setOnKeyReleased((event) -> {

                if (event.getCode() == KeyCode.ENTER) {
                    ObservableList<ProviderReturnedDetails> items = invoiceTable.getItems();
                    boolean found = false;
                    for (ProviderReturnedDetails item : items) {
                        StoreProducts a = (StoreProducts) item.getProductsCombo().getSelectionModel().getSelectedItem();
                        if (Double.parseDouble(item.getAmountField().getText()) > Double.parseDouble(a.getUnitAmount())) {
                            found = true;

                        }
                    }
                    if (found) {
                        AlertDialogs.showError("الكمية المرتجعة اكبر من الموجودة بالمخزن");
                    }
                    setTotal();
                }
                if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getProductsCombo().getSelectionModel().getSelectedIndex() != -1
                        && !invoiceTable.getSelectionModel().getSelectedItem().getAmountField().getText().equals("0")
                        && !invoiceTable.getSelectionModel().getSelectedItem().getCostField().getText().equals("0")) {
                    setTotal();
                    invoiceTable.getItems().add(new ProviderReturnedDetails(invoiceTable.getItems().size() + 1, data, "0", "0", "0", "0"));
                    invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
                }

            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    public void setTotal() {

        try {

            ObservableList<ProviderReturnedDetails> items1 = invoiceTable.getItems();
            double total = 0;
            for (ProviderReturnedDetails a : items1) {
                total += Double.parseDouble(a.getAmountField().getText()) * Double.parseDouble(a.getCostField().getText());
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
            ObservableList<InvoiceBuy> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            data = InvoiceBuy.getStockedData();
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
                invoiceID.setItems(data);
                invoiceID.setConverter(new StringConverter<InvoiceBuy>() {
                    @Override
                    public String toString(InvoiceBuy object) {
                        return Integer.toString(object.getId());
                    }

                    @Override
                    public InvoiceBuy fromString(String string) {
                        return null;
                    }
                });
                invoiceID.setCellFactory(cell -> new ListCell<InvoiceBuy>() {

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
                    protected void updateItem(InvoiceBuy person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblOrg.setText("التاريخ: " + person.getDate());
                            lblName.setText("المورد: " + person.getProvider());
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
    private void invoiveAdd(ActionEvent event) {
        if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                ProviderReturned in;
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
                                        ObservableList<ProviderReturnedDetails> items = invoiceTable.getItems();
                                        if (items.size() - 1 == 0) {
                                            AlertDialogs.showError("اضغط Enter اذا كان الجدول غير فارغ على اخر خانة");
                                        } else {
                                            in = new ProviderReturned();
                                            in.setId(Integer.parseInt(id.getText()));
                                            in.setInvoiceId(invoiceID.getSelectionModel().getSelectedItem().getId());
                                            in.setDate(date.getValue().format(format));

                                            in.setTotal(invoiceLastTotal.getText());
                                            in.setNotes(notes.getText());
                                            items.remove(items.size() - 1); 
                                            in.setDetails(items);
                                            in.setUserId(Integer.parseInt(prefs.get(USER_ID, "0")));
                                            in.setStatue("pending");
                                            in.Add();

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
                        clear();

                        AlertDialogs.success();
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void setDisc(KeyEvent event) {
        setTotal();
    }

    @FXML
    private void setProducts(ActionEvent event) {
        getData(invoiceID.getSelectionModel().getSelectedItem().getId());
    }

    private boolean Validate() {

        if (date.getValue() == null) {
            AlertDialogs.showError("برجاء ادخال تاريخ الفاتورة");
            return false;
        } else if (invoiceTable.getItems().isEmpty()) {
            AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
            return false;
        } else if (invoiceTable.getItems().size() == 1 && invoiceTotal.getText().equals("0") || invoiceTotal.getText().equals("0.0")) {
            AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
            return false;
        } else if (invoiceTotal.getText().equals("0")) {
            setTotal();
        } else if (invoiceTable.getItems().size() == 1) {
            AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
            return false;
        }
        return true;
    }
}
