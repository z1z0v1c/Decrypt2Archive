package databases.impl;

import databases.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/// @author Aleksandar Zizovic
public final class SqliteDatabase implements Database {
    private Connection connection;

    public SqliteDatabase(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        createTable("actions");
    }

    /// Select all rows in the warehouses table
    @Override
    public String selectKey(String file) throws SQLException {
        String key = "";

        Statement stmt = connection.createStatement();

        String sql = "SELECT KEY FROM AES_KEYS WHERE FILE='" + file + "'";

        ResultSet rs = stmt.executeQuery(sql);

        // Loop through the result set
        while (rs.next()) {
            key = rs.getString("KEY");
        }

        return key;
    }

    @Override
    public void log(String action, String date) throws SQLException {
        String sql = "INSERT INTO actions(action,date) VALUES(?,?)";

        PreparedStatement insert = connection.prepareStatement(sql);

        insert.setString(1, action);
        insert.setString(2, date);

        insert.executeUpdate();
    }

    private void createTable(String table) throws SQLException {
        Statement stmt = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS " + table + " (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	action text NOT NULL,\n"
                + "	date text NOT NULL\n"
                + ");";

        stmt.execute(sql);
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
