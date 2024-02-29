package com.example.phpgui.Utils;

import com.example.phpgui.Objects.Bruger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UseCase {
    private MySqlConnection mysqlConnection = new MySqlConnection();
    public Bruger bruger;




    public void opretBruger(){

    }
    public Bruger login(String brugernavn, String password) throws SQLException, IOException {
        Connection connection = mysqlConnection.getConnection();
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

    public void OpretTidsbestilling(){



    }






}
