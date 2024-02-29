package com.example.phpgui.Controller;
import com.example.phpgui.App;
import com.example.phpgui.Objects.Bruger;
import com.example.phpgui.Utils.UseCase;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    private UseCase UC = new UseCase();




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

    }

    @FXML
    private void LoginButtonAction(ActionEvent event) throws IOException, SQLException {
        App m = new App();

        Bruger bruger;
        bruger = UC.login(userLogin.getText(),kodeLogin.getText());
        String rolle = bruger.getRolle();

        //Skifter til forskellige startsider baseret på brugerens rolle
        switch (rolle) {
            case null:
                //Ingen rolle fundet
                System.out.println("Forkert password");
                break;
            case "Admin":
                m.changeScene("StartSideAdmin.fxml");
                break;
            case "Medarbejder":
                m.changeScene("StartSideMedarbejder.fxml");
                break;
            case "Kunde":
                m.changeScene("StartSideKunde.fxml");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + bruger.getRolle());
                //Indsæt "Forkert password" besked
        }

    }


     @FXML
    private void SkiftTilTilmeld(MouseEvent event) throws IOException {
         App m = new App();
         m.changeScene("Tilmeld.fxml");
    }

    @FXML
    private void EnterPressed(KeyEvent event) throws IOException, SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            // Handle the Enter key press by calling the LoginButtonAction method
            try {
                LoginButtonAction(new ActionEvent());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                // Handle any exceptions that occur during the execution
            }
        }
    }

}

