/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter; 
import sales.assets.Calendar;
import sales.assets.SalesClient;
import sales.assets.SalesMembers;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenAddEventController implements Initializable {

    @FXML
    private Label id;
    @FXML
    private ComboBox<SalesMembers> sales;
    @FXML
    private ComboBox<SalesClient> client;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker time;
    @FXML
    private TextArea details;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button formAdd;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            String autoNum;
            ObservableList<SalesMembers> salesData;
            ObservableList<SalesClient> clientData;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            salesData = SalesMembers.getData();
                            clientData = SalesClient.getData();
                            autoNum = Calendar.getAutoNum();
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
                    protected void updateItem(SalesClient person, boolean empty) {
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
                    protected void updateItem(SalesMembers person, boolean empty) {
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
    private void Add(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            boolean ok = true;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Calendar cl = new Calendar();
                            cl.setId(Integer.parseInt(id.getText()));
                            cl.setClient_id(client.getSelectionModel().getSelectedItem().getId());
                            cl.setSales_id(sales.getSelectionModel().getSelectedItem().getId());
                            cl.setDate(date.getValue().format(format));
                            cl.setTime(time.getValue().format(DateTimeFormatter.ISO_TIME));
                            cl.setDetails(details.getText());
                            cl.Add();
                        } catch (Exception ex) {
                            ok = false;
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
                if (ok) {
                    AlertDialogs.showmessage("تم");
                }
                super.succeeded();
            }
        };
        service.start();
    }

}
