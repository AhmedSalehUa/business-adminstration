/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import store.assets.StoreProducts;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StockedItemController {

    @FXML
    private HBox item;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label invoiceNumber;
    @FXML
    private Label units;
    @FXML
    private Label unitQuatity;
    @FXML
    private Label total;
    @FXML
    private Label buy;
    @FXML
    private Label sell;

    public StockedItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/store/StockedItem.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setInfo(StoreProducts st) { 
        id.setText(Integer.toString(st.getId()));
        name.setText(st.getProductName());
        invoiceNumber.setText(Integer.toString(st.getInvoiceId()));
        units.setText(st.getUnitAmount());
        unitQuatity.setText(Double.toString(Double.parseDouble(st.getTotalAmount()) / Double.parseDouble(st.getUnitAmount())));
        total.setText(st.getTotalAmount());
        buy.setText(st.getCostOfBuy());
        sell.setText(st.getCostOfWhole());
    }

    public HBox getBox() {
        return item;
    }
}
