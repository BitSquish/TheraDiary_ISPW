package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.LoggedUser;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;

import java.sql.SQLException;

public class LoginController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final LoginAndRegistrationDAO loginGeneric;
    public LoginController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.loginGeneric = FactoryDAO.getDAO();
    }

    public void log(CredentialsBean credentialsBean) throws WrongEmailOrPasswordException {
        try {
            Credentials credentials = beanAndModelMapperFactory.fromBeanToModel(credentialsBean, CredentialsBean.class);
            loginGeneric.login(credentials);
            credentialsBean.setRole(credentials.getRole());
        } catch (WrongEmailOrPasswordException e) {
            throw new WrongEmailOrPasswordException(e.getMessage());
        } catch (LoginAndRegistrationException e) {
            Printer.errorPrint(e.getMessage());
        }
    }

    public void retrievePatient(PatientBean patientBean) throws NoResultException {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        retrieveUser(patient, patientBean);
    }

    public void retrievePsychologist(PsychologistBean psychologistBean) throws NoResultException {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        retrieveUser(psychologist, psychologistBean);
    }

    private <M extends LoggedUser, B extends LoggedUserBean> void retrieveUser (M user, B userBean) throws NoResultException {
        // Recupera l'utente dal DAO
        if (user.getCredentials().getRole().equals(Role.PATIENT)) {
           loginGeneric.retrievePatient((Patient) user);
            PsychologistBean psychologistBean = beanAndModelMapperFactory.fromModelToBean((((Patient) user).getPsychologist()), Psychologist.class);
            ((PatientBean)userBean).setPsychologistBean(psychologistBean);
        }else if (user.getCredentials().getRole().equals(Role.PSYCHOLOGIST)) {
            loginGeneric.retrievePsychologist((Psychologist) user);
        }
        // Imposta i dati nel bean
        userBean.setName(user.getName());
        userBean.setSurname(user.getSurname());
        userBean.setCity(user.getCity());
        userBean.setDescription(user.getDescription());
        userBean.setInPerson(user.isInPerson());
        userBean.setOnline(user.isOnline());
        userBean.setPag(user.isPag());

    }
}
