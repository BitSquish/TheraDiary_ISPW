package com.theradiary.ispwtheradiary.engineering.patterns.factory;

import com.theradiary.ispwtheradiary.engineering.exceptions.LoadingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    //Impedisce che la classe ConnectionFactory possa essere inizializzata da altre classi esterne
    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        try (InputStream input = new FileInputStream("src/main/resources/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty("LOGIN_USER");
            String pass = properties.getProperty("LOGIN_PASS");
            return DriverManager.getConnection(connectionUrl, user, pass);
        } catch (IOException e) {
            throw new LoadingException("Errore nel caricamento del file di configurazione", e);
        }
    }
}
