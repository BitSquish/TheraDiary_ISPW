package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

public class Account {
    public void addCategory(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        for (int i = 0; i < patientBean.getCategories().size(); i++) {
            patient.addCategory(patientBean.getCategories().get(i));
        }
        CategoryAndMajorDAO.addCategory(patient);
    }

    public void addMajor(PsychologistBean psychologistBean) {
        Psychologist psychologist= new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null, psychologistBean.getMajors());
        for(int i=0;i<psychologistBean.getMajors().size();i++){
            psychologist.addMajor(psychologistBean.getMajors().get(i));
        }
        CategoryAndMajorDAO.addMajor(psychologist);
    }
    public boolean retrieveCategories(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        boolean categoriesAlreadyInserted = RetrieveDAO.retrieveCategories(patient);
        if(categoriesAlreadyInserted){
            patientBean.setCategories(patient.getCategories());
        }
        return categoriesAlreadyInserted;
    }

    public boolean retrieveMajors(PsychologistBean psychologistBean) {
        Psychologist psychologist= new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null, psychologistBean.getMajors());
        boolean majorsAlreadyInserted = RetrieveDAO.retrieveMajors(psychologist);
        if(majorsAlreadyInserted){
            psychologistBean.setMajor(psychologist.getMajors());
        }
        return majorsAlreadyInserted;

    }

    public void removeCategory(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        for (int i = 0; i < patientBean.getCategories().size(); i++) {
            patient.removeCategory(patientBean.getCategories().get(i));
        }
        CategoryAndMajorDAO.removeCategory(patient);
    }

    public void removeMajor(PsychologistBean psychologistBean) {
        Psychologist psychologist= new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null, psychologistBean.getMajors());
        for(int i=0;i<psychologistBean.getMajors().size();i++){
            psychologist.removeMajor(psychologistBean.getMajors().get(i));
        }
        CategoryAndMajorDAO.removeMajor(psychologist);
    }
}
