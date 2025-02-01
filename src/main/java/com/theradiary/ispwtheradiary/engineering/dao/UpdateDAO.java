package com.theradiary.ispwtheradiary.engineering.dao;


import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.*;

import java.sql.SQLException;

public interface UpdateDAO {
    //metodo per la modifica delle credenziali
    boolean emailExists(String mail) throws SQLException, DatabaseOperationException;

    void modifyCredentials(Credentials newCredentials, Credentials oldCredentials) throws MailAlreadyExistsException;
    void modifyPatient(Patient patient);
    void modifyPsychologist(Psychologist psychologist);
    //metodi per la getsione del pag
    void joinPagPsychologist(Psychologist psychologist);
    void joinPagPatient(Patient patient);
    //metodi per la gestione delle richieste
    void deleteRequest(Request request);
    //metodi per la gestione degli appuntamenti
    void setRequestForAppointment(Appointment appointment);
    void clearAppointments(Psychologist psychologist, DayOfTheWeek day);
    void addAppointments(Appointment appointmentToAdd);
    void setPatientsPsychologist(Patient patient);
    //metodi per la gestione degli studi medici
    void registerMedicalOffice(MedicalOffice medicalOffice);

    void deleteMedicalOffice(MedicalOffice medicalOffice);
    void modifyMedicalOffice(MedicalOffice medicalOffice);


}
