package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;

import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.util.List;

public class AppointmentPsController extends AppointmentController {

    public AppointmentPsController() {
        super();
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    // Questo metodo recupera gli appuntamenti di un determinato giorno della settimana
    public void getDayOfTheWeekAppointments(List<AppointmentBean> appointmentsBean, DayOfTheWeek dayOfTheWeek, List<TimeSlot> inPersonTimeSlot, List<TimeSlot> onlineTimeSlot) {
        for(AppointmentBean appointmentBean : appointmentsBean) {
            if(appointmentBean.getDay().equals(dayOfTheWeek)) {
                if(appointmentBean.isInPerson())
                    inPersonTimeSlot.add(appointmentBean.getTimeSlot());
                if (appointmentBean.isOnline())
                    onlineTimeSlot.add(appointmentBean.getTimeSlot());
            }
        }
    }
    //Salva gli appuntamenti
    public void saveAppointments(PsychologistBean psychologistBean, List<AppointmentBean> appointmentToAdd) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        DayOfTheWeek day = appointmentToAdd.get(0).getDay();
        updateDAO.clearAppointments(psychologist, day); //rimuovo dal database tutti gli appuntamenti dove il giorno della settimana corrisponde ai nuovi appuntamenti da caricare
        //Da qui in giù da controllare
        for(AppointmentBean appointmentBean : appointmentToAdd) {
            Appointment appointment = beanAndModelMapperFactory.fromBeanToModel(appointmentBean, AppointmentBean.class);
            appointment.setPatient(new Patient(new Credentials(appointmentBean.getPatientBean(), Role.PATIENT)));
            appointment.setAvailable(appointmentBean.isAvailable());
            updateDAO.addAppointments(appointment);
        }
    }

    //Cambia la modalità di appuntamento sul profilo psicologo
    public boolean changeModality(List<AppointmentBean> appointmentsToAdd) {
        Psychologist psychologist = new Psychologist(new Credentials(appointmentsToAdd.get(0).getPsychologistBean().getCredentialsBean().getMail(), Role.PSYCHOLOGIST), appointmentsToAdd.get(0).getPsychologistBean().getName(), appointmentsToAdd.get(0).getPsychologistBean().getSurname(), appointmentsToAdd.get(0).getPsychologistBean().getCity(), appointmentsToAdd.get(0).getPsychologistBean().getDescription(), appointmentsToAdd.get(0).getPsychologistBean().isInPerson(), appointmentsToAdd.get(0).getPsychologistBean().isOnline());
        boolean hasChanged = false;
        for(AppointmentBean appointmentBean : appointmentsToAdd) {
            if(appointmentBean.isInPerson() && !psychologist.isInPerson()){
                psychologist.setInPerson(true);
                hasChanged = true;
            }
            if(appointmentBean.isOnline() && !psychologist.isOnline()){
                psychologist.setOnline(true);
                hasChanged = true;
            }
        }
        if(hasChanged) {
            updateDAO.modifyPsychologist(psychologist);
        }
        return hasChanged;
    }
}
