/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Overtime {

    int id;
    String name;
    String calcAfter;
    String calc_type;

    static String TYPE_1 = "working hours - shift hours";
    static String TYPE_2 = "leave time  - shift leave time";
    static String TYPE_3 = "early income";
    static String TYPE_4 = "no overtime";

    public Overtime() {
    }

    public Overtime(int id, String name, String calcAfter, String calc_type) {
        this.id = id;
        this.name = name;
        this.calcAfter = calcAfter;
        this.calc_type = calc_type;
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

    public String getCalcAfter() {
        return calcAfter;
    }

    public void setCalcAfter(String calcAfter) {
        this.calcAfter = calcAfter;
    }

    public String getCalc_type() {
        return calc_type;
    }

    public void setCalc_type(String calc_type) {
        this.calc_type = calc_type;
    }

    public static ObservableList<String> getTypes() {
        ObservableList<String> data = FXCollections.observableArrayList();
        data.add(TYPE_1);
        data.add(TYPE_2);
        data.add(TYPE_3);
        data.add(TYPE_4);

        return data;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_overtime`(`id`, `name`, `start_after`, `calc_type`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, calcAfter);
        ps.setString(4, getTypeName());
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `att_overtime` SET `name`=?,`start_after`=?,`calc_type`=? WHERE `id`=?");
        ps.setInt(4, id);
        ps.setString(1, name);
        ps.setString(2, calcAfter);
        ps.setString(3, getTypeName());
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_overtime` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Overtime> getData() throws Exception {
        ObservableList<Overtime> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name`, `start_after`, `calc_type` FROM `att_overtime`");
        while (rs.next()) {
            data.add(new Overtime(rs.getInt(1), rs.getString(2), rs.getString(3), getType(rs.getString(4))));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_overtime`").getValueAt(0, 0).toString();
    }

    public static String getType(String string) {
        switch (string) {
            case "TYPE_1":
                return TYPE_1;
            case "TYPE_2":
                return TYPE_2;
            case "TYPE_3":
                return TYPE_3; case "TYPE_4":
                return TYPE_4;

        }
        return null;
    }

    public String getTypeName() {
        switch (calc_type) {

            case "working hours - shift hours":
                return "TYPE_1";
            case "leave time  - shift leave time":
                return "TYPE_2";
            case "early income":
                return "TYPE_3";
            case "no overtime":
                return "TYPE_4";

        }
        return null;
    }
}
