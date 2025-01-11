package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;

import java.sql.SQLException;

public class LoginDAOJSON implements LoginGenericDAO {
    //Filepath
    private static final String PERSISTENCE_FILE = "src/main/resources/com/theradiary/ispwtheradiary/login.json";
    //map <String, credentials> credentialsMap = new HashMap<>();

    public LoginDAOJSON() {
        loadFile();
    }

    private void loadFile() {
        //qui leggo il file e carico i dati in credentialsMap
    }

    @Override
    public void login(Credentials credentials) throws SQLException, WrongEmailOrPasswordException {
        //TODO
    }

}
