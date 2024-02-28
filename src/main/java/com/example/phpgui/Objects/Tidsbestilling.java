package com.example.phpgui.Objects;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.SortedMap;

public class Tidsbestilling {
    private String Behandling;
    private LocalDateTime datoTid;


    public Tidsbestilling(LocalDateTime datoTid, String behandling) {
        this.datoTid = datoTid;
        Behandling = behandling;
    }

    public LocalDateTime getDatoTid() {
        return datoTid;
    }

    public void setDatoTid(LocalDateTime datoTid) {
        this.datoTid = datoTid;
    }

    public String getBehandling() {
        return Behandling;
    }

    public void setBehandling(String behandling) {
        Behandling = behandling;
    }
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

    public static void main(String[] args) {
        Tidsbestilling tidsbestilling = lavNyTidsbestilling();
        tidsbestilling.display();
        System.out.println("Er din bestilling i dag? "+ tidsbestilling.tidIdag());
        System.out.println(tidsbestilling.tidFremtid());
        System.out.println("Behandling: " + tidsbestilling.getBehandling());
    }

    }

