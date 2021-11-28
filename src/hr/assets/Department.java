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
public class Department {

    int id;
    String name;
    String Organization;
    int org_id;

    public Department() {
    }

    public Department(int id, String name, String Organization) {
        this.id = id;
        this.name = name;
        this.Organization = Organization;
    }

    public Department(int id, String name, int org_id) {
        this.id = id;
        this.name = name;
        this.org_id = org_id;
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

    public String getOrganization() {
        return Organization;
    }

    public void setOrganization(String Organization) {
        this.Organization = Organization;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_department`(`id`, `name`, `organization_id`) VALUES (?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, org_id);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `att_department` SET `name`=?,`organization_id`=? WHERE `id`=?");
        ps.setInt(3, id);
        ps.setString(1, name);
        ps.setInt(2, org_id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_department` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Department> getData() throws Exception {
        ObservableList<Department> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `att_department`.`id`, `att_department`.`name`, `att_organization`.`name` FROM `att_department`,`att_organization` WHERE `att_organization`.`id`= `att_department`.`organization_id`");
        while (rs.next()) {
            data.add(new Department(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_department`").getValueAt(0, 0).toString();
    }
}
