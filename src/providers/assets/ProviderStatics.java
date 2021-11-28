/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package providers.assets;

import java.sql.ResultSet;
import javafx.scene.chart.XYChart;

/**
 *
 * @author AHMED
 */
public class ProviderStatics {

    public static String getAllProviders() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `st_provider`");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;

    }

    public static String getAllReturnedInvoices() throws Exception {
         String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `st_returned_invoice` WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data; 
    }

    public static String getAllInvoices() throws Exception {
       String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `st_invoices` WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data; 
    }
     public static XYChart.Series getInvoicesByMonth() throws Exception {
        XYChart.Series series1 = new XYChart.Series();
        
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( st_invoices.id ) cnt FROM sys_months LEFT JOIN st_invoices ON (sys_months.monthNumber = MONTH(st_invoices.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {
             
            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1; 
    }
     public static XYChart.Series getReturnedInvoicesByMonth() throws Exception {
          XYChart.Series series1 = new XYChart.Series();
        
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( st_returned_invoice.id ) cnt FROM sys_months LEFT JOIN st_returned_invoice ON (sys_months.monthNumber = MONTH(st_returned_invoice.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {
             
            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1; 
    }
}
