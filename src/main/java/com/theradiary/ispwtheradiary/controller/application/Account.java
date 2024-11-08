package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;

import java.util.List;

public class Account {

    public void addCategory(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        CategoryAndMajorDAO.addCategory(patient);
    }

    public void addMajor(PsychologistBean psychologistBean) {
        Psychologist psychologist= new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null, psychologistBean.getMajors());
        CategoryAndMajorDAO.addMajor(psychologist);
    }
    public void retrieveCategories(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        boolean categoriesAlreadyInserted = RetrieveDAO.retrieveCategories(patient);
        if(categoriesAlreadyInserted){
            patientBean.setCategories(patient.getCategories());
        }
    }

    public void retrieveMajors(PsychologistBean psychologistBean) {
        Psychologist psychologist= new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null, psychologistBean.getMajors());
        boolean majorsAlreadyInserted = RetrieveDAO.retrieveMajors(psychologist);
        if(majorsAlreadyInserted){
            psychologistBean.setMajor(psychologist.getMajors());
        }
    }

    public void removeCategory(PatientBean patientBean) {
        List<Category> categories=patientBean.getCategories();
        String mail=patientBean.getCredentialsBean().getMail();
        CategoryAndMajorDAO.removeCategory(categories,mail);
    }

    public void removeMajor(PsychologistBean psychologistBean) {
        List<Major> majors=psychologistBean.getMajors();
        String mail=psychologistBean.getCredentialsBean().getMail();
        CategoryAndMajorDAO.removeMajor(majors,mail);
    }


}
