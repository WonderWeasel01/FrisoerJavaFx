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
import java.sql.ClientInfoStatus;
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
    @FXML
    CheckBox klipDame;
    @FXML
    CheckBox skaeg;
    @FXML
    CheckBox haarsaet;

    private UseCase UC = new UseCase();

    @FXML
    private void klipDame(ActionEvent event){
        if(klipDame.isSelected()){
            if(UC.tilfoejBehandlingTilTidsbestilling(2)){
                if(comboBoxTider.getValue() != null){
                    comboBoxTider.getSelectionModel().clearSelection();
                }
                if(datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null){
                    setComboBoxTider();
                }
            }
        } else{
            comboBoxTider.getSelectionModel().clearSelection();
            UC.fjernTidspunktFraTidsbestilling();
            UC.fjernBehandlingFraTidsbestilling(2);
            if(datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null){
                setComboBoxTider();
            }
        }
    }
    @FXML
    private void skaeg(ActionEvent event) {
        if (skaeg.isSelected()) {
            if(UC.tilfoejBehandlingTilTidsbestilling(4)){
                if(comboBoxTider.getValue() != null){
                    comboBoxTider.getSelectionModel().clearSelection();
                }
                if (datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null) {
                    setComboBoxTider();
                }
            }
        } else {
            comboBoxTider.getSelectionModel().clearSelection();
            UC.fjernTidspunktFraTidsbestilling();
            UC.fjernBehandlingFraTidsbestilling(4);
            if (datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null) {
                setComboBoxTider();
            }
        }
    }

    @FXML
    private void haarsaetning(ActionEvent event){
        if(haarsaet.isSelected()){
            if(UC.tilfoejBehandlingTilTidsbestilling(5)){
                if(comboBoxTider.getValue() != null){
                    comboBoxTider.getSelectionModel().clearSelection();
                }
                if(datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null){
                  setComboBoxTider();
                }
            }
        } else {
            comboBoxTider.getSelectionModel().clearSelection();
            UC.fjernTidspunktFraTidsbestilling();
            UC.fjernBehandlingFraTidsbestilling(5);
            if (datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null) {
                setComboBoxTider();
            }
        }
    }
    @FXML
    private void skiftTilMineAftaler(MouseEvent event) throws IOException {
        App m = new App();
        SeAftalerKundeController.findBrugernavn = UseCase.bruger.getBrugernavn();
        m.changeScene("FXML/seAftalerKunde.fxml");
    }
    @FXML
    private void skiftTilHjem(MouseEvent event) throws IOException {
        App m = new App();
        m.changeScene("FXML/StartSideKunde.fxml");
    }

    @FXML
    private void datePicker(ActionEvent evt){
        if(UC.tilfoejDatoTilTidsbestilling(datePicker.getValue())){
            ArrayList<String> medarbejdere = UC.getMedarbejderBrugernavne();
            ObservableList<String> list = FXCollections.observableArrayList(medarbejdere);
            if(comboBoxMedarbejdere.getValue() == null){
                comboBoxMedarbejdere.setItems(list);
            } else{
                setComboBoxTider();
            }
        }
    }

    @FXML
    private void farvningCheckbox(ActionEvent event){
        if(farvning.isSelected()){
            if(UC.tilfoejBehandlingTilTidsbestilling(3)){
                if(comboBoxTider.getValue() != null){
                    comboBoxTider.getSelectionModel().clearSelection();
                }
                if(datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null){
                    setComboBoxTider();
                }
            }
        } else {
            comboBoxTider.getSelectionModel().clearSelection();
            UC.fjernTidspunktFraTidsbestilling();
            UC.fjernBehandlingFraTidsbestilling(3);
            if (datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null) {
                setComboBoxTider();
            }
        }
    }

    public void setComboBoxTider(){
        ArrayList<LocalTime> ledigeTider = UC.ledigeTider();
        ObservableList<LocalTime> list = FXCollections.observableArrayList(ledigeTider);
        comboBoxTider.setItems(list);
    }

    @FXML
    private void klipMand(ActionEvent event){
        if(klipMand.isSelected()){
            if(UC.tilfoejBehandlingTilTidsbestilling(1)){
                if(comboBoxTider.getValue() != null){
                    comboBoxTider.getSelectionModel().clearSelection();
                }
                if(datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null){
                    setComboBoxTider();
                }
            }
        } else {
            comboBoxTider.getSelectionModel().clearSelection();
            UC.fjernTidspunktFraTidsbestilling();
            UC.fjernBehandlingFraTidsbestilling(1);
            if (datePicker.getValue() != null && comboBoxMedarbejdere.getValue() != null) {
                setComboBoxTider();
            }
        }
    }
    @FXML
    private void comboBoxMedarbejderSelect(ActionEvent event){
        String s = comboBoxMedarbejdere.getSelectionModel().getSelectedItem().toString();
        UC.tilfoejMedarbejderTilTidsbestilling(s);
        setComboBoxTider();
    }

    @FXML
    private void ComboBoxTiderSelect(){
        if (comboBoxTider.getValue() != null){
            LocalTime startTidspunkt = (LocalTime) comboBoxTider.getValue();
            UC.tilfoejTidspunktTilTidsbestilling(startTidspunkt);
        }
    }

    @FXML
    private void onOpretTidsbestillingClick(ActionEvent event){
        UC.tilfoejBrugerIDtilTidsbestilling();
        if(UC.opretTidsbestilling()){
            comboBoxTider.getSelectionModel().clearSelection();
            setComboBoxTider();
        }
    }

    @FXML
    private void logUd(MouseEvent event) throws IOException {
        UC.logUd();
        App m = new App();
        m.changeScene("FXML/Login.fxml");

    }


}
