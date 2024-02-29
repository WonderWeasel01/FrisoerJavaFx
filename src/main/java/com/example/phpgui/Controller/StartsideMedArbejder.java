package com.example.phpgui.Controller;

import com.example.phpgui.App;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class StartsideMedArbejder {
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
}
