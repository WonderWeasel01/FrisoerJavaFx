package com.example.phpgui.Controller;

import com.example.phpgui.Objects.Tidsbestilling;
import com.example.phpgui.Utils.UseCase;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeAftalerController {

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






    public void seAftaler(String brugernavn){
        ArrayList<Tidsbestilling> tb = UC.getTidsbestillingerAdmin(brugernavn);
        System.out.println(tb);
        ObservableList<Tidsbestilling> list = FXCollections.observableArrayList(tb);

        id.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Integer>("id"));
        dato.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, LocalDate>("dato"));
        startTidspunkt.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Time>("startTidspunkt"));
        slutTidspunkt.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Time>("slutTidspunkt"));
        brugerID.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Integer>("kundeID"));
        medarbejderID.setCellValueFactory(new PropertyValueFactory<Tidsbestilling, Integer>("medarbejderID"));
        tv.setItems(list);
    }


}
