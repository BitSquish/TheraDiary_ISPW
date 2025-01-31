package com.theradiary.ispwtheradiary.engineering.dao;


import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.*;

import java.sql.SQLException;

public interface UpdateDAO {
    boolean emailExists(String mail) throws SQLException, DatabaseOperationException;
    void modifyMedicalOffice(MedicalOffice medicalOffice);
    void modifyCredentials(Credentials newCredentials, Credentials oldCredentials) throws MailAlreadyExistsException;
    void modifyPatient(Patient patient);
    void modifyPsychologist(Psychologist psychologist);
    void joinPagPsychologist(Psychologist psychologist);
    void joinPagPatient(Patient patient);
    void deleteRequest(Request request);
    void setRequestForAppointment(Appointment appointment);
    void clearAppointments(Psychologist psychologist, DayOfTheWeek day);
    void addAppointments(Appointment appointmentToAdd);
    void setPatientsPsychologist(Patient patient);
    void registerMedicalOffice(MedicalOffice medicalOffice);



    void deleteMedicalOffice(MedicalOffice medicalOffice);


}
