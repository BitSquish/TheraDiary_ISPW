package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Psychologist;
import java.util.List;

public class AppointmentPs {
    public void loadAppointment(List<AppointmentBean> appointmentsBean, PsychologistBean psychologistBean, DayOfTheWeek dayOfTheWeek) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        List<Appointment> appointments = RetrieveDAO.retrieveSlotTime(psychologist, dayOfTheWeek);
        for(Appointment appointment : appointments) {
            AppointmentBean appointmentBean = appointment.toBean();
            appointmentBean.setPatientBean(appointment.getPatient().toBean());
            appointmentsBean.add(appointmentBean);
        }
    }
}
