/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts;

import accounts.assets.Accounts; 
import accounts.assets.ProviderAccounts;
import accounts.assets.ProviderAccountsPays;
import assets.classes.AlertDialogs;
import static assets.classes.statics.USER_ID;
import businessadministration.BusinessAdministration; 
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
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
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import providers.assets.Provider;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ProviderAccountsController implements Initializable {

    @FXML
    private Label rest;
    @FXML
    private Label totalPaied;
    @FXML
    private Label totalInvoices;
    @FXML
    private Button print;
    @FXML
    private JFXTextField searchInvoices;
    @FXML
    private TableView<ProviderAccounts> invoicesTab;
    @FXML
    private TableColumn<ProviderAccounts, String> invoicesTabDateTime;
    @FXML
    private TableColumn<ProviderAccounts, String> invoicesTabUser;
    @FXML
    private TableColumn<ProviderAccounts, String> invoicesTabDate;
    @FXML
    private TableColumn<ProviderAccounts, String> invoicesTabAmount;
    @FXML
    private TableColumn<ProviderAccounts, String> invoicesTabInvoice;
    @FXML
    private TableColumn<ProviderAccounts, String> invoicesTabId;
    @FXML
    private JFXTextField searchPays;
    @FXML
    private Button addPays;
    @FXML
    private TableView<ProviderAccountsPays> paysTab;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabDateTime;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabAccount;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabStatue;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabCheekNumber;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabDateOfCash;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabDateOfDoc;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabPriceType;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabPayType;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabAmount;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabInvoice;
    @FXML
    private TableColumn<ProviderAccountsPays, String> paysTabId;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private Label id;
    @FXML
    private TextField cheekNumber;
    @FXML
    private JFXDatePicker dateOfDoc;
    @FXML
    private JFXComboBox<String> cashType;
    @FXML
    private JFXDatePicker dateOfCash;
    @FXML
    private JFXComboBox<Accounts> accNum;
    @FXML
    private TextField amount;
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
    @FXML
    private TextField payType;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Preferences prefs;
    @FXML
    private JFXComboBox<Provider> provider;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        dateOfCash.setConverter(new StringConverter<LocalDate>() {

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
        dateOfDoc.setConverter(new StringConverter<LocalDate>() {

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

                                    intialColumn();
                                    fillCombo();
                                    clear();

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

                super.succeeded();
            }
        };
        service.start();
        paysTab.setOnMouseClicked((e) -> {
            if (paysTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                btnNew.setDisable(false);

                btnDelete.setDisable(false);

                btnEdite.setDisable(false);

                btnAdd.setDisable(true);

                ProviderAccountsPays selected = paysTab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                cheekNumber.setText(selected.getCheekNumber());

                dateOfDoc.setValue(LocalDate.parse(selected.getDateOfDoc()));

                payType.setText(selected.getPayType());

                dateOfCash.setValue(LocalDate.parse(selected.getDateOfCash()));

                amount.setText(selected.getAmount());

                cashType.getSelectionModel().select(selected.getCashType());
                accNum.getSelectionModel().clearSelection();
                ObservableList<Accounts> items1 = accNum.getItems();
                for (Accounts a : items1) {
                    if (a.getId() == selected.getAccId()) {
                        accNum.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        paysTabDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        paysTabAccount.setCellValueFactory(new PropertyValueFactory<>("accId"));

        paysTabStatue.setCellValueFactory(new PropertyValueFactory<>("statue"));

        paysTabCheekNumber.setCellValueFactory(new PropertyValueFactory<>("cheekNumber"));

        paysTabDateOfCash.setCellValueFactory(new PropertyValueFactory<>("dateOfCash"));

        paysTabDateOfDoc.setCellValueFactory(new PropertyValueFactory<>("dateOfDoc"));

        paysTabPriceType.setCellValueFactory(new PropertyValueFactory<>("cashType"));

        paysTabPayType.setCellValueFactory(new PropertyValueFactory<>("payType"));

        paysTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        paysTabInvoice.setCellValueFactory(new PropertyValueFactory<>("providerAccId"));

        paysTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        invoicesTabDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        invoicesTabUser.setCellValueFactory(new PropertyValueFactory<>("user"));

        invoicesTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        invoicesTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        invoicesTabInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));

        invoicesTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();

        btnNew.setDisable(true);

        btnDelete.setDisable(true);

        btnEdite.setDisable(true);

        btnAdd.setDisable(false);

        cheekNumber.setText("");

        dateOfDoc.setValue(null);

        payType.setText("");

        dateOfCash.setValue(null);

        accNum.getSelectionModel().clearSelection();
        accNum.getEditor().setText("");

        amount.setText("");

        cashType.getSelectionModel().clearSelection();

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
                            autoNum = ProviderAccountsPays.getAutoNum();
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
    ObservableList<Provider> data;
    ObservableList<Provider> dataSearched = FXCollections.observableArrayList();
    ObservableList<Accounts> accouns;

    private void fillCombo() {

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Provider.getData();
                            accouns = Accounts.getData();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                provider.setItems(data);
                provider.setEditable(true);
                provider.setOnKeyReleased((event) -> {

                    if (provider.getEditor().getText().length() == 0) {
                        provider.setItems(data);
                    } else {
                        dataSearched = FXCollections.observableArrayList();

                        for (Provider a : data) {
                            if (a.getName().contains(provider.getEditor().getText())) {
                                dataSearched.add(a);
                            }
                        }
                        provider.setItems(dataSearched);
                        provider.show();
                    }
                });
                provider.setConverter(new StringConverter<Provider>() {
                    @Override
                    public String toString(Provider patient) {
                        return patient.getOrganization();
                    }

                    @Override
                    public Provider fromString(String string) {
                        return null;
                    }
                });
                provider.setCellFactory(cell -> new ListCell<Provider>() {

                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblOrg = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(50, 50, 50),
                                new ColumnConstraints(150, 150, 150),
                                new ColumnConstraints(100, 100, 100)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblOrg, 1, 1);
                        gridPane.add(lblName, 0, 2);

                    }

                    @Override
                    protected void updateItem(Provider person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("المؤسسة: " + person.getOrganization());
                            lblName.setText("الاسم: " + person.getName());
                            setGraphic(gridPane);
                        } else {
                            setGraphic(null);
                        }
                    }
                });
                cashType.getItems().clear();
                cashType.getItems().addAll("نقدا", "شيك");
                accNum.setItems(accouns);
                accNum.setConverter(new StringConverter<Accounts>() {
                    @Override
                    public String toString(Accounts patient) {
                        return patient.getName();
                    }

                    @Override
                    public Accounts fromString(String string) {
                        return null;
                    }
                });
                accNum.setCellFactory(cell -> new ListCell<Accounts>() {

                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblOrg = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(50, 50, 50),
                                new ColumnConstraints(150, 150, 150),
                                new ColumnConstraints(100, 100, 100)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblOrg, 1, 1);
                        gridPane.add(lblName, 0, 2);

                    }

                    @Override
                    protected void updateItem(Accounts person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("الاسم: " + person.getName());
                            lblName.setText("الرصيد: " + person.getBalance());
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
    private void print(ActionEvent event) { if (provider.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المورد اولا");
        } else {
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                HashMap hash = new HashMap();
                                BufferedImage leftLogo = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                hash.put("leftLogo", leftLogo);
                                BufferedImage rightLogo = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                hash.put("rightLogo", rightLogo);
                                hash.put("providerId", Integer.toString(provider.getItems().get(provider.getSelectionModel().getSelectedIndex()).getId()));
                                hash.put("providerName", provider.getItems().get(provider.getSelectionModel().getSelectedIndex()).getOrganization());
                                hash.put("totalInvoices", totalInvoices.getText());
                                hash.put("totalPaied", totalPaied.getText());
                                hash.put("rest", rest.getText());

                                InputStream invoiceReport = getClass().getResourceAsStream("/providers/reports/InvoiceBuy.jasper");
                                JasperReport invoiceReportReport = (JasperReport) JRLoader.loadObject(invoiceReport);
                                hash.put("invoiceReport", invoiceReportReport);
 
                                InputStream invoiceReportDetails = getClass().getResourceAsStream("/providers/reports/InvoiceBuyDetails.jasper");
                                JasperReport invoiceReportDetailsReport = (JasperReport) JRLoader.loadObject(invoiceReportDetails);
                                hash.put("invoiceDetailsReport", invoiceReportDetailsReport);

                                InputStream paysReport = getClass().getResourceAsStream("/accounts/reports/ProviderAccountPays.jasper");
                                JasperReport paysReportReport = (JasperReport) JRLoader.loadObject(paysReport);
                                hash.put("paysReport", paysReportReport);

                                InputStream a = getClass().getResourceAsStream("/accounts/reports/ProviderAccountStatement.jrxml");
                                JasperDesign design = JRXmlLoader.load(a);
                                JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                JasperViewer.viewReport(jasperprint, false);

                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            }
                            return null;
                        }
                    };

                }

                @Override
                protected void succeeded() {

                    super.succeeded();
                }
            };
            service.start();
        }
    }
    int ProviderId;
    ObservableList<ProviderAccounts> accData;
    ObservableList<ProviderAccountsPays> paysData;

    @FXML
    private void setData(ActionEvent event) {
        
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            ProviderId = provider.getItems().get(provider.getSelectionModel().getSelectedIndex()).getId();
                            accData = ProviderAccounts.getData(ProviderId);
                            paysData = ProviderAccountsPays.getData(ProviderId);

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                invoicesTab.setItems(accData);
                paysTab.setItems(paysData);
                totalInvoices.setText("0");
                totalPaied.setText("0");
                for (ProviderAccounts clients1 : accData) {
                    totalInvoices.setText(Double.toString(Double.parseDouble(totalInvoices.getText()) + Double.parseDouble(clients1.getAmount())));
                }
                for (ProviderAccountsPays clients1 : paysData) {
                    totalPaied.setText(Double.toString(Double.parseDouble(totalPaied.getText()) + Double.parseDouble(clients1.getAmount())));
                }
                rest.setText(Double.toString(Double.parseDouble(totalInvoices.getText()) - Double.parseDouble(totalPaied.getText())));
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void searchInvoices(KeyEvent event) {
        FilteredList<ProviderAccounts> filteredData = new FilteredList<>(accData, p -> true);

        filteredData.setPredicate(pa -> {

            if (searchInvoices.getText() == null || searchInvoices.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchInvoices.getText().toLowerCase();

            return (pa.getDate().contains(lowerCaseFilter)
                    || pa.getUser().contains(lowerCaseFilter)
                    || pa.getAmount().contains(lowerCaseFilter)
                    || Integer.toString(pa.getInvoiceId()).contains(lowerCaseFilter));

        });

        SortedList<ProviderAccounts> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(invoicesTab.comparatorProperty());
        invoicesTab.setItems(sortedData);
    }

    @FXML
    private void searchPays(KeyEvent event) {
        FilteredList<ProviderAccountsPays> filteredData = new FilteredList<>(paysData, p -> true);

        filteredData.setPredicate(pa -> {

            if (searchPays.getText() == null || searchPays.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchPays.getText().toLowerCase();

            return (pa.getDateOfCash().contains(lowerCaseFilter)
                    || pa.getDateOfDoc().contains(lowerCaseFilter)
                    || pa.getUser().contains(lowerCaseFilter)
                    || pa.getAmount().contains(lowerCaseFilter)
                    || Integer.toString(pa.getAccId()).contains(lowerCaseFilter)
                    || pa.getStatue().contains(lowerCaseFilter)
                    || pa.getPayType().contains(lowerCaseFilter)
                    || pa.getCashType().contains(lowerCaseFilter));

        });

        SortedList<ProviderAccountsPays> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(paysTab.comparatorProperty());
        paysTab.setItems(sortedData);
    }

    @FXML
    private void addPays(ActionEvent events) {
        double drawerx = drawer.getLayoutX();
        double drawery = drawer.getLayoutY();
        drawer.setLayoutX(drawerx + 923);
        drawer.setLayoutY(drawery);
        drawer.setVisible(false);
        drawer.setMaxWidth(0);

        drawer.setOnDrawerOpening(event
                -> {
            drawer.setLayoutX(drawerx);
            drawer.setLayoutY(drawery);
            drawer.setMaxWidth(923);
        });

        drawer.setOnDrawerClosed(event
                -> {
            drawer.setLayoutX(drawerx + 250);
            drawer.setLayoutY(drawery);
            drawer.setVisible(false);
            drawer.setMaxWidth(0);
        });
        drawer.setVisible(true);
        if (drawer.isOpened()) {
            drawer.close();
        } else {
            drawer.open();
        }
    }

    @FXML
    private void New(ActionEvent event) {  clear();
    }

    @FXML
    private void Delete(ActionEvent event) {progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ProviderAccountsPays cl;
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
                                    alert.setTitle("Deleting Pays");
                                    alert.setHeaderText("سيتم حذف الدفعة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        cl = new ProviderAccountsPays();
                                        cl.setId(Integer.parseInt(id.getText()));
                                        cl.Delete();
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
                    setData(null);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void Edite(ActionEvent event) { if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                ProviderAccountsPays cl;
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
                                        alert.setTitle("Editing Pays");
                                        alert.setHeaderText("سيتم حذف  الدفعة ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            cl = new ProviderAccountsPays();
                                            cl.setId(Integer.parseInt(id.getText()));
                                            cl.setAmount(amount.getText());
                                            cl.setDateOfCash(dateOfCash.getValue().format(format));
                                            cl.setDateOfDoc(dateOfDoc.getValue().format(format));
                                            cl.setCashType(cashType.getSelectionModel().getSelectedItem());
                                            cl.setStatue(cashType.getSelectionModel().getSelectedItem().equals("نقدا") ? "cashed" : "pendding");
                                            cl.setCheekNumber(cheekNumber.getText());
                                            cl.setAccId(accNum.getSelectionModel().getSelectedItem().getId());
                                            cl.setProviderAccId(invoicesTab.getSelectionModel().getSelectedItem().getId());
                                            cl.setPayType(payType.getText());
                                            cl.setUserId(Integer.parseInt(prefs.get(USER_ID, "0")));
                                            cl.Edite();
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
                        setData(null);
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
                ProviderAccountsPays cl;
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
                                        cl = new ProviderAccountsPays();
                                        cl.setAmount(amount.getText());
                                        cl.setDateOfCash(dateOfCash.getValue().format(format));
                                        cl.setDateOfDoc(dateOfDoc.getValue().format(format));
                                        cl.setCashType(cashType.getSelectionModel().getSelectedItem());
                                        cl.setStatue(cashType.getSelectionModel().getSelectedItem().equals("نقدا") ? "cashed" : "pendding");
                                        cl.setCheekNumber(cheekNumber.getText());
                                        cl.setAccId(accNum.getSelectionModel().getSelectedItem().getId());
                                        cl.setProviderAccId(invoicesTab.getSelectionModel().getSelectedItem().getId());
                                        cl.setPayType(payType.getText());
                                        cl.setUserId(Integer.parseInt(prefs.get(USER_ID, "0")));
                                        cl.Add();
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
                        setData(null);
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    private boolean Validate() {
        if (amount.getText().isEmpty() || amount.getText().length() == 0) {
            AlertDialogs.validateError("المبلغ لا يجب ان يكون فارغ");
            return false;
        }

        if (dateOfCash.getValue() == null) {
            AlertDialogs.validateError(" تاريخ التحصيل لا يجب ان يكون فارغ");
            return false;
        }
        if (dateOfDoc.getValue() == null) {
            AlertDialogs.validateError(" تاريخ الدفع لا يجب ان يكون فارغ");
            return false;
        }
        if (cashType.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.validateError("طريقة الدفع لا يجب ان يكون فارغ");
            return false;
        }
        if (accNum.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.validateError("رقم الحساب لا يجب ان يكون فارغ");
            return false;
        }
        if (invoicesTab.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.validateError("اختار الحساب اولا من الجدول");
            return false;
        }
        if (payType.getText().isEmpty() || payType.getText().length() == 0) {
            AlertDialogs.validateError("طريقه التحصيل لا يجب ان يكون فارغ");
            return false;
        }
        return true;
    }
}
