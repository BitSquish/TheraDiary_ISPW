package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;

public class AppointmentMapper implements BeanAndModelMapper<AppointmentBean, Appointment>{

    PsychologistMapper psychologistMapper = new PsychologistMapper();
    @Override
    public Appointment fromBeanToModel(AppointmentBean bean) {
        if(bean.getDay() != null){
            Appointment appointment = new  Appointment(psychologistMapper.fromBeanToModel(bean.getPsychologistBean()), bean.getDay(), bean.getTimeSlot(), bean.isInPerson(), bean.isOnline());
            appointment.setPatient(null);
            appointment.setAvailable(true);
            return appointment;
        }
        else
            return new Appointment(psychologistMapper.fromBeanToModel(bean.getPsychologistBean()), new Patient(new Credentials(bean.getPatientBean(), Role.PATIENT)));
    }


    @Override
    public AppointmentBean fromModelToBean(Appointment model) {
        return new AppointmentBean(psychologistMapper.fromModelToBean(model.getPsychologist()), model.getDay(), model.getTimeSlot(), model.isInPerson(), model.isOnline());
    }
}
