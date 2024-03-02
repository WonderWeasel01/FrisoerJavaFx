package com.example.phpgui.Controller;

import com.example.phpgui.App;
import com.example.phpgui.Utils.UseCase;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PrisListeController {

    private UseCase UC = new UseCase();


    @FXML
    private void logUd(MouseEvent event) throws IOException {
        UC.logUd();
        App m = new App();
        m.changeScene("FXML/Login.fxml");
    }
}
