package com.example.phpgui.Controller;

import com.example.phpgui.App;
import com.example.phpgui.Utils.UseCase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class KalenderAdminController {



    @FXML
    DatePicker adminKalender;
    @FXML
    HBox hjemButton;
    @FXML
    HBox prislisteButton;

    private UseCase UC = new UseCase();


    @FXML
    private void logUd(MouseEvent event) throws IOException {
        UC.logUd();
        App m = new App();
        m.changeScene("FXML/Login.fxml");
    }

    @FXML
    private void adminSeKalender(MouseEvent event)throws  IOException{
        UC.getTidsbestillinger(adminKalender.getValue());

    }

    @FXML
    private void skiftTilHjem(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/KalenderAdmin.fxml");
    }
    @FXML
    private void skiftTilPrisliste(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/PrisListe.fxml");
    }
}
