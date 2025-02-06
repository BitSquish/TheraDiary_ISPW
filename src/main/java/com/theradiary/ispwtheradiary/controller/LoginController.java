package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.LoginAndRegistrationDAO;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.LoggedUser;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;

/***********************Parte del caso d'uso: Login*************************/
public class LoginController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final LoginAndRegistrationDAO loginGeneric;
    // Costruttore
    public LoginController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.loginGeneric = FactoryDAO.getDAO();
    }
    // Metodo per il login
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
    // Metodo per il recupero dell'utente
    public void retrievePatient(PatientBean patientBean) throws NoResultException {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        retrieveUser(patient, patientBean);
    }
    // Metodo per il recupero dello psicologo
    public void retrievePsychologist(PsychologistBean psychologistBean) throws NoResultException {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        retrieveUser(psychologist, psychologistBean);
    }
    // Metodo per il recupero dell'utente
    private <M extends LoggedUser, B extends LoggedUserBean> void retrieveUser (M user, B userBean) throws NoResultException {
        // Recupera l'utente dal DAO
        if (user.getCredentials().getRole().equals(Role.PATIENT)) {
           loginGeneric.retrievePatient((Patient) user);
           if(((Patient) user).getPsychologist() != null){
                PsychologistBean psychologistBean = beanAndModelMapperFactory.fromModelToBean((((Patient) user).getPsychologist()), Psychologist.class);
                ((PatientBean)userBean).setPsychologistBean(psychologistBean);
            }
        }else if (user.getCredentials().getRole().equals(Role.PSYCHOLOGIST)) {
            loginGeneric.retrievePsychologist((Psychologist) user);
        }
        // Imposta i dati nel bean
        userBean.setName(user.getName());
        userBean.setSurname(user.getSurname());
        userBean.setCity(user.getCity());
        userBean.setInPerson(user.isInPerson());
        userBean.setOnline(user.isOnline());
        userBean.setDescription(user.getDescription());
        userBean.setPag(user.isPag());

    }
}
