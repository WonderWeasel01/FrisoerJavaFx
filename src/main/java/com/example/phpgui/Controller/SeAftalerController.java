package com.example.phpgui.Controller;

import com.example.phpgui.Objects.Tidsbestilling;
import com.example.phpgui.Utils.UseCase;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SeAftalerController implements Initializable {

    UseCase UC = new UseCase();
    @FXML
    TableView<Tidsbestilling> tv;
    @FXML
    TableColumn<Tidsbestilling, Integer> id;
    @FXML
    TableColumn<Tidsbestilling, LocalDate> dato;
    @FXML
    TableColumn<Tidsbestilling, Time> startTidspunkt;
    @FXML
    TableColumn<Tidsbestilling, Time> slutTidspunkt;
    @FXML
    TableColumn<Tidsbestilling, Integer> brugerID;
    @FXML
    TableColumn<Tidsbestilling, Integer> medarbejderID;
    @FXML
    TextField tidsbestillingID;
    @FXML
    Button retTidsbestilling;
    @FXML
    Button aflysTid;
    @FXML
    Label rettesLabel;
    @FXML
    Label rettesIDLabel;
    @FXML
    DatePicker datePicker;
    @FXML
    ComboBox comboBoxMedarbejdere;
    @FXML
    Button opdaterButton;

    @FXML
    ComboBox comboBoxTider;
    Tidsbestilling tb = new Tidsbestilling();




    public static String findBrugernavn;

    @FXML
    private void opdaterButton(ActionEvent event){
        UC.opdaterTidsbestilling(tb);
        opdaterTabel();
    }

    public static String getFindBrugernavn() {
        return findBrugernavn;
    }

    public static void setFindBrugernavn(String findBrugernavn) {
        System.out.println(findBrugernavn);
        SeAftalerController.findBrugernavn = findBrugernavn;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opdaterTabel();
    }
    public void opdaterTabel(){
        ArrayList<Tidsbestilling> tb = UC.getTidsbestillingerAdmin(findBrugernavn);
        ObservableList<Tidsbestilling> list = FXCollections.observableArrayList(tb);

        id.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Integer>("id"));
        dato.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, LocalDate>("dato"));
        startTidspunkt.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Time>("startTidspunkt"));
        slutTidspunkt.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Time>("slutTidspunkt"));
        brugerID.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Integer>("kundeID"));
        medarbejderID.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Integer>("medarbejderID"));
        tv.setItems(list);
        tb.clear();
    }

    @FXML
    private void retTidsbestilling(ActionEvent event){
        int tidsbestillingID = Integer.parseInt(this.tidsbestillingID.getText());
        tb = UC.getTidsbestilling(tidsbestillingID);
        rettesIDLabel.setText(Integer.toString(tb.getId()));
    }
    @FXML
    private void datePicker(ActionEvent evt){
        tb.setDato(datePicker.getValue());
        ArrayList<String> medarbejdere = UC.getMedarbejderBrugernavne();
        ObservableList<String> list = FXCollections.observableArrayList(medarbejdere);
        if(comboBoxMedarbejdere.getValue() == null){
            comboBoxMedarbejdere.setItems(list);
        } else{
            setComboBoxTider();
        }
    }
    @FXML
    private void comboBoxMedarbejderSelect(ActionEvent event){
        String s = comboBoxMedarbejdere.getSelectionModel().getSelectedItem().toString();
        int medarbejderID = UC.getMedarbejderID(s);
        tb.setMedarbejderID(medarbejderID);
        setComboBoxTider();
    }
    public void setComboBoxTider(){
        int medarbejderID = UC.getMedarbejderID(comboBoxMedarbejdere.getValue().toString());
        ArrayList<LocalTime> ledigeTider = UC.ledigeTider(tb.getBehandlingsVarigheder(),datePicker.getValue(),medarbejderID);
        ObservableList<LocalTime> list1 = FXCollections.observableArrayList(ledigeTider);
        comboBoxTider.setItems(list1);
    }
    @FXML
    private void comboBoxTiderSelect(){
        if (comboBoxTider.getValue() != null){
            LocalTime startTidspunkt = (LocalTime) comboBoxTider.getValue();
            Time behandlingsVarighed = tb.getBehandlingsVarigheder();
            LocalTime bv = behandlingsVarighed.toLocalTime();
            LocalTime slutTidspunkt = startTidspunkt.plusHours(bv.getHour()).plusMinutes(bv.getMinute()).plusSeconds(bv.getSecond());

            tb.setStartTidspunkt(Time.valueOf(startTidspunkt));
            tb.setSlutTidspunkt(Time.valueOf(slutTidspunkt));
        }
    }



}
