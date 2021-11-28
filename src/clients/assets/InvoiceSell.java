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
public class InvoiceSell {
    
    int id;
    int clientId;
    String client;
    String date;
    String originalCost;
    String discount;
    String discountPercent;
    String total;
    String hasTaxs;
    String payType;
    String notes;
    InputStream doc;
    String docExt;
    int userId;
    String user;
    String statue;
    ObservableList<InvoiceSellDetails> details; 

    public InvoiceSell() {
    }

    public InvoiceSell(int id, String client, String date, String originalCost, String discount, String discountPercent, String hasTaxs, String total, String payType, String notes, String user, String statue) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.originalCost = originalCost;
        this.discount = discount;
        this.discountPercent = discountPercent;
        this.total = total;
        this.hasTaxs = hasTaxs;
        this.payType = payType;
        this.notes = notes;
        this.user = user;
        this.statue = statue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ObservableList<InvoiceSellDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<InvoiceSellDetails> details) {
        this.details = details;
    }
 

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(String originalCost) {
        this.originalCost = originalCost;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHasTaxs() {
        return hasTaxs.equals("true") ? "بضريبة" : "بدون ضريبة";
    }

    public void setHasTaxs(String hasTaxs) {
        this.hasTaxs = hasTaxs;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public InputStream getDoc() {
        return doc;
    }

    public void setDoc(InputStream doc) {
        this.doc = doc;
    }

    public String getDocExt() {
        return docExt;
    }

    public void setDocExt(String docExt) {
        this.docExt = docExt;
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
 

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_invoices`(`client_id`, `date`, `original_cost`, `discount`, `discount_percent`, `with_tax`, `total`, `pay_type`,`price_type`, `notes`, `doc`, `doc_ext`, `user_id`, `statue`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(i++, clientId);
        ps.setString(i++, date);
        ps.setString(i++, originalCost); 
        ps.setString(i++, discount);
        ps.setString(i++, discountPercent);
        ps.setString(i++, hasTaxs);
        ps.setString(i++, total);
        ps.setString(i++, payType);
        ps.setString(i++, "نقدا");
        ps.setString(i++, notes);
        ps.setBlob(i++, doc);
        ps.setString(i++, docExt);
        ps.setInt(i++, userId);
        ps.setString(i++, "pending");
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails(); 
        return true;
    }

    public boolean AddWithoutPhoto() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_invoices`(`client_id`, `date`, `original_cost`, `discount`, `discount_percent`, `with_tax`, `total`, `pay_type`,`price_type`, `notes`, `user_id`, `statue`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(i++, clientId);
        ps.setString(i++, date);
        ps.setString(i++, originalCost);
        ps.setString(i++, discount);
        ps.setString(i++, discountPercent);
        ps.setString(i++, hasTaxs);
        ps.setString(i++, total);
        ps.setString(i++, payType);
        ps.setString(i++, "نقدا");
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
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_invoices` SET `client_id`=?,`date`=?,`original_cost`=?,`discount`=?,`discount_percent`=?,`with_tax`=?,`total`=?,`pay_type`=?,`notes`=?,`doc`=?,`doc_ext`=?,`user_id`=?,`statue`=? WHERE `id`=?");
        ps.setInt(i++, clientId);
        ps.setString(i++, date);
        ps.setString(i++, originalCost);
        ps.setString(i++, discount);
        ps.setString(i++, discountPercent);
        ps.setString(i++, hasTaxs);
        ps.setString(i++, total);
        ps.setString(i++, payType);
        ps.setString(i++, notes);
        ps.setBlob(i++, doc);
        ps.setString(i++, docExt);
        ps.setInt(i++, userId);
        ps.setString(i++, "pending");
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails(); 
        return true;
    }

    public boolean EditeWithoutPhoto() throws Exception {
        DeleteDetails(); 
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_invoices` SET `client_id`=?,`date`=?,`original_cost`=?,`discount`=?,`discount_percent`=?,`with_tax`=?,`total`=?,`pay_type`=?,`notes`=?,`user_id`=?,`statue`=? WHERE `id`=?");
        ps.setInt(i++, clientId);
        ps.setString(i++, date);
        ps.setString(i++, originalCost);
        ps.setString(i++, discount);
        ps.setString(i++, discountPercent);
        ps.setString(i++, hasTaxs);
        ps.setString(i++, total);
        ps.setString(i++, payType);
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
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_invoices` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_invoices_details`( `invoice_id`, `store_product_id`, `price_type`, `cost`, `amount`, `total_cost`, `statue`) VALUES (?,?,?,?,?,?,?)");

        for (InvoiceSellDetails a : details) {
            ps.setInt(1, id);
            StoreProducts b = (StoreProducts) a.getProductsCombo().getSelectionModel().getSelectedItem();
            ps.setInt(2, b.getId());
            ps.setString(3, "wholesale");
            ps.setString(4, a.getCostField().getText());
            ps.setString(5, a.getAmountField().getText());
            ps.setString(6, Double.toString(Double.parseDouble(a.getAmountField().getText()) * Double.parseDouble(a.getCostField().getText())));
            ps.setString(7, "pending");
            ps.addBatch();
        }
        ps.executeBatch();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean DeleteDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_invoices_details` WHERE `invoice_id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }
 
    public static ObservableList<InvoiceSell> getData() throws Exception {
        ObservableList<InvoiceSell> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices`.`id`, `cli_clients`.`organization`, `cli_invoices`.`date`, `cli_invoices`.`original_cost`, `cli_invoices`.`discount`, `cli_invoices`.`discount_percent`, `cli_invoices`.`with_tax`, `cli_invoices`.`total`, `cli_invoices`.`pay_type`, `cli_invoices`.`notes`, `sys_users`.`username`, `cli_invoices`.`statue` FROM `cli_invoices`,`cli_clients`,`sys_users` WHERE (`cli_invoices`.`statue`='pending' OR `cli_invoices`.`statue`='penupdate') AND `cli_clients`.`id`=`cli_invoices`.`client_id` and `sys_users`.`id` =`cli_invoices`.`user_id`");
        while (rs.next()) {
            data.add(new InvoiceSell(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
        }
        return data;
    }
public static ObservableList<InvoiceSell> getStockedData() throws Exception {
        ObservableList<InvoiceSell> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices`.`id`, `cli_clients`.`organization`, `cli_invoices`.`date`, `cli_invoices`.`original_cost`, `cli_invoices`.`discount`, `cli_invoices`.`discount_percent`, `cli_invoices`.`with_tax`, `cli_invoices`.`total`, `cli_invoices`.`pay_type`, `cli_invoices`.`notes`, `sys_users`.`username`, `cli_invoices`.`statue` FROM `cli_invoices`,`cli_clients`,`sys_users` WHERE (`cli_invoices`.`statue`!='pending' AND `cli_invoices`.`statue`!='penupdate') AND `cli_clients`.`id`=`cli_invoices`.`client_id` and `sys_users`.`id` =`cli_invoices`.`user_id`");
        while (rs.next()) {
            data.add(new InvoiceSell(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
        }
        return data;
    }
    public static ObservableList<InvoiceSell> getDataById(int id) throws Exception {
        ObservableList<InvoiceSell> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices`.`id`, `cli_clients`.`organization`, `cli_invoices`.`date`, `cli_invoices`.`original_cost`, `cli_invoices`.`discount`, `cli_invoices`.`discount_percent`, `cli_invoices`.`with_tax`, `cli_invoices`.`total`, `cli_invoices`.`pay_type`, `cli_invoices`.`notes`, `sys_users`.`username`, `cli_invoices`.`statue` FROM `cli_invoices`,`cli_clients`,`sys_users` WHERE (`cli_invoices`.`statue`='pending' OR `cli_invoices`.`statue`='penupdate') AND `cli_clients`.`id`=`cli_invoices`.`client_id` and `sys_users`.`id` =`cli_invoices`.`user_id` AND `cli_invoices`.`id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoiceSell(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_invoices`").getValueAt(0, 0).toString();
    }
}
