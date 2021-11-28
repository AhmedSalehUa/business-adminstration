/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts.assets;

import java.sql.ResultSet;
import javafx.scene.chart.XYChart;

/**
 *
 * @author AHMED
 */
public class AccountsStatics {

    public static String getCurrenAccount() throws Exception {
        String data = "0";
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_returned_invoice` WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
//        while (rs.next()) {
//            data = rs.getString(1);
//        }
        return data;
    }

    public static String getClientsNotPaied() throws Exception {
        String data = "0";
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_returned_invoice` WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
//        while (rs.next()) {
//            data = rs.getString(1);
//        }
        return data;
    }

    public static String getProviderNotPaid() throws Exception {
        String data = "0";
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_returned_invoice` WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
//        while (rs.next()) {
//            data = rs.getString(1);
//        }
        return data;
    }

    public static String getUncollectedCheeks() throws Exception {
        String data = "0";
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_returned_invoice` WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())");
//        while (rs.next()) {
//            data = rs.getString(1);
//        }
        return data;
    }

    public static XYChart.Series getClientsPaysChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_clients.id ) cnt FROM sys_months LEFT JOIN cli_clients ON (sys_months.monthNumber = MONTH(cli_clients.date_time) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
//        while (rs.next()) {
//             
//            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
//        }
        return series1;
    }

    public static XYChart.Series getProviderPaysChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_clients.id ) cnt FROM sys_months LEFT JOIN cli_clients ON (sys_months.monthNumber = MONTH(cli_clients.date_time) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
//        while (rs.next()) {
//             
//            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
//        }
        return series1;
    }

    public static XYChart.Series getTransactionsChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_clients.id ) cnt FROM sys_months LEFT JOIN cli_clients ON (sys_months.monthNumber = MONTH(cli_clients.date_time) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
//        while (rs.next()) {
//             
//            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
//        }
        return series1;
    }

}
