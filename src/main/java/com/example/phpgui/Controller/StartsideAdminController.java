package com.example.phpgui.Controller;

import com.example.phpgui.App;
import com.example.phpgui.Utils.UseCase;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class StartsideAdminController {

    UseCase UC = new UseCase();
    @FXML
    HBox bookingsButton;
    @FXML
    HBox mineAftalerButton;



    @FXML
    private void skiftTilBookings(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/Bookings.fxml");
    }
    @FXML
    private void skiftTilMineAftaler(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/MineAftaler.fxml");
    }

    @FXML
    private void logUd(MouseEvent event) throws IOException {
        UC.logUd();
        App m = new App();
        m.changeScene("FXML/Login.fxml");
    }
}
