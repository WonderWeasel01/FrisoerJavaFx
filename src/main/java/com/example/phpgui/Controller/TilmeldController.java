package com.example.phpgui.Controller;

import com.example.phpgui.App;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class TilmeldController {


    @FXML
    Text SkiftLoginKnap;


    @FXML
    private void SkiftTilLogin(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("Login.fxml");
    }
}
