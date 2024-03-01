package com.example.phpgui.Objects;

import java.sql.Time;
import java.time.Duration;

public class Behandling {
    private int Id;
    private String navn;
    private int pris;
    private Time varighed;

    @Override
    public String toString() {
        return "Behandling{" +
                "Id=" + Id +
                ", navn='" + navn + '\'' +
                ", pris=" + pris +
                ", varighed=" + varighed +
                '}';
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public Time getVarighed() {
        return varighed;
    }

    public void setVarighed(Time varighed) {
        this.varighed = varighed;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
