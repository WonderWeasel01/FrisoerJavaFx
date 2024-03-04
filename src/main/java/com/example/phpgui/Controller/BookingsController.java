package com.example.phpgui.Controller;

import com.example.phpgui.App;
import com.example.phpgui.Objects.Behandling;
import com.example.phpgui.Objects.Tidsbestilling;
import com.example.phpgui.Utils.UseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class BookingsController {
    @FXML
    HBox mineAftalerButton;
    @FXML
    HBox hjemButton;

    @FXML
    DatePicker datePicker;
    @FXML
    CheckBox klipMand;
    @FXML
    CheckBox farvning;
    @FXML
    ComboBox comboBox;



    private UseCase UC = new UseCase();



    @FXML
    private void skiftTilMineAftaler(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/MineAftaler.fxml");
    }
    @FXML
    private void skiftTilHjem(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/StartSideKunde.fxml");
    }

    @FXML
    private void datePicker(ActionEvent evt){
        UC.tilfoejDateTimeTilTidsbestilling(datePicker.getValue());
    }

    @FXML
    private void farvningCheckbox(ActionEvent event){
        UC.tilfoejBehandlingTilTidsbestilling(3); // 3 = farvning id i DB

    }



    @FXML
    private void klipMand(ActionEvent event){
        UC.tilfoejBehandlingTilTidsbestilling(1); // 1 = klip(mand) id i DB
    }

    /*
    @FXML
    private void comboBox(ActionEvent event){
        UC.tilfoejMedarbejderTilTidsbestilling();

    }

     */




    @FXML
    private void opretTidsbestilling(){
        UC.opretTidsbestilling();
    }

    @FXML
    private void logUd(MouseEvent event) throws IOException {
        UC.logUd();
        App m = new App();
        m.changeScene("FXML/Login.fxml");
    }

}
