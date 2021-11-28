/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import assets.classes.AlertDialogs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

/**
 *
 * @author ahmed
 */
public class lite {

    Connection connection;
    private static String url = "";
    private static Connection con;

    public lite() {

    }

    private static void setURL() {
        url = "jdbc:sqlite:src/db/elbarbary.db";

    }
//
//    public static boolean setConnection() {
//
//        try {
//            setURL();
//            Class.forName("org.sqlite.JDBC");
//            con = DriverManager.getConnection(url);
//            return true;
//        } catch (SQLException ex) {
//            AlertDialogs.showErrors(ex);
//            return false;
//
//        } catch (ClassNotFoundException ex) {
//            AlertDialogs.showErrors(ex);
//            return false;
//        }
//
//    }
//
//    public static Connection getCon() {
//        try {
//            if (con == null) {
//                setConnection();
//            }else{
//            return con;
//            }
//        } catch (Exception ex) {
//            AlertDialogs.showErrors(ex);
//        }
//        return con;
//    }
//
//    public static boolean getPermission(String per) {
//        try {
//
//            ResultSet rs = con.createStatement().executeQuery("select value from users_permissions where privileges='" + per + "'");
//            while (rs.next()) {
//                return Boolean.parseBoolean(rs.getString(1));
//            }
//            return false;
//        } catch (SQLException ex) {
//
//            System.out.println("SQLError: " + ex.getMessage());
//            return false;
//        }
//    }
//
//    public static boolean addToData(String sql) {
//        try {
//
//            Statement st = con.createStatement();
//            st.executeUpdate(sql);
//            return true;
//        } catch (SQLException ex) {
//            AlertDialogs.showErrors(ex);s
//            return false;
//        }
//
//    }
//
//    public static boolean excuteNon(String sql) {
//        try {
//            setConnection();
//            Statement st = con.createStatement();
//            st.execute(sql);
//            return true;
//        } catch (SQLException ex) {
//            AlertDialogs.showErrors(ex);
//            return false;
//        }
//
//    }
}
