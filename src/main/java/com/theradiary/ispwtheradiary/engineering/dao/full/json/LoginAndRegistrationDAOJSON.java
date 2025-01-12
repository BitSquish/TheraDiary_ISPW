package com.theradiary.ispwtheradiary.engineering.dao.full.json;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginAndRegistrationDAOJSON implements LoginAndRegistrationDAO {
        //Filepath
        private static final String PERSISTENCE_FILE = "src/main/resources/com/theradiary/ispwtheradiary/login.json";
        Map<String, LoggedUser> loggedUserHashMap = new HashMap<>();
        List<LoggedUser> userList = new ArrayList<>();
        public LoginAndRegistrationDAOJSON() {
            loadFile();
        }
        private void loadFile() {
            //qui leggo il file e carico i dati in loggedUserHashMap
            try (BufferedReader reader = new BufferedReader(new FileReader(PERSISTENCE_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Credentials credentials = new Credentials(data[0], data[1], Role.valueOf(data[2]));
                    if(credentials.getRole() == null)
                        throw new IllegalArgumentException("Invalid role");
                    else{
                        LoggedUser loggedUser = getLoggedUser(credentials, data);
                        loggedUserHashMap.put(credentials.getMail(), loggedUser);
                        userList.add(loggedUser);
                    }
                }
            } catch (IOException e) {
                Printer.errorPrint("Impossibile caricare gli utenti dal file utenti.");
            }
        }

    private static LoggedUser getLoggedUser(Credentials credentials, String[] data) {
        LoggedUser loggedUser = null;
        if(credentials.getRole().equals(Role.PSYCHOLOGIST)) {
            loggedUser = new Psychologist(credentials, data[3], data[4], data[5], data[6], Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]));

        }
        else if(credentials.getRole().equals(Role.PATIENT)) {
            loggedUser = new Patient(credentials, data[3], data[4], data[5], data[6], Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]));
        }
        return loggedUser;
    }

    @Override
    public boolean emailExists(String mail) throws SQLException {
        return false;
    }

    @Override
    public boolean insertUser(Credentials credentials) throws SQLException {
        return false;
    }


    @Override
    public void registerPatient(Patient patient) throws SQLException, MailAlreadyExistsException {

    }

    @Override
    public void registerPsychologist(Psychologist psychologist) throws SQLException, MailAlreadyExistsException {

    }

    @Override
    public void login(Credentials credentials) throws WrongEmailOrPasswordException {
        LoggedUser loggedUser = loggedUserHashMap.get(credentials.getMail());
        if (loggedUser.getCredentials().getMail() == null || !loggedUser.getCredentials().getPassword().equals(credentials.getPassword())) {
            throw new WrongEmailOrPasswordException("Mail o password errati");
        }
        credentials.setRole(loggedUser.getCredentials().getRole());
    }
}
