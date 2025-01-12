package com.theradiary.ispwtheradiary.engineering.dao.full.json;

import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.SQLException;

public class LoginAndRegistrationDAOJSON implements LoginAndRegistrationDAO {
        //Filepath
        private static final String PERSISTENCE_FILE = "src/main/resources/com/theradiary/ispwtheradiary/login.json";
        //map <String, credentials> credentialsMap = new HashMap<>();
        public LoginAndRegistrationDAOJSON() {
            loadFile();
        }
        private void loadFile() {
            //qui leggo il file e carico i dati in credentialsMap
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
    public void registerMedicalOffice(MedicalOffice medicalOffice) throws SQLException {

    }

    @Override
    public void registerPatient(Patient patient) throws SQLException, MailAlreadyExistsException {

    }

    @Override
    public void registerPsychologist(Psychologist psychologist) throws SQLException, MailAlreadyExistsException {

    }

    @Override
        public void login(Credentials credentials) throws SQLException, WrongEmailOrPasswordException {
            //TODO
        }
}
