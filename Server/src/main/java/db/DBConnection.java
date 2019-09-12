/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import fileprocessor.impl.TXTFileProcessor;
import fileprocessor.impl.XLSXFileProcessor;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author luciano
 */
public final class DBConnection {

    private Connection connection;

    public DBConnection(String path) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            this.createNewTable("actions");
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

    /**
     * select all rows in the warehouses table
     */
    public String selectKey(String file) throws Exception {
        String extension = "";
        if (file.endsWith("txt")) {
            extension = "txt";
        } else if (file.endsWith("xlsx")) {
            extension = "xlsx";
        } else {
            throw new Exception("Wrong extension");
        }
        String sql = "SELECT KEY FROM AES_KEYS WHERE FILE='ExternalInput." + extension + "'";
        String key = "";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                key = rs.getString("KEY");
                System.out.println(key);
            }
        } catch (NullPointerException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return key;
    }

    public void createNewTable(String table) {
        String sql
                = //"DROP TABLE IF EXISTS " + table + ";\n" 
                "CREATE TABLE IF NOT EXISTS " + table + " (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	action text NOT NULL,\n"
                + "	date text NOT NULL\n"
                + ");";

        try {
            Statement stmt = connection.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (NullPointerException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void logToDatabase(String action, String date) {

        String sql = "INSERT INTO actions(action,date) VALUES(?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, action);
            pstmt.setString(2, date);
            pstmt.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        DBConnection dbCon = new DBConnection("C:\\Users\\luciano\\Desktop\\TTP Level2\\database.sqlite3");
//        dbCon.selectKey();
//        dbCon.selectKeyForXLSX();
//        dbCon.createNewTable("actions");
//        dbCon.logToDatabase("test", new Date(System.currentTimeMillis()).toString());
//    }
}
