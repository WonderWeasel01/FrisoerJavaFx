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
    TextField userLogin;
    @FXML
    PasswordField kodeLogin;
    @FXML
    Button loginButton;
    @FXML
    Text skiftTilmeldKnap;




    @FXML
    private void initialize() {
        mysqlConnection = new MySqlConnection(); // Initialize MySqlConnection
    }

    @FXML
    private void LoginButtonAction(ActionEvent event) throws IOException, SQLException {
        Connection connection = mysqlConnection.getConnection();
        String Brugernavn = userLogin.getText();
        String Kodeord = kodeLogin.getText();


        String hashedPassword = hashPassword(Kodeord);

            // Check if the entered user credentials are correct
            if (mysqlConnection.isValidUser(Brugernavn, Kodeord)) {

                System.out.println("Det Virker!");
                App m = new App();
                m.changeScene("StartSide.fxml");

            } else {
                System.out.println("Invalid Brugernavn or password");
                // Show an error message or handle failed login
            } connection.close();
    }


     @FXML
    private void SkiftTilTilmeld(MouseEvent event) throws IOException {
         App m = new App();
         m.changeScene("Tilmeld.fxml");
    }

    // Method to hash a password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }




}
