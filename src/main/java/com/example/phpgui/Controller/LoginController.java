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
    private void LoginButtonAction(ActionEvent event) throws IOException, SQLException {
        App m = new App();

        Bruger bruger = UC.login(userLogin.getText(),kodeLogin.getText());
        int rolle = bruger.getRolle();

        //Skifter til forskellige startsider baseret på brugerens rolle
        switch (rolle) {
            case 1:
                m.changeScene("FXML/StartSideAdmin.fxml");
                break;
            case 2:
                m.changeScene("FXML/StartSideMedarbejder.fxml");
                break;
            case 3:
                m.changeScene("FXML/StartSideKunde.fxml");
                System.out.println(bruger);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + bruger.getRolle());
                //Indsæt "Forkert password" besked
        }

    }


     @FXML
    private void SkiftTilTilmeld(MouseEvent event) throws IOException {
         App m = new App();
         m.changeScene("FXML/Tilmeld.fxml");
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

