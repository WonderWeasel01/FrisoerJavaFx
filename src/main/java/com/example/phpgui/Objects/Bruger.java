package com.example.phpgui.Objects;

public class Bruger {
    private int Id;
    private String brugernavn;
    private String password;
    private String email;
    private String mobil;
    private int rolle;

    public int getId() {
        return Id;
    }

    @Override
    public String toString() {
        return "Bruger{" +
                "Id=" + Id +
                ", brugernavn='" + brugernavn + '\'' +
                ", email='" + email + '\'' +
                ", mobil='" + mobil + '\'' +
                ", rolle='" + rolle + '\'' +
                '}';
    }

    public void aflysTid(){
    }

    public Bruger() {
    }

    public void setId(int id) {
        Id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public void setBrugernavn(String brugernavn) {
        this.brugernavn = brugernavn;
    }

    public int getRolle() {
        return rolle;
    }

    public void setRolle(int rolle) {
        this.rolle = rolle;
    }
}
