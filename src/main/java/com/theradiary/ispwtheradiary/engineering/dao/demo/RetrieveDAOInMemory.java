package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.model.*;

import java.util.ArrayList;
import java.util.List;

public class RetrieveDAOInMemory implements RetrieveDAO {
    @Override
    public void searchPsychologists(List<Psychologist> psychologists, String name, String surname, String city, boolean inPerson, boolean online, boolean pag)  {

    }

    @Override
    public boolean retrieveMedicalOffice(MedicalOffice medicalOffice) {
        return false;
    }

    @Override
    public void retrievePatient(Patient patient) {

    }

    @Override
    public void retrievePsychologist(Psychologist psychologist) {

    }

    @Override
    public boolean retrieveCategories(Patient patient) {
        return false;
    }

    @Override
    public void addCategory(ArrayList<Category> categories, String categoryName) {

    }

    @Override
    public boolean retrieveMajors(Psychologist psychologist) {
        return false;
    }

    @Override
    public void addMajor(ArrayList<Major> majors, String majorName) {

    }

    @Override
    public List<Patient> retrievePatientList(Psychologist psychologist) {
        return null;
    }

    @Override
    public void checkPag(LoggedUser loggedUser) {

    }

    @Override
    public void retrievePatientsRequest(Psychologist psychologist, List<Request> requests) {

    }

    @Override
    public Psychologist yourPsychologist(Patient patient) {
        return null;
    }

    @Override
    public void retrieveAllAppointments(Psychologist psychologist, List<Appointment> appointments) {

    }

    @Override
    public Appointment retrievePatientAppointment(Patient patient, Psychologist psychologist)  {
        return null;
    }
    //TODO
}
