package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.SQLException;

public interface RegistrationGenericDAO {
    String REGISTER_ERROR="Errore nella registrazione";
    boolean emailExists(String mail) throws SQLException;
    boolean insertUser(Credentials credentials) throws SQLException;
    void registerMedicalOffice(MedicalOffice medicalOffice) throws SQLException;
    void registerPatient(Patient patient) throws SQLException, MailAlreadyExistsException;
    void registerPsychologist(Psychologist psychologist) throws SQLException, MailAlreadyExistsException;
}
