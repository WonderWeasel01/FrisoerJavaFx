package com.example.phpgui.Controller;

import com.example.phpgui.App;
import com.example.phpgui.Utils.UseCase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class StartsideAdminController {

    UseCase UC = new UseCase();

    @FXML
    HBox PrisListeButton;
    @FXML
    HBox kalenderButton;



    @FXML
    private void skiftTilKalender(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/KalenderAdmin.fxml");
    }
    @FXML
    private void skiftTilPrisListe(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/PrisListe.fxml");
    }

    @FXML
    private void logUd(MouseEvent event) throws IOException {
        UC.logUd();
        App m = new App();
        m.changeScene("FXML/Login.fxml");
    }
}
