/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Employee {

    int id;
    String name;
    String tele;
    String adress;
    String age;
    String salary;
    int section_id;
    String section;
    String shifts;

    public Employee(int id, String shifts) {
        this.id = id;
        this.shifts = shifts;
    }

    public Employee() {
    }

    public Employee(int id, String name, String tele, String adress, String age, String salary, String section) {
        this.id = id;
        this.name = name;
        this.tele = tele;
        this.adress = adress;
        this.age = age;
        this.salary = salary;
        this.section = section;
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

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getShifts() {
        return shifts;
    }

    public void setShifts(String shifts) {
        this.shifts = shifts;
    }

    public boolean DeleteShifts() throws Exception {
        return db.get.getReportCon().createStatement().execute("DELETE FROM `att_employee_shifts` WHERE `employee_id`='" + id + "'");
    }

    public boolean AddShifts() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_employee_shifts`(`employee_id`, `shift_id`) VALUES (?,?)");
        if (shifts.contains("-")) {
            String[] split = shifts.split("-");
            for (int i = 0; i < split.length; i++) {
                ps.setInt(1, id);
                ps.setInt(2, Integer.parseInt(split[i]));
                ps.addBatch();
            }
            ps.executeBatch();
        } else {
            ps.setInt(1, id);
            ps.setInt(2, Integer.parseInt(shifts));
            ps.execute();
        }

        return true;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_employee`(`id`, `name`, `tele`, `adress`, `age`, `salary`, `section_id`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, tele);
        ps.setString(4, adress);
        ps.setString(5, age);
        ps.setString(6, salary);
        ps.setInt(7, section_id);
        ps.execute();
        AddShifts();
        return true;
    }

    public boolean Edite() throws Exception {
        DeleteShifts();
        PreparedStatement ps = db.get.Prepare("UPDATE `att_employee` SET `name`=?,`tele`=?,`adress`=?,`age`=?,`salary`=?,`section_id`=? WHERE `id`=?");
        ps.setInt(7, id);
        ps.setString(1, name);
        ps.setString(2, tele);
        ps.setString(3, adress);
        ps.setString(4, age);
        ps.setString(5, salary);
        ps.setInt(6, section_id);
        ps.execute();
        AddShifts();
        return true;
    }

    public boolean Delete() throws Exception {
        DeleteShifts();
        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_employee` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Employee> getData() throws Exception {
        ObservableList<Employee> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `att_employee`.`id`, `att_employee`.`name`, `att_employee`.`tele`, `att_employee`.`adress`,`att_employee`.`age`, `att_employee`.`salary`,`att_section`.`name` FROM `att_employee`,`att_section` WHERE `att_section`.`id`=`att_employee`.`section_id`");
        while (rs.next()) {
            data.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        return data;
    }

    public static ObservableList<Employee> getData(int empId) throws Exception {
        ObservableList<Employee> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `employee_id`, `shift_id` FROM `att_employee_shifts` WHERE `employee_id`='" + empId + "'");
        while (rs.next()) {
            data.add(new Employee(rs.getInt(1), rs.getString(2)));
        }
        return data;
    }

    public static boolean syncData() throws Exception {
        Statement st = db.get.getReportCon().createStatement();
        ResultSet as = db.get.getReportCon().createStatement().executeQuery("SELECT `uid`, `name`, `privileges`, `password`, `groupId`, `userId`, `card` FROM `att_device_users`");
        while (as.next()) {
            st.addBatch("INSERT INTO att_employee (`id`, `name`,`section_id`) VALUES ('"+as.getString(6)+"', '"+as.getString(2)+"','1') ON DUPLICATE KEY UPDATE name = '"+as.getString(2)+"'");
        }
        st.executeBatch(); 
        return true;
    }
     public static boolean syncUpload() throws Exception {
        Statement st = db.get.getReportCon().createStatement();
        db.get.getReportCon().createStatement().execute("UPDATE att_device_users AS U1, att_employee AS U2 SET U1.name = U2.name WHERE U2.id = U1.userId");
        ResultSet as = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name` FROM `att_employee` where `id` not in (SELECT `userId` FROM `att_device_users`)");
        while (as.next()) {
            st.addBatch("INSERT INTO att_device_users (`name`, `privileges`,`userId`) VALUES ('"+as.getString(2)+"','User','"+as.getString(1)+"')");
        }
        st.executeBatch(); 
        return true;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_employee`").getValueAt(0, 0).toString();
    }
}
