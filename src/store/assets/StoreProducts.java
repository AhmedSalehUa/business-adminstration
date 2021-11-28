/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.assets;

import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class StoreProducts {

    int id;
    int storeId;
    int invoiceId;
    int invoiceDetailsId;
    int productId;
    String productName;
    String unitAmount;
    String TotalAmount;
    String costOfBuy;
    String costOfRetail;
    String costOfWhole;

    public StoreProducts() {
    }

    public StoreProducts(int id, int storeId, int invoiceId, int invoiceDetailsId, String productName, String unitAmount, String TotalAmount, String costOfBuy, String costOfRetail, String costOfWhole, int productId) {
        this.id = id;
        this.storeId = storeId;
        this.invoiceId = invoiceId;
        this.invoiceDetailsId = invoiceDetailsId;
        this.productName = productName;
        this.unitAmount = unitAmount;
        this.TotalAmount = TotalAmount;
        this.costOfBuy = costOfBuy;
        this.costOfRetail = costOfRetail;
        this.costOfWhole = costOfWhole;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getInvoiceDetailsId() {
        return invoiceDetailsId;
    }

    public void setInvoiceDetailsId(int invoiceDetailsId) {
        this.invoiceDetailsId = invoiceDetailsId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(String unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public String getCostOfBuy() {
        return costOfBuy;
    }

    public void setCostOfBuy(String costOfBuy) {
        this.costOfBuy = costOfBuy;
    }

    public String getCostOfRetail() {
        return costOfRetail;
    }

    public void setCostOfRetail(String costOfRetail) {
        this.costOfRetail = costOfRetail;
    }

    public String getCostOfWhole() {
        return costOfWhole;
    }

    public void setCostOfWhole(String costOfWhole) {
        this.costOfWhole = costOfWhole;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<StoreProducts> getData() throws Exception {
        ObservableList<StoreProducts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`, `st_store_products`.`store_id`, `st_store_products`.`invoice_id`, `st_store_products`.`invoice_details_id`,`st_products`.`name` , sum(cast(`st_store_products`.`unite_amount` as unsigned)) as `unite_amount`, sum(cast(`st_store_products`.`total_amount` as unsigned)) as `total_amount`,`st_store_products`. `cost_of_buy`, `st_store_products`.`cost_for_retail`, `st_store_products`.`cost_for_wholesale`,`st_store_products`.`product_id`,`st_products`.`exp_date` FROM `st_store_products`,`st_products` WHERE `st_products`.`id`=`st_store_products`.`product_id` GROUP by `st_store_products`. `cost_of_buy`having sum(cast(`st_store_products`.`unite_amount` as unsigned))>0 ORDER by `st_products`.`exp_date`,`st_products`.`name`");
        while (rs.next()) {

            data.add(new StoreProducts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11)));
        }
        return data;
    }
 public static ObservableList<StoreProducts> getDataOfStore(int id) throws Exception {
        ObservableList<StoreProducts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`, `st_store_products`.`store_id`, `st_store_products`.`invoice_id`, `st_store_products`.`invoice_details_id`,`st_products`.`name` , `st_store_products`.`unite_amount`, `st_store_products`.`total_amount`,`st_store_products`. `cost_of_buy`, `st_store_products`.`cost_for_retail`, `st_store_products`.`cost_for_wholesale`,`st_store_products`.`product_id` FROM `st_store_products`,`st_products` WHERE `st_products`.`id`=`st_store_products`.`product_id` AND `st_store_products`.`store_id`='"+id+"'");
        while (rs.next()) {

            data.add(new StoreProducts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11)));
        }
        return data;
    }
    public static ObservableList<StoreProducts> getDataOfInvoice(int id) throws Exception {
        ObservableList<StoreProducts> data = FXCollections.observableArrayList(); 
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`, `st_store_products`.`store_id`, `st_store_products`.`invoice_id`, `st_store_products`.`invoice_details_id`,`st_products`.`name` , `st_store_products`.`unite_amount`, `st_store_products`.`total_amount`,`st_store_products`. `cost_of_buy`, `st_store_products`.`cost_for_retail`, `st_store_products`.`cost_for_wholesale`,`st_store_products`.`product_id` FROM `st_store_products`,`st_products` WHERE `st_products`.`id`=`st_store_products`.`product_id` AND `st_store_products`.`invoice_id` ='" + id + "'");
        while (rs.next()) {

            data.add(new StoreProducts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11)));
        }
        return data;
    }

    public static ObservableList<StoreProducts> getDataOfClientsInvoice(int id) throws Exception {
        ObservableList<StoreProducts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`, `st_store_products`.`store_id`, `st_store_products`.`invoice_id`, `st_store_products`.`invoice_details_id`, `st_products`.`name`, `cli_invoices_details`.`amount`, `cli_invoices_details`.`cost`, `cli_invoices_details`.`total_cost`, `st_store_products`.`cost_for_retail`, `st_store_products`.`cost_for_wholesale`, `st_store_products`.`product_id` FROM `st_store_products`, `st_products`,cli_invoices_details WHERE `st_products`.`id` = `st_store_products`.`product_id` AND `st_store_products`.`id` = cli_invoices_details.store_product_id AND cli_invoices_details.invoice_id = '"+id+"'");
        while (rs.next()) {

            data.add(new StoreProducts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11)));
        }
        return data;
    }
    public static ObservableList<StoreProducts> getDataOfTrack(int id) throws Exception {
        ObservableList<StoreProducts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`, `sl_out_line_products`.`id`, `sl_out_line_products`.`product_id`, `st_store_products`.`invoice_details_id`, `st_products`.`name`, `sl_out_line_products`.`amount`, `sl_out_line_products`.`cost`, cast(`sl_out_line_products`.`amount` as unsigned) * cast(`sl_out_line_products`.`cost` as unsigned), `st_store_products`.`cost_for_retail`, `st_store_products`.`cost_for_wholesale`, `st_store_products`.`product_id` FROM `st_store_products`, `st_products`,sl_out_line_products WHERE `st_products`.`id` = `st_store_products`.`product_id` AND `st_store_products`.`id` = sl_out_line_products.store_product_id AND sl_out_line_products.line_id = '"+id+"'");
        while (rs.next()) {

            data.add(new StoreProducts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11)));
        }
        return data;
    }  public static ObservableList<StoreProducts> gettDataOfClientsInvoiceOfTrack(int id) throws Exception {
        ObservableList<StoreProducts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`, `sl_out_invoice_details`.`id`, `st_store_products`.`product_id`, `st_store_products`.`invoice_details_id`, `st_products`.`name`, `sl_out_invoice_details`.`amount`, `sl_out_invoice_details`.`cost`, cast(`sl_out_invoice_details`.`amount` as unsigned) * cast(`sl_out_invoice_details`.`cost` as unsigned), `st_store_products`.`cost_for_retail`, `st_store_products`.`cost_for_wholesale`, `st_store_products`.`product_id` FROM `st_store_products`, `st_products`,sl_out_invoice_details WHERE `st_products`.`id` = `st_store_products`.`product_id` AND `st_store_products`.`id` = sl_out_invoice_details.store_product_id AND sl_out_invoice_details.out_line_id = '"+id+"'");
        while (rs.next()) {

            data.add(new StoreProducts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11)));
        }
        return data;
    } 
public static ObservableList<StoreProducts> getDataOfClientsReturnedInvoice(int id) throws Exception {
        ObservableList<StoreProducts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_store_products`.`id`, `st_store_products`.`store_id`, `st_store_products`.`invoice_id`, `st_store_products`.`invoice_details_id`, `st_products`.`name`, `cli_returned_invoice_details`.`amount`, `cli_returned_invoice_details`.`cost`, `cli_returned_invoice_details`.`total_cost`, `st_store_products`.`cost_for_retail`, `st_store_products`.`cost_for_wholesale`, `st_store_products`.`product_id` FROM `st_store_products`, `st_products`,cli_returned_invoice_details WHERE `st_products`.`id` = `st_store_products`.`product_id` AND `st_store_products`.`id` = cli_returned_invoice_details.store_product_id AND cli_returned_invoice_details.returned_id = '"+id+"'");
        while (rs.next()) {

            data.add(new StoreProducts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11)));
        }
        return data;
    }
    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_store_products`").getValueAt(0, 0).toString();
    }
}
