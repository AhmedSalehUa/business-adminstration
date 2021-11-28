/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales.assets;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JTable; 
import store.assets.Products;

public class Offers {

    int id;
    int client_id;
    String client;
    String date;
    String cost;
    String dicount;
    String discount_percent;
    String total_cost;
    String notes;
    InputStream doc;
    String ext;
    int sales_id;
    String sales;
    ObservableList<OffersDetails> details;
    ObservableList<OffersConditions> conditions;

    public Offers() {
    }

    public Offers(int id, String client, String date, String cost, String dicount, String discount_percent, String total_cost, String sales, String notes) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.cost = cost;
        this.dicount = dicount;
        this.discount_percent = discount_percent;
        this.total_cost = total_cost;
        this.sales = sales;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDicount() {
        return dicount;
    }

    public void setDicount(String dicount) {
        this.dicount = dicount;
    }

    public String getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public InputStream getDoc() {
        return doc;
    }

    public void setDoc(InputStream doc) {
        this.doc = doc;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public int getSales_id() {
        return sales_id;
    }

    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ObservableList<OffersDetails> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<OffersDetails> details) {
        this.details = details;
    }

    public ObservableList<OffersConditions> getConditions() {
        return conditions;
    }

    public void setConditions(ObservableList<OffersConditions> conditions) {
        this.conditions = conditions;
    }

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_offers_details`( `offer_id`, `product_id`, `cost`, `amount`, `total_cost`) VALUES (?,?,?,?,?)");

        for (OffersDetails a : details) {
            ps.setInt(1, id);
            Products b = (Products) a.getProducts().getSelectionModel().getSelectedItem();
            ps.setInt(2, b.getId());
            ps.setString(3, a.getCost().getText());
            ps.setString(4, a.getAmount().getText());
            ps.setString(5, Integer.toString(Integer.parseInt(a.getAmount().getText()) * Integer.parseInt(a.getCost().getText())));
            ps.addBatch();
        }
        ps.executeBatch();
        return true;
    }

    public boolean AddConditions() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_offers_condition`( `offer_id`, `attribute`, `value`) VALUES (?,?,?)");

        for (OffersConditions a : conditions) {
            ps.setInt(1, id);
            ps.setString(2, a.getAttribute());
            ps.setString(3, a.getValue());
            ps.addBatch();
        }
        ps.executeBatch();
        return true;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_offers`(`id`, `client_id`, `date`, `cost`, `discount`, `discount_percent`, `total_cost`, `doc`, `doc_ext`, `sales_id`,`notes`) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, client_id);
        ps.setString(3, date);
        ps.setString(4, cost);
        ps.setString(5, dicount);
        ps.setString(6, discount_percent);
        ps.setString(7, total_cost);
        ps.setBlob(8, doc);
        ps.setString(9, ext);
        ps.setInt(10, sales_id);
        ps.setString(11, notes);
        AddDetails();
        AddConditions();

        ps.execute();
        return true;
    }

    public boolean AddTemp() throws Exception {
        DeleteTemps();
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_offers_temp`(`id`, `client_id`, `date`, `cost`, `discount`, `discount_percent`, `total_cost`, `sales_id`,`notes`) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, client_id);
        ps.setString(3, date);
        ps.setString(4, cost);
        ps.setString(5, dicount);
        ps.setString(6, discount_percent);
        ps.setString(7, total_cost);
        ps.setInt(8, sales_id);
        ps.setString(9, notes);

        AddDetailsTemp();
        AddConditionsTemp();

        ps.execute();
        return true;
    }

    public boolean AddDetailsTemp() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_offers_details_temp`( `offer_id`, `product_id`, `cost`, `amount`, `total_cost`) VALUES (?,?,?,?,?)");

        for (OffersDetails a : details) {
            ps.setInt(1, id);
            Products b = (Products) a.getProducts().getSelectionModel().getSelectedItem();
            ps.setInt(2, b.getId());
            ps.setString(3, a.getCost().getText());
            ps.setString(4, a.getAmount().getText());
            ps.setString(5, Integer.toString(Integer.parseInt(a.getAmount().getText()) * Integer.parseInt(a.getCost().getText())));
            ps.addBatch();
        }
        ps.executeBatch();
        return true;
    }

    public boolean AddConditionsTemp() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_offers_condition_temp`( `offer_id`, `attribute`, `value`) VALUES (?,?,?)");

        for (OffersConditions a : conditions) {
            ps.setInt(1, id);
            ps.setString(2, a.getAttribute());
            ps.setString(3, a.getValue());
            ps.addBatch();
        }
        ps.executeBatch();
        return true;
    }

    public boolean AddWithouPhoto() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sl_in_offers`(`id`, `client_id`, `date`, `cost`, `discount`, `discount_percent`, `total_cost`, `sales_id`,`notes`) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, client_id);
        ps.setString(3, date);
        ps.setString(4, cost);
        ps.setString(5, dicount);
        ps.setString(6, discount_percent);
        ps.setString(7, total_cost);
        ps.setInt(8, sales_id);
        ps.setString(9, notes);

        AddDetails();
        AddConditions();

        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_in_offers` SET `client_id`=?,`date`=?,`cost`=?,`discount`=?,`discount_percent`=?,`total_cost`=?,`doc`=?,`doc_ext`=?,`sales_id`=?,`notes`=? WHERE `id`=?");
        ps.setInt(1, client_id);
        ps.setString(2, date);
        ps.setString(3, cost);
        ps.setString(4, dicount);
        ps.setString(5, discount_percent);
        ps.setString(6, total_cost);
        ps.setBlob(7, doc);
        ps.setString(8, ext);
        ps.setInt(9, sales_id);
        ps.setString(10, notes);
        ps.setInt(11, id);

        DeleteDetails();
        DeleteConditions();

        AddDetails();
        AddConditions();

        ps.execute();
        return true;
    }

    public boolean EditeWithouPhoto() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `sl_in_offers` SET `client_id`=?,`date`=?,`cost`=?,`discount`=?,`discount_percent`=?,`total_cost`=?,`sales_id`=?,`notes`=? WHERE `id`=?");
        ps.setInt(1, client_id);
        ps.setString(2, date);
        ps.setString(3, cost);
        ps.setString(4, dicount);
        ps.setString(5, discount_percent);
        ps.setString(6, total_cost);
        ps.setInt(7, sales_id);
        ps.setString(8, notes);
        ps.setInt(9, id);

        DeleteDetails();
        DeleteConditions();

        AddDetails();
        AddConditions();

        ps.execute();
        return true;
    }

    public boolean DeleteDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_in_offers_details` WHERE `offer_id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public boolean DeleteConditions() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_in_offers_condition` WHERE `offer_id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_in_offers` WHERE `id`=?");
        ps.setInt(1, id);

        DeleteDetails();
        DeleteConditions();

        ps.execute();
        return true;
    }

    public boolean DeleteTemps() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `sl_in_offers_temp` ");
        ps.execute();
        ps = db.get.Prepare("DELETE FROM `sl_in_offers_condition_temp` ");
        ps.execute();
        ps = db.get.Prepare("DELETE FROM `sl_in_offers_details_temp` ");
        ps.execute();
        return true;
    }

    public static ObservableList<Offers> getData() throws Exception {
        ObservableList<Offers> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_in_offers`.`id`,`sl_client`.`name`, `sl_in_offers`.`date`, `sl_in_offers`.`cost`, `sl_in_offers`.`discount`, `sl_in_offers`.`discount_percent`, `sl_in_offers`.`total_cost`,`sl_members`.`name`,`sl_in_offers`.`notes` FROM `sl_in_offers`,`sl_client`,`sl_members` WHERE `sl_client`.`id` =`sl_in_offers`.`client_id` and `sl_members`.`id` = `sl_in_offers`.`sales_id`");
        while (rs.next()) {
            data.add(new Offers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static ObservableList<Offers> getData(int id) throws Exception {
        ObservableList<Offers> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `sl_in_offers`.`id`,`sl_client`.`name`, `sl_in_offers`.`date`, `sl_in_offers`.`cost`, `sl_in_offers`.`discount`, `sl_in_offers`.`discount_percent`, `sl_in_offers`.`total_cost`,`sl_members`.`name`,`sl_in_offers`.`notes` FROM `sl_in_offers`,`sl_client`,`sl_members` WHERE `sl_client`.`id` =`sl_in_offers`.`client_id` and `sl_members`.`id` = `sl_in_offers`.`sales_id` AND `sl_in_offers`.`id`='" + id + "'");
        while (rs.next()) {
            data.add(new Offers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static ObservableList<Offers> getCutomData(String sql) throws Exception {
        ObservableList<Offers> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(sql);
        while (rs.next()) {
            data.add(new Offers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sl_in_offers`").getValueAt(0, 0).toString();
    }

    public void getOfferDoc() throws Exception {

        File file = null;
        String selectSQL = "SELECT `doc` FROM `sl_in_offers` WHERE `id`='" + id + "'";
        JTable tab = db.get.getTableData("SELECT `doc_ext`,`date` FROM `sl_in_offers` WHERE `id`='" + id + "'");

        if (tab.getRowCount() <= 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("لا يوجد صورة متوفرة");
            alert.show();
        } else {
            String ext = tab.getValueAt(0, 0).toString();
            ResultSet rs = null;

            try {
                PreparedStatement pstmt = db.get.Prepare(selectSQL);

                rs = pstmt.executeQuery();

                File directory = new File(System.getProperty("user.home") + "\\Desktop\\Business Adminstartion\\documents");
                directory.mkdirs();

                file = new File(directory + "\\" + id + "-" + tab.getValueAt(0, 1).toString() + "." + ext);

                FileOutputStream output = new FileOutputStream(file);

                String payPath = file.getAbsolutePath();
                while (rs.next()) {
                    InputStream input = rs.getBinaryStream("doc");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }
                }
                Desktop d = Desktop.getDesktop();
                d.open(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
