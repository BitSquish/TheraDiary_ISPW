package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.SQLException;

public interface LoginAndRegistrationDAO {
    String REGISTER_ERROR="Errore nella registrazione";
    boolean emailExists(String mail) throws SQLException;
    boolean insertUser(Credentials credentials) throws SQLException;
    void registerPatient(Patient patient) throws SQLException, MailAlreadyExistsException, LoginAndRegistrationException;
    void registerPsychologist(Psychologist psychologist) throws SQLException, MailAlreadyExistsException, LoginAndRegistrationException;
    void login(Credentials credentials) throws SQLException, WrongEmailOrPasswordException;
    void retrievePatient(Patient patient) throws NoResultException;

    void retrievePsychologist(Psychologist psychologist) throws NoResultException;
}
