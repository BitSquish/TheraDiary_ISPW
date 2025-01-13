package com.theradiary.ispwtheradiary.engineering.patterns.factory;

import com.theradiary.ispwtheradiary.engineering.dao.*;
import com.theradiary.ispwtheradiary.engineering.dao.demo.*;
import com.theradiary.ispwtheradiary.engineering.dao.full.json.LoginAndRegistrationDAOJSON;
import com.theradiary.ispwtheradiary.engineering.dao.full.sql.*;
import com.theradiary.ispwtheradiary.engineering.others.Printer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Supplier;

public class FactoryDAO {
    private FactoryDAO(){}
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();
    private static boolean isPropertiesLoaded = false;
    private static final String PERSISTENCE_TYPE = "persistence.type";
    private static LoginAndRegistrationDAO daoInstance;
    private static TaskAndToDoDAO taskAndToDoDAOInstance;
    private static UpdateDAO updateDAOInstance;
    private static CategoryAndMajorDAO categoryAndMajorDAOInstance;
    private static PtAndPsDAO ptAndPsDAOInstance;
    private static RetrieveDAO retrieveDAOInstance;

    // Caricamento delle proprietà una sola volta
    private static void loadProperties() {
        if (!isPropertiesLoaded) {
            try (InputStream input = FactoryDAO.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                if (input == null) {
                    throw new IOException("Properties file not found: " + CONFIG_FILE);
                }
                properties.load(input);
                isPropertiesLoaded = true;
                Printer.println("Properties:" + properties);
            } catch (IOException e) {
                Printer.errorPrint("Error loading properties file: " + e.getMessage());
            }
        }
    }

    // Verifica che il tipo di persistenza sia valido
    private static String getPersistenceType() {
        loadProperties();
        String daoType = properties.getProperty(PERSISTENCE_TYPE);
        if (daoType == null || daoType.isEmpty()) {
            throw new IllegalArgumentException("DAO type is not specified in config.properties");
        }
        return daoType;
    }

    // Metodo generico per creare DAO
    private static <T> T createDAO(String daoType, Supplier<T> mysqlSupplier, Supplier<T> demoSupplier) {
        return switch (daoType) {
            case "mysql" -> mysqlSupplier.get();
            case "demo" -> demoSupplier.get();
            default -> throw new IllegalArgumentException("Invalid DAO type: " + daoType);
        };
    }

    // Metodo specifico per creare DAO che supportano JSON
    private static <T> T createJsonSupportedDAO(String daoType, Supplier<T> mysqlSupplier, Supplier<T> demoSupplier, Supplier<T> jsonSupplier) {
        return switch (daoType) {
            case "mysql" -> mysqlSupplier.get();
            case "demo" -> demoSupplier.get();
            case "json" -> {
                if (jsonSupplier == null) {
                    throw new IllegalArgumentException("JSON DAO not supported for this type.");
                }
                yield jsonSupplier.get();
            }
            default -> throw new IllegalArgumentException("Invalid DAO type: " + daoType);
        };
    }

    // DAO Getters

    public static LoginAndRegistrationDAO getDAO() {
        String daoType = getPersistenceType();
        if(daoType.equals("demo")){
            if(daoInstance == null){
                daoInstance = new LoginAndRegistrationDAOInMemory();
            }
            return daoInstance;
        }
        return createJsonSupportedDAO(
                daoType,
                LoginAndRegistrationDAOSQL::new,
                LoginAndRegistrationDAOInMemory::new,
                LoginAndRegistrationDAOJSON::new
        );
    }

    public static TaskAndToDoDAO getTaskAndToDoDAO() {
        String daoType = getPersistenceType();
        if(daoType.equals("demo")){
            if(taskAndToDoDAOInstance == null){
                taskAndToDoDAOInstance = new TaskAndToDoDAOInMemory();
            }
            return taskAndToDoDAOInstance;
        }
        return createDAO(
                daoType,
                TaskAndToDoDAOSQL::new,
                TaskAndToDoDAOInMemory::new
        );
    }

    public static UpdateDAO getUpdateDAO() {
        String daoType = getPersistenceType();
        if(daoType.equals("demo")){
            if(updateDAOInstance == null){
                updateDAOInstance = new UpdateDAOInMemory();
            }
            return updateDAOInstance;
        }
        return createDAO(
                daoType,
                UpdateDAOSQL::new,
                UpdateDAOInMemory::new
        );
    }

    public static CategoryAndMajorDAO getCategoryAndMajorDao() {
        String daoType = getPersistenceType();
        if (daoType.equals("demo")) {
            if (categoryAndMajorDAOInstance == null) {
                categoryAndMajorDAOInstance = new CategoryAndMajorDAOInMemory();
            }
            return categoryAndMajorDAOInstance;
        }
        return createDAO(
                daoType,
                CategoryAndMajorDAOSQL::new,
                CategoryAndMajorDAOInMemory::new
        );
    }

    public static PtAndPsDAO getPtAndPsDAO() {
        String daoType = getPersistenceType();
        if(daoType.equals("demo")){
            if(ptAndPsDAOInstance == null){
                ptAndPsDAOInstance = new PtAndPsDAOInMemory();
            }
            return ptAndPsDAOInstance;
        }
        return createDAO(
                daoType,
                PtAndPsDAOSQL::new,
                PtAndPsDAOInMemory::new
        );
    }

    public static RetrieveDAO getRetrieveDAO() {
        String daoType = getPersistenceType();
        if (daoType.equals("demo")) {
            if (retrieveDAOInstance == null) {
                retrieveDAOInstance = new RetrieveDAOInMemory();
            }
            return retrieveDAOInstance;
        }
        return createDAO(
                daoType,
                RetrieveDAOSQL::new,
                RetrieveDAOInMemory::new
        );
    }
}
