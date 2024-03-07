package com.example.phpgui.Controller;

import com.example.phpgui.Objects.Tidsbestilling;
import com.example.phpgui.Utils.UseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SeAftalerKundeController implements Initializable {

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
    Button aflysTid;

    @FXML
    Label rettesIDLabel;






    public static String findBrugernavn;

    public void aflysButton(ActionEvent event){
        UC.aflysTidsbestilling(Integer.parseInt(tidsbestillingID.getText()));
        opdaterTabel();
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



}
