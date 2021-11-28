/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.permissions.assets;

import clients.assets.*;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import providers.assets.ProviderReturnedDetails;
import store.assets.StoreProducts;

/**
 *
 * @author AHMED
 */
public class TrackOutDetails {

    int id;
    int productID;
    ComboBox productsCombo;
    TextField amountField;
    TextField costField;
    TextField totalcostField;

    String product;
    String amount;
    String cost;
    String totalcost;
    ObservableList<StoreProducts> searchData = FXCollections.observableArrayList();

    public TrackOutDetails(int id, String product, String amount, String cost, String totalcost) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.cost = cost;
        this.totalcost = totalcost;
    }

    public TrackOutDetails(int id, ObservableList<StoreProducts> data, String selectedpro, String amount, String cost, String totalCostString) {
        this.id = id;
        this.productsCombo = new ComboBox(data);
        this.amountField = new TextField(amount);
        this.costField = new TextField(cost);
        this.totalcost = totalCostString;
        productsCombo.setConverter(new StringConverter<StoreProducts>() {
            @Override
            public String toString(StoreProducts contract) {
                costField.setText(contract.getCostOfWhole());
                return contract.getProductName();
            }

            @Override
            public StoreProducts fromString(String string) {
                return null;
            }
        });
        productsCombo.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        for (StoreProducts a : data) {
            if (a.getProductName().equals(selectedpro)) {
                productsCombo.getSelectionModel().select(a);

            }
        }
        productsCombo.setCellFactory(cell -> new ListCell<StoreProducts>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(200, 200, 200),
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 0, 2);

            }

            @Override
            protected void updateItem(StoreProducts person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("الاسم: " + person.getProductName());
                    lblName.setText("عدد الوحدات: " + person.getUnitAmount());
                    lblQuali.setText("اجمالي العدد: " + person.getTotalAmount());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });

    } 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public ComboBox getProductsCombo() {
        return productsCombo;
    }

    public void setProductsCombo(ComboBox productsCombo) {
        this.productsCombo = productsCombo;
    }

    public TextField getAmountField() {
        return amountField;
    }

    public void setAmountField(TextField amountField) {
        this.amountField = amountField;
    }

    public TextField getCostField() {
        return costField;
    }

    public void setCostField(TextField costField) {
        this.costField = costField;
    }

    public TextField getTotalcostField() {
        return totalcostField;
    }

    public void setTotalcostField(TextField totalcostField) {
        this.totalcostField = totalcostField;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public ObservableList<StoreProducts> getSearchData() {
        return searchData;
    }

    public void setSearchData(ObservableList<StoreProducts> searchData) {
        this.searchData = searchData;
    }

//    public static ObservableList<TrackOutDetails> getData(int id, int invoiceId) throws Exception {
//        ObservableList<TrackOutDetails> data = FXCollections.observableArrayList();
//        System.out.println(id);
//        ObservableList<StoreProducts> data1 = StoreProducts.getData();
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_per_line_out_details`.`id`,`st_products`.`name`, `st_per_line_out_details`.`amount`, `st_per_line_out_details`.`cost`  FROM `st_per_line_out_details`,`st_products` WHERE `st_products`.`id` =`cli_invoices_details`.`product_id` AND `st_returned_invoice_details`.`invoice_id`='" + id + "'");
//        while (rs.next()) {
//            data.add(new TrackOutDetails(rs.getInt(1), data1, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
//        }
//        return data;
//    }
//    public static ObservableList<TrackOutDetails> getDataForExite(int id) throws Exception {
//        ObservableList<TrackOutDetails> data = FXCollections.observableArrayList(); 
//        ObservableList<StoreProducts> data1 = StoreProducts.getDataOfInvoice(id);
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices_details`.`id`,`st_products`.`name`, `cli_invoices_details`.`amount`, `cli_invoices_details`.`cost`,`cli_invoices_details`.`total_cost` FROM `cli_invoices_details`,`st_products`,`st_store_products` WHERE `st_store_products`.`id` = `cli_invoices_details`.`store_product_id` AND `st_products`.`id` =`st_store_products`.`product_id` AND `cli_invoices_details`.`invoice_id`='" + id + "'");
//        while (rs.next()) {
//            data.add(new TrackOutDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
//        }
//        return data;
//    }
}
