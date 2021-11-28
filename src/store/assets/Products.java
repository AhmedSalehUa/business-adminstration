/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.assets;

import businessadministration.Logs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Products {

    int id;
    String name;
    String barcode;
    String model;
    String details;
    int unitId;
    String unitName;
    String quantityPerUnit;
    int catId;
    String catName;
    String expDate;
    String warnNum;

    public Products() {
    }

    public Products(int id, String name, String barcode, String model, String details, String unitName, String quantityPerUnit, String catName, String expDate, String warnNum) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.model = model;
        this.details = details;
        this.unitName = unitName;
        this.quantityPerUnit = quantityPerUnit;
        this.catName = catName;
        this.expDate = expDate;  this.warnNum = warnNum;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(String warnNum) {
        this.warnNum = warnNum;
    }

    public boolean Add() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `st_products`(`name`, `barcode`, `model`, `details`, `unite_id`, `quantity_per_unite`, `cat_id`, `exp_date`,`warn_num`) VALUES(?,?,?,?,?,?,?,?,?) ");
        ps.setString(i++, name);
        ps.setString(i++, barcode);
        ps.setString(i++, model);
        ps.setString(i++, details);
        ps.setInt(i++, unitId);
        ps.setString(i++, quantityPerUnit);
        ps.setInt(i++, catId);
        ps.setString(i++, expDate); ps.setString(i++, warnNum);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Edite() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("UPDATE `st_products` SET `name`=?,`barcode`=?,`model`=?,`details`=?,`unite_id`=?,`quantity_per_unite`=?,`cat_id`=?,`exp_date`=?,`warn_num`=? WHERE `id`=?");
        ps.setString(i++, name);
        ps.setString(i++, barcode);
        ps.setString(i++, model);
        ps.setString(i++, details);
        ps.setInt(i++, unitId);
        ps.setString(i++, quantityPerUnit);
        ps.setInt(i++, catId);
        ps.setString(i++, expDate);ps.setString(i++, warnNum);
        ps.setInt(i++, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `st_products` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        Logs.Add(ps.toString());
        return true;
    }

    public static ObservableList<Products> getData() throws Exception {
        ObservableList<Products> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `st_products`.`id`, `st_products`.`name`, `st_products`.`barcode`, `st_products`.`model`, `st_products`.`details`,`st_units`.`name`, `st_products`.`quantity_per_unite`, `sys_category`.`name`, `st_products`.`exp_date`, `st_products`.`warn_num` FROM `st_products`,`sys_category`,`st_units` WHERE `sys_category`.`id`=`st_products`.`cat_id` and `st_products`.`unite_id` = `st_units`.`id`");
        while (rs.next()) {
            data.add(new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `st_products`").getValueAt(0, 0).toString();
    }

}
