/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts.assets;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class ProviderAccounts {
    
    int id;
    int providerId;
    int invoiceId;
    String amount;
    String date;
    int userId;
    String user;
    String dateTime;

    public ProviderAccounts() {
    }

    public ProviderAccounts(int id, int providerId, int invoiceId, String amount, String date, String user, String dateTime) {
        this.id = id;
        this.providerId = providerId;
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

              

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public static ObservableList<ProviderAccounts> getData(int id) throws Exception {
        ObservableList<ProviderAccounts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `acc_provider`.`id`,`acc_provider`.`provider_id`, `acc_provider`.`invoice_id`, `acc_provider`.`amount`, `acc_provider`.`date`, `sys_users`.`username`, `acc_provider`.`date_time` FROM `acc_provider`,`sys_users` WHERE `sys_users`.`id`=`acc_provider`.`user_id` AND `acc_provider`.`provider_id`='" + id + "'");
        while (rs.next()) {
            data.add(new ProviderAccounts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        return data;
    }
}
