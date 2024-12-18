package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;

import java.sql.SQLException;

public class PagController {
    public void joinPag(LoggedUserBean loggedUserBean) throws SQLException {
        if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
            Psychologist psychologist = new Psychologist(new Credentials(loggedUserBean.getCredentialsBean().getMail(), loggedUserBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline());
            UpdateDAO.joinPagPsychologist(psychologist);
        }
        else if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
            Patient patient = new Patient(new Credentials(loggedUserBean.getCredentialsBean().getMail(), loggedUserBean.getCredentialsBean().getPassword(), Role.PATIENT), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline());
            UpdateDAO.joinPagPatient(patient);
        }
    }
}
