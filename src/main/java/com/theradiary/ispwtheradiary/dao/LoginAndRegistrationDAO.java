package com.theradiary.ispwtheradiary.dao;

import com.theradiary.ispwtheradiary.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

public interface LoginAndRegistrationDAO {
    //Metodi per il login e la registrazione

    boolean emailExists(String mail) ;
    boolean insertUser(Credentials credentials) ;
    void registerPatient(Patient patient) throws MailAlreadyExistsException, LoginAndRegistrationException;
    void registerPsychologist(Psychologist psychologist) throws MailAlreadyExistsException, LoginAndRegistrationException;
    void login(Credentials credentials) throws WrongEmailOrPasswordException,LoginAndRegistrationException;
    void retrievePatient(Patient patient) throws NoResultException;

    void retrievePsychologist(Psychologist psychologist) throws NoResultException;
    void removePatient(Patient patient);


}
