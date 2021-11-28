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
public class Shifts {

    int id;
    String name;

    int overtime_id;
    String overtime;

    int workdays_id;
    String workdays;

    String startTime;
    String endTime;
    String isDaily;
    String lateTime;
    String earlyLeave;

    public Shifts() {
    }

    public Shifts(int id, String name, String overtime, String workdays, String startTime, String endTime, String isDaily, String lateTime, String earlyLeave) {
        this.id = id;
        this.name = name;
        this.overtime = overtime;
        this.workdays = workdays;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDaily = isDaily;
        this.lateTime = lateTime;
        this.earlyLeave = earlyLeave;
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

    public int getOvertime_id() {
        return overtime_id;
    }

    public void setOvertime_id(int overtime_id) {
        this.overtime_id = overtime_id;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public int getWorkdays_id() {
        return workdays_id;
    }

    public void setWorkdays_id(int workdays_id) {
        this.workdays_id = workdays_id;
    }

    public String getWorkdays() {
        return workdays;
    }

    public void setWorkdays(String workdays) {
        this.workdays = workdays;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsDaily() {
        return isDaily;
    }

    public void setIsDaily(String isDaily) {
        this.isDaily = isDaily;
    }

    public String getLateTime() {
        return lateTime;
    }

    public void setLateTime(String lateTime) {
        this.lateTime = lateTime;
    }

    public String getEarlyLeave() {
        return earlyLeave;
    }

    public void setEarlyLeave(String earlyLeave) {
        this.earlyLeave = earlyLeave;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_shifts`(`id`, `name`, `overtime_id`, `working_days_id`, `start_time`, `end_time`, `in_same_day`, `late_time`, `early_time`) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, overtime_id);
        ps.setInt(4, workdays_id);
        ps.setString(5, startTime);
        ps.setString(6, endTime);
        ps.setString(7, isDaily);
        ps.setString(8, lateTime);
        ps.setString(9, earlyLeave);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `att_shifts` SET `name`=?,`overtime_id`=?,`working_days_id`=?,`start_time`=?,`end_time`=?,`in_same_day`=?,`late_time`=?,`early_time`=? WHERE `id`=?");
        ps.setInt(9, id);
        ps.setString(1, name);
        ps.setInt(2, overtime_id);
        ps.setInt(3, workdays_id);
        ps.setString(4, startTime);
        ps.setString(5, endTime);
        ps.setString(6, isDaily);
        ps.setString(7, lateTime);
        ps.setString(8, earlyLeave);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_shifts` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Shifts> getData() throws Exception {
        ObservableList<Shifts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `att_shifts`.`id`, `att_shifts`.`name` as 'shift',`att_overtime`.`name` as 'overtime', `att_working_days`.`name` as 'workingdays', `att_shifts`.`start_time`, `att_shifts`.`end_time`, `att_shifts`.`in_same_day`, `att_shifts`.`late_time`, `att_shifts`.`early_time` FROM `att_shifts`,`att_working_days`,`att_overtime` WHERE `att_shifts`.`overtime_id`=`att_overtime`.`id` and `att_shifts`.`working_days_id` =`att_working_days`.`id`");
        while (rs.next()) {
            data.add(new Shifts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }
    public static ObservableList<Shifts> getData(int empId) throws Exception {
        ObservableList<Shifts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `att_shifts`.`id`, `att_shifts`.`name` as 'shift',`att_overtime`.`name` as 'overtime', `att_working_days`.`name` as 'workingdays', `att_shifts`.`start_time`, `att_shifts`.`end_time`, `att_shifts`.`in_same_day`, `att_shifts`.`late_time`, `att_shifts`.`early_time` FROM `att_shifts`,`att_working_days`,`att_overtime` WHERE `att_shifts`.`overtime_id`=`att_overtime`.`id` and `att_shifts`.`working_days_id` =`att_working_days`.`id` and `att_shifts`.`id` in (SELECT `shift_id` from `att_employee_shifts` where `employee_id`='"+empId+"')");
        while (rs.next()) {
            data.add(new Shifts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_shifts`").getValueAt(0, 0).toString();
    }

}
