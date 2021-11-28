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
public class LateRules {

    int id;
    String rule;
    String action;
    int shift;

    public LateRules() {
    }

    public LateRules(int id, int shift, String rule, String action) {
        this.id = id;
        this.rule = rule;
        this.action = action;
        this.shift = shift;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_late_rule`(`id`, `shift_id`, `rule`, `action`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, shift);
        ps.setString(3, rule);
        ps.setString(4, action);

        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `att_late_rule` SET `shift_id`=?,`rule`=?,`action`=? WHERE `id`=?");
        ps.setInt(4, id);
        ps.setInt(1, shift);
        ps.setString(2, rule);
        ps.setString(3, action);

        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_late_rule` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<LateRules> getData() throws Exception {
        ObservableList<LateRules> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `att_late_rule`");
        while (rs.next()) {
            data.add(new LateRules(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_late_rule`").getValueAt(0, 0).toString();
    }
}
