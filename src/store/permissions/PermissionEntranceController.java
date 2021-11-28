/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.permissions;

import assets.classes.AlertDialogs;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import providers.assets.InvoiceBuy;
import providers.assets.InvoiceBuyDetails;
import store.assets.Stores;
import store.permissions.assets.StoreOperations;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class PermissionEntranceController implements Initializable {
    
    @FXML
    private TableView<InvoiceBuy> invoiceTable;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabNotes;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabWithTax;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabTotalCost;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabPayType;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabDisc;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabCost;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabDate;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabComp;
    @FXML
    private TableColumn<InvoiceBuy, String> invoiceTabId;
    @FXML
    private TableView<InvoiceBuyDetails> invoiceDetailsTable;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> invoiceDetailsTabCostOfSell;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> invoiceDetailsTabCost;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> invoiceDetailsTabAmount;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> invoiceDetailsTabMediccine;
    @FXML
    private TableColumn<InvoiceBuyDetails, String> invoiceDetailsTabId;
    @FXML
    private ComboBox<Stores> stores;
    @FXML
    private Button save;
    @FXML
    private ProgressIndicator progress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                                    
                                    initColumn();
                                    
                                    filCombo();
                                    getData();
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
        invoiceTable.setOnMouseClicked((e) -> {
            if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
                invoiceDetailsTable.setItems(null);
                invoiceTable.getSelectionModel().clearSelection();
            } else {
                getInvoiceDetails(invoiceTable.getSelectionModel().getSelectedItem().getId());
            }
        });
    }
    
    private void filCombo() throws Exception {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<Stores> data;
            
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Stores.getData();
                            
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
                stores.setItems(data);
                stores.setConverter(new StringConverter<Stores>() {
                    @Override
                    public String toString(Stores st) {
                        return st.getName();
                    }
                    
                    @Override
                    public Stores fromString(String string) {
                        return null;
                    }
                });
                stores.setCellFactory(cell -> new ListCell<Stores>() {
                    
                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    
                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(250, 250, 250)
                        );
                        gridPane.add(lblid, 0, 1);
                        
                    }
                    
                    @Override
                    protected void updateItem(Stores person, boolean empty) {
                        super.updateItem(person, empty);
                        
                        if (!empty && person != null) {
                            lblid.setText("الاسم: " + person.getName());
                            
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
    
    private void getData() throws Exception {
        invoiceDetailsTable.setItems(null);
        invoiceTable.getSelectionModel().clearSelection();
        invoiceTable.setItems(InvoiceBuy.getData());
    }
    
    private void initColumn() {
        invoiceTabNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        invoiceTabWithTax.setCellValueFactory(new PropertyValueFactory<>("hasTaxs"));
        
        invoiceTabTotalCost.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        invoiceTabPayType.setCellValueFactory(new PropertyValueFactory<>("payType"));
        
        invoiceTabDisc.setCellValueFactory(new PropertyValueFactory<>("dicount"));
        
        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        
        invoiceTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        invoiceTabComp.setCellValueFactory(new PropertyValueFactory<>("provider"));
        
        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        invoiceDetailsTabCostOfSell.setCellValueFactory(new PropertyValueFactory<>("costOfSell"));
        
        invoiceDetailsTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        
        invoiceDetailsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        
        invoiceDetailsTabMediccine.setCellValueFactory(new PropertyValueFactory<>("product"));
        
        invoiceDetailsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
    }
    
    private void getInvoiceDetails(int id) {
        try {
            invoiceDetailsTable.setItems(InvoiceBuyDetails.getDataForEntance(id));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private void save(ActionEvent event) {
        if (Validate()) {
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
                                        StoreOperations a = new StoreOperations();
                                        a.setDetails(invoiceDetailsTable.getItems());  
                                        a.setStoreId(stores.getSelectionModel().getSelectedItem().getId());
                                        a.setInvoicecId(invoiceTable.getSelectionModel().getSelectedItem().getId());
                                        a.setDate(LocalDate.now().format(format)); 
                                        a.EntrancePermission();

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
                    try {
                        getData();
                    } catch (Exception ex) {
                        AlertDialogs.showErrors(ex);
                    }
                    super.succeeded();
                }
            };
            service.start();
            
        }
    }
    
    private boolean Validate() {
        if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الفاتورة اولا");
            return false;
        } else if (stores.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المخزن");
            return false;
        }
        return true;
    }
}
