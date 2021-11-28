/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesTrack.assets;

import clients.assets.*;
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
public class TrackSell {

    int id;
    int clientId;
    String client;
    String date;
    String originalCost;
    String discount;
    String discountPercent;
    String hasTaxs;
    String total;
    int salesId;
    int lineId;
    String payType;
    String priceType;
    String notes;
    InputStream doc;
    String docExt;
    int userId;
    String user;
    ObservableList<TrackSellDetails> details;

    public TrackSell() {
    }

    public TrackSell(int id, int clientId, String date, String originalCost, String discount, String discountPercent, String hasTaxs, String total, int salesId, int lineId, String payType, String priceType, String notes, int userId) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.originalCost = originalCost;
        this.discount = discount;
        this.discountPercent = discountPercent;
        this.hasTaxs = hasTaxs;
        this.total = total;
        this.salesId = salesId;
        this.lineId = lineId;
        this.payType = payType;
        this.priceType = priceType;
        this.notes = notes;
        this.userId = userId;
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

    public ObservableList<TrackSellDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<TrackSellDetails> details) {
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

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_out_invoice`(`id`,`client_id`, `date`, `original_cost`, `discount`, `discount_percent`, `with_tax`, `total`, `sales_id`, `line_id`, `pay_type`, `price_type`, `notes`, `doc`, `doc_ext`, `user_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(i++, id); ps.setInt(i++, clientId);
        ps.setString(i++, date);
        ps.setString(i++, originalCost);
        ps.setString(i++, discount);
        ps.setString(i++, discountPercent);
        ps.setString(i++, hasTaxs);
        ps.setString(i++, total);
        ps.setInt(i++, salesId);
        ps.setInt(i++, lineId);
        ps.setString(i++, payType);
        ps.setString(i++, priceType);
//        ps.setString(i++, "نقدا");
        ps.setString(i++, notes);
        ps.setBlob(i++, doc);
        ps.setString(i++, docExt);
        ps.setInt(i++, userId);
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        return true;
    }

    public boolean AddWithoutPhoto() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_out_invoice`(`id`,`client_id`, `date`, `original_cost`, `discount`, `discount_percent`, `with_tax`, `total`, `sales_id`, `line_id`, `pay_type`,`price_type`, `notes`,`user_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
         ps.setInt(i++, id);ps.setInt(i++, clientId);
        ps.setString(i++, date);
        ps.setString(i++, originalCost);
        ps.setString(i++, discount);
        ps.setString(i++, discountPercent);
        ps.setString(i++, hasTaxs);
        ps.setString(i++, total);
        ps.setInt(i++, salesId);
        ps.setInt(i++, lineId);
        ps.setString(i++, payType);
        ps.setString(i++, priceType);
//        ps.setString(i++, "نقدا");
        ps.setString(i++, notes);
        ps.setInt(i++, userId);
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        return true;
    }

    public boolean Edite() throws Exception {
        DeleteDetails();
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_out_invoice` SET `client_id`=?,`date`=?,`original_cost`=?,`discount`=?,`discount_percent`=?,`with_tax`=?,`total`=?,`sales_id`=?,`line_id`=?,`pay_type`=?,`price_type`=?,`notes`=?,`doc`=?,`doc_ext`=?,`user_id`=? WHERE `id`=?");
        ps.setInt(i++, clientId);
        ps.setString(i++, date);
        ps.setString(i++, originalCost);
        ps.setString(i++, discount);
        ps.setString(i++, discountPercent);
        ps.setString(i++, hasTaxs);
        ps.setString(i++, total);
        ps.setInt(i++, salesId);
        ps.setInt(i++, lineId);
        ps.setString(i++, payType);
        ps.setString(i++, priceType);
//        ps.setString(i++, "نقدا");
        ps.setString(i++, notes);
        ps.setBlob(i++, doc);
        ps.setString(i++, docExt);
        ps.setInt(i++, userId);
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        return true;
    }

    public boolean EditeWithoutPhoto() throws Exception {
        DeleteDetails();
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_out_invoice` SET `client_id`=?,`date`=?,`original_cost`=?,`discount`=?,`discount_percent`=?,`with_tax`=?,`total`=?,`sales_id`=?,`line_id`=?,`pay_type`=?,`price_type`=?,`notes`=?,`user_id`=? WHERE `id`=?");
        ps.setInt(i++, clientId);
        ps.setString(i++, date);
        ps.setString(i++, originalCost);
        ps.setString(i++, discount);
        ps.setString(i++, discountPercent);
        ps.setString(i++, hasTaxs);
        ps.setString(i++, total);
        ps.setInt(i++, salesId);
        ps.setInt(i++, lineId);
        ps.setString(i++, payType);
        ps.setString(i++, priceType);
//        ps.setString(i++, "نقدا");
        ps.setString(i++, notes);
        ps.setInt(i++, userId);
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        return true;
    }

    public boolean Delete() throws Exception {
        DeleteDetails();
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_out_invoice` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_out_invoice_details`(`invoice_id`, `out_line_id`, `out_line_product_id`, `store_product_id`, `price_type`, `cost`, `amount`, `total_cost`) VALUES (?,?,?,?,?,?,?,?)");

        for (TrackSellDetails a : details) {
            int i = 1;
            ps.setInt(i++, id);
            StoreProducts b = (StoreProducts) a.getProductsCombo().getSelectionModel().getSelectedItem();
            ps.setInt(i++, b.getStoreId());
            ps.setInt(i++, b.getInvoiceId());
            ps.setInt(i++, b.getId());
            ps.setString(i++, "wholesale");
            ps.setString(i++, a.getCostField().getText());
            ps.setString(i++, a.getAmountField().getText());
            ps.setString(i++, Double.toString(Double.parseDouble(a.getAmountField().getText()) * Double.parseDouble(a.getCostField().getText())));
            ps.addBatch();
        }
        ps.executeBatch();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean DeleteDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_out_invoice_details` WHERE `invoice_id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<TrackSell> getData() throws Exception {
        ObservableList<TrackSell> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `client_id`, `date`, `original_cost`, `discount`, `discount_percent`, `with_tax`, `total`, `sales_id`, `line_id`, `pay_type`, `price_type`, `notes`,  `user_id` FROM `sl_out_invoice`");
        while (rs.next()) {
            data.add(new TrackSell(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14)));
        }
        return data;
    }
//
//    public static ObservableList<TrackSell> getStockedData() throws Exception {
//        ObservableList<TrackSell> data = FXCollections.observableArrayList();
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices`.`id`, `cli_clients`.`organization`, `cli_invoices`.`date`, `cli_invoices`.`original_cost`, `cli_invoices`.`discount`, `cli_invoices`.`discount_percent`, `cli_invoices`.`with_tax`, `cli_invoices`.`total`, `cli_invoices`.`pay_type`, `cli_invoices`.`notes`, `sys_users`.`username`, `cli_invoices`.`statue` FROM `cli_invoices`,`cli_clients`,`sys_users` WHERE (`cli_invoices`.`statue`!='pending' AND `cli_invoices`.`statue`!='penupdate') AND `cli_clients`.`id`=`cli_invoices`.`client_id` and `sys_users`.`id` =`cli_invoices`.`user_id`");
//        while (rs.next()) {
//            data.add(new TrackSell(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
//        }
//        return data;
//    }
//
    public static ObservableList<TrackSell> getDataById(int id) throws Exception {
       ObservableList<TrackSell> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `client_id`, `date`, `original_cost`, `discount`, `discount_percent`, `with_tax`, `total`, `sales_id`, `line_id`, `pay_type`, `price_type`, `notes`,  `user_id` FROM `sl_out_invoice` WHERE `id`='"+id+"'");
        while (rs.next()) {
            data.add(new TrackSell(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14)));
        }
        return data;
    }
    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_out_invoice`").getValueAt(0, 0).toString();
    }
}
