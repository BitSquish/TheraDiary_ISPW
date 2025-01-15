package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.model.*;


import java.util.ArrayList;
import java.util.List;

public interface RetrieveDAO {
    void searchPsychologists(List<Psychologist> psychologists, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) throws NoResultException;

    boolean retrieveMedicalOffice(MedicalOffice medicalOffice);



    boolean retrieveCategories(Patient patient);


    boolean retrieveMajors(Psychologist psychologist);


    List<Patient> retrievePatientList(Psychologist psychologist);

    void checkPag(LoggedUser loggedUser);

    void retrievePatientsRequest(Psychologist psychologist, List<Request> requests);

    Psychologist yourPsychologist(Patient patient);

    void retrieveAllAppointments(Psychologist psychologist, List<Appointment> appointments);

    Appointment retrievePatientAppointment(Patient patient, Psychologist psychologist) ;
}




