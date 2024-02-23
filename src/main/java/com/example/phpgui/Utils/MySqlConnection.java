package com.example.phpgui.Utils;

import java.sql.*;

public class MySqlConnection {

    private Connection connection;
    private Statement stmt;

    public MySqlConnection() {
        connection = null;
        stmt = null;
    }

    public Connection getConnection() {
        // Update the connection URL, username, and password according to your MySQL setup
        String url = "jdbc:mysql://mysql63.unoeuro.com:3306/wentzelevent_dk_db_Frisoer";
        String user = "wentzelevent_dk";
        String password = "pBHeRyFdzc4G";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
