package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.dao.UpdateDAO;

import com.theradiary.ispwtheradiary.beans.AppointmentBean;

import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Appointment;

import com.theradiary.ispwtheradiary.model.Psychologist;

import java.util.ArrayList;
import java.util.List;

/***********************Parte del caso d'uso: gestisci apppuntamento (psicologo) e prenota appuntamento (paziente)******************/
public abstract class AppointmentController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    protected final RetrieveDAO retrieveDAO= FactoryDAO.getRetrieveDAO();
    protected final UpdateDAO updateDAO= FactoryDAO.getUpdateDAO();
    protected AppointmentController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    //Questo metodo recupera tutte le disponibilità dello psicologo
    public void loadAllAppointments(List<AppointmentBean> appointmentsBean, PsychologistBean psychologistBean) {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        List<Appointment> appointments = new ArrayList<>();
        retrieveDAO.retrieveAllAppointments(psychologist, appointments);
        for(Appointment appointment : appointments) {
            AppointmentBean appointmentBean = beanAndModelMapperFactory.fromModelToBean(appointment, Appointment.class);
            if(appointment.getPatient().getCredentials().getMail() != null){
                appointmentBean.setPatientBean(appointment.getPatient().getCredentials().getMail());
            }
            appointmentBean.setAvailable(appointment.isAvailable());
            appointmentsBean.add(appointmentBean);
        }
    }







}
