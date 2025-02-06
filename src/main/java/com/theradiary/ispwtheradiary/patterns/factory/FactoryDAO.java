package com.theradiary.ispwtheradiary.patterns.factory;

import com.theradiary.ispwtheradiary.dao.*;
import com.theradiary.ispwtheradiary.dao.demo.*;
import com.theradiary.ispwtheradiary.dao.full.sql.*;
import com.theradiary.ispwtheradiary.dao.full.json.LoginAndRegistrationDAOJSON;
import com.theradiary.ispwtheradiary.engineering.others.Printer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Supplier;

public class FactoryDAO {

    //Sofia Tosti, matricola: 0308023
    private FactoryDAO(){}
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();
    private static final String PERSISTENCE_TYPE = "persistence.type";
    private static LoginAndRegistrationDAO loginAndRegistrationDAO;
    private static TaskAndToDoDAO taskAndToDoDAO;
    private static UpdateDAO updateDAO;
    private static CategoryAndMajorDAO categoryAndMajorDAO;
    private static PtAndPsDAO ptAndPsDAO;
    private static RetrieveDAO retrieveDAO;

    // Caricamento delle proprietà una sola volta
    private static void loadProperties() {
        try (InputStream input = FactoryDAO.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IOException("Properties file not found: " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            Printer.errorPrint("Error loading properties file: " + e.getMessage());
        }
    }

    // Verifica che il tipo di persistenza sia valido
    private static String getPersistenceType() {
        if(properties.isEmpty()){
            loadProperties();
        }
        String type = properties.getProperty(PERSISTENCE_TYPE);
        if (type == null) {
            throw new IllegalArgumentException("Persistence type not found in properties file.");
        }
        return type;
    }

    // Metodo generico per creare DAO
    private static <T> T createDAO(String type, Supplier<T> mysqlSupplier, Supplier<T> demoSupplier, Supplier<T> jsonSupplier) {
        return switch (type) {
            case "mysql" -> mysqlSupplier.get();
            case "demo" -> demoSupplier.get();
            case "json" -> {
                if (jsonSupplier == null) {
                    throw new IllegalArgumentException("DAO JSON non supportato.");
                }
                yield jsonSupplier.get();
            }
            default -> throw new IllegalArgumentException("Tipo di DAO non valido: " + type);
        };
    }


    // Getters per i DAO
    // Sincronizzazione per evitare problemi di concorrenza
    public static synchronized LoginAndRegistrationDAO getDAO() {
        if (loginAndRegistrationDAO == null) {
            loginAndRegistrationDAO = createDAO(
                    getPersistenceType(),
                    LoginAndRegistrationDAOSQL::new,
                    LoginAndRegistrationDAOInMemory::new,
                    LoginAndRegistrationDAOJSON::getInstance
            );
        }
        return loginAndRegistrationDAO;
    }

    public static synchronized TaskAndToDoDAO getTaskAndToDoDAO() {
        if (taskAndToDoDAO == null) {
            taskAndToDoDAO = createDAO(
                    getPersistenceType(),
                    TaskAndToDoDAOSQL::new,
                    TaskAndToDoDAOInMemory::new,
                    null // JSON non supportato per questo DAO
            );
        }
        return taskAndToDoDAO;
    }

    public static synchronized UpdateDAO getUpdateDAO() {
        if (updateDAO == null) {
            updateDAO = createDAO(
                    getPersistenceType(),
                    UpdateDAOSQL::new,
                    UpdateDAOInMemory::new,
                    null // JSON non supportato per questo DAO
            );
        }
        return updateDAO;
    }

    public static synchronized CategoryAndMajorDAO getCategoryAndMajorDAO() {
        if (categoryAndMajorDAO == null) {
            categoryAndMajorDAO = createDAO(
                    getPersistenceType(),
                    CategoryAndMajorDAOSQL::new,
                    CategoryAndMajorDAOInMemory::new,
                    null // JSON non supportato per questo DAO
            );
        }
        return categoryAndMajorDAO;
    }

    public static synchronized PtAndPsDAO getPtAndPsDAO() {
        if (ptAndPsDAO == null) {
            ptAndPsDAO = createDAO(
                    getPersistenceType(),
                    PtAndPsDAOSQL::new,
                    PtAndPsDAOInMemory::new,
                    null // JSON non supportato per questo DAO
            );
        }
        return ptAndPsDAO;
    }

    public static synchronized RetrieveDAO getRetrieveDAO() {
        if (retrieveDAO == null) {
            retrieveDAO = createDAO(
                    getPersistenceType(),
                    RetrieveDAOSQL::new,
                    RetrieveDAOInMemory::new,
                    null // JSON non supportato per questo DAO
            );
        }
        return retrieveDAO;
    }
}

