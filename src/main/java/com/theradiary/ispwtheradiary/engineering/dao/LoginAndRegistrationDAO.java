package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

public interface LoginAndRegistrationDAO {

    boolean emailExists(String mail) ;
    boolean insertUser(Credentials credentials) ;
    void registerPatient(Patient patient) throws MailAlreadyExistsException, LoginAndRegistrationException;
    void registerPsychologist(Psychologist psychologist) throws MailAlreadyExistsException, LoginAndRegistrationException;
    void login(Credentials credentials) throws WrongEmailOrPasswordException,LoginAndRegistrationException;
    void retrievePatient(Patient patient) throws NoResultException;

    void retrievePsychologist(Psychologist psychologist) throws NoResultException;
    void removePatient(Patient patient);

    void removePsychologist(Psychologist psychologist);
}
