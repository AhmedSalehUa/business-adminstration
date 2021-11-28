/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import  sales.assets.Calendar;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SalesScreenCalendarEventController implements Initializable {

    @FXML
    private Label date;
    @FXML
    private TableView<Calendar> tab;
    @FXML
    private TableColumn<Calendar, String> tabDetails;
    @FXML
    private TableColumn<Calendar, String> tabTime;
    @FXML
    private TableColumn<Calendar, String> tabSales;
    @FXML
    private TableColumn<Calendar, String> tabClient;
    @FXML
    private TableColumn<Calendar, String> tabId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabDetails.setCellValueFactory(new PropertyValueFactory<>("details"));

        tabTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        tabSales.setCellValueFactory(new PropertyValueFactory<>("sales"));

        tabClient.setCellValueFactory(new PropertyValueFactory<>("client"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    public void setData(String date, ObservableList<Calendar> CalendarDates) {
        this.date.setText(date);
        tab.setItems(CalendarDates);
    }
}
