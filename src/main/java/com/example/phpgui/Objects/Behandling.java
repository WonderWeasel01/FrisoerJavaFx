package com.example.phpgui.Objects;

public class Behandling {
    private int Id;
    private String behandlingsNavn;
    private String pris;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBehandlingsNavn() {
        return behandlingsNavn;
    }

    public void setBehandlingsNavn(String behandlingsNavn) {
        this.behandlingsNavn = behandlingsNavn;
    }

    public String getPris() {
        return pris;
    }

    public void setPris(String pris) {
        this.pris = pris;
    }
}
