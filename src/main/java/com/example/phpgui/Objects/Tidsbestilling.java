package com.example.phpgui.Objects;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Tidsbestilling {
    private int id;
    private ArrayList<Behandling> behandlinger = new ArrayList<>();
    private Time startTidspunkt;
    private Time slutTidspunkt;
    private LocalDate dato;
    private int medarbejderID;
    private int kundeID;

    public Time getBehandlingsVarigheder(){
        LocalTime result = LocalTime.of(0,0,0);
        for(int i = 0; i<behandlinger.size();i++){
            LocalTime behandlingsVarighed = behandlinger.get(i).getVarighed().toLocalTime();
            result = result.plusHours(behandlingsVarighed.getHour()).plusMinutes(behandlingsVarighed.getMinute()).plusSeconds(behandlingsVarighed.getSecond());
        }
        Time resultInTime = Time.valueOf(result);
        return resultInTime;
    }

    public int getMedarbejderID() {
        return medarbejderID;
    }

    public void setMedarbejderID(int medarbejderID) {
        this.medarbejderID = medarbejderID;
    }

    public int getKundeID() {
        return kundeID;
    }

    public void setKundeID(int kundeID) {
        this.kundeID = kundeID;
    }

    public Tidsbestilling() {
    }

    public Time getStartTidspunkt() {
        return startTidspunkt;
    }

    public void setStartTidspunkt(Time startTidspunkt) {
        this.startTidspunkt = startTidspunkt;
    }

    public Time getSlutTidspunkt() {
        return slutTidspunkt;
    }

    public void setSlutTidspunkt(Time slutTidspunkt) {
        this.slutTidspunkt = slutTidspunkt;
    }

    public LocalDate getDato() {
        return dato;
    }

    public void setDato(LocalDate dato) {
        this.dato = dato;
    }

    @Override
    public String toString() {
        return "Tidsbestilling{" +
                "id=" + id +
                ", behandlinger=" + behandlinger +
                ", startTidspunkt=" + startTidspunkt +
                ", slutTidspunkt=" + slutTidspunkt +
                ", dato=" + dato +
                '}';
    }

    public ArrayList<Behandling> getBehandlinger() {
        return behandlinger;
    }

    public void setBehandlinger(ArrayList<Behandling> behandlinger) {
        this.behandlinger = behandlinger;
    }

  /*  public void tilfoejBehandling(Behandling behandling){
        this.behandlinger.add(behandling);
    }

   */



    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }


/*
    //Metode til at se om tiden er i dag, og hvornår i dag.
    public boolean tidIdag(){
        LocalDateTime idag = LocalDateTime.now();
        return datoTid.toLocalDate().equals(idag.toLocalDate()) && datoTid.getHour() == idag.getHour() && datoTid.getMinute() == idag.getMinute();
    }
    //Metode til at se om tiden er i fremtiden, og hvornår i fremtiden.
    public String tidFremtid() {
        LocalDateTime idag = LocalDateTime.now();
        if (datoTid.isAfter(idag)) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formateretDatoTid = datoTid.format(format);
            return "Du har en tid " + formateretDatoTid;
        } else {
            return "Du har ikke bestilt tid.";
        }
    }
    //Metode til at se information om sin tidsbestilling
        public void display () {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formateretDatoTid = datoTid.format(format);
            System.out.println("Behandling: ");
            System.out.println("Tid og dato for behandling: ");
        }
    //Metode til at lave en ny bestilling
    public static Tidsbestilling lavNyTidsbestilling() {
        Scanner input = new Scanner (System.in);
        System.out.println("Indtast Behandling: ");
        String behandling = input.nextLine();
        System.out.println("Indtast dato og tid: ");
        String datoTidString = input.nextLine();
        LocalDateTime datoTid = LocalDateTime.parse(datoTidString, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        return new Tidsbestilling(datoTid, behandling);
    }

    /*public static void main2(String[] args) {
        Tidsbestilling tidsbestilling = lavNyTidsbestilling();
        tidsbestilling.display();
        System.out.println("Er din bestilling i dag? "+ tidsbestilling.tidIdag());
        System.out.println(tidsbestilling.tidFremtid());
        System.out.println("Behandling: " + tidsbestilling.getBehandling());
    }*/


    }

