/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers.assets;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class InvoiceBuyExpenses {
    int id;int invoiceId;String destination;String amount;

    public InvoiceBuyExpenses() {
    }

    public InvoiceBuyExpenses(int id, int invoiceId, String destination, String amount) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.destination = destination;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
       public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_invoices_expenses`").getValueAt(0, 0).toString();
    }

    public static ObservableList<InvoiceBuyExpenses> getData(int id) throws Exception {
        ObservableList<InvoiceBuyExpenses> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `st_invoices_expenses` WHERE `invoice_id`='"+id+"'");
        while (rs.next()) {
            data.add(new InvoiceBuyExpenses(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }
}
