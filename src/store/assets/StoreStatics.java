/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.assets;

import java.sql.ResultSet;
import javafx.scene.chart.XYChart;

/**
 *
 * @author AHMED
 */
public class StoreStatics {

    public static String getAllTrashed() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `st_store_trashed`");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static String getAllShorts() throws Exception {
        return "0";
    }

    public static String getAllPendedPermission() throws Exception {
        String data = "0";
        //entrance
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `st_invoices` WHERE (`st_invoices`.`statue`='pending' OR `st_invoices`.`statue`='penupdate')");
        while (rs.next()) {
            data = Integer.toString(Integer.parseInt(data) + rs.getInt(1));
        }
        //exit
        rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) from cli_invoices WHERE (`cli_invoices`.`statue`='pending' OR `cli_invoices`.`statue`='penupdate')");
        while (rs.next()) {
            data = Integer.toString(Integer.parseInt(data) + rs.getInt(1));
        }
        //cli retu
        rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `cli_returned_invoice` where (`cli_returned_invoice`.`statue`='pending' OR `cli_returned_invoice`.`statue`='penupdate')");
        while (rs.next()) {
            data = Integer.toString(Integer.parseInt(data) + rs.getInt(1));
        }
        //prov ret
        rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `st_returned_invoice` where (`st_returned_invoice`.`statue`='pending' OR `st_returned_invoice`.`statue`='penupdate')");
        while (rs.next()) {
            data = Integer.toString(Integer.parseInt(data) + rs.getInt(1));
        }
        return data;

    }

    public static String getAllProducts() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `st_products`");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static XYChart.Series getEntrances() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( st_per_entrance.id ) cnt FROM sys_months LEFT JOIN st_per_entrance ON (sys_months.monthNumber = MONTH(st_per_entrance.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {

            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1;
    }

    public static XYChart.Series getExit() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( st_per_exit.id ) cnt FROM sys_months LEFT JOIN st_per_exit ON (sys_months.monthNumber = MONTH(st_per_exit.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {

            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1;
    }

    public static XYChart.Series getProviderReturns() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( st_per_provider_returned.id ) cnt FROM sys_months LEFT JOIN st_per_provider_returned ON (sys_months.monthNumber = MONTH(st_per_provider_returned.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {

            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1;
    }

    public static XYChart.Series getClientReturns() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( st_per_client_returned.id ) cnt FROM sys_months LEFT JOIN st_per_client_returned ON (sys_months.monthNumber = MONTH(st_per_client_returned.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {

            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1;
    }

    public static XYChart.Series getTrashed() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( st_store_trashed.id ) cnt FROM sys_months LEFT JOIN st_store_trashed ON (sys_months.monthNumber = MONTH(st_store_trashed.date) ) GROUP BY sys_months.month ORDER BY  sys_months.id ASC");
        while (rs.next()) {

            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1;
    }

}
