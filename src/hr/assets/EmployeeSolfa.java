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
import javax.swing.JTable; 

/**
 *
 * @author ahmed
 */
public class EmployeeSolfa {

    int id;
    int employee_id;
    String employee; 
    String amount;
    String date;
    
    public EmployeeSolfa(int id, int employee_id, String amount, String date) {
        this.id = id;
        this.employee_id = employee_id; 
        this.amount = amount;
        this.date = date;
    }

    public EmployeeSolfa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
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

    public boolean Add() throws Exception {

        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_employee_solfa`(`id`, `emp_id`, `amount`, `date`) VALUES (?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, employee_id);
        ps.setString(3, amount);
        ps.setString(4, date); 
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception { 

        PreparedStatement ps = db.get.Prepare("UPDATE `att_employee_solfa` SET `emp_id`=?,`amount`=?,`date`=? WHERE `id`=?");
        ps.setInt(1, employee_id);  
        ps.setString(2, amount);
        ps.setString(3, date);
        ps.setInt(4, id); 
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception { 
        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_employee_solfa` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<EmployeeSolfa> getData(int id) throws Exception {
        ObservableList<EmployeeSolfa> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `att_employee_solfa`.`id`,`att_employee_solfa`.`emp_id`, `att_employee_solfa`.`amount`, `att_employee_solfa`.`date` FROM  `att_employee_solfa`  WHERE `att_employee_solfa`.`emp_id`='" + id + "'");
        while (rs.next()) {
            data.add(new EmployeeSolfa(rs.getInt(1), rs.getInt(2),   rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_employee_solfa`").getValueAt(0, 0).toString();
    }
}
