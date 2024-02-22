package com.example.phpgui.Controller;
import com.example.phpgui.App;
import org.mindrot.jbcrypt.BCrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.phpgui.Utils.MySqlConnection;

public class LoginController {

    private MySqlConnection mysqlConnection;

    @FXML
    TextField UserLogin;
    @FXML
    PasswordField KodeLogin;
    @FXML
    Button LoginButton;




    @FXML
    private void initialize() {
        mysqlConnection = new MySqlConnection(); // Initialize MySqlConnection
    }

    @FXML
    private void LoginButtonAction(ActionEvent event) throws IOException {
        String username = UserLogin.getText();
        String password = KodeLogin.getText();
        //String password = "Wentzel";
        String hashedPassword = hashPassword(password);

        try {
            Connection connection = mysqlConnection.getConnection();

            // Check if the entered user credentials are correct
            if (isValidUser(username, password, connection)) {

                System.out.println("Det Virker!");
                App m = new App();
                m.changeScene("StartSide.fxml");

            } else {
                System.out.println("Invalid username or password");
                // Show an error message or handle failed login
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        } finally {
            mysqlConnection.closeConnection();
        }
    }

    private boolean isValidUser(String username, String password, Connection connection) throws SQLException {
        System.out.println("Checking user credentials: " + username + password);

        String sql = "SELECT password FROM user WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                return verifyPassword(password, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to hash a password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Method to verify a password against its hash
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
