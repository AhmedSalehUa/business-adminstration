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
import store.assets.Stores;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoresItemsController {

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label totalProduct;
    @FXML
    private Label totalReturned;
    @FXML
    private Label transactions;
    @FXML
    private HBox item;

    public StoresItemsController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/store/StoresItems.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setInfo(Stores st, String totalProduct, String totalReturned, String transaction) {
        id.setText(Integer.toString(st.getId()));
        name.setText(st.getName());
        this.totalProduct.setText(st.getTotalProducts());
        this.totalReturned.setText(totalReturned);
        transactions.setText(transaction);
    }

    public HBox getBox() {
        return item;
    }
}
