package com.example.phpgui.Controller;

import com.example.phpgui.App;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class BookingsController {
    @FXML
    HBox mineAftalerButton;
    @FXML
    HBox hjemButton;



    @FXML
    private void skiftTilMineAftaler(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("MineAftaler.fxml");
    }
    @FXML
    private void skiftTilHjem(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("StartSideKunde.fxml");
    }

}
