/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.permissions;

import assets.classes.AlertDialogs;
import clients.assets.InvoiceSell;
import clients.assets.InvoiceSellDetails;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import store.permissions.assets.StoreOperations;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class PermissionExitController implements Initializable {

    @FXML
    private TableView<InvoiceSell> invoiceTable;
    @FXML
    private TableColumn<InvoiceSell,String> invoiceTabNotes;
    @FXML
    private TableColumn<InvoiceSell,String> invoiceTabUser;
    @FXML
    private TableColumn<InvoiceSell,String> invoiceTabTotal;
    @FXML
    private TableColumn<InvoiceSell,String> invoiceTabDate; 
    @FXML
    private TableColumn<InvoiceSell,String> invoiceTabId;
    @FXML
    private TableView<InvoiceSellDetails> invoiceDetailsTable;
    @FXML
    private TableColumn<InvoiceSellDetails,String> invoiceDetailsTabProduct;
    @FXML
    private TableColumn<InvoiceSellDetails,String> invoiceDetailsTabId;
    @FXML
    private Button save;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TableColumn<InvoiceSellDetails,String> invoiceDetailsTabAmount;
    @FXML
    private TableColumn<InvoiceSell,String> invoiceTabClient;

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
  
 private void getData() throws Exception {
        invoiceDetailsTable.setItems(null);
        invoiceTable.getSelectionModel().clearSelection();
        invoiceTable.setItems(InvoiceSell.getData());
    }
    private void initColumn() {
        invoiceTabNotes.setCellValueFactory(new PropertyValueFactory<>("notes")); 
        
        invoiceTabUser.setCellValueFactory(new PropertyValueFactory<>("userId"));
        
        invoiceTabTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        invoiceTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        invoiceTabClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        
        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
         
        invoiceDetailsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        
        invoiceDetailsTabProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        
        invoiceDetailsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
    }
     private void getInvoiceDetails(int id) {
        try {
            invoiceDetailsTable.setItems(InvoiceSellDetails.getDataForExite(id));
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
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                try {
                                    StoreOperations a = new StoreOperations();
                                    a.setInvoicecId(invoiceTable.getSelectionModel().getSelectedItem().getId());
                                    a.setDate(LocalDate.now().format(format));
                                    a.setNotes(invoiceTable.getSelectionModel().getSelectedItem().getNotes());
                                    a.ExitePermission();
                                    
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
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
        }  
        return true;
    }
}
