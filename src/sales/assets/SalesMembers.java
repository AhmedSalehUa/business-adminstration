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
public class SalesMembers {

    int id;
    String name;
    String NumOfSuccess;
    String APP_TOKEN;

    public SalesMembers() {
    }

    public SalesMembers(int id, String name, String NumOfSuccess, String APP_TOKEN) {
        this.id = id;
        this.name = name;
        this.NumOfSuccess = NumOfSuccess;
        this.APP_TOKEN = APP_TOKEN;
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

    public String getNumOfSuccess() {
        return NumOfSuccess;
    }

    public void setNumOfSuccess(String NumOfSuccess) {
        this.NumOfSuccess = NumOfSuccess;
    }

    public String getAPP_TOKEN() {
        return APP_TOKEN;
    }

    public void setAPP_TOKEN(String APP_TOKEN) {
        this.APP_TOKEN = APP_TOKEN;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_members`(`id`, `name`, `num_of_success`, `app_token`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, NumOfSuccess);
        ps.setString(4, APP_TOKEN);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_members` SET `name`=?,`num_of_success`=?,`app_token`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setString(2, NumOfSuccess);
        ps.setString(3, APP_TOKEN);
        ps.setInt(4, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_members` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<SalesMembers> getData() throws Exception {
        ObservableList<SalesMembers> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `sl_members` ");
        while (rs.next()) {
            data.add(new SalesMembers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_members`").getValueAt(0, 0).toString();
    }
}
