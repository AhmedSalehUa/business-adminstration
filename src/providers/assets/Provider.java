/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers.assets;

import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Provider {

    int id;
    String organization;
    String name;
    String address;

    String accountNumber;
    String taxNumber;
    String tele1;
    String tele2;
    String catName;
    int catId;

    public Provider() {
    }

    public Provider(int id, String organization, String name, String address, String accountNumber, String taxNumber, String tele1, String tele2, String catName) {
        this.id = id;
        this.organization = organization;
        this.name = name;
        this.address = address;
        this.accountNumber = accountNumber;
        this.taxNumber = taxNumber;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.catName = catName;
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

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
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

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_provider`(`organization`, `name`, `address`, `account_number`, `tax_number`, `cat_id`, `tele1`, `tele2`) VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(i++, organization);
        ps.setString(i++, name);
        ps.setString(i++, address);
        ps.setString(i++, accountNumber);
        ps.setString(i++, taxNumber);
        ps.setInt(i++, catId);
        ps.setString(i++, tele1);
        ps.setString(i++, tele2);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `st_provider` SET `organization`=?,`name`=?,`address`=?,`email`=?,`account_number`=?,`tax_number`=?,`tele1`=?,`tele2`=?,`cat_id`=? WHERE `id`=?");
        ps.setString(i++, organization);
        ps.setString(i++, name);
        ps.setString(i++, address);
        ps.setString(i++, accountNumber);
        ps.setString(i++, taxNumber);
        ps.setString(i++, tele1);
        ps.setString(i++, tele2);
        ps.setInt(i++, catId);
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_provider` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<Provider> getData() throws Exception {
        ObservableList<Provider> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_provider`.`id`, `st_provider`.`organization`, `st_provider`.`name`, `st_provider`.`address`, `st_provider`.`account_number`, `st_provider`.`tax_number`,  `st_provider`.`tele1`, `st_provider`.`tele2`,`sys_category`.`name` FROM `st_provider`,`sys_category` WHERE `sys_category`.`id`=`st_provider`.`cat_id`");
        while (rs.next()) {
            data.add(new Provider(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_provider`").getValueAt(0, 0).toString();
    }

}
