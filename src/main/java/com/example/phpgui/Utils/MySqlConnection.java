package com.example.phpgui.Utils;

import com.example.phpgui.Controller.KalenderAdminController;
import com.example.phpgui.Objects.Behandling;
import com.example.phpgui.Objects.Bruger;
import com.example.phpgui.Objects.Tidsbestilling;
import javafx.scene.control.DatePicker;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MySqlConnection {

    private Connection connection;
    private Statement stmt;


    public MySqlConnection() {
        connection = null;
        stmt = null;
    }

    public Connection getConnection() {
        // Update the connection URL, username, and password according to your MySQL setup
        String url = "jdbc:mysql://mysql63.unoeuro.com:3306/wentzelevent_dk_db_Frisoer";
        String user = "wentzelevent_dk";
        String password = "pBHeRyFdzc4G";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidUser(String Brugernavn, String Kodeord) throws SQLException {
        System.out.println("Checking user credentials: " + Brugernavn + Kodeord);

        String sql = "SELECT kodeord FROM Bruger WHERE Brugernavn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, Brugernavn);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("Kodeord");
                return verifyPassword(Kodeord, hashedPassword);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userSignUp(String TilmeldNavn, String TilmeldTelefon, String TilmeldMail, String TilmeldKodeord) throws SQLException {
        // Hash the password
        String hashedPassword = BCrypt.hashpw(TilmeldKodeord, BCrypt.gensalt());

        // Default rolle = 3 (Kunde)
        int rolle = 3;

        String sql = "INSERT INTO Bruger (Brugernavn, Telefonnummer, Email, Kodeord, Rolle ) VALUES (?, ?, ?, ?, " + rolle + ")";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, TilmeldNavn);
            pstmt.setString(2, TilmeldTelefon);
            pstmt.setString(3, TilmeldMail);
            pstmt.setString(4, hashedPassword); // Store the hashed password in the database
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Sign up successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Sign up failed
    }

    public int getBrugerRolle(String brugernavn) throws SQLException {

        String sql = "SELECT Rolle FROM `Bruger` WHERE Brugernavn = '" + brugernavn +"';";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("Rolle");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public Bruger getBruger(String brugernavn){
        Bruger bruger = new Bruger();
        bruger.setBrugernavn(brugernavn);

        String sql = "SELECT * FROM `Bruger` inner JOIN Roller on Bruger.Rolle = Roller.rolleID WHERE Brugernavn = '" + brugernavn +"';";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                bruger.setId(rs.getInt("Id"));
                bruger.setMobil(rs.getString("Telefonnummer"));
                bruger.setEmail(rs.getString("Email"));
                bruger.setRolle(rs.getString("RolleNavn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bruger;
    }

    public int getMedarbejderId(String brugernavn){
        int medarbejderId = 0;
        String sql = "SELECT * FROM `Bruger` inner JOIN Roller on Bruger.Rolle = Roller.rolleID WHERE Brugernavn = '" + brugernavn +"' and Rolle = 2;";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                medarbejderId = rs.getInt("Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medarbejderId;
    }

    public boolean opretTidsbestilling(Tidsbestilling tidsbestilling){
        String sql = "INSERT INTO `Tidsbestillinger`(`Dato`, `StartTidspunkt`, `SlutTidspunkt`, `BrugerID`, `MedarbejderID`) VALUES (?,?,?,?,?);";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, tidsbestilling.getDato());
            pstmt.setObject(2, tidsbestilling.getStartTidspunkt());
            pstmt.setObject(3,tidsbestilling.getSlutTidspunkt());
            pstmt.setInt(4, tidsbestilling.getKundeID());
            pstmt.setInt(5, tidsbestilling.getMedarbejderID());

            sql = "INSERT INTO `TidsbestillingHarBehandlinger`(`TidsbestillingID`, `BehandlingID`) VALUES (?,?)";
            for(int i = 0; i<tidsbestilling.getBehandlinger().size();i++){
                pstmt.setInt(1,tidsbestilling.getId());
                pstmt.setInt(2, tidsbestilling.getBehandlinger().get(i).getId());
            }

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true; //Tidsbestilling oprettet succesfuldt
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } return false;
    }

    public Behandling getBehandling(int behandlingId){
        Behandling behandling = new Behandling();
        behandling.setId(behandlingId);

        String sql = "SELECT `BehandlingID`, `Navn`, `Pris`, `Varighed` FROM `Behandlinger` WHERE BehandlingID = " + behandlingId +";";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
               behandling.setNavn(rs.getString("Navn"));
               behandling.setPris(rs.getInt("Pris"));
               behandling.setVarighed(rs.getTime("Varighed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return behandling;
    }

    public ArrayList<Time> getTider(LocalDate date, int medarbejderID){
        ArrayList<Time> tider = new ArrayList<>();
        String sql = "SELECT * FROM `Tidsbestillinger` where dato = '" + date.toString() + "' and MedarbejderID = " + medarbejderID + " ORDER BY Dato, MedarbejderID, StartTidspunkt;";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                tider.add(rs.getTime("StartTidspunkt"));
                tider.add(rs.getTime("SlutTidspunkt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tider;
    }

    public ArrayList<String> getAlleMedarbejderBrugernavne(){
        ArrayList<String> medarbejdere = new ArrayList<>();
        String sql ="SELECT * FROM `Bruger` WHERE Rolle = 2;";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
               medarbejdere.add(rs.getString("Brugernavn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medarbejdere;
    }



    /*public void getTidsBestillingAdmin(DatePicker datePicker) {
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate != null) {
            try {
                String sql = "SELECT * FROM tidsbetillinger WHERE dato = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setDate(1, java.sql.Date.valueOf(selectedDate));

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Process and print results
                    // Example:
                    String timeSlot = resultSet.getString("time_slot");
                    System.out.println("Time Slot: " + timeSlot);
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/





    // Method to verify a password against its hash
    public static boolean verifyPassword(String Kodeord, String hashedPassword) {
        return BCrypt.checkpw(Kodeord, hashedPassword);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
