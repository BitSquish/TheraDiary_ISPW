package com.theradiary.ispwtheradiary.engineering.others;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    //Impedisce che la classe ConnectionFactory possa essere inizializzata da altre classi (singleton)
    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        try (InputStream input = new FileInputStream("src/main/resources/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String connection_url = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty("LOGIN_USER");
            String pass = properties.getProperty("LOGIN_PASS");
            return DriverManager.getConnection(connection_url, user, pass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException("Errore nel caricamento delle proprietà del database.");
        }
    }
}
