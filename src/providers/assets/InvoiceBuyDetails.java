/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers.assets;

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
import store.assets.Products;

/**
 *
 * @author AHMED
 */
public class InvoiceBuyDetails {

    int id;
    int productID;
    ComboBox productsCombo;
    TextField amountField;
    TextField costField;
    TextField totalcostField;
  TextField costOfSell;
  
    String product;
    String amount;
    String cost;
    String totalcost;
    ObservableList<Products> searchData = FXCollections.observableArrayList();

    public InvoiceBuyDetails(int id, String product, String amount, String cost, String totalcost) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.cost = cost;
        this.totalcost = totalcost;
    }
public InvoiceBuyDetails(int id, String product, String amountString, String costString, String totalCostString, int productID) {
        this.id = id;
        this.product = product;
        this.amount = amountString;
        this.cost = costString;
        this.productID = productID;
        this.costOfSell = new TextField(totalCostString);
    }
    public InvoiceBuyDetails(int id, ObservableList<Products> data, String selectedpro, String amount, String cost, String totalCostString) {
        this.id = id;
        this.productsCombo = new ComboBox(data);

        productsCombo.setOnKeyReleased((event) -> {

            if (productsCombo.getEditor().getText().length() == 0) {
                productsCombo.setItems(data);
            } else {
                searchData = FXCollections.observableArrayList();

                for (Products a : data) {
                    if (a.getName().contains(productsCombo.getEditor().getText())) {
                        searchData.add(a);
                    }
                }
                productsCombo.setItems(searchData);
                productsCombo.show();
            }
        });
        productsCombo.setConverter(new StringConverter<Products>() {
            @Override
            public String toString(Products contract) {
                  
                return contract.getName() == null ? null : contract.getName();
            }

            @Override
            public Products fromString(String string) {
                return null;
            }
        });
        productsCombo.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        for (Products a : data) {
            if (a.getName().equals(selectedpro)) {
                productsCombo.getSelectionModel().select(a);

            }
        }
        productsCombo.setCellFactory(cell -> new ListCell<Products>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(200, 200, 200),
                        new ColumnConstraints(200, 200, 200)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 0, 2);

            }

            @Override
            protected void updateItem(Products person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("الاسم: " + person.getName());
                    lblName.setText("الموديل: " + person.getModel());
                    lblQuali.setText("التفاصيل: " + person.getDetails());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        this.amountField = new TextField(amount);
        this.costField = new TextField(cost);
        this.totalcost = totalCostString;
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

    public ObservableList<Products> getSearchData() {
        return searchData;
    }

    public TextField getCostOfSell() {
        return costOfSell;
    }

    public void setCostOfSell(TextField costOfSell) {
        this.costOfSell = costOfSell;
    }

    public void setSearchData(ObservableList<Products> searchData) {
        this.searchData = searchData;
    }
    public static ObservableList<InvoiceBuyDetails> getData(int id) throws Exception {
        ObservableList<InvoiceBuyDetails> data = FXCollections.observableArrayList();
        ObservableList<Products> data1 = Products.getData();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices_details`.`id`,`st_products`.`name`, `st_invoices_details`.`amount`, `st_invoices_details`.`cost`,`st_invoices_details`.`total_cost` FROM `st_invoices_details`,`st_products` WHERE `st_products`.`id` =`st_invoices_details`.`product_id` AND `st_invoices_details`.`invoice_id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoiceBuyDetails(rs.getInt(1), data1, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }
    public static ObservableList<InvoiceBuyDetails> getDataForEntance(int id) throws Exception {
        ObservableList<InvoiceBuyDetails> data = FXCollections.observableArrayList();
        ObservableList<Products> data1 = Products.getData();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices_details`.`id`,`st_products`.`name`, `st_invoices_details`.`amount`, `st_invoices_details`.`cost`,`st_invoices_details`.`cost`,`st_products`.`id` FROM `st_invoices_details`,`st_products` WHERE `st_products`.`id` =`st_invoices_details`.`product_id` AND `st_invoices_details`.`invoice_id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoiceBuyDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6)));
        }
        return data;
    }
}
