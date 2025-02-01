package com.theradiary.ispwtheradiary.controller.application;


import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;


public class UserRegistrationController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final LoginAndRegistrationDAO registrationGenericDAO;
    //Costruttore
    public UserRegistrationController(){
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
        this.registrationGenericDAO = FactoryDAO.getDAO();
    }
    //Metodo per registrare un utente
    public void registerUser(LoggedUserBean loggedUserBean) throws MailAlreadyExistsException, LoginAndRegistrationException {
        if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
            registerPatient(new PatientBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline()));
        }
        else if (loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
            registerPsychologist(new PsychologistBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline()));
        }
    }

    //Metodo per registrare un paziente
    public void registerPatient(PatientBean patientBean) throws MailAlreadyExistsException, LoginAndRegistrationException {//metodo per registrare un paziente nel database
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        try {
            registrationGenericDAO.registerPatient(patient);
        } catch (MailAlreadyExistsException exception) {
            throw new MailAlreadyExistsException(exception.getMessage());
        } catch (LoginAndRegistrationException e) {
            throw new LoginAndRegistrationException(e.getMessage());
        }
    }
    //Metodo per registrare uno psicologo
    public void registerPsychologist(PsychologistBean psychologistBean) throws MailAlreadyExistsException {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        try {
            registrationGenericDAO.registerPsychologist(psychologist);
         //DA VERIFICARE IL TIPO DI ECCEZIONE
        } catch (MailAlreadyExistsException | LoginAndRegistrationException exception){
            throw new MailAlreadyExistsException(exception.getMessage());
        }
    }
}
