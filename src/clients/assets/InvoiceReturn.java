/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.assets;

import businessadministration.Logs;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import store.assets.StoreProducts;

/**
 *
 * @author AHMED
 */
public class InvoiceReturn {

    int id;
    int invoiceId;
    String return_type;
    String date;
    String total;
    String notes;
    int userId;
    String user;
    String statue;
    ObservableList<InvoiceReturnDetails> details;

    public InvoiceReturn() {
    }

    public InvoiceReturn(int id, int invoiceId, String return_type, String date, String total, String notes, int userId, String statue) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.return_type = return_type;
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

    public String getReturn_type() {
        return return_type;
    }

    public void setReturn_type(String return_type) {
        this.return_type = return_type;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public ObservableList<InvoiceReturnDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<InvoiceReturnDetails> details) {
        this.details = details;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_returned_invoice`(`invoice_id`, `return_type`, `date`, `total`, `noted`, `user_id`, `statue`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(i++, invoiceId);
        ps.setString(i++, return_type);
        ps.setString(i++, date);
        ps.setString(i++, total);
        ps.setString(i++, notes);
        ps.setInt(i++, userId);
        ps.setString(i++, "pending");
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        return true;
    }

    public boolean Edite() throws Exception {
        DeleteDetails();
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_returned_invoice` SET `invoice_id`=?,`return_type`=?,`date`=?,`total`=?,`noted`=?,`user_id`=?,`statue`=? WHERE `id`=?");
        ps.setInt(i++, invoiceId);
        ps.setString(i++, return_type);
        ps.setString(i++, date);
        ps.setString(i++, total);
        ps.setString(i++, notes);
        ps.setInt(i++, userId);
        ps.setString(i++, "pending");
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        return true;
    }
 
    public boolean Delete() throws Exception {
        DeleteDetails();
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_returned_invoice` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_returned_invoice_details`(  `returned_id`, `store_product_id`, `product_id`, `amount`, `cost`, `total_cost`, `statue`) VALUES (?,?,?,?,?,?,?)");

        for (InvoiceReturnDetails a : details) {
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
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_returned_invoice_details` WHERE `returned_id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<InvoiceReturn> getData() throws Exception {
        ObservableList<InvoiceReturn> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `invoice_id`, `return_type`, `date`, `total`, `noted`, `user_id`, `statue` FROM `cli_returned_invoice` where (`cli_returned_invoice`.`statue`='pending' OR `cli_returned_invoice`.`statue`='penupdate')");
        while (rs.next()) {
            data.add(new InvoiceReturn(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8)));
        }
        return data;
    }
//
//    public static ObservableList<InvoiceSell> getStockedData() throws Exception {
//        ObservableList<InvoiceSell> data = FXCollections.observableArrayList();
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices`.`id`, `cli_clients`.`organization`, `cli_invoices`.`date`, `cli_invoices`.`original_cost`, `cli_invoices`.`discount`, `cli_invoices`.`discount_percent`, `cli_invoices`.`with_tax`, `cli_invoices`.`total`, `cli_invoices`.`pay_type`, `cli_invoices`.`notes`, `sys_users`.`username`, `st_invoices`.`statue` FROM `cli_invoices`,`cli_clients`,`sys_users` WHERE (`cli_invoices`.`statue`!='pending' AND `cli_invoices`.`statue`!='penupdate') AND `cli_clients`.`id`=`cli_invoices`.`client_id` and `sys_users`.`id` =`cli_invoices`.`user_id`");
//        while (rs.next()) {
//            data.add(new InvoiceSell(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
//        }
//        return data;
//    }

    public static ObservableList<InvoiceReturn> getDataById(int id) throws Exception {
        ObservableList<InvoiceReturn> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `invoice_id`, `return_type`, `date`, `total`, `noted`, `user_id`, `statue` FROM `cli_returned_invoice` where (`cli_returned_invoice`.`statue`='pending' OR `cli_returned_invoice`.`statue`='penupdate') AND `cli_returned_invoice`.`id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoiceReturn(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_returned_invoice`").getValueAt(0, 0).toString();
    }
}
