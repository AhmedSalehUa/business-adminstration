/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesTrack.assets;

import javafx.scene.chart.XYChart;

/**
 *
 * @author AHMED
 */
public class SalesTrackStatics {

    public static String getAllLines() throws Exception {
        String data = "0";
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_clients`");
//        while (rs.next()) {
//            data = rs.getString(1);
//        }
        return data;
    }

    public static String getNewClients() throws Exception {
        String data = "0";
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_clients`");
//        while (rs.next()) {
//            data = rs.getString(1);
//        }
        return data;
    }

    public static String getNewOrders() throws Exception {
        String data = "0";
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_clients`");
//        while (rs.next()) {
//            data = rs.getString(1);
//        }
        return data;
    }

    public static String getNewReturned() throws Exception {
        String data = "0";
//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_clients`");
//        while (rs.next()) {
//            data = rs.getString(1);
//        }
        return data;
    }

    public static XYChart.Series getAllLinesChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_clients.id ) cnt FROM sys_months LEFT JOIN cli_clients ON (sys_months.monthNumber = MONTH(cli_clients.date_time) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
//        while (rs.next()) {
//             
//            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
//        }
        return series1;
    }

    public static XYChart.Series getNewClientsChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_clients.id ) cnt FROM sys_months LEFT JOIN cli_clients ON (sys_months.monthNumber = MONTH(cli_clients.date_time) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
//        while (rs.next()) {
//             
//            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
//        }
        return series1;
    }

    public static XYChart.Series getNewOrdersChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_clients.id ) cnt FROM sys_months LEFT JOIN cli_clients ON (sys_months.monthNumber = MONTH(cli_clients.date_time) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
//        while (rs.next()) {
//             
//            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
//        }
        return series1;
    }

    public static XYChart.Series getNewReturnedChart() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

//        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( cli_clients.id ) cnt FROM sys_months LEFT JOIN cli_clients ON (sys_months.monthNumber = MONTH(cli_clients.date_time) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
//        while (rs.next()) {
//             
//            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
//        }
        return series1;
    }

}
