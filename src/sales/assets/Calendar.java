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
public class Calendar {

    int id;
    int client_id;
    String client;
    int sales_id;
    String sales;
    String date;
    String time;
    String details;

    public Calendar(int id, String client, String sales, String date, String time, String details) {
        this.id = id;
        this.client = client;
        this.sales = sales;
        this.date = date;
        this.time = time;
        this.details = details;
    }

    public Calendar() {
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_calender`(`id`, `client_id`, `sales_id`, `date`, `time`, `details`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, client_id);
        ps.setInt(3, sales_id);
        ps.setString(4, date);
        ps.setString(5, time);
        ps.setString(6, details);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_in_calender` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Calendar> getData() throws Exception {
        ObservableList<Calendar> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_in_calender`.`id`,`sl_client`.`name`,`sl_members`.`name`, `sl_in_calender`.`date`, `sl_in_calender`.`time`, `sl_in_calender`.`details` FROM `sl_in_calender`,`sl_client`,`sl_members` WHERE `sl_in_calender`.`client_id` =`sl_client`.`id` and `sl_in_calender`.`sales_id`=`sl_members`.`id`");
        while (rs.next()) {
            data.add(new Calendar(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static ObservableList<Calendar> getData(String salesID) throws Exception {
        ObservableList<Calendar> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_in_calender`.`id`,`sl_client`.`name`,`sl_members`.`name`, `sl_in_calender`.`date`, `sl_in_calender`.`time`, `sl_in_calender`.`details` FROM `sl_in_calender`,`sl_client`,`sl_members` WHERE `sl_in_calender`.`client_id` =`sl_client`.`id` and `sl_in_calender`.`sales_id`='"+salesID+"' and `sl_in_calender`.`sales_id`=`sl_members`.`id`");
        while (rs.next()) {
            data.add(new Calendar(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_in_calender`").getValueAt(0, 0).toString();
    }
}
