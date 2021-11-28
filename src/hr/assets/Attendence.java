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
public class Attendence {

    int id;
    int empId;
    String empName;
    String date;
    String time;
    String statue;
//
//    public Attendence(int empId, String empName, String date, String time) {
//        this.empId = empId;
//        this.empName = empName;
//        this.date = date;
//        this.time = time;
//    }

    public Attendence() {
    }

    public Attendence(int empId, String empName, String date, String time, String statue) {
        this.empId = empId;
        this.empName = empName;
        this.date = date;
        this.time = time;
        this.statue = statue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_attendance`(`employee_id`, `date`, `time`, `statue`) VALUES (?,?,?,?)");
        ps.setInt(1, empId);
        ps.setString(2, date);
        ps.setString(3, time);
        ps.setString(4, statue);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `att_attendance` SET `employee_id`=?,`date`=?,`time`=?,`statue`=? WHERE `id`=?");
        ps.setInt(1, empId);
        ps.setString(2, date);
        ps.setString(3, time);
        ps.setString(4, statue);
        ps.setInt(5, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_attendance` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Attendence> getData() throws Exception {
        ObservableList<Attendence> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `att_attendance`.`employee_id`,`att_employee`.`name`, `att_attendance`.`date`, `att_attendance`.`time`,`att_attendance`.`statue` FROM `att_attendance`,`att_employee` WHERE `att_employee`.`id` = `att_attendance`.`employee_id` ORDER BY `employee_id` ,`date`,`time`");
        while (rs.next()) {
            data.add(new Attendence(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static ObservableList<Attendence> getDataInInterval(String start, String end) throws Exception {
        ObservableList<Attendence> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `att_attendance`.`employee_id`,`att_employee`.`name`, `att_attendance`.`date`, `att_attendance`.`time`,`att_attendance`.`statue` FROM `att_attendance`,`att_employee` WHERE `att_employee`.`id` = `att_attendance`.`employee_id` and `att_attendance`.`date` >= '" + start + "' and `att_attendance`.`date` <= '" + end + "' ORDER BY `employee_id` ,`date`,`time`");
        while (rs.next()) {
            data.add(new Attendence(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_attendance`").getValueAt(0, 0).toString();
    }

}
