package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import java.util.ArrayList;

public class Account {

    public void addCategory(PatientBean patientBean, Category category) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        CategoryAndMajorDAO.addCategory(patient, category);
        patientBean.setCategories(patient.getCategories());
    }

    public void addMajor(PsychologistBean psychologistBean, Major major) {
        Psychologist psychologist= new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), (ArrayList<Patient>) null, psychologistBean.getMajors());
        CategoryAndMajorDAO.addMajor(psychologist, major);
        psychologistBean.setMajor(psychologist.getMajors());
    }
    public void retrieveCategories(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        boolean categoriesAlreadyInserted = RetrieveDAO.retrieveCategories(patient);
        if(categoriesAlreadyInserted){
            patientBean.setCategories(patient.getCategories());
        }
    }

    public void retrieveMajors(PsychologistBean psychologistBean) {
        Psychologist psychologist= new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), (ArrayList<Patient>) null, psychologistBean.getMajors());
        boolean majorsAlreadyInserted = RetrieveDAO.retrieveMajors(psychologist);
        if(majorsAlreadyInserted){
            psychologistBean.setMajor(psychologist.getMajors());
        }
    }

    public void removeCategory(PatientBean patientBean, Category category) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        CategoryAndMajorDAO.removeCategory(patient, category);
        patientBean.setCategories(patient.getCategories());

    }

    public void removeMajor(PsychologistBean psychologistBean, Major major) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null, psychologistBean.getMajors());
        CategoryAndMajorDAO.removeMajor(psychologist, major);
        psychologistBean.setMajor(psychologist.getMajors());
    }


}
