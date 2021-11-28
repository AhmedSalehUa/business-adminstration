/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.assets;

import java.sql.ResultSet;
import javafx.scene.chart.XYChart;

/**
 *
 * @author AHMED
 */
public class hrStatics {

    public static String getAllEmploye() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `att_employee`");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static String getNewAttend() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `att_attendance`  WHERE DAY(date) = DAY(CURRENT_DATE()) AND Month(date) = Month(CURRENT_DATE())");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static String getNewLeave() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(att_employee.`id`) FROM `att_employee`  WHERE att_employee.`id` not in ( SELECT att_attendance.employee_id  FROM `att_attendance`  WHERE DAY(date) = DAY(CURRENT_DATE()) AND Month(date) = Month(CURRENT_DATE()))");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static String getNewHoliday() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `att_employee`");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static XYChart.Series getAttendChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

        
        return series1;
    }

    public static XYChart.Series getLeaveChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();
         
        return series1;
    }

}
