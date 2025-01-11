package com.theradiary.ispwtheradiary.engineering.patterns.factory;

import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAOJSON;
import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAOSQL;
import com.theradiary.ispwtheradiary.engineering.others.Printer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FactoryDAO {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    public static LoginAndRegistrationDAO getDAO() {
        try (InputStream input = FactoryDAO.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            Printer.errorPrint("Error loading properties file: " + e.getMessage());
        }
        String daoType = properties.getProperty("persistence.type");
        return switch (daoType) {
            case "mysql" -> new LoginAndRegistrationDAOSQL();
            case "json" -> new LoginAndRegistrationDAOJSON();
            default -> throw new IllegalArgumentException("Invalid DAO type");
        };
    }
}
