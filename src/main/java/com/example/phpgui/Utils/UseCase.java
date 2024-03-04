package com.example.phpgui.Utils;

import com.example.phpgui.Objects.Behandling;
import com.example.phpgui.Objects.Bruger;
import com.example.phpgui.Objects.Tidsbestilling;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class UseCase {
    private MySqlConnection mysqlConnection = new MySqlConnection();
    Connection connection = mysqlConnection.getConnection();
    public Bruger bruger;
    public Tidsbestilling tidsbestilling = new Tidsbestilling();

    public static void main(String[] args){
        UseCase uc = new UseCase();
        ArrayList<LocalTime> tider = uc.ledigeTider();

        System.out.println(tider);

    }


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


    public ArrayList<LocalTime> ledigeTider(LocalDate date, String medarbejderUsername){
        int medarbejderID = mysqlConnection.getMedarbejderId(medarbejderUsername);
        ArrayList<Time> tider = mysqlConnection.getTider(date,medarbejderID);

       ArrayList<LocalTime> tider1 = new ArrayList<>();
       ArrayList<LocalTime> ledigeTider = new ArrayList<>(100);

       LocalTime aabningstid = LocalTime.of(8,0,0);
       LocalTime lukkeTid = LocalTime.of(21,0,0);

       //Tilføjer åbningstid til arrayet så vi har en start tid at kontrollerer fra.
       tider1.add(aabningstid);

       //Konvertere til localtime i stedet for time så vi kan regne med tiderne.
       for(int i = 0; i<tider.size(); i++){
           tider1.add(tider.get(i).toLocalTime());
       }
       System.out.println("Aftaler: " + tider1 + "\n");

       Time time = Time.valueOf("01:00:00");
       LocalTime tid = time.toLocalTime();

       // Kontrollerer om der er plads til at sætte den valgte aftale ind før den næste aftale starter.
       for(int slutTidspunkt = 0; slutTidspunkt <= tider1.size(); slutTidspunkt+=2) {
           int startTidspunkt = slutTidspunkt + 1;
           LocalTime timeToCheck = tider1.get(slutTidspunkt).plusHours(tid.getHour()).plusMinutes(tid.getMinute()).plusSeconds(tid.getSecond());

           System.out.println("Slut:" + tider1.get(slutTidspunkt));
           System.out.println("time to check: " + timeToCheck);


           //Kontrollere om der er en aftale i systemet at kontrollere imod. Bruger lukketid hvis der ikke er nogle.
           if(startTidspunkt < tider1.size()){
               System.out.println("Time to check against " + tider1.get(startTidspunkt));
               System.out.println();
               //Hvis der er plads til den valgte behandling, indsættes tiden i arraylisten af ledige tider.
               if ((timeToCheck.isBefore(tider1.get(startTidspunkt))) && timeToCheck.isBefore(lukkeTid)) {
                   ledigeTider.add(tider1.get(slutTidspunkt));
               }
           } else {
               System.out.println("Time to check against (Lukketid) " + lukkeTid);
               timeToCheck.isBefore(lukkeTid);
           }
       }
        return ledigeTider;

    }








}
