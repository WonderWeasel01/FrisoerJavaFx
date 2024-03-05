package com.example.phpgui.Controller;

import com.example.phpgui.App;
import com.example.phpgui.Objects.Behandling;
import com.example.phpgui.Objects.Tidsbestilling;
import com.example.phpgui.Utils.UseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    ComboBox comboBoxMedarbejdere;

    @FXML
    ComboBox comboBoxTider;
    @FXML
    Button opretTidsbestilling;

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
        UC.tilfoejDatoTilTidsbestilling(datePicker.getValue());

        ArrayList<String> medarbejdere = UC.getMedarbejderBrugernavne();
        ObservableList<String> list = FXCollections.observableArrayList(medarbejdere);
        comboBoxMedarbejdere.setItems(list);
    }

    @FXML
    private void farvningCheckbox(ActionEvent event){
        UC.tilfoejBehandlingTilTidsbestilling(3); // 3 = farvning id i DB

    }

    @FXML
    private void klipMand(ActionEvent event){
        UC.tilfoejBehandlingTilTidsbestilling(1); // 1 = klip(mand) id i DB
    }


    @FXML
    private void comboBoxMedarbejderSelect(ActionEvent event){
        String s = comboBoxMedarbejdere.getSelectionModel().getSelectedItem().toString();
        UC.tilfoejMedarbejderTilTidsbestilling(s);

        ArrayList<LocalTime> ledigeTider = UC.ledigeTider();
        ObservableList<LocalTime> list = FXCollections.observableArrayList(ledigeTider);
        comboBoxTider.setItems(list);


    }

    @FXML
    private void ComboBoxTiderSelect(){
        LocalTime startTidspunkt = (LocalTime) comboBoxTider.getValue();
        UC.tilfoejTidspunktTilTidsbestilling(startTidspunkt);
    }

    @FXML
    private void onOpretTidsbestillingClick(ActionEvent event){
        UC.tilfoejBrugerIDtilTidsbestilling();
        UC.opretTidsbestilling();
    }


    @FXML
    private void logUd(MouseEvent event) throws IOException {
        UC.logUd();
        App m = new App();
        m.changeScene("FXML/Login.fxml");

    }


}
