/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.permissions.assets;

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
public class TrackOut {

    int id;
    int lineId;
    String line;
    String date;
    int userId;
    String user;
    ObservableList<TrackOutDetails> details;

    public TrackOut() {
    }

    public TrackOut(int id, int lineId, String date, String user) {
        this.id = id;
        this.lineId = lineId;
        this.date = date;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObservableList<TrackOutDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<TrackOutDetails> details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_per_line_out`(`line_id`, `date`, `user_id`) VALUES (?,?,?)");
        ps.setInt(i++, lineId);
        ps.setString(i++, date);
        ps.setInt(i++, userId);
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        return true;
    }

    public boolean Edite() throws Exception {
        DeleteDetails();
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `st_per_line_out` SET `line_id`=?,`date`=?,`user_id`=? WHERE `id`=?");
        ps.setInt(i++, lineId);
        ps.setString(i++, date);
        ps.setInt(i++, userId);
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        AddDetails();
        return true;
    }

    public boolean Delete() throws Exception {
        DeleteDetails();
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_per_line_out` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }
//

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_per_line_out_details`( `line_out_id`, `store_product_id`,`product_id`,`amount`, `cost`) VALUES (?,?,?,?,?)");

        for (TrackOutDetails a : details) {
            ps.setInt(1, id);
            StoreProducts b = (StoreProducts) a.getProductsCombo().getSelectionModel().getSelectedItem();
            ps.setInt(2, b.getId());
            ps.setInt(3, b.getProductId());
            ps.setString(4, a.getAmountField().getText());
            ps.setString(5, a.getCostField().getText());
            ps.addBatch();
        }
        ps.executeBatch();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean DeleteDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_per_line_out_details` WHERE `line_out_id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }
// 
//    public static ObservableList<TrackOut> getData() throws Exception {
//        ObservableList<TrackOut> data = FXCollections.observableArrayList();
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices`.`id`, `cli_clients`.`organization`, `cli_invoices`.`date`, `cli_invoices`.`original_cost`, `cli_invoices`.`discount`, `cli_invoices`.`discount_percent`, `cli_invoices`.`with_tax`, `cli_invoices`.`total`, `cli_invoices`.`pay_type`, `cli_invoices`.`notes`, `sys_users`.`username`, `cli_invoices`.`statue` FROM `cli_invoices`,`cli_clients`,`sys_users` WHERE (`cli_invoices`.`statue`='pending' OR `cli_invoices`.`statue`='penupdate') AND `cli_clients`.`id`=`cli_invoices`.`client_id` and `sys_users`.`id` =`cli_invoices`.`user_id`");
//        while (rs.next()) {
//            data.add(new TrackOut(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
//        }
//        return data;
//    }
//public static ObservableList<TrackOut> getStockedData() throws Exception {
//        ObservableList<TrackOut> data = FXCollections.observableArrayList();
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices`.`id`, `cli_clients`.`organization`, `cli_invoices`.`date`, `cli_invoices`.`original_cost`, `cli_invoices`.`discount`, `cli_invoices`.`discount_percent`, `cli_invoices`.`with_tax`, `cli_invoices`.`total`, `cli_invoices`.`pay_type`, `cli_invoices`.`notes`, `sys_users`.`username`, `cli_invoices`.`statue` FROM `cli_invoices`,`cli_clients`,`sys_users` WHERE (`cli_invoices`.`statue`!='pending' AND `cli_invoices`.`statue`!='penupdate') AND `cli_clients`.`id`=`cli_invoices`.`client_id` and `sys_users`.`id` =`cli_invoices`.`user_id`");
//        while (rs.next()) {
//            data.add(new TrackOut(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
//        }
//        return data;
//    }
//    public static ObservableList<TrackOut> getDataById(int id) throws Exception {
//        ObservableList<TrackOut> data = FXCollections.observableArrayList();
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `cli_invoices`.`id`, `cli_clients`.`organization`, `cli_invoices`.`date`, `cli_invoices`.`original_cost`, `cli_invoices`.`discount`, `cli_invoices`.`discount_percent`, `cli_invoices`.`with_tax`, `cli_invoices`.`total`, `cli_invoices`.`pay_type`, `cli_invoices`.`notes`, `sys_users`.`username`, `cli_invoices`.`statue` FROM `cli_invoices`,`cli_clients`,`sys_users` WHERE (`cli_invoices`.`statue`='pending' OR `cli_invoices`.`statue`='penupdate') AND `cli_clients`.`id`=`cli_invoices`.`client_id` and `sys_users`.`id` =`cli_invoices`.`user_id` AND `cli_invoices`.`id`='" + id + "'");
//        while (rs.next()) {
//            data.add(new TrackOut(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
//        }
//        return data;
//    }
//

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_per_line_out`").getValueAt(0, 0).toString();
    }
}
