package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import java.sql.SQLException;

public class UserModify {
    public UserModify(LoggedUserBean loggedUserBean, CredentialsBean credentialsBean) throws MailAlreadyExistsException {
        try{
            UpdateDAO.modifyUsers(loggedUserBean.getCredentialsBean().getMail(), credentialsBean.getMail(), credentialsBean.getPassword(),loggedUserBean.getCredentialsBean().getPassword());
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate entry")){
                throw new MailAlreadyExistsException("Mail già esistente");
            }
        }
        if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
            modifyPatient(new PatientBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline()));
        }
        else if (loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
            modifyPsychologist(new PsychologistBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline()));
        }
    }

    private void modifyPsychologist(PsychologistBean psychologistBean) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), psychologistBean.getCredentialsBean().getRole()), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        try{
            UpdateDAO.modifyPsychologist(psychologist);
        }catch (Exception e){
            //gestione dell'eccezione
        }

    }

    private void modifyPatient(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), patientBean.getCredentialsBean().getRole()), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        try {
            UpdateDAO.modifyPatient(patient);
        } catch (Exception e){
            //gestione dell'eccezione
        }
    }
}
