/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessadministration;

import static assets.classes.statics.USER_ID;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Logs {

    public static boolean Add(String Transaction) throws Exception {
        Preferences  prefs = Preferences.userNodeForPackage(BusinessAdministration.class);
        PreparedStatement ps = db.get.Prepare("INSERT INTO `sys_logs`(`user_id`, `transaction`) VALUES (?,?)");
        ps.setInt(1, Integer.parseInt(prefs.get(USER_ID, "0")));
        ps.setString(2, Transaction);  
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

    public static ObservableList<String> getData() throws Exception {
        ObservableList<String> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("");
        while (rs.next()) {
//            data.add();
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM ``").getValueAt(0, 0).toString();
    }
}
