/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sales.assets.Offers;
import sales.assets.OffersConditions;
import sales.assets.OffersDetails;
import sales.assets.SalesClient;
import sales.assets.SalesMembers;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenOffersShowController implements Initializable {

    @FXML
    private ComboBox<SalesClient> client;
    @FXML
    private CheckBox withClient;
    @FXML
    private JFXDatePicker dateFrom;
    @FXML
    private CheckBox withDateFrom;
    @FXML
    private JFXDatePicker dateTo;
    @FXML
    private CheckBox withDateTo;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button showInvoices;
    @FXML
    private Button print;
    @FXML
    private ComboBox<SalesMembers> sales;
    @FXML
    private CheckBox withSales;
    @FXML
    private TableView<Offers> invoiceTable;
    @FXML
    private TableColumn<Offers, String> invoiceTabNotes;
    @FXML
    private TableColumn<Offers, String> invoiceTabTotalCost;
    @FXML
    private TableColumn<Offers, String> invoiceTabDiscPerc;
    @FXML
    private TableColumn<Offers, String> invoiceTabDisc;
    @FXML
    private TableColumn<Offers, String> invoiceTabCost;
    @FXML
    private TableColumn<Offers, String> invoiceTabDate;
    @FXML
    private TableColumn<Offers, String> invoiceTabSales;
    @FXML
    private TableColumn<Offers, String> invoiceTabClient;
    @FXML
    private TableColumn<Offers, String> invoiceTabId;
    @FXML
    private TableView<OffersDetails> invoiceTable1;
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

    String repName = " ";
    String sql = "SELECT `sl_offers`.`id`,`sl_client`.`name`, `sl_offers`.`date`, `sl_offers`.`cost`, `sl_offers`.`discount`, `sl_offers`.`discount_percent`, `sl_offers`.`total_cost`,`sl_sales_members`.`name`, `sl_offers`.`notes` FROM `sl_offers`,`sl_client`,`sl_sales_members` WHERE `sl_client`.`id` = `sl_offers`.`client_id` and `sl_offers`. `sales_id`= `sl_sales_members`.`id`";
    @FXML
    private TableColumn<OffersDetails, String> tabTotalCost;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateFrom.setConverter(new StringConverter<LocalDate>() {
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
        dateTo.setConverter(new StringConverter<LocalDate>() {
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

                                    fillCombo();
                                    intialColumn();
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

    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<SalesMembers> salesData;
            ObservableList<SalesClient> clientData;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            clientData = SalesClient.getData();
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
                    protected void updateItem(SalesClient person, boolean empty) {
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

    private void intialColumn() {
        invoiceTabNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        invoiceTabTotalCost.setCellValueFactory(new PropertyValueFactory<>("total_cost"));

        invoiceTabDiscPerc.setCellValueFactory(new PropertyValueFactory<>("discount_percent"));

        invoiceTabDisc.setCellValueFactory(new PropertyValueFactory<>("dicount"));

        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        invoiceTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        invoiceTabSales.setCellValueFactory(new PropertyValueFactory<>("sales"));

        invoiceTabClient.setCellValueFactory(new PropertyValueFactory<>("client"));

        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tabTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCostString"));

        tabCost.setCellValueFactory(new PropertyValueFactory<>("costString"));

        tabAmount.setCellValueFactory(new PropertyValueFactory<>("amountString"));

        tabProduct.setCellValueFactory(new PropertyValueFactory<>("product"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        CondTabValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        CondTabAttribute.setCellValueFactory(new PropertyValueFactory<>("attribute"));

        CondTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    @FXML
    private void showInvoices(ActionEvent event) {

        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Offers> cutomData;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            sql = "SELECT `sl_offers`.`id`,`sl_client`.`name`, `sl_offers`.`date`, `sl_offers`.`cost`, `sl_offers`.`discount`, `sl_offers`.`discount_percent`, `sl_offers`.`total_cost`,`sl_sales_members`.`name`, `sl_offers`.`notes` FROM `sl_offers`,`sl_client`,`sl_sales_members` WHERE `sl_client`.`id` = `sl_offers`.`client_id` and `sl_offers`. `sales_id`= `sl_sales_members`.`id`";
                            repName = "";
                            if (withClient.isSelected()) {
                                int compId = client.getSelectionModel().getSelectedItem().getId();
                                sql += " AND `sl_offers`.`client_id`='" + compId + "'";
                                //            repName += "الخاصة بشركة: " + client.getSelectionModel().getSelectedItem().getName() + " ";
                            }
                            if (withSales.isSelected()) {
                                int salesId = sales.getSelectionModel().getSelectedItem().getId();
                                sql += " AND `sl_offers`.`sales_id`='" + salesId + "'";
                                //            repName += "الخاصة بشركة: " + sales.getSelectionModel().getSelectedItem().getName() + " ";
                            }
                            if (withDateFrom.isSelected()) {
                                String dateFr = dateFrom.getValue().format(format);
                                sql += " AND `sl_offers`.`date` >= '" + dateFr + "'";
                                //            repName += " للفترة من : " + this.dateFrom.getValue().format(format);
                            }
                            if (withDateTo.isSelected()) {
                                String dateT = dateTo.getValue().format(format);
                                sql += " AND `sl_offers`.`date` <= '" + dateT + "'";
                                //            repName += " للفترة الي: " + this.dateTo.getValue().format(format);
                            }
                            cutomData = Offers.getCutomData(sql);

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
                invoiceTable.setItems(null);
                invoiceTable.setItems(cutomData);
                invoiceTable1.setItems(null);
                CondTab.setItems(null);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void printInvoices(ActionEvent event) {
    }

    @FXML
    private void getInvoiceData(MouseEvent event) {
        if (invoiceTable.getSelectionModel().getSelectedIndex() != -1) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                ObservableList<OffersDetails> details;
                ObservableList<OffersConditions> conditions;

                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                int id = invoiceTable.getSelectionModel().getSelectedItem().getId();
                                details = OffersDetails.getData(id);
                                conditions = OffersConditions.getData(id);
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
                    invoiceTable1.setItems(details);
                    CondTab.setItems(conditions);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

}
