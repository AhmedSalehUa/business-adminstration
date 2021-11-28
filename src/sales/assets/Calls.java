/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Calls {

    int id;
    int client_id;
    String client;
    String date;
    String time;
    String details;
    int sales_id;
    String sales;

    public Calls() {
    }

    public Calls(int id, String client, String date, String time, String details, String sales) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.time = time;
        this.details = details;
        this.sales = sales;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getSales_id() {
        return sales_id;
    }

    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_calls`(`id`, `client_id`, `date`, `time`, `details`, `sales_id`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, client_id);
        ps.setString(3, date);
        ps.setString(4, time);
        ps.setString(5, details);
        ps.setInt(6, sales_id);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_in_calls` SET `client_id`=?,`date`=?,`time`=?,`details`=?,`sales_id`=? WHERE `id`=?");
        ps.setInt(1, client_id);
        ps.setString(2, date);
        ps.setString(3, time);
        ps.setString(4, details);
        ps.setInt(5, sales_id);
        ps.setInt(6, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_in_calls` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Calls> getData() throws Exception {
        ObservableList<Calls> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_in_calls`.`id`,`sl_client`.`name`, `sl_in_calls`.`date`, `sl_in_calls`.`time`, `sl_in_calls`.`details`,`sl_members`.`name` FROM `sl_in_calls`,`sl_members`,`sl_client` WHERE `sl_members`.`id`=`sl_in_calls`.`sales_id` and `sl_client`.`id` = `sl_in_calls`.`client_id`");
        while (rs.next()) {
            data.add(new Calls(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static ObservableList<Calls> getData(String salesId) throws Exception {
        ObservableList<Calls> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_in_calls`.`id`,`sl_client`.`name`, `sl_in_calls`.`date`, `sl_in_calls`.`time`, `sl_in_calls`.`details`,`sl_members`.`name` FROM `sl_in_calls`,`sl_members`,`sl_client` WHERE`sl_in_calls`.`sales_id`='" + salesId + "' and  `sl_members`.`id`=`sl_in_calls`.`sales_id` and `sl_client`.`id` = `sl_in_calls`.`client_id`");
        while (rs.next()) {
            data.add(new Calls(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_in_calls`").getValueAt(0, 0).toString();
    }
}
