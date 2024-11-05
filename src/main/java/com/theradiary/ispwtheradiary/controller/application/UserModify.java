package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

public class UserModify {
    public UserModify(LoggedUserBean loggedUserBean) {
        if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
            modifyPatient(new PatientBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline(), false, null, null));
        }
        else if (loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
            modifyPsychologist(new PsychologistBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline(), false, null, null));
        }
    }

    private void modifyPsychologist(PsychologistBean psychologistBean) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), psychologistBean.getCredentialsBean().getRole()), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null, null);
        try{
            UpdateDAO.modifyPsychologist(psychologist);
        }catch (Exception e){
            //gestione dell'eccezione
        }

    }

    private void modifyPatient(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), patientBean.getCredentialsBean().getRole()), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), false, null, null);
        try {
            UpdateDAO.modifyPatient(patient);
        } catch (Exception e){
            //gestione dell'eccezione
        }
    }
}
