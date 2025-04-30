package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Aleksandar Zizovic
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
    public String selectKey(String file) throws IOException {
        String extension;

        if (file.endsWith("txt")) {
            extension = "txt";
        } else if (file.endsWith("xlsx")) {
            extension = "xlsx";
        } else {
            throw new IOException("Wrong extension");
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
        String sql = //"DROP TABLE IF EXISTS " + table + ";\n"
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
            PreparedStatement insert = connection.prepareStatement(sql);

            insert.setString(1, action);
            insert.setString(2, date);

            insert.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
