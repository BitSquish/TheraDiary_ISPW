package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

/***********************Parte del caso d'uso: Gestisci paziente*************************/
public class PatientProfileController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    //Costruttore
    public PatientProfileController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    //Metodo per recuperare le categorie del paziente
    public void retrieveCategories(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        boolean categoriesAlreadyInserted = retrieveDAO.retrieveCategories(patient);
        if (categoriesAlreadyInserted) {
            patientBean.setCategories(patient.getCategories());
        }
    }
    //Metodo per recuperare un eventuale appuntamento fissato col paziente
    public AppointmentBean retrieveAppointment(PatientBean patientBean, PsychologistBean psychologistBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        Appointment appointment = retrieveDAO.retrievePatientAppointment(patient, psychologist);
        if(appointment == null) {
            return null;
        }
        return beanAndModelMapperFactory.fromModelToBean(appointment, Appointment.class);
    }
}
