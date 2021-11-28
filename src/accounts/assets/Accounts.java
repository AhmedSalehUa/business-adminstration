/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts.assets;

import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Accounts {

    int id;
    String name;
    String bank;
    String accNum;
    String balance;

    public Accounts() {
    }

    public Accounts(int id, String name, String bank, String accNum, String balance) {
        this.id = id;
        this.name = name;
        this.bank = bank;
        this.accNum = accNum;
        this.balance = balance;
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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<Accounts> getData() throws Exception {
        ObservableList<Accounts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("select * from acc_accounts");
        while (rs.next()) {
            data.add(new Accounts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `acc_accounts`").getValueAt(0, 0).toString();
    }
}
