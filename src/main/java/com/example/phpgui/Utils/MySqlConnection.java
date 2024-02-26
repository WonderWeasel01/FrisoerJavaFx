package com.example.phpgui.Utils;

import org.mindrot.jbcrypt.BCrypt;

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

    public boolean isValidUser(String Brugernavn, String Kodeord) throws SQLException {
        System.out.println("Checking user credentials: " + Brugernavn + Kodeord);

        String sql = "SELECT kodeord FROM Bruger WHERE Brugernavn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, Brugernavn);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("Kodeord");
                return verifyPassword(Kodeord, hashedPassword);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userSignUp(String TilmeldNavn, String TilmeldTelefon, String TilmeldMail, String TilmeldKodeord) throws SQLException {
        // Hash the password
        String hashedPassword = BCrypt.hashpw(TilmeldKodeord, BCrypt.gensalt());

        String sql = "INSERT INTO Bruger (Brugernavn, Telefonnummer, Email, Kodeord) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, TilmeldNavn);
            pstmt.setString(2, TilmeldTelefon);
            pstmt.setString(3, TilmeldMail);
            pstmt.setString(4, hashedPassword); // Store the hashed password in the database
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Sign up successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Sign up failed
    }



    // Method to verify a password against its hash
    public static boolean verifyPassword(String Kodeord, String hashedPassword) {
        return BCrypt.checkpw(Kodeord, hashedPassword);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
