/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.permissions.assets;

import static assets.classes.statics.USER_ID;
import businessadministration.BusinessAdministration;
import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.util.prefs.Preferences;
import javafx.collections.ObservableList;
import providers.assets.InvoiceBuyDetails;
import store.assets.Products;

/**
 *
 * @author AHMED
 */
public class StoreOperations {

    //global
    int id;
    int invoicecId;
    int userId;
    String date;

    //for entrance
    int storeId;
    String notes;

    //for returnes
    int returnedId;

    ObservableList<InvoiceBuyDetails> details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoicecId() {
        return invoicecId;
    }

    public void setInvoicecId(int invoicecId) {
        this.invoicecId = invoicecId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getReturnedId() {
        return returnedId;
    }

    public void setReturnedId(int returnedId) {
        this.returnedId = returnedId;
    }

    public ObservableList<InvoiceBuyDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<InvoiceBuyDetails> details) {
        this.details = details;
    }

    public boolean updatePriceToSell() throws Exception {
        PreparedStatement ps = db.get.Prepare("Update `st_invoices_details` set `price_of_cell`=? ,store_id=? where `id`=?");

        for (InvoiceBuyDetails a : details) {
            ps.setString(1, a.getCostOfSell().getText());
            ps.setInt(2, storeId);
            ps.setInt(3, a.getId());
            ps.addBatch();
        }
        ps.executeBatch();
        return true;

    }

    public boolean EntrancePermission() throws Exception {
        updatePriceToSell();
        Preferences prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_per_entrance`(`store_id`, `invoice_id`, `date`, `user_id`) VALUES (?,?,?,?)");
        ps.setInt(i++, storeId);
        ps.setInt(i++, invoicecId);
        ps.setString(i++, date);
        ps.setInt(i++, Integer.parseInt(prefs.get(USER_ID, "0")));
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean ReturnExitePermission() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_per_provider_returned`(`returned_id`, `date`, `user_id`) VALUES (?,?,?)");
        ps.setInt(i++, invoicecId);
        ps.setString(i++, date);
        ps.setInt(i++, Integer.parseInt(prefs.get(USER_ID, "0")));
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean ExitePermission() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_per_exit`(`invoice_id`, `date`,`notes`,`user_id`) VALUES (?,?,?,?)");
        ps.setInt(i++, invoicecId);
        ps.setString(i++, date);
        ps.setString(i++, notes);
        ps.setInt(i++, Integer.parseInt(prefs.get(USER_ID, "0")));
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }
     public boolean ReturnEntrancePermission() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_per_client_returned`(`returned_id`, `date`, `user_id`) VALUES (?,?,?)");
        ps.setInt(i++, invoicecId);
        ps.setString(i++, date);
        ps.setInt(i++, Integer.parseInt(prefs.get(USER_ID, "0")));
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }
}
