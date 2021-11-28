/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers.assets;

import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import store.assets.StoreProducts;

/**
 *
 * @author AHMED
 */
public class ProviderReturned {

    int id;
    int invoiceId;
    String returntype;
    String date;
    String total;
    String notes;
    int userId;
    String statue;

    ObservableList<ProviderReturnedDetails> details;

    public ProviderReturned() {
    }

    public ProviderReturned(int id, int invoiceId, String returntype, String date, String total, String notes, int userId, String statue) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.returntype = returntype;
        this.date = date;
        this.total = total;
        this.notes = notes;
        this.userId = userId;
        this.statue = statue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getReturntype() {
        return returntype;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public ObservableList<ProviderReturnedDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<ProviderReturnedDetails> details) {
        this.details = details;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_returned_invoice`(`invoice_id`, `return_type`, `date`, `total`, `notes`, `user_id`,`statue`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(i++, invoiceId);
        ps.setString(i++, "some");
        ps.setString(i++, date);
        ps.setString(i++, total);
        ps.setString(i++, notes);
        ps.setInt(i++, userId);
        ps.setString(i++, "pending");
        ps.execute();
        AddDetails();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        DeleteDetails();
        int i = 1; 
        PreparedStatement ps = db.get.Prepare("UPDATE `st_returned_invoice` SET `invoice_id`=?,`return_type`=?,`date`=?,`total`=?,`notes`=?,`user_id`=?,`statue`=? WHERE `id`=?");
        ps.setInt(i++, invoiceId);
        ps.setString(i++, "some");
        ps.setString(i++, date);
        ps.setString(i++, total);
        ps.setString(i++, notes);
        ps.setInt(i++, userId);
        ps.setString(i++, "pending");
        ps.setInt(i++, id); 
        ps.execute();
        AddDetails();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_returned_invoice` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_returned_invoice_details`(`returned_id`, `store_product_id`, `product_id`, `amount`, `cost`, `total_cost`, `statue`) VALUES (?,?,?,?,?,?,?)");

        for (ProviderReturnedDetails a : details) {
            ps.setInt(1, id);
            StoreProducts b = (StoreProducts) a.getProductsCombo().getSelectionModel().getSelectedItem();
            ps.setInt(2, b.getId());
            ps.setInt(3, b.getProductId());
            ps.setString(4, a.getAmountField().getText());
            ps.setString(5, a.getCostField().getText());
            ps.setString(6, Double.toString(Double.parseDouble(a.getAmountField().getText()) * Double.parseDouble(a.getCostField().getText())));
            ps.setString(7, "pending");
            ps.addBatch();
        }
        ps.executeBatch();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean DeleteDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_returned_invoice_details` WHERE `returned_id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<ProviderReturned> getDataById(int id) throws Exception {
        ObservableList<ProviderReturned> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `invoice_id`, `return_type`, `date`, `total`, `notes`, `user_id`, `statue` FROM `st_returned_invoice` where `id` ='"+id+"'");
        while (rs.next()) {
            data.add(new ProviderReturned(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getString(8)));
        }
        return data;
    }
 public static ObservableList<ProviderReturned> getData() throws Exception {
        ObservableList<ProviderReturned> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `invoice_id`, `return_type`, `date`, `total`, `notes`, `user_id`, `statue` FROM `st_returned_invoice` where (`st_returned_invoice`.`statue`='pending' OR `st_returned_invoice`.`statue`='penupdate')");
        while (rs.next()) {
            data.add(new ProviderReturned(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getString(8)));
        }
        return data;
    }
    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_returned_invoice`").getValueAt(0, 0).toString();
    }
}
