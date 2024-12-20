package com.theradiary.ispwtheradiary.controller.application;


import com.theradiary.ispwtheradiary.engineering.dao.RegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.DAOException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;

import java.sql.SQLException;

public class UserRegistrationController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    public UserRegistrationController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }



    //NOTA: Su registrazione il metodo è privato e viene chiamato dal costruttore. Su login il metodo è statico e non serve istanziare la classe.
    //VEDERE QUALE DELLE DUE SOLUZIONI È MIGLIORE

    public UserRegistrationController(LoggedUserBean loggedUserBean) throws MailAlreadyExistsException {
        if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
            registerPatient(new PatientBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline()));
        }
        else if (loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
            registerPsychologist(new PsychologistBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline()));
        }

    }


    public void registerPatient(PatientBean patientBean) throws MailAlreadyExistsException {//metodo per registrare un paziente nel database
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        try {
            RegistrationDAO.registerPatient(patient);
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(),exception);  //DA VERIFICARE IL TIPO DI ECCEZIONE
        } catch (MailAlreadyExistsException exception) {
            throw new MailAlreadyExistsException(exception.getMessage());
        }
    }

    public void registerPsychologist(PsychologistBean psychologistBean) throws MailAlreadyExistsException {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        try {
            RegistrationDAO.registerPsychologist(psychologist);
        } catch (SQLException exception) {
            throw new DAOException(exception.getMessage(),exception);  //DA VERIFICARE IL TIPO DI ECCEZIONE
        } catch (MailAlreadyExistsException exception){
            throw new MailAlreadyExistsException(exception.getMessage());
        }
    }
}
