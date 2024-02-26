package com.example.phpgui.Controller;
import com.example.phpgui.App;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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
    Text SkiftTilmeldKnap;




    @FXML
    private void initialize() {
        mysqlConnection = new MySqlConnection(); // Initialize MySqlConnection
    }

    @FXML
    private void LoginButtonAction(ActionEvent event) throws IOException {
        String Brugernavn = UserLogin.getText();
        String Kodeord = KodeLogin.getText();
        //String password = "Wentzel";
        String hashedPassword = hashPassword(Kodeord);

        try {
            Connection connection = mysqlConnection.getConnection();

            // Check if the entered user credentials are correct
            if (isValidUser(Brugernavn, Kodeord, connection)) {

                System.out.println("Det Virker!");
                App m = new App();
                m.changeScene("StartSide.fxml");

            } else {
                System.out.println("Invalid Brugernavn or password");
                // Show an error message or handle failed login
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        } finally {
            mysqlConnection.closeConnection();
        }
    }
     @FXML
    private void SkiftTilTilmeld(MouseEvent event) throws IOException {
         App m = new App();
         m.changeScene("Tilmeld.fxml");
    }



    private boolean isValidUser(String Brugernavn, String Kodeord, Connection connection) throws SQLException {
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

    // Method to hash a password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Method to verify a password against its hash
    public static boolean verifyPassword(String Kodeord, String hashedPassword) {
        return BCrypt.checkpw(Kodeord, hashedPassword);
    }

}
