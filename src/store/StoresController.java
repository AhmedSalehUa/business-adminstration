/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import assets.classes.AlertDialogs;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import store.assets.ProductItemCell;
import store.assets.StoreItemCell;
import store.assets.StoreProducts;
import store.assets.Stores;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoresController implements Initializable {

    @FXML
    private ListView<Stores> listView;
    @FXML
    private Label id;
    @FXML
    private TextField name;
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

    ObservableList<Stores> data;
    @FXML
    private AnchorPane container;
    @FXML
    private ListView<StoreProducts> listView1;

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
                            autoNum = Stores.getAutoNum();

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

    private void clear() {
        getAutoNum();
        btnNew.setDisable(true);

        btnDelete.setDisable(true);

        btnEdite.setDisable(true);

        btnAdd.setDisable(false);
        name.setText("");
    }

    private void getData() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
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
                listView.getItems().clear();
                listView.setItems(data);
                listView.setOrientation(Orientation.HORIZONTAL);

                listView.setCellFactory(new Callback<ListView<Stores>, javafx.scene.control.ListCell<Stores>>() {
                    @Override
                    public ListCell<Stores> call(ListView<Stores> listView) {
                        return new StoreItemCell();
                    }
                });
                listView.setOnMouseClicked((event) -> {

                    btnNew.setDisable(false);

                    btnDelete.setDisable(false);

                    btnEdite.setDisable(false);

                    btnAdd.setDisable(true);
                    Stores selectedItem = listView.getSelectionModel().getSelectedItem();
                    id.setText(Integer.toString(selectedItem.getId()));
                    name.setText(selectedItem.getName());
                    try {
                        ObservableList<StoreProducts> dataOfStore = StoreProducts.getDataOfStore(selectedItem.getId()); 
                        listView1.getItems().clear();
                        listView1.setItems(dataOfStore);
                        listView1.setOrientation(Orientation.HORIZONTAL);
                        listView1.setCellFactory(new Callback<ListView<StoreProducts>, javafx.scene.control.ListCell<StoreProducts>>() {
                            @Override
                            public ListCell<StoreProducts> call(ListView<StoreProducts> listView) {
                                return new ProductItemCell();
                            }
                        });
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                });

            }
        };
        service.start();
    }

    @FXML
    private void New(ActionEvent event) {
        clear();

    }

    @FXML
    private void Delete(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            Stores st;

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
                                    st = new Stores();
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Store");
                                    alert.setHeaderText("سيتم  حذف المخزن ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        st.setId(Integer.parseInt(id.getText()));

                                        st.Delete();
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
                Stores st;

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
                                        st = new Stores();
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editing  Store");
                                        alert.setHeaderText("سيتم  تعديل المخزن ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            st.setId(Integer.parseInt(id.getText()));
                                            st.setName(name.getText());
                                            st.Edite();
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
                Stores st;

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
                                        st = new Stores();

                                        st.setId(Integer.parseInt(id.getText()));
                                        st.setName(name.getText());
                                        st.Add();

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
        if (name.getText() == null || name.getText().length() == 0) {
            AlertDialogs.validateError("اسم المخزن لا يجب ان يكون فارغ");
            return false;
        }
        return true;
    }

}
