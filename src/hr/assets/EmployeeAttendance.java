/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.assets;

/**
 *
 * @author AHMED
 */
public class EmployeeAttendance {

    int empId;
    String empName;
    String date;
    String shiftName;
    String shiftStart;
    String shiftEnd;
    String empAttend;
    String empLeave;
    String empLate;
    String earlyLeave;
    String empOvertime;
    String Statue;
    String salaryValue;
    String notes;

    public EmployeeAttendance() {
    }

    public EmployeeAttendance(int empId, String empName, String date, String shiftName, String shiftStart, String shiftEnd, String empAttend, String empLeave, String empLate, String earlyLeave, String empOvertime, String Statue, String salaryValue, String notes) {
        this.empId = empId;
        this.empName = empName;
        this.date = date;
        this.shiftName = shiftName;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.empAttend = empAttend;
        this.empLeave = empLeave;
        this.empLate = empLate;
        this.earlyLeave = earlyLeave;
        this.empOvertime = empOvertime;
        this.Statue = Statue;
        this.salaryValue = salaryValue;
        this.notes = notes;
    }

    public String getEarlyLeave() {
        return earlyLeave;
    }

    public void setEarlyLeave(String earlyLeave) {
        this.earlyLeave = earlyLeave;
    }

    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public String getEmpAttend() {
        return empAttend;
    }

    public void setEmpAttend(String empAttend) {
        this.empAttend = empAttend;
    }

    public String getEmpLeave() {
        return empLeave;
    }

    public void setEmpLeave(String empLeave) {
        this.empLeave = empLeave;
    }

    public String getEmpLate() {
        return empLate;
    }

    public void setEmpLate(String empLate) {
        this.empLate = empLate;
    }

    public String getEmpOvertime() {
        return empOvertime;
    }

    public void setEmpOvertime(String empOvertime) {
        this.empOvertime = empOvertime;
    }

    public String getStatue() {
        return Statue;
    }

    public void setStatue(String Statue) {
        this.Statue = Statue;
    }

    public String getSalaryValue() {
        return salaryValue;
    }

    public void setSalaryValue(String salaryValue) {
        this.salaryValue = salaryValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
