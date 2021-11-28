/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers.assets;

import businessadministration.Logs;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import store.assets.Products;

/**
 *
 * @author AHMED
 */
public class InvoiceBuy {

    int id;
    int providerId;
    String provider;
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
    ObservableList<InvoiceBuyDetails> details;
    ObservableList<InvoiceBuyExpenses> expenses;

    public InvoiceBuy() {
    }

    public InvoiceBuy(int id, String provider, String date, String originalCost, String discount, String discountPercent, String hasTaxs, String total, String payType, String notes, String user, String statue) {
        this.id = id;
        this.provider = provider;
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

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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

    public ObservableList<InvoiceBuyDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<InvoiceBuyDetails> details) {
        this.details = details;
    }

    public ObservableList<InvoiceBuyExpenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(ObservableList<InvoiceBuyExpenses> expenses) {
        this.expenses = expenses;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_invoices`(`provider_id`, `date`, `original_cost`, `discount`, `discount_percent`, `with_tax`, `total`, `pay_type`, `notes`, `doc`, `doc_ext`, `user_id`, `statue`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(i++, providerId);
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
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        AddExpenses();
        return true;
    }

    public boolean AddWithoutPhoto() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_invoices`(`provider_id`, `date`, `original_cost`, `discount`, `discount_percent`, `with_tax`, `total`, `pay_type`, `notes`, `user_id`, `statue`) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(i++, providerId);
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
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        AddExpenses();
        return true;
    }

    public boolean Edite() throws Exception {
        DeleteDetails();
        DeleteExpenses();
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `st_invoices` SET `provider_id`=?,`date`=?,`original_cost`=?,`discount`=?,`discount_percent`=?,`with_tax`=?,`total`=?,`pay_type`=?,`notes`=?,`doc`=?,`doc_ext`=?,`user_id`=?,`statue`=? WHERE `id`=?");
        ps.setInt(i++, providerId);
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
        AddExpenses();
        return true;
    }

    public boolean EditeWithoutPhoto() throws Exception {
        DeleteDetails();
        DeleteExpenses();
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `st_invoices` SET `provider_id`=?,`date`=?,`original_cost`=?,`discount`=?,`discount_percent`=?,`with_tax`=?,`total`=?,`pay_type`=?,`notes`=?,`user_id`=?,`statue`=? WHERE `id`=?");
        ps.setInt(i++, providerId);
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
        AddExpenses();
        return true;
    }

    public boolean Delete() throws Exception {
        DeleteDetails();
        DeleteExpenses();
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_invoices` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_invoices_details`(`invoice_id`, `product_id`, `amount`, `cost`, `total_cost`,statue) VALUES (?,?,?,?,?,?)");

        for (InvoiceBuyDetails a : details) {
            ps.setInt(1, id);
            Products b = (Products) a.getProductsCombo().getSelectionModel().getSelectedItem();
            ps.setInt(2, b.getId());
            ps.setString(3, a.getCostField().getText());
            ps.setString(4, a.getAmountField().getText());
            ps.setString(5, Double.toString(Double.parseDouble(a.getAmountField().getText()) * Double.parseDouble(a.getCostField().getText())));
            ps.setString(6, "pending");
            ps.addBatch();
        }
        ps.executeBatch();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean DeleteDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_invoices_details` WHERE `invoice_id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean AddExpenses() throws Exception {
        if (expenses != null) {
            PreparedStatement ps = db.get.Prepare("INSERT INTO `st_invoices_expenses`(`invoice_id`, `destination`, `cost`) VALUES (?,?,?)");

            for (InvoiceBuyExpenses a : expenses) {
                ps.setInt(1, id);
                ps.setString(2, a.getDestination());
                ps.setString(3, a.getAmount());
                ps.addBatch();
            }

            ps.executeBatch();
            Logs.Add(ps.toString());
        }
        return true;
    }

    public boolean DeleteExpenses() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_invoices_expenses` WHERE `invoice_id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<InvoiceBuy> getData() throws Exception {
        ObservableList<InvoiceBuy> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices`.`id`, `st_provider`.`organization`, `st_invoices`.`date`, `st_invoices`.`original_cost`, `st_invoices`.`discount`, `st_invoices`.`discount_percent`, `st_invoices`.`with_tax`, `st_invoices`.`total`, `st_invoices`.`pay_type`, `st_invoices`.`notes`, `sys_users`.`username`, `st_invoices`.`statue` FROM `st_invoices`,`st_provider`,`sys_users` WHERE (`st_invoices`.`statue`='pending' OR `st_invoices`.`statue`='penupdate') AND `st_provider`.`id`=`st_invoices`.`provider_id` and `sys_users`.`id` =`st_invoices`.`user_id`");
        while (rs.next()) {
            data.add(new InvoiceBuy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
        }
        return data;
    }
public static ObservableList<InvoiceBuy> getStockedData() throws Exception {
        ObservableList<InvoiceBuy> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices`.`id`, `st_provider`.`organization`, `st_invoices`.`date`, `st_invoices`.`original_cost`, `st_invoices`.`discount`, `st_invoices`.`discount_percent`, `st_invoices`.`with_tax`, `st_invoices`.`total`, `st_invoices`.`pay_type`, `st_invoices`.`notes`, `sys_users`.`username`, `st_invoices`.`statue` FROM `st_invoices`,`st_provider`,`sys_users` WHERE (`st_invoices`.`statue`!='pending' AND `st_invoices`.`statue`!='penupdate') AND `st_provider`.`id`=`st_invoices`.`provider_id` and `sys_users`.`id` =`st_invoices`.`user_id`");
        while (rs.next()) {
            data.add(new InvoiceBuy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
        }
        return data;
    }
    public static ObservableList<InvoiceBuy> getDataById(int id) throws Exception {
        ObservableList<InvoiceBuy> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_invoices`.`id`, `st_provider`.`organization`, `st_invoices`.`date`, `st_invoices`.`original_cost`, `st_invoices`.`discount`, `st_invoices`.`discount_percent`, `st_invoices`.`with_tax`, `st_invoices`.`total`, `st_invoices`.`pay_type`, `st_invoices`.`notes`, `sys_users`.`username`, `st_invoices`.`statue` FROM `st_invoices`,`st_provider`,`sys_users` WHERE (`st_invoices`.`statue`='pending' OR `st_invoices`.`statue`='penupdate') AND `st_provider`.`id`=`st_invoices`.`provider_id` and `sys_users`.`id` =`st_invoices`.`user_id` AND `st_invoices`.`id`='" + id + "'");
        while (rs.next()) {
            data.add(new InvoiceBuy(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_invoices`").getValueAt(0, 0).toString();
    }
}
