package com.example.phpgui.Controller;

import com.example.phpgui.App;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MineAftalerController {
    @FXML
    HBox bookingsButton;
    @FXML
    HBox hjemButton;



    @FXML
    private void skiftTilBookings(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("Bookings.fxml");
    }
    @FXML
    private void skiftTilHjem(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("StartSide.fxml");
    }
}
