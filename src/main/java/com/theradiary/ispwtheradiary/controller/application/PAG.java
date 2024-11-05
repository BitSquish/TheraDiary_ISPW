package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.LoggedUser;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import java.sql.SQLException;

public class PAG {
    public void joinPag(LoggedUserBean loggedUserBean) throws SQLException {
        System.out.println("LoggedUserBean in applicativo " + loggedUserBean.isPag());
        if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
            Psychologist psychologist = new Psychologist(new Credentials(loggedUserBean.getCredentialsBean().getMail(), loggedUserBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline(), loggedUserBean.isPag(), null, null);
            System.out.println("Psicologo: " + psychologist.isPag());
            UpdateDAO.modifyPsychologist(psychologist);
        }
        else if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
            System.out.println("User if paziente " + loggedUserBean.isPag());
            Patient patient = new Patient(new Credentials(loggedUserBean.getCredentialsBean().getMail(), loggedUserBean.getCredentialsBean().getPassword(), Role.PATIENT), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline(), loggedUserBean.isPag(), null, null);
            System.out.println("Paziente: " + patient.isPag());
            UpdateDAO.modifyPatient(patient);
        }
    }
}
