/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers;

import assets.classes.AlertDialogs;
import clients.assets.Clients;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import providers.assets.Provider;
import systems.Category;
 

public class ProvidersController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Provider> tab;
    @FXML
    private TableColumn<Provider, String> tabTele2;
    @FXML
    private TableColumn<Provider, String> tabTele1;
    @FXML
    private TableColumn<Provider, String> tabOrg;
    @FXML
    private TableColumn<Provider, String> tabName;
    @FXML
    private TableColumn<Provider, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField organization;
    @FXML
    private TextField name;
    @FXML
    private TextField adress;
    @FXML
    private TextField acc;
    @FXML
    private TextField tax;
    @FXML
    private TextField tele1;
    @FXML
    private TextField tele2;
    @FXML
    private JFXComboBox<Category> cats;
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
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    clear();
                                    intialColumn();
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
        tab.setOnMouseClicked((e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                btnNew.setDisable(false);

                btnDelete.setDisable(false);

                btnEdite.setDisable(false);

                btnAdd.setDisable(true);

                Provider selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                name.setText(selected.getName());
                organization.setText(selected.getOrganization());
                adress.setText(selected.getAddress());
                tax.setText(selected.getTaxNumber());
                acc.setText(selected.getAccountNumber());
                tele1.setText(selected.getTele1());
                tele2.setText(selected.getTele2());

                ObservableList<Category> items1 = cats.getItems();
                for (Category a : items1) {
                    if (a.getName().equals(selected.getCatName())) {
                        cats.getSelectionModel().select(a);
                    }
                }

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
        cats.getSelectionModel().clearSelection();
        tax.setText("");
        acc.setText("");
        tele1.setText("");
        tele2.setText("");
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
                            autoNum = Provider.getAutoNum();

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
            ObservableList<Provider> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            data = Provider.getData();
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
    ObservableList<Provider> items;
  ObservableList<Category> catsData;ObservableList<Category>  catsDataSearched = FXCollections.observableArrayList();
    private void fillCombo() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
          

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            catsData = Category.getData();
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
                  cats.setItems(catsData);
                cats.setEditable(true);
                cats.setOnKeyReleased((event) -> {

                    if (cats.getEditor().getText().length() == 0) {
                        cats.setItems(catsData);
                    } else {
                        catsDataSearched = FXCollections.observableArrayList();

                        for (Category a : catsData) {
                            if (a.getName().contains(cats.getEditor().getText())) {
                                catsDataSearched.add(a);
                            }
                        }
                        cats.setItems(catsDataSearched);
                        cats.show();
                    }
                });
                cats.setConverter(new StringConverter<Category>() {
                    @Override
                    public String toString(Category object) {
                        return object.getName() ;
                    }

                    @Override
                    public Category fromString(String string) {
                        return null;
                    }
                });
                cats.setCellFactory(cell -> new ListCell<Category>() {

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
                    protected void updateItem(Category person, boolean empty) {
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
    private void search(KeyEvent event) {
        FilteredList<Provider> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getCatName().contains(lowerCaseFilter)
                    || pa.getOrganization().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter));

        });

        SortedList<Provider> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);

    }

    @FXML
    private void addNewCat(MouseEvent event) {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Cat Name");
        dialog.setHeaderText("اضافة تصنيف جديد");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم نوع");
            } else {
                final String results = result.get();
                try {
                    Service service = new Service() {
                        @Override
                        protected Task createTask() {
                            return new Task() {
                                @Override
                                protected Object call() throws Exception {
                                    final CountDownLatch latch = new CountDownLatch(1);
                                    Platform.runLater(() -> {
                                        try {
                                            Category.Add(results);
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        } finally {
                                            latch.countDown();
                                        }
                                    });
                                    latch.await();

                                    return null;
                                }

                                @Override
                                protected void succeeded() {
                                    try {
                                        fillCombo();
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
                                }
                            };
                        }
                    };
                    service.start();

                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        }
    }

    @FXML
    private void New(ActionEvent event) {
        clear();
    }

    @FXML
    private void Delete(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            Provider pr;
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
                                    alert.setTitle("Deleting  Provider");
                                    alert.setHeaderText("سيتم حذف المورد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        pr = new Provider();
                                        pr.setId(Integer.parseInt(id.getText())); 
                                        pr.Delete(); 
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
                Provider pr;
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
                                        alert.setTitle("Editing  Provider");
                                        alert.setHeaderText("سيتم تعديل المورد ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            pr = new Provider();
                                            pr.setId(Integer.parseInt(id.getText()));
                                            pr.setName(name.getText());
                                            pr.setOrganization(organization.getText());
                                            pr.setAddress(adress.getText());
                                            pr.setAccountNumber(acc.getText());
                                            pr.setTaxNumber(tax.getText());
                                            pr.setTele1(tele1.getText());
                                            pr.setTele2(tele2.getText());
                                            pr.setCatId(cats.getItems().get(cats.getSelectionModel().getSelectedIndex()).getId());
                                            pr.Edite();

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
                Provider pr;
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
                                        pr = new Provider();
                                        pr.setName(name.getText());
                                        pr.setOrganization(organization.getText());
                                        pr.setAddress(adress.getText());
                                        pr.setAccountNumber(acc.getText());
                                        pr.setTaxNumber(tax.getText());
                                        pr.setTele1(tele1.getText());
                                        pr.setTele2(tele2.getText());
                                        pr.setCatId(cats.getItems().get(cats.getSelectionModel().getSelectedIndex()).getId());
                                        pr.Add();

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
        if (cats.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.validateError("يجب اختيار تصنيف");
            return false;
        }
        if (tax.getText().length() != 0) {
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
