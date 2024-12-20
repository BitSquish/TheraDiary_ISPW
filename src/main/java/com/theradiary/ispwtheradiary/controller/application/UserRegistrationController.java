package com.theradiary.ispwtheradiary.controller.application;


import com.theradiary.ispwtheradiary.engineering.dao.RegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;

import java.sql.SQLException;

public class UserRegistrationController {
    public UserRegistrationController() {
    }


    //NOTA: Su registrazione il metodo è privato e viene chiamato dal costruttore. Su login il metodo è statico e non serve istanziare la classe.
    //VEDERE QUALE DELLE DUE SOLUZIONI È MIGLIORE

    public  UserRegistrationController(LoggedUserBean loggedUserBean) throws MailAlreadyExistsException {
        if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
            registerPatient(new PatientBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline()));
        }
        else if (loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
            registerPsychologist(new PsychologistBean(loggedUserBean.getCredentialsBean(), loggedUserBean.getName(), loggedUserBean.getSurname(), loggedUserBean.getCity(), loggedUserBean.getDescription(), loggedUserBean.isInPerson(), loggedUserBean.isOnline()));
        }

    }


    public static void registerPatient(PatientBean patientBean) throws MailAlreadyExistsException {//metodo per registrare un paziente nel database
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        try {
            RegistrationDAO.registerPatient(patient);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);  //DA VERIFICARE IL TIPO DI ECCEZIONE
        } catch (MailAlreadyExistsException exception) {
            throw new MailAlreadyExistsException(exception.getMessage());
        }
    }

    public static void registerPsychologist(PsychologistBean psychologistBean) throws MailAlreadyExistsException {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST) ,psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        try {
            RegistrationDAO.registerPsychologist(psychologist);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } catch (MailAlreadyExistsException exception){
            throw new MailAlreadyExistsException(exception.getMessage());
        }
    }
}
