/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;
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
import org.controlsfx.control.textfield.TextFields;
import store.assets.Products;
import store.assets.Units;
import systems.Category;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreProductsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Products> tab;
    @FXML
    private TableColumn<Products, String> tabCat;
    @FXML
    private TableColumn<Products, String> tabUnit;
    @FXML
    private TableColumn<Products, String> tabModel;
    @FXML
    private TableColumn<Products, String> tabName;
    @FXML
    private TableColumn<Products, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField name;
    @FXML
    private TextField barcode;
    @FXML
    private TextField model;
    @FXML
    private TextField details;
    @FXML
    private TextField quantity;
    @FXML
    private JFXComboBox<Category> cats;
    @FXML
    private JFXComboBox<Units> units;
    @FXML
    private JFXDatePicker expDate;
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
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private TextField warnNum;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        expDate.setConverter(new StringConverter<LocalDate>() {

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

                Products selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                name.setText(selected.getName());
                barcode.setText(selected.getBarcode());
                model.setText(selected.getModel());
                details.setText(selected.getDetails());
                quantity.setText(selected.getQuantityPerUnit());
                expDate.setValue(selected.getExpDate() == null ? null : LocalDate.parse(selected.getExpDate()));
                warnNum.setText(selected.getWarnNum());
                ObservableList<Units> items1 = units.getItems();
                for (Units a : items1) {
                    if (a.getName().equals(selected.getUnitName())) {
                        units.getSelectionModel().select(a);
                    }
                }
                ObservableList<Category> items = cats.getItems();
                for (Category a : items) {
                    if (a.getName().equals(selected.getCatName())) {
                        cats.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tabCat.setCellValueFactory(new PropertyValueFactory<>("catName"));

        tabUnit.setCellValueFactory(new PropertyValueFactory<>("unitName"));

        tabModel.setCellValueFactory(new PropertyValueFactory<>("model"));

        tabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();
        btnNew.setDisable(true);

        btnDelete.setDisable(true);

        btnEdite.setDisable(true);

        btnAdd.setDisable(false);
        id.setText("");
        name.setText("");
        barcode.setText("");
        model.setText("");
        details.setText("");
        quantity.setText("");
        warnNum.setText("");
        expDate.setValue(null);
        units.getSelectionModel().clearSelection();
        cats.getSelectionModel().clearSelection();
        units.getEditor().setText("");
        cats.getEditor().setText("");
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
                            autoNum = Products.getAutoNum();

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
            ObservableList<Products> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

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
                progress.setVisible(false);
                tab.setItems(data);
                items = data;
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Products> items;
    ObservableList<Category> catsData;
    ObservableList<Units> unitsData;
    ObservableList<Units> searched = FXCollections.observableArrayList();
    ObservableList<Category> catsDataSearched = FXCollections.observableArrayList();

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
                            unitsData = Units.getData();

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

                units.setItems(unitsData);
                units.setEditable(true);
                units.setOnKeyReleased((event) -> {

                    if (units.getEditor().getText().length() == 0) {
                        units.setItems(unitsData);
                    } else {
                        searched = FXCollections.observableArrayList();

                        for (Units a : unitsData) {
                            if (a.getName().contains(units.getEditor().getText())) {
                                searched.add(a);
                            }
                        }
                        units.setItems(searched);
                        units.show();
                    }
                });
                units.setConverter(new StringConverter<Units>() {

                    @Override
                    public String toString(Units object) {
                        return object.getName();
                    }

                    @Override
                    public Units fromString(String string) {

                        return null;
                    }
                });
                units.setCellFactory(cell -> new ListCell<Units>() {

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
                    protected void updateItem(Units person, boolean empty) {
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
                        return object.getName();
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
        FilteredList<Products> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getBarcode().contains(lowerCaseFilter)
                    || pa.getModel().contains(lowerCaseFilter)
                    || pa.getUnitName().contains(lowerCaseFilter));

        });

        SortedList<Products> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);
    }

    @FXML
    private void generateRandom(MouseEvent event) {
        Random rand = new Random();

        long x = (long) (rand.nextDouble() * 100000000000000L);

        String s = String.valueOf("0") + String.format("%014d", x);
        barcode.setText(Long.toString(Long.valueOf(s)));
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
    private void addNewUnit(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Unit Name");
        dialog.setHeaderText("اضافة وحدة جديد");

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
                                            Units.Add(results);
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
        if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                Products pr;
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
                                        alert.setTitle("Deleting Product  ");
                                        alert.setHeaderText("سيتم حذف  المنتج ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            pr = new Products();
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
    }

    @FXML
    private void Edite(ActionEvent event) {
        if (Validate()) {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                Products pr;
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
                                        alert.setTitle("Editing Product  ");
                                        alert.setHeaderText("سيتم تعديل  المنتج ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            pr = new Products();
                                            pr.setId(Integer.parseInt(id.getText()));
                                            pr.setName(name.getText());
                                            pr.setBarcode(barcode.getText());
                                            pr.setModel(model.getText());
                                            pr.setDetails(details.getText());
                                            pr.setUnitId(units.getItems().get(units.getSelectionModel().getSelectedIndex()).getId());
                                            pr.setQuantityPerUnit(quantity.getText());
                                            pr.setExpDate(expDate.getValue() == null ? null : expDate.getValue().format(format));
                                            pr.setCatId(cats.getItems().get(cats.getSelectionModel().getSelectedIndex()).getId());
                                            pr.setWarnNum(warnNum.getText());
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
                Products pr;
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
                                        pr = new Products();
                                        pr.setName(name.getText());
                                        pr.setBarcode(barcode.getText());
                                        pr.setModel(model.getText());
                                        pr.setDetails(details.getText());
                                        pr.setUnitId(units.getItems().get(units.getSelectionModel().getSelectedIndex()).getId());
                                        pr.setQuantityPerUnit(quantity.getText());
                                        pr.setExpDate(expDate.getValue() == null ? null : expDate.getValue().format(format));
                                        pr.setCatId(cats.getItems().get(cats.getSelectionModel().getSelectedIndex()).getId());
                                        pr.setWarnNum(warnNum.getText());
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
        if (name.getText().isEmpty() || name.getText().length() == 0) {
            AlertDialogs.validateError("اسم المنتج لا يجب ان يكون فارغ");
            return false;
        }

        if (barcode.getText().isEmpty() || barcode.getText().length() == 0) {
            AlertDialogs.validateError(" الباركود لا يجب ان يكون فارغ");
            return false;
        }
        if (units.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.validateError("الوحدة لا يجب ان يكون فارغ");
            return false;
        }
        if (quantity.getText().isEmpty() || quantity.getText().length() == 0) {
            AlertDialogs.validateError("الكمية بالوحدة لا يجب ان يكون فارغ");
            return false;
        }
        if (cats.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.validateError("  التصنيف لا يجب ان يكون فارغ");
            return false;
        }
        if (warnNum.getText().isEmpty() || warnNum.getText().length() == 0) {
            AlertDialogs.validateError("اقل كمية للتحذير لا يجب ان يكون فارغ");
            return false;
        }
        return true;
    }

}
