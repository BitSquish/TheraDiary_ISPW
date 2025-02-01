package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.model.*;


import java.util.List;

public interface RetrieveDAO {
    //Metodi per la ricerca di psicologi
    void searchPsychologists(List<Psychologist> psychologists, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) throws NoResultException;
    //Recupera lo studio medico
    boolean retrieveMedicalOffice(MedicalOffice medicalOffice);


    //Recupera le categorie e le specializzazioni
    boolean retrieveCategories(Patient patient);
    boolean retrieveMajors(Psychologist psychologist);

    //Recupera la lista dei pazienti
    List<Patient> retrievePatientList(Psychologist psychologist);
    //Recupera le richieste di appuntamento
    void checkPag(LoggedUser loggedUser);
    //Recupera le richieste di appuntamento
    void retrievePatientsRequest(Psychologist psychologist, List<Request> requests);
    //Recupera lo psicologo del paziente
    Psychologist yourPsychologist(Patient patient);
    //Recupera la lista degli appuntamenti
    void retrieveAllAppointments(Psychologist psychologist, List<Appointment> appointments);
    //Recupera l'appuntamento del paziente
    Appointment retrievePatientAppointment(Patient patient, Psychologist psychologist);


}




