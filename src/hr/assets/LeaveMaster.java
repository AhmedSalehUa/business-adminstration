/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class LeaveMaster {

    int id;
    String name;
    String maxNum;
    String action;

    public LeaveMaster() {
    }

    public LeaveMaster(int id, String name, String maxNum, String action) {
        this.id = id;
        this.name = name;
        this.maxNum = maxNum;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_leave_master`(`id`, `name`, `max_num`, `paied_type`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, maxNum);
        ps.setString(4, action);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `att_leave_master` SET `name`=?,`max_num`=?,`paied_type`=? WHERE `id`=?");
        ps.setInt(4, id);
        ps.setString(1, name);
        ps.setString(2, maxNum);
        ps.setString(3, action);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_leave_master` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<LeaveMaster> getData() throws Exception {
        ObservableList<LeaveMaster> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `att_leave_master`");
        while (rs.next()) {
            data.add(new LeaveMaster(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_leave_master`").getValueAt(0, 0).toString();
    }
}
