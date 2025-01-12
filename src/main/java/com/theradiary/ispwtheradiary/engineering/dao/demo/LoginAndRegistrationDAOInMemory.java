package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.SQLException;

public class LoginAndRegistrationDAOInMemory implements LoginAndRegistrationDAO {
    @Override
    public boolean emailExists(String mail) throws SQLException {
        return false;
    }
    @Override
    public void retrievePatient(Patient patient) {

    }

    @Override
    public void retrievePsychologist(Psychologist psychologist) {

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
    public void login(Credentials credentials) throws SQLException, WrongEmailOrPasswordException {

    }
    //TODO: Implement the methods of the interface
}
