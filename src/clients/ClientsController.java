/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import assets.classes.AlertDialogs;
import clients.assets.Clients;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ClientsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Clients> tab;
    @FXML
    private TableColumn<Clients, String> tabTele2;
    @FXML
    private TableColumn<Clients, String> tabTele1;
    @FXML
    private TableColumn<Clients, String> tabOrg;
    @FXML
    private TableColumn<Clients, String> tabName;
    @FXML
    private TableColumn<Clients, String> tabId;
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
    private TextField acc;
    @FXML
    private TextField tele1;
    @FXML
    private TextField tele2;
    @FXML
    private TextField tax;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                btnNew.setDisable(false);

                btnDelete.setDisable(false);

                btnEdite.setDisable(false);

                btnAdd.setDisable(true);

                Clients selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                name.setText(selected.getName());
                organization.setText(selected.getOrganization());

                adress.setText(selected.getAddress());
                email.setText(selected.getEmail());
                tax.setText(selected.getTaxNumber());
                acc.setText(selected.getAccountNumber());
                tele1.setText(selected.getTele1());
                tele2.setText(selected.getTele2());

            }
        });
    }

    private void intialColumn() {
        tabTele2.setCellValueFactory(new PropertyValueFactory<>("tele2"));

        tabTele1.setCellValueFactory(new PropertyValueFactory<>("tele1"));

        tabOrg.setCellValueFactory(new PropertyValueFactory<>("organization"));

        tabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();
        btnNew.setDisable(true);

        btnDelete.setDisable(true);

        btnEdite.setDisable(true);

        btnAdd.setDisable(false);

        organization.setText("");
        name.setText("");
        adress.setText("");
        email.setText("");
        tax.setText("");
        acc.setText("");
        tele1.setText("");
        tele2.setText("");
    }

    private void getAutoNum() {
        progress.setVisible(true);
        Service<Void> service;
        service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            autoNum = Clients.getAutoNum();
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
            ObservableList<Clients> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = Clients.getData();

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
                tab.setItems(data);
                items = data;
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Clients> items;

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Clients> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getOrganization().contains(lowerCaseFilter)
                    || pa.getTaxNumber().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter));

        });

        SortedList<Clients> sortedData = new SortedList<>(filteredData);
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
            Clients cl;
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
                                    alert.setTitle("Deleting Client ");
                                    alert.setHeaderText("سيتم  حذف العميل ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        cl = new Clients();
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
                    getData();
                }
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void Edite(ActionEvent event) {
        if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                Clients cl;
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
                                        alert.setTitle("Editing Client ");
                                        alert.setHeaderText("سيتم  تعديل العميل ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            cl = new Clients();
                                            cl.setId(Integer.parseInt(id.getText()));
                                            cl.setName(name.getText());
                                            cl.setOrganization(organization.getText());
                                            cl.setTaxNumber(tax.getText());
                                            cl.setAddress(adress.getText());
                                            cl.setEmail(email.getText());
                                            cl.setAccountNumber(acc.getText());
                                            cl.setTele1(tele1.getText());
                                            cl.setTele2(tele2.getText());
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
                        getData();
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
                Clients cl;
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
                                        cl = new Clients();
                                        cl.setName(name.getText());
                                        cl.setOrganization(organization.getText());
                                        cl.setTaxNumber(tax.getText());
                                        cl.setAddress(adress.getText());
                                        cl.setEmail(email.getText());
                                        cl.setAccountNumber(acc.getText());
                                        cl.setTele1(tele1.getText());
                                        cl.setTele2(tele2.getText());
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
                        getData();
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    private boolean Validate() {
        if (organization.getText() == null || organization.getText().length() == 0) {
            AlertDialogs.validateError("اسم المؤسسة لا يجب ان يكون فارغ");
            return false;
        }
        if ( tax.getText().length() != 0) {
            Pattern p = Pattern.compile("[0-9]{3}-[0-9]{3}-[0-9]{3}");
            Matcher m = p.matcher(tax.getText());
            boolean b = m.matches();
            if (b) {
                return true;
            } else {
                AlertDialogs.validateError("الرقم الضريبي يكون على هذا الشكل   {000-000-000}");
                return false;
            }

        }
        return true;
    }

}
