package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentPsController {
    //Questo metodo recupera tutte le disponibilità dello psicologo
    public void loadAllAppointments(List<AppointmentBean> appointmentsBean, PsychologistBean psychologistBean) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        List<Appointment> appointments = new ArrayList<>();
        RetrieveDAO.retrieveAllAppointments(psychologist, appointments);
        for(Appointment appointment : appointments) {
            AppointmentBean appointmentBean = appointment.toBean();
            if(appointment.getPatient() != null){
                appointmentBean.setPatientBean(appointment.getPatient().getCredentials().getMail());
            }
            appointmentBean.setAvailable(appointment.isAvailable());
            appointmentsBean.add(appointmentBean);
        }
    }

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

    public void saveAppointments(PsychologistBean psychologistBean, List<AppointmentBean> appointmentToAdd) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        UpdateDAO.clearAppointments(psychologist);
        for(AppointmentBean appointmentBean : appointmentToAdd) {
            Appointment appointment = new Appointment(psychologist, appointmentBean.getDay(), appointmentBean.getTimeSlot(), appointmentBean.isInPerson(), appointmentBean.isOnline());
            appointment.setPatient(new Patient(new Credentials(appointmentBean.getPatientBean(), Role.PATIENT)));
            appointment.setAvailable(appointmentBean.isAvailable());
            UpdateDAO.addAppointments(appointment);
        }
    }

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
            try {
                UpdateDAO.modifyPsychologist(psychologist);
            } catch (SQLException e) {
                throw new RuntimeException(e);  //TODO gestione eccezione
            }
        }
        return hasChanged;
    }
}
