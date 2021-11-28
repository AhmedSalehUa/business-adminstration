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
public class ProviderAccountsPays {
    
    int id;
    int providerAccId;
    String amount;
    String payType;
    String cashType;
    String dateOfDoc;
    String dateOfCash;
    String cheekNumber;
    String statue;
    int accId;
    int userId;
    String user;
    String dateTime;

    public ProviderAccountsPays() {
    }

    public ProviderAccountsPays(int id, int providerAccId, String amount, String payType, String cashType, String dateOfDoc, String dateOfCash, String cheekNumber, String statue, int accId, String user, String dateTime) {
        this.id = id;
        this.providerAccId = providerAccId;
        this.amount = amount;
        this.payType = payType;
        this.cashType = cashType;
        this.dateOfDoc = dateOfDoc;
        this.dateOfCash = dateOfCash;
        this.cheekNumber = cheekNumber;
        this.statue = statue;
        this.accId = accId;
        this.user = user;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProviderAccId() {
        return providerAccId;
    }

    public void setProviderAccId(int providerAccId) {
        this.providerAccId = providerAccId;
    }
 

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCashType() {
        return cashType;
    }

    public void setCashType(String cashType) {
        this.cashType = cashType;
    }

    public String getDateOfDoc() {
        return dateOfDoc;
    }

    public void setDateOfDoc(String dateOfDoc) {
        this.dateOfDoc = dateOfDoc;
    }

    public String getDateOfCash() {
        return dateOfCash;
    }

    public void setDateOfCash(String dateOfCash) {
        this.dateOfCash = dateOfCash;
    }

    public String getCheekNumber() {
        return cheekNumber;
    }

    public void setCheekNumber(String cheekNumber) {
        this.cheekNumber = cheekNumber;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `acc_provider_pays`(`provider_acc_id`, `amount`, `pay_type`, `cash_type`, `date_of_doc`, `date_of_cash`, `cheek_number`, `statue`, `acc_id`, `user_id`) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(i++, providerAccId);
        ps.setString(i++, amount);
        ps.setString(i++, payType);
        ps.setString(i++, cashType);
        ps.setString(i++, dateOfDoc);
        ps.setString(i++, dateOfCash);
        ps.setString(i++, cheekNumber);
        ps.setString(i++, statue);
        ps.setInt(i++, accId);
        ps.setInt(i++, userId);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `acc_provider_pays` SET  provider_acc_id=?, amount=?, pay_type=?, cash_type=?, date_of_doc=?, date_of_cash=?, cheek_number=?, statue=?, acc_id=?, user_id=?, WHERE `id`=?");
        ps.setInt(i++, providerAccId);
        ps.setString(i++, amount);
        ps.setString(i++, payType);
        ps.setString(i++, cashType);
        ps.setString(i++, dateOfDoc);
        ps.setString(i++, dateOfCash);
        ps.setString(i++, cheekNumber);
        ps.setString(i++, statue);
        ps.setInt(i++, accId);
        ps.setInt(i++, userId);
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `acc_provider_pays` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<ProviderAccountsPays> getData(int id) throws Exception {
        ObservableList<ProviderAccountsPays> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `acc_provider_pays`.`id`, `acc_provider_pays`.`provider_acc_id`, `acc_provider_pays`.`amount`, `acc_provider_pays`.`pay_type`, `acc_provider_pays`.`cash_type`, `acc_provider_pays`.`date_of_doc`, `acc_provider_pays`.`date_of_cash`, `acc_provider_pays`.`cheek_number`, `acc_provider_pays`.`statue`, `acc_provider_pays`.`acc_id` ,`sys_users`.`username`, `acc_provider_pays`.`date_time` FROM `acc_provider_pays`,`sys_users` WHERE `sys_users`.`id`=`acc_provider_pays`.`user_id` and `acc_provider_pays`.`provider_acc_id` in (SELECT `id` FROM `acc_provider` WHERE `provider_id`='" + id + "')");
        while (rs.next()) {
            data.add(new ProviderAccountsPays(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `acc_provider_pays`").getValueAt(0, 0).toString();
    }
}
