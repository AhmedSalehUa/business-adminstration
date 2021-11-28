/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales.assets;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class OffersConditions {

    int id;
    int invoice_id;
    String attribute;
    String value;

    public OffersConditions() {
    }

    public OffersConditions(int id, int invoice_id, String attribute, String value) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.attribute = attribute;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_in_offers_condition`").getValueAt(0, 0).toString();
    }

    public static ObservableList<OffersConditions> getData(int id) throws Exception {
        ObservableList<OffersConditions> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `sl_in_offers_condition` WHERE `offer_id`='"+id+"'");
        while (rs.next()) {
            data.add(new OffersConditions(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

}
