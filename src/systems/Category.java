/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Category {

    int id;
    String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static boolean Add(String name) throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sys_category`(`name`) VALUES (?)");
        ps.setString(i, name);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        int i = 1;
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        return true;
    }

    public static ObservableList<Category> getData() throws Exception {
        ObservableList<Category> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("select * from `sys_category`");
        while (rs.next()) {
             data.add(new Category(rs.getInt(1),rs.getString(2)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `sys_category`").getValueAt(0, 0).toString();
    }
}
