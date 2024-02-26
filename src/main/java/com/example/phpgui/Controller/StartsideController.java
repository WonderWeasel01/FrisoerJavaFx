package com.example.phpgui.Controller;

import com.example.phpgui.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class StartsideController {
    @FXML
    Button bookingsButton;



    @FXML
    private void skiftTilBookings(ActionEvent event) throws IOException {
        App m = new App();
        m.changeScene("Bookings.fxml");
    }
}
