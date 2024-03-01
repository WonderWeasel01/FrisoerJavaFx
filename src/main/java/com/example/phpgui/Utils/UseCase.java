package com.example.phpgui.Utils;

import com.example.phpgui.Objects.Behandling;
import com.example.phpgui.Objects.Bruger;
import com.example.phpgui.Objects.Tidsbestilling;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;

public class UseCase {
    private MySqlConnection mysqlConnection = new MySqlConnection();
    Connection connection = mysqlConnection.getConnection();
    public Bruger bruger;
    public Tidsbestilling tidsbestilling = new Tidsbestilling();




    public void opretBruger(){

    }
    public Bruger login(String brugernavn, String password) throws SQLException, IOException {
       // Connection connection = mysqlConnection.getConnection();
        // Check if the entered user credentials are correct
        if (mysqlConnection.isValidUser(brugernavn, password)) {
            System.out.println("Det Virker!");
            this.bruger = mysqlConnection.getBruger(brugernavn);
            System.out.println("Logget ind som: \n" + bruger);
            connection.close();
        } else {
            System.out.println("Invalid Brugernavn or password");
            connection.close();

            // Show an error message or handle failed login
        }
        return bruger;
    }

    public void logUd(){
        this.bruger = null;
        System.out.println("Log ud succesful\nBruger: " + bruger);
    }

    public void opretTidsbestilling(){

        mysqlConnection.opretTidsbestilling(this.tidsbestilling);

    }

    public void tilfoejBehandlingTilTidsbestilling(int behandlingID){
        Behandling behandling = new Behandling();
        behandling = mysqlConnection.getBehandling(behandlingID);
        this.tidsbestilling.getBehandlinger().add(behandling);
        System.out.println("Behandlinger tilføjet: " + tidsbestilling.getBehandlinger());
    }

    public void tilfoejDateTimeTilTidsbestilling(LocalDate dato){
        this.tidsbestilling.setDato(dato);
        System.out.println("Dato tilføjet til tidsbestilling: " + tidsbestilling.getDato());
    }
    public void tilfoejMedarbejderTilTidsbestilling(String brugernavn){
        int medarbejderId = mysqlConnection.getMedarbejderId(brugernavn);
        this.tidsbestilling.setMedarbejderID(medarbejderId);
    }
    //public void getLedigeMedarbejdere







}
