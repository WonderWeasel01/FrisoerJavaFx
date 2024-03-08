package com.example.phpgui.Utils;

import com.example.phpgui.Objects.Behandling;
import com.example.phpgui.Objects.Bruger;
import com.example.phpgui.Objects.Tidsbestilling;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class UseCase {
    private MySqlConnection mysqlConnection = new MySqlConnection();
    Connection connection = mysqlConnection.getConnection();
    public static Bruger bruger = new Bruger();
    public Tidsbestilling tidsbestilling = new Tidsbestilling();

    public static void main(String[] args) {

    }


    public void opretBruger() {
    }

    public Bruger login(String brugernavn, String password) throws SQLException, IOException {
        // Check if the entered user credentials are correct
        if (mysqlConnection.isValidUser(brugernavn, password)) {
            System.out.println("Det Virker!");
            this.bruger = mysqlConnection.getBruger(brugernavn);
            System.out.println("Logget ind som: \n" + this.bruger);
        } else {
            System.out.println("Invalid Brugernavn or password");

            // Show an error message or handle failed login
        }
        return bruger;
    }

    public void logUd() throws IOException {
        this.bruger = null;
        System.out.println("Log ud succesful\nBruger: " + bruger);
    }

    public Boolean opretTidsbestilling() {
        if(mysqlConnection.opretTidsbestilling(this.tidsbestilling)){
            System.out.println("Tidsbestilling oprettet");
            System.out.println(this.tidsbestilling);
            return true;
        } else {
            System.out.println("Der skete en fejl med tidsbestillingen");
            return false;
        }
    }

    public void fjernTidspunktFraTidsbestilling(){
        this.tidsbestilling.setStartTidspunkt(null);
        this.tidsbestilling.setSlutTidspunkt(null);
        System.out.println("Tidspunkt fjernet: \n" + "Start tidspunkt: " + tidsbestilling.getStartTidspunkt() + "   Slut tidspunkt: " + tidsbestilling.getSlutTidspunkt());
    }

    public Boolean tilfoejBehandlingTilTidsbestilling(int behandlingID) {
        Behandling behandling;
        behandling = mysqlConnection.getBehandling(behandlingID);
        this.tidsbestilling.getBehandlinger().add(behandling);
        System.out.println("Behandlinger tilføjet: " + tidsbestilling.getBehandlinger());
        return true;
    }

    public void fjernBehandlingFraTidsbestilling(int behandlingID){
        this.tidsbestilling.fjernBehandling(behandlingID);
        System.out.println("Behandling fjernet: " + tidsbestilling.getBehandlinger());
    }

    public Boolean tilfoejDatoTilTidsbestilling(LocalDate dato) {
        this.tidsbestilling.setDato(dato);
        System.out.println("Dato tilføjet til tidsbestilling: " + tidsbestilling.getDato());
        return true;
    }

    public void tilfoejBrugerIDtilTidsbestilling(){
        System.out.println(bruger);
        System.out.println(bruger.getId());
        this.tidsbestilling.setKundeID(bruger.getId());
        System.out.println("BrugerID tilføjet til tidsbestilling: " + bruger.getId());
    }

    public void tilfoejMedarbejderTilTidsbestilling(String brugernavn) {
        int medarbejderId = mysqlConnection.getMedarbejderId(brugernavn);
        System.out.println("Medarbejder tilføjet til tidsbestilling : " + brugernavn + "    ID: " + medarbejderId);
        this.tidsbestilling.setMedarbejderID(medarbejderId);

    }

    public Boolean tilfoejTidspunktTilTidsbestilling(LocalTime tidspunkt){
        Time startTidspunkt = Time.valueOf(tidspunkt);
        this.tidsbestilling.setStartTidspunkt(startTidspunkt);
        System.out.println("Starttidspunkt tilføjet: " + startTidspunkt);


        //Konverter til localTime så vi kan beregne med tid
        LocalTime behandlingsvarighed = tidsbestilling.getBehandlingsVarigheder().toLocalTime();
        LocalTime lokalSlutTidspunkt = tidspunkt.plusHours(behandlingsvarighed.getHour()).plusMinutes(behandlingsvarighed.getMinute()).plusSeconds(behandlingsvarighed.getSecond());


        //Konverter tilbage til Time
        Time slutTidspunkt = Time.valueOf(lokalSlutTidspunkt);
        this.tidsbestilling.setSlutTidspunkt(slutTidspunkt);
        System.out.println("Sluttidspunkt tilføjet: " + slutTidspunkt);

        return true;
    }

    public ArrayList<LocalTime> ledigeTider() {
        int medarbejderID = tidsbestilling.getMedarbejderID();
        LocalDate date = tidsbestilling.getDato();
        ArrayList<Time> bestilteTider = mysqlConnection.getTider(date, medarbejderID);
        ArrayList<LocalTime> andreTidsbestillinger = new ArrayList<>();
        ArrayList<LocalTime> ledigeTider = new ArrayList<>(100);
        LocalTime aabningstid = LocalTime.of(8, 0, 0);
        LocalTime lukkeTid = LocalTime.of(21, 0, 0);

        //Tilføjer åbningstid til arrayet så vi har en start tid at kontrollerer fra.
        while (aabningstid.isBefore(lukkeTid)) {
            ledigeTider.add(aabningstid);
            aabningstid = aabningstid.plusMinutes(30);
        }

        //Konvertere til localtime i stedet for time så vi kan beregne med tiderne.
        for (int i = 0; i < bestilteTider.size(); i++) {
            andreTidsbestillinger.add(bestilteTider.get(i).toLocalTime());
        }
        System.out.println("Aftaler: " + andreTidsbestillinger + "\n");

        Time bv = this.tidsbestilling.getBehandlingsVarigheder();
        LocalTime behandlingsVarighed = bv.toLocalTime();
        System.out.println("Behandlingsvarighed: " + behandlingsVarighed);

        // Kontrollerer om der er plads til at sætte den valgte aftale ind før den næste aftale starter.
        ArrayList<LocalTime> tiderDerSkalFjernes = new ArrayList<>();
        for (int i = ledigeTider.size() - 1; i >= 0; i--) {
            LocalTime minTBSlut = ledigeTider.get(i).plusHours(behandlingsVarighed.getHour()).plusMinutes(behandlingsVarighed.getMinute()).plusSeconds(behandlingsVarighed.getSecond());

            for (int j = 0; j < andreTidsbestillinger.size(); j += 2) {
                LocalTime andreTBStart = andreTidsbestillinger.get(j);
                LocalTime andreTBSlut = andreTidsbestillinger.get(j + 1);

                // Kontrollerer om ens tid ville ramme en anden booket tid
                if ((andreTBStart.isBefore(minTBSlut) && minTBSlut.isBefore(andreTBSlut)) ||
                        (andreTBStart.isBefore(ledigeTider.get(i)) && andreTBSlut.isAfter(ledigeTider.get(i)) || andreTBSlut.isAfter(ledigeTider.get(i)) && minTBSlut.isAfter(andreTBStart))) {
                    tiderDerSkalFjernes.add(ledigeTider.get(i));
                    break;
                }
            }

            // Kontrollere om aftalen overskrider lukketiden;
            if (minTBSlut.isAfter(lukkeTid)) {
                tiderDerSkalFjernes.add(ledigeTider.get(i));
            }
        }
        System.out.println("Tider der skal fjernes: " + tiderDerSkalFjernes);
        ledigeTider.removeAll(tiderDerSkalFjernes);
        System.out.println("Ledige tider: " + ledigeTider);
        return ledigeTider;
    }
    public ArrayList<LocalTime> ledigeTider(Time behandlingsvarighed, LocalDate date, int medarbejderID) {
        ArrayList<Time> bestilteTider = mysqlConnection.getTider(date, medarbejderID);
        ArrayList<LocalTime> andreTidsbestillinger = new ArrayList<>();
        ArrayList<LocalTime> ledigeTider = new ArrayList<>(100);
        LocalTime aabningstid = LocalTime.of(8, 0, 0);
        LocalTime lukkeTid = LocalTime.of(21, 0, 0);

        //Tilføjer åbningstid til arrayet så vi har en start tid at kontrollerer fra.
        while (aabningstid.isBefore(lukkeTid)) {
            ledigeTider.add(aabningstid);
            aabningstid = aabningstid.plusMinutes(30);
        }

        //Konvertere til localtime i stedet for time så vi kan beregne med tiderne.
        for (int i = 0; i < bestilteTider.size(); i++) {
            andreTidsbestillinger.add(bestilteTider.get(i).toLocalTime());
        }
        System.out.println("Aftaler: " + andreTidsbestillinger + "\n");

        Time bv = behandlingsvarighed;
        LocalTime behandlingsVarighed = bv.toLocalTime();
        System.out.println("Behandlingsvarighed: " + behandlingsVarighed);

        // Kontrollerer om der er plads til at sætte den valgte aftale ind før den næste aftale starter.
        ArrayList<LocalTime> tiderDerSkalFjernes = new ArrayList<>();
        for (int i = ledigeTider.size() - 1; i >= 0; i--) {
            LocalTime minTBSlut = ledigeTider.get(i).plusHours(behandlingsVarighed.getHour()).plusMinutes(behandlingsVarighed.getMinute()).plusSeconds(behandlingsVarighed.getSecond());

            for (int j = 0; j < andreTidsbestillinger.size(); j += 2) {
                LocalTime andreTBStart = andreTidsbestillinger.get(j);
                LocalTime andreTBSlut = andreTidsbestillinger.get(j + 1);

                // Kontrollerer om ens tid ville ramme en anden booket tid
                if ((andreTBStart.isBefore(minTBSlut) && minTBSlut.isBefore(andreTBSlut)) ||
                        (andreTBStart.isBefore(ledigeTider.get(i)) && andreTBSlut.isAfter(ledigeTider.get(i)) || andreTBSlut.isAfter(ledigeTider.get(i)) && minTBSlut.isAfter(andreTBStart))) {
                    tiderDerSkalFjernes.add(ledigeTider.get(i));
                    break;
                }
            }

            // Kontrollere om aftalen overskrider lukketiden;
            if (minTBSlut.isAfter(lukkeTid)) {
                tiderDerSkalFjernes.add(ledigeTider.get(i));
            }
        }
        System.out.println("Tider der skal fjernes: " + tiderDerSkalFjernes);
        ledigeTider.removeAll(tiderDerSkalFjernes);
        System.out.println("Ledige tider: " + ledigeTider);
        return ledigeTider;
    }

    public ArrayList<String> getMedarbejderBrugernavne () {
        ArrayList<String> medarbejderBrugernavne = new ArrayList<>();
        for (int i = 0; i < mysqlConnection.getAlleMedarbejdere().size(); i++) {
            medarbejderBrugernavne.add(mysqlConnection.getAlleMedarbejdere().get(i).getBrugernavn());
        }
        return medarbejderBrugernavne;
    }

    public int getMedarbejderID(String brugernavn){
        int medarbejderID = mysqlConnection.getMedarbejderId(brugernavn);
        return medarbejderID;
    }

    public void getTidsbestillinger(LocalDate date){
        ArrayList<Tidsbestilling> tb = mysqlConnection.getTidsBestillingAdmin(date);
        System.out.println(tb);
    }
    public Tidsbestilling getTidsbestilling(int tidsbestillingID){
        Tidsbestilling tb = mysqlConnection.getTidsBestillingAdmin(tidsbestillingID);
        System.out.println("Tidsbestillinger: " + tb);
        return tb;
    }
    public ArrayList<Tidsbestilling> getTidsbestillingerAdmin(String brugernavn){
        ArrayList<Tidsbestilling> tb = mysqlConnection.getTidsBestillingAdmin(brugernavn);
        return tb;
    }

    public void aflysTidsbestilling(int tidsbestillingID){
        if(mysqlConnection.aflysTidsbestilling(tidsbestillingID)){
            System.out.println("Tidsbestilling aflyst");
        } else System.out.println("Der skete en fejl. Tidsbestilling ikke aflyst");
    }

    public void opdaterTidsbestilling(Tidsbestilling tidsbestilling){
        if(mysqlConnection.opdaterTidsbestilling(tidsbestilling)){
            System.out.println("Tidsbestilling opdateret!");
        } else System.out.println("Tidsbestilling ikke opdateret. Der er sket en fejl");
    }

}
