package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

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
    //Metodo per recuperare le specializzazioni dello psicologo

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
