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
public class SalesClient {

    int id;
    String name;
    String organization;
    String relation;
    String location;
    String email;
    String tele1;
    String tele2;
    int Sales_id;
    String sales;

    public SalesClient() {
    }

    public SalesClient(int id, String name, String organization, String relation, String location, String email, String tele1, String tele2, String sales) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.relation = relation;
        this.location = location;
        this.email = email;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.sales = sales;
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
        return organization;
    }

    public void setOrganization(String organizaztion) {
        this.organization = organizaztion;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTele1() {
        return tele1;
    }

    public void setTele1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTele2() {
        return tele2;
    }

    public void setTele2(String tele2) {
        this.tele2 = tele2;
    }

    public int getSales_id() {
        return Sales_id;
    }

    public void setSales_id(int Sales_id) {
        this.Sales_id = Sales_id;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_client`(`id`, `name`, `organization`, `relation`, `location`, `email`, `tele1`, `tele2`,`sales_id`) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, organization);
        ps.setString(4, relation);
        ps.setString(5, location);
        ps.setString(6, email);
        ps.setString(7, tele1);
        ps.setString(8, tele2);
        ps.setInt(9, Sales_id);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_client` SET `name`=?,`organization`=?,`relation`=?,`location`=?,`email`=?,`tele1`=?,`tele2`=?,`sales_id`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setString(2, organization);
        ps.setString(3, relation);
        ps.setString(4, location);
        ps.setString(5, email);
        ps.setString(6, tele1);
        ps.setString(7, tele2);
        ps.setInt(8, Sales_id);
        ps.setInt(9, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_client` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<SalesClient> getData() throws Exception {
        ObservableList<SalesClient> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_client`.`id`, `sl_client`.`name`, `sl_client`.`organization`, `sl_client`.`relation`, `sl_client`.`location`, `sl_client`.`email`, `sl_client`.`tele1`, `sl_client`.`tele2`,`sl_members`.`name` FROM `sl_client`,`sl_members` WHERE `sl_members`.`id` = `sl_client`.`sales_id`");
        while (rs.next()) {
            data.add(new SalesClient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static ObservableList<SalesClient> getData(String salesId) throws Exception {
        ObservableList<SalesClient> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_client`.`id`, `sl_client`.`name`, `sl_client`.`organization`, `sl_client`.`relation`, `sl_client`.`location`, `sl_client`.`email`, `sl_client`.`tele1`, `sl_client`.`tele2`,`sl_members`.`name` FROM `sl_client`,`sl_members` WHERE `sl_client`.`sales_id`='"+salesId+"' and `sl_members`.`id` = `sl_client`.`sales_id`");
        while (rs.next()) {
            data.add(new SalesClient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_client`").getValueAt(0, 0).toString();
    }
}
