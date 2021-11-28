/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigator;

import clients.assets.ClientsStatics;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import providers.assets.ProviderStatics;

/**
 *
 * @author AHMED
 */
public class HomeStatics {

    public static LineChart getClientLineChart() throws Exception {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String, Number> lineChart
                = new LineChart<String, Number>(xAxis, yAxis);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
        lineChart.setTitle("حركة العملاء خلال  " + LocalDate.now().format(format));

        XYChart.Series series1 = ClientsStatics.getAllInvoiceChart();
        series1.setName("الاوردرات");

        XYChart.Series series2 = ClientsStatics.getAllReturnesChart();
        series2.setName("المرتجعات");

        XYChart.Series series3 = ClientsStatics.getAllClientChart();
        series3.setName("العملاء");

        lineChart.getData().addAll(series1, series2, series3);

        return lineChart;
    }

    public static LineChart getProviersLineChart() throws Exception {
        final CategoryAxis ProviderxAxis = new CategoryAxis();
        final NumberAxis ProvideryAxis = new NumberAxis();
        ProviderxAxis.setLabel("Month");
        final LineChart<String, Number> ProviderlineChart
                = new LineChart<String, Number>(ProviderxAxis, ProvideryAxis);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
        ProviderlineChart.setTitle("حركة الموردين خلال  " + LocalDate.now().format(format));

        XYChart.Series Providerseries1 = ProviderStatics.getInvoicesByMonth();
        Providerseries1.setName("الاوردرات");

        XYChart.Series Providerseries2 = ProviderStatics.getReturnedInvoicesByMonth();
        Providerseries2.setName("المرتجعات");

        ProviderlineChart.getData().addAll(Providerseries1, Providerseries2);

        return ProviderlineChart;
    }

    public static PieChart getProductCatPie() throws Exception {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_category.name,count(st_products.id) from st_products,sys_category where sys_category.id=st_products.cat_id GROUP BY st_products.cat_id");
        while (rs.next()) {
            pieChartData.add(new PieChart.Data(rs.getString(1), rs.getInt(2)));
        }
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("توزيع المنتجات على التصنيفات");
        chart.setLabelLineLength(10);

        chart.setLegendSide(Side.LEFT);

        return chart;
    }

    public static BarChart getAccountBalance() throws Exception {
        String austria = "Austria";
        String brazil = "Brazil";
        String france = "France";
        String italy = "Italy";
        String usa = "USA";
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc
                = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("حركة الحسابات");
        yAxis.setLabel("المبلغ");
        xAxis.setLabel("الحساب");

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("select  mo,transIn  from ( SELECT  sys_months.month as mo, ifnull(sum(cast(amount as unsigned)),0) as transIn FROM sys_months LEFT JOIN acc_accounts_transactions ON (sys_months.monthNumber = MONTH(acc_accounts_transactions.date) ) where acc_accounts_transactions.type='in' GROUP BY sys_months.month union all SELECT  sys_months.month as mo, 0-ifnull(sum(cast(amount as unsigned)),0) as transIn FROM sys_months LEFT JOIN acc_accounts_transactions ON (sys_months.monthNumber = MONTH(acc_accounts_transactions.date) ) where acc_accounts_transactions.type='out' GROUP BY sys_months.month ) t GROUP by mo");
        while (rs.next()) {
            XYChart.Series series1 = new XYChart.Series();
            series1.setName(rs.getString(1));
            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
            bc.getData().add(series1);
        }

        return bc;
    }

}
