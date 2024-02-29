package com.example.phpgui.Controller;

import com.example.phpgui.App;
import com.example.phpgui.Utils.MySqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import static com.example.phpgui.Utils.MySqlConnection.hashPassword;

public class TilmeldController {

    private MySqlConnection mysqlConnection;


    @FXML
    Text SkiftLoginKnap;

    @FXML
    TextField NavnTilmeld;
    @FXML
    TextField MailTilmeld;
    @FXML
    TextField telefonTilmeld;
    @FXML
    PasswordField KodeTilmeld;



    @FXML
    private void initialize() {
        mysqlConnection = new MySqlConnection(); // Initialize MySqlConnection
    }

    @FXML
    private void opretKontoAction(ActionEvent event) throws IOException, SQLException {
        Connection connection = mysqlConnection.getConnection();
        String TilmeldNavn = NavnTilmeld.getText();
        String TilmeldMail = MailTilmeld.getText();
        String TilmeldTelefon = telefonTilmeld.getText();
        String TilmeldKodeord = KodeTilmeld.getText();
        if (mysqlConnection.userSignUp(TilmeldNavn, TilmeldTelefon, TilmeldMail, TilmeldKodeord)) {
            System.out.println("Det Virker");
        }else{
            System.out.println("Der fejl");
        }
        connection.close();
    }

    @FXML
    private void SkiftTilLogin(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/Login.fxml");
    }

    @FXML
    private void EnterPressed(KeyEvent event) throws IOException, SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            // Handle the Enter key press by calling the LoginButtonAction method
            try {
                opretKontoAction(new ActionEvent());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                // Handle any exceptions that occur during the execution
            }
        }
    }


}
