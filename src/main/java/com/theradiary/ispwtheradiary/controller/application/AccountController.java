package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;

import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;

import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;

import java.util.ArrayList;
import java.util.List;

public class AccountController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final RetrieveDAO retrieveDAO=FactoryDAO.getRetrieveDAO();
    private final CategoryAndMajorDAO categoryAndMajorDAO =  FactoryDAO.getCategoryAndMajorDAO();
    public AccountController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }


    public void addCategory(PatientBean patientBean, Category category) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        categoryAndMajorDAO.addCategory(patient, category);
        patientBean.setCategories(patient.getCategories());
    }

    public void addMajor(PsychologistBean psychologistBean, Major major) {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        categoryAndMajorDAO.addMajor(psychologist, major);
        psychologistBean.setMajor(psychologist.getMajors());
    }

    public void retrieveCategories(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        boolean categoriesAlreadyInserted = retrieveDAO.retrieveCategories(patient);
        if (categoriesAlreadyInserted) {
            patientBean.setCategories(patient.getCategories());
        }
    }

    public void retrieveMajors(PsychologistBean psychologistBean) {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        boolean majorsAlreadyInserted = retrieveDAO.retrieveMajors(psychologist);
        if (majorsAlreadyInserted) {
            psychologistBean.setMajor(psychologist.getMajors());
        }
    }

    public void removeCategory(PatientBean patientBean, Category category) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        categoryAndMajorDAO.removeCategory(patient, category);
        patientBean.setCategories(patient.getCategories());

    }

    public void removeMajor(PsychologistBean psychologistBean, Major major) {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        categoryAndMajorDAO.removeMajor(psychologist, major);
        psychologistBean.setMajor(psychologist.getMajors());
    }

    public List<PatientBean> retrievePatientList(PsychologistBean psychologistBean) {
        // Conversione del PsychologistBean in Psychologist (entità)
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);

        List<PatientBean> patientBeans = new ArrayList<>();

        // Recupero la lista di pazienti dal DAO
        List<Patient> patients = retrieveDAO.retrievePatientList(psychologist);

        // Conversione da Patient (entità) a PatientBean
        for (Patient patient : patients) {
            PatientBean patientBean = beanAndModelMapperFactory.fromModelToBean(patient, Patient.class);
            patientBeans.add(patientBean);
        }
        return patientBeans;
    }
    public void yourPsychologist(PatientBean patientBean, PsychologistBean psychologistBean){
        if(psychologistBean != null) {
            // Conversione del PatientBean in Patient (entità)
            Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);

            // Recupero il Psychologist dal DAO
            Psychologist psychologist = retrieveDAO.yourPsychologist(patient);
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



