package com.theradiary.ispwtheradiary.controller.application;


import com.theradiary.ispwtheradiary.engineering.dao.RegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import java.sql.SQLException;

public class UserRegistration {

    //NOTA: Su registrazione il metodo è privato e viene chiamato dal costruttore. Su login il metodo è statico e non serve istanziare la classe.
    //VEDERE QUALE DELLE DUE SOLUZIONI È MIGLIORE

    public UserRegistration(Object bean) throws MailAlreadyExistsException {
        if(bean instanceof PatientBean)
            registerPatient((PatientBean) bean);
        else if (bean instanceof PsychologistBean)
            registerPsychologist((PsychologistBean) bean);
    }


    private void registerPatient(PatientBean patientBean) throws MailAlreadyExistsException {//metodo per registrare un paziente nel database
        Credentials credentials = new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT);
        Patient patient = new Patient(credentials, patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), false);
        try {
            RegistrationDAO.registerPatient(patient);
        } catch (SQLException exception) {
            System.out.println("Error:" + exception.getMessage()); //DA MODIFICARE
            throw new RuntimeException(exception);  //DA VERIFICARE IL TIPO DI ECCEZIONE
        } catch (MailAlreadyExistsException exception) {
            throw new MailAlreadyExistsException(exception.getMessage());
        }
    }

    private void registerPsychologist(PsychologistBean psychologistBean) throws MailAlreadyExistsException {
        Credentials credentials = new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST);
        Psychologist psychologist = new Psychologist(credentials, psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null);
        try {
            RegistrationDAO.registerPsychologist(psychologist);
        } catch (SQLException exception) {
            System.out.println("Error:" + exception.getMessage());
            throw new RuntimeException(exception);
        } catch (MailAlreadyExistsException exception){
            throw new MailAlreadyExistsException(exception.getMessage());
        }
    }
}
