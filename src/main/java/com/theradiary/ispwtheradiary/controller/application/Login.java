package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.LoginDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import java.sql.SQLException;

public class Login {
    public void log(CredentialsBean credentialsBean) throws WrongEmailOrPasswordException {
        try {
            Credentials credentials = new Credentials(credentialsBean.getMail(), credentialsBean.getPassword(), credentialsBean.getRole());
            LoginDAO.login(credentials);
            credentialsBean.setRole(credentials.getRole());
        } catch(SQLException e) { //CONTROLLARE ECCEZIONI
            throw new RuntimeException(e);
        } catch (WrongEmailOrPasswordException e) {
            throw new WrongEmailOrPasswordException(e.getMessage());
        }
    }

    public void retrievePatient(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), null, null, null, null, false, false, false, null, null);
        RetrieveDAO.retrievePatient(patient);
        patientBean.setName(patient.getName());
        patientBean.setSurname(patient.getSurname());
        patientBean.setCity(patient.getCity());
        patientBean.setDescription(patient.getDescription());
        patientBean.setInPerson(patient.isInPerson());
        patientBean.setOnline(patient.isOnline());
        patientBean.setPag(patient.isPag());
    }

    public void retrievePsychologist(PsychologistBean psychologistBean) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), null, null, null, null, false, false, false, null, null);
        RetrieveDAO.retrievePsychologist(psychologist);
        psychologistBean.setName(psychologist.getName());
        psychologistBean.setSurname(psychologist.getSurname());
        psychologistBean.setCity(psychologist.getCity());
        psychologistBean.setDescription(psychologist.getDescription());
        psychologistBean.setInPerson(psychologist.isInPerson());
        psychologistBean.setOnline(psychologist.isOnline());
        psychologistBean.setPag(psychologist.isPag());
    }
}
