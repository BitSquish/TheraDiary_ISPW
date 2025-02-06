package com.theradiary.ispwtheradiary.controller;


import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;

import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;

import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;

import java.util.List;
import java.util.Objects;

public class AppointmentPtController extends AppointmentController {

    //Costruttore
    public AppointmentPtController() {
        super();
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }


    // Controlla se il paziente ha già un appuntamento associato
    public AppointmentBean getAppointmentIfExists(PatientBean patientBean, List<AppointmentBean> allAppointments) {
        return allAppointments.stream()
                .filter(appointmentBean -> appointmentBean.getPatientBean() != null
                        && appointmentBean.getPatientBean().equals(patientBean.getCredentialsBean().getMail())
                        && !appointmentBean.isAvailable())
                .findFirst() // Trova al massimo il primo appuntamento che soddisfa la condizione
                .orElse(null); // Restituisce null se non c'è alcun appuntamento che soddisfa la condizione
    }

    // invio della richiesta di appuntamento
    public void askForAnAppointment(AppointmentBean appointmentBean) {
        Appointment appointment = beanAndModelMapperFactory.fromBeanToModel(appointmentBean, AppointmentBean.class);
        appointment.setPatient(new Patient(new Credentials(appointmentBean.getPatientBean(), Role.PATIENT)));
        appointment.setAvailable(false);
        updateDAO.setRequestForAppointment(appointment);
    }


    // Rimuove dalla lista degli appuntamenti quelli non disponibili e quelli per cui il paziente ha già fatto richiesta
    public void loadAvailableAppointments(List<AppointmentBean> allAppointments, PatientBean patientBean) {
        allAppointments.removeIf(appointmentBean -> !appointmentBean.isAvailable() || (appointmentBean.isAvailable() && Objects.equals(appointmentBean.getPatientBean(), patientBean.getCredentialsBean().getMail())));
    }
}
