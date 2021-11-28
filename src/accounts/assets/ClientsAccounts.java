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
public class ClientsAccounts {

    int id;
    int clientId;
    int invoiceId;
    String amount;
    String date;
    int userId;
    String user;
    String dateTime;

    public ClientsAccounts() {
    }

    public ClientsAccounts(int id, int clientId, int invoiceId, String amount, String date, String user, String dateTime) {
        this.id = id;
        this.clientId = clientId;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public static ObservableList<ClientsAccounts> getData(int id) throws Exception {
        ObservableList<ClientsAccounts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `acc_clients`.`id`,`acc_clients`.`client_id`, `acc_clients`.`invoice_id`, `acc_clients`.`amount`, `acc_clients`.`date`, `sys_users`.`username`, `acc_clients`.`date_time` FROM `acc_clients`,`sys_users` WHERE `sys_users`.`id`=`acc_clients`.`user_id` AND `acc_clients`.`client_id`='" + id + "'");
        while (rs.next()) {
            data.add(new ClientsAccounts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        return data;
    }
}
