package com.example.phpgui.Utils;

import com.example.phpgui.Objects.Bruger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UseCase {
    private MySqlConnection mysqlConnection = new MySqlConnection();



    public void opretBruger(){

    }
    public Bruger login(String brugernavn, String password) throws SQLException, IOException {
        Connection connection = mysqlConnection.getConnection();
        Bruger bruger = new Bruger();
        // Check if the entered user credentials are correct
        if (mysqlConnection.isValidUser(brugernavn, password)) {
            System.out.println("Det Virker!");
            bruger = mysqlConnection.getBruger(brugernavn);
            System.out.println("Logget ind som: \n" + bruger);
            connection.close();
        } else {
            System.out.println("Invalid Brugernavn or password");
            connection.close();

            // Show an error message or handle failed login
        }
        return bruger;
    }




}
