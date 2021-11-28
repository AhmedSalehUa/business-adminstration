/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.assets;

import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Clients {

    int id;
    String organization;
    String name;
    String address;
    String email;
    String accountNumber;
    String taxNumber;
    String tele1;
    String tele2;

    public Clients() {
    }

    public Clients(int id, String organization, String name, String address, String email, String accountNumber, String taxNumber, String tele1, String tele2) {
        this.id = id;
        this.organization = organization;
        this.name = name;
        this.address = address;
        this.email = email;
        this.accountNumber = accountNumber;
        this.taxNumber = taxNumber;
        this.tele1 = tele1;
        this.tele2 = tele2;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        PreparedStatement ps = db.get.Prepare("INSERT INTO `cli_clients`( `organization`, `name`, `address`, `email`, `account_number`, `tax_number`, `tele1`, `tele2`) VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(i++, organization);
        ps.setString(i++, name);
        ps.setString(i++, address);
        ps.setString(i++, email);
        ps.setString(i++, accountNumber);
        ps.setString(i++, taxNumber);
        ps.setString(i++, tele1);
        ps.setString(i++, tele2);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `cli_clients` SET `organization`=?,`name`=?,`address`=?,`email`=?,`account_number`=?,`tax_number`=?,`tele1`=?,`tele2`=? WHERE `id`=?");
        ps.setString(i++, organization);
        ps.setString(i++, name);
        ps.setString(i++, address);
        ps.setString(i++, email);
        ps.setString(i++, accountNumber);
        ps.setString(i++, taxNumber);
        ps.setString(i++, tele1);
        ps.setString(i++, tele2);
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `cli_clients` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<Clients> getData() throws Exception {
        ObservableList<Clients> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `cli_clients`");
        while (rs.next()) {
            data.add(new Clients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `cli_clients`").getValueAt(0, 0).toString();
    }

}
