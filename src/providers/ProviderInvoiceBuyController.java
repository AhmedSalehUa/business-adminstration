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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import providers.assets.InvoiceBuy;
import providers.assets.InvoiceBuyDetails;
import providers.assets.InvoiceBuyExpenses;
import providers.assets.Provider;
import store.assets.Products;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ProviderInvoiceBuyController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private Label id;
    @FXML
    private JFXDatePicker date;
    @FXML
    private ComboBox<Provider> provider;
    @FXML
    private CheckBox noTaxes;
    @FXML
    private CheckBox hasTaxes;
    @FXML
    private TextField filesPath;
    @FXML
    private CheckBox addtionalCost;
    @FXML
    private CheckBox onNote;
    @FXML
    private TableView<InvoiceBuyDetails> invoiceTable;
    @FXML
    private MenuItem deleteRow;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> tabCost;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> tabAmount;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> tabProduct;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> tabId;
    @FXML
    private TableView<InvoiceBuyExpenses> CondTab;
    @FXML
    private TableColumn<InvoiceBuyExpenses, String> CondTabValue;
    @FXML
    private TableColumn<InvoiceBuyExpenses, String> CondTabAttribute;
    @FXML
    private TableColumn<InvoiceBuyExpenses, String> CondTabId;
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
    @FXML
    private AnchorPane editePane;
    @FXML
    private Tab editePaneTab;

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
                                    getData();
                                    fillCombo();getCondAuto();
                                    clearCond();
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

                InvoiceBuyExpenses selected = CondTab.getSelectionModel().getSelectedItem();
                condId.setText(Integer.toString(selected.getId()));
                condAtrribute.setText(selected.getDestination());
                condValue.setText(selected.getAmount());

            }
        });
    }

    private void ConfigPanels() throws Exception {
        editePaneTab.setContent(FXMLLoader.load(getClass().getResource("ProviderInvoiceBuyEdits.fxml")));
    }

    private void intialColumn() {
        tabCost.setCellValueFactory(new PropertyValueFactory<>("costField"));

        tabAmount.setCellValueFactory(new PropertyValueFactory<>("amountField"));

        tabProduct.setCellValueFactory(new PropertyValueFactory<>("productsCombo"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        CondTabValue.setCellValueFactory(new PropertyValueFactory<>("amount"));

        CondTabAttribute.setCellValueFactory(new PropertyValueFactory<>("destination"));

        CondTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
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

                            autoNum = InvoiceBuy.getAutoNum();

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
        try {
            ObservableList<Products> data = Products.getData();
            ObservableList<InvoiceBuyDetails> list = FXCollections.observableArrayList();
            list.add(new InvoiceBuyDetails(1, data, "0", "0", "0", "0"));
            invoiceTable.setItems(list);
            invoiceTable.setOnKeyReleased((event) -> {

                if (event.getCode() == KeyCode.ENTER) {
                    setTotal();
                }
                if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getProductsCombo().getSelectionModel().getSelectedIndex() != -1
                        && !invoiceTable.getSelectionModel().getSelectedItem().getAmountField().getText().equals("0")
                        && !invoiceTable.getSelectionModel().getSelectedItem().getCostField().getText().equals("0")) {
                    setTotal();
                    invoiceTable.getItems().add(new InvoiceBuyDetails(invoiceTable.getItems().size() + 1, data, "0", "0", "0", "0"));
                    invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
                }

            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    public void setTotal() {
        try {

            ObservableList<InvoiceBuyDetails> items1 = invoiceTable.getItems();
            double total = 0;
            for (InvoiceBuyDetails a : items1) {
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
    ObservableList<Provider> providerData;
    ObservableList<Provider> providerDataSearched = FXCollections.observableArrayList();

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            providerData = Provider.getData();
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
                provider.setItems(providerData);
                provider.setEditable(true);
                provider.setOnKeyReleased((event) -> {

                    if (provider.getEditor().getText().length() == 0) {
                        provider.setItems(providerData);
                    } else {
                        providerDataSearched = FXCollections.observableArrayList();

                        for (Provider a : providerData) {
                            if (a.getName().contains(provider.getEditor().getText()) || a.getOrganization().contains(provider.getEditor().getText())) {
                                providerDataSearched.add(a);
                            }
                        }
                        provider.setItems(providerDataSearched);
                        provider.show();
                    }
                });
                provider.setConverter(new StringConverter<Provider>() {
                    @Override
                    public String toString(Provider object) {
                        return  object.getOrganization() ;
                    }

                    @Override
                    public Provider fromString(String string) {
                        return null;
                    }
                });
                provider.setCellFactory(cell -> new ListCell<Provider>() {

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
                    protected void updateItem(Provider person, boolean empty) {
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

    @FXML
    private void removeSelect(ActionEvent event) {
        addtionalCost.setSelected(false);
        noTaxes.setSelected(false);
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
    private void addDariba(ActionEvent event) {
        hasTaxes.setSelected(false);
        setTotal();
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
            }
        }
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
                            autoNum = InvoiceBuyExpenses.getAutoNum();

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

    @FXML
    private void NewCond(ActionEvent event) {
        clearCond();
    }

    @FXML
    private void DeleteCond(ActionEvent event) {
        int i = CondTab.getSelectionModel().getSelectedIndex();
        CondTab.getItems().remove(i);
        clearCond();
    }

    @FXML
    private void EditeCond(ActionEvent event) {
        InvoiceBuyExpenses of = new InvoiceBuyExpenses();
        of.setId(Integer.parseInt(condId.getText()));
        of.setDestination(condAtrribute.getText());
        of.setAmount(condValue.getText());
        of.setInvoiceId(Integer.parseInt(id.getText()));
        int i = CondTab.getSelectionModel().getSelectedIndex();
        CondTab.getItems().remove(i);
        CondTab.getItems().add(i, of);
        clearCond();
    }

    @FXML
    private void AddCond(ActionEvent event) {
        InvoiceBuyExpenses a = new InvoiceBuyExpenses();
        a.setId(Integer.parseInt(condId.getText())); 
        a.setInvoiceId(Integer.parseInt(id.getText()));
        a.setDestination(condAtrribute.getText());
        a.setAmount(condValue.getText());
        if (CondTab.getItems() == null) {
            ObservableList<InvoiceBuyExpenses> items = FXCollections.observableArrayList();
            items.add(a);
            CondTab.setItems(items);
        } else {
            CondTab.getItems().add(a);
        }
        clearCond();
    }

    @FXML
    private void invoiveAdd(ActionEvent event) {
        if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                InvoiceBuy in;
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
                                        ObservableList<InvoiceBuyDetails> items = invoiceTable.getItems();
                                        if (items.size() - 1 == 0) {
                                            AlertDialogs.showError("اضغط Enter اذا كان الجدول غير فارغ على اخر خانة");
                                        } else {
                                            in = new InvoiceBuy();
                                            in.setId(Integer.parseInt(id.getText()));
                                            in.setProviderId(provider.getItems().get(provider.getSelectionModel().getSelectedIndex()).getId());
                                            in.setDate(date.getValue().format(format));
                                            in.setOriginalCost(invoiceTotal.getText());
                                            in.setDiscount(invoicedisc.getText());
                                            in.setDiscountPercent(invoiceDiscPercent.getText());
                                            in.setTotal(invoiceLastTotal.getText());
                                            in.setHasTaxs(Boolean.toString(addtionalCost.isSelected() || hasTaxes.isSelected()));
                                            in.setPayType(onNote.isSelected() ? "تقسيط" : "كاش");
                                            in.setNotes(notes.getText());
                                            items.remove(items.size() - 1);
                                            in.setDetails(items);
                                            in.setExpenses(CondTab.getItems());
                                            in.setUserId(Integer.parseInt(prefs.get(USER_ID, "0")));
                                            in.setStatue("pending");
                                            if (filesPath.getText().isEmpty() || filesPath.getText().length() == 0) {
                                                in.AddWithoutPhoto();
                                            } else {
                                                InputStream input = new FileInputStream(new File(filesPath.getText()));
                                                in.setDoc(input);

                                                String[] st = filesPath.getText().split(Pattern.quote("."));
                                                in.setDocExt(st[st.length - 1]);
                                                in.Add();
                                            }
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
                        getData();clearCond();CondTab.setItems(null);
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

    private boolean Validate() {
        if (date.getValue() == null) {
            AlertDialogs.showError("برجاء ادخال تاريخ الفاتورة");
            return false;
        } else if (provider.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("برجاء اختيار المورد");
            return false;
        } else if (filesPath.getText().length() > 0) {
            File a = new File(filesPath.getText());
            boolean file = a.isFile();
            if (!file) {
                AlertDialogs.showError("الملف غير صحيح");
                return false;
            }
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
