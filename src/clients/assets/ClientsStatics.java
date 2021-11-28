/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.assets;

import java.sql.ResultSet;
import javafx.scene.chart.XYChart;

/**
 *
 * @author AHMED
 */
public class ClientsStatics {

    public static String getAllClient() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `cli_clients`");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static String getNewClient() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_clients` WHERE MONTH(date_time) = MONTH(CURRENT_DATE()) AND YEAR(date_time) = YEAR(CURRENT_DATE())");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static String getAllInvoice() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_invoices` WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static String getAllReturnes() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_returned_invoice` WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static XYChart.Series getAllClientChart() throws Exception {  
        XYChart.Series series1 = new XYChart.Series();
        
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_clients.id ) cnt FROM sys_months LEFT JOIN cli_clients ON (sys_months.monthNumber = MONTH(cli_clients.date_time) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {
             
            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1; 
    }
 

    public static XYChart.Series getAllInvoiceChart() throws Exception {
         XYChart.Series series1 = new XYChart.Series();
        
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_invoices.id ) cnt FROM sys_months LEFT JOIN cli_invoices ON (sys_months.monthNumber = MONTH(cli_invoices.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {
             
            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1; 
    }

    public static XYChart.Series getAllReturnesChart() throws Exception {
         XYChart.Series series1 = new XYChart.Series();
        
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_returned_invoice.id ) cnt FROM sys_months LEFT JOIN cli_returned_invoice ON (sys_months.monthNumber = MONTH(cli_returned_invoice.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {
             
            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1; 
    }

}
