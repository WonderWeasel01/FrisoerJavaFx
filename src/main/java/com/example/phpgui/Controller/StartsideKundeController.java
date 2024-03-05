package com.example.phpgui.Controller;

import com.example.phpgui.App;
import com.example.phpgui.Utils.UseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.controlsfx.control.action.Action;

import java.io.IOException;

public class StartsideKundeController {

    @FXML
    HBox bookingsButton;
    @FXML
    HBox mineAftalerButton;
    UseCase UC = new UseCase();
    @FXML
    Button kunde;



    @FXML
    private void skiftTilBookings(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/Bookings.fxml");
        System.out.println(UC.bruger);
    }
    @FXML
    private void skiftTilMineAftaler(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/MineAftaler.fxml");
    }
    @FXML
    private void onclick(ActionEvent event){
        System.out.println(UC.bruger);
    }


    /*
    @FXML
    private void logUd(MouseEvent event) throws IOException {
        UC.logUd();
        App m = new App();
        m.changeScene("FXML/Login.fxml");
    }

     */
}
