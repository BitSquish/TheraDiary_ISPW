package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;

import java.util.ArrayList;
import java.util.List;

public class AccountController {


    public void addCategory(PatientBean patientBean, Category category) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        CategoryAndMajorDAO.addCategory(patient, category);
        patientBean.setCategories(patient.getCategories());
    }

    public void addMajor(PsychologistBean psychologistBean, Major major) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        CategoryAndMajorDAO.addMajor(psychologist, major);
        psychologistBean.setMajor(psychologist.getMajors());
    }

    public void retrieveCategories(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        boolean categoriesAlreadyInserted = RetrieveDAO.retrieveCategories(patient);
        if (categoriesAlreadyInserted) {
            patientBean.setCategories(patient.getCategories());
        }
    }

    public void retrieveMajors(PsychologistBean psychologistBean) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        boolean majorsAlreadyInserted = RetrieveDAO.retrieveMajors(psychologist);
        if (majorsAlreadyInserted) {
            psychologistBean.setMajor(psychologist.getMajors());
        }
    }

    public void removeCategory(PatientBean patientBean, Category category) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        CategoryAndMajorDAO.removeCategory(patient, category);
        patientBean.setCategories(patient.getCategories());

    }

    public void removeMajor(PsychologistBean psychologistBean, Major major) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        CategoryAndMajorDAO.removeMajor(psychologist, major);
        psychologistBean.setMajor(psychologist.getMajors());
    }

    public List<PatientBean> retrievePatientList(PsychologistBean psychologistBean) {
        // Conversione del PsychologistBean in Psychologist (entità)
        Psychologist psychologist = new Psychologist(
                new Credentials(
                        psychologistBean.getCredentialsBean().getMail(),
                        psychologistBean.getCredentialsBean().getPassword(),
                        Role.PSYCHOLOGIST
                ),
                psychologistBean.getName(),
                psychologistBean.getSurname(),
                psychologistBean.getCity(),
                psychologistBean.getDescription(),
                psychologistBean.isInPerson(),
                psychologistBean.isOnline()
        );

        List<PatientBean> patientBeans = new ArrayList<>();

        // Recupero la lista di pazienti dal DAO
        List<Patient> patients = RetrieveDAO.retrievePatientList(psychologist);

        // Conversione da Patient (entità) a PatientBean
        for (Patient patient : patients) {
            PatientBean patientBean = patient.toBean();
            patientBeans.add(patientBean);
        }
        return patientBeans;
    }
    public void yourPsychologist(PatientBean patientBean, PsychologistBean psychologistBean) {
        if(psychologistBean != null) {
            // Conversione del PatientBean in Patient (entità)
            Patient patient = new Patient(
                    new Credentials(
                            patientBean.getCredentialsBean().getMail(),
                            patientBean.getCredentialsBean().getPassword(),
                            Role.PATIENT
                    ),
                    patientBean.getName(),
                    patientBean.getSurname(),
                    patientBean.getCity(),
                    patientBean.getDescription(),
                    patientBean.isInPerson(),
                    patientBean.isOnline()
            );

            // Recupero il Psychologist dal DAO
            Psychologist psychologist = RetrieveDAO.yourPsychologist(patient);
            // Conversione da Psychologist (entità) a PsychologistBean
            if(psychologist != null){
                psychologistBean.setName(psychologist.getName());
                psychologistBean.setSurname(psychologist.getSurname());
                psychologistBean.setCity(psychologist.getCity());
                psychologistBean.setDescription(psychologist.getDescription());
                psychologistBean.setInPerson(psychologist.isInPerson());
                psychologistBean.setOnline(psychologist.isOnline());
            }
        }
    }
}



