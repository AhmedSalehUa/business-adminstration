/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesTrack.assets;

import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class TrackLine {

    int id;
    int salesId;
    String Sales;
    String location;
    String date;

    public TrackLine() {
    }

    public TrackLine(int id, String Sales, String location, String date) {
        this.id = id;
        this.Sales = Sales;
        this.location = location;
        this.date = date;
    }

    public TrackLine(int id, int salesId, String location, String date) {
        this.id = id;
        this.salesId = salesId;
        this.location = location;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSales() {
        return Sales;
    }

    public void setSales(String Sales) {
        this.Sales = Sales;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_out_line`(`sales_id`, `location`, `date`) VALUES (?,?,?)");
        ps.setInt(i++, salesId);
        ps.setString(i++, location);
        ps.setString(i++, date);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_out_line` SET `sales_id`=?,`location`=?,`date`=? WHERE `id`=?");
        ps.setInt(i++, salesId);
        ps.setString(i++, location);
        ps.setString(i++, date);
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_out_line` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<TrackLine> getData() throws Exception {
        ObservableList<TrackLine> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_out_line`.`id`, `sl_members`.`name`, `sl_out_line`.`location`, `sl_out_line`.`date` FROM `sl_out_line`,`sl_members` WHERE `sl_members`.`id` =`sl_out_line`.`sales_id`");
        while (rs.next()) {
            data.add(new TrackLine(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
        }
        return data;
    }
     public static ObservableList<TrackLine> getDataWithId() throws Exception {
        ObservableList<TrackLine> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_out_line`.`id`, `sl_out_line`.`sales_id`, `sl_out_line`.`location`, `sl_out_line`.`date` FROM `sl_out_line`,`sl_members`");
        while (rs.next()) {
            data.add(new TrackLine(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_out_line`").getValueAt(0, 0).toString();
    }
}
