package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.model.*;

public class UpdateDAOInMemory implements UpdateDAO {
    @Override
    public boolean emailExists(String mail) {
        return false;
    }

    @Override
    public void modifyMedicalOffice(MedicalOffice medicalOffice) {

    }

    @Override
    public void modifyCredentials(Credentials newCredentials, Credentials oldCredentials) {

    }

    @Override
    public void modifyPatient(Patient patient) {

    }

    @Override
    public void modifyPsychologist(Psychologist psychologist) {

    }

    @Override
    public void joinPagPsychologist(Psychologist psychologist) {

    }

    @Override
    public void joinPagPatient(Patient patient) {

    }

    @Override
    public void deleteRequest(Request request) {

    }

    @Override
    public void setRequestForAppointment(Appointment appointment) {

    }

    @Override
    public void clearAppointments(Psychologist psychologist, DayOfTheWeek day) {

    }

    @Override
    public void addAppointments(Appointment appointmentToAdd) {

    }

    @Override
    public void setPatientsPsychologist(Patient patient) {

    }
    //TODO
}
