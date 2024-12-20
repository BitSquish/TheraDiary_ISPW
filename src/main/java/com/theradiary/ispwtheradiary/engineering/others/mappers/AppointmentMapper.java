package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.Appointment;

public class AppointmentMapper implements BeanAndModelMapper<AppointmentBean, Appointment>{

    PsychologistMapper psychologistMapper = new PsychologistMapper();
    PatientMapper patientMapper = new PatientMapper();
    @Override
    public Appointment fromBeanToModel(AppointmentBean bean) {
        if(!bean.isInPerson() && !bean.isOnline()){         //Costruttore usato per inviare la richiesta
            return new Appointment(psychologistMapper.fromBeanToModel(bean.getPsychologistBean()), bean.getDay(), bean.getTimeSlot(), patientMapper.fromBeanToModel(new PatientBean(new CredentialsBean(bean.getPatientBean(), Role.PATIENT))));
        }
        else
            return new Appointment(psychologistMapper.fromBeanToModel(bean.getPsychologistBean()), bean.getDay(), bean.getTimeSlot(), bean.isInPerson(), bean.isOnline());
    }

    @Override
    public AppointmentBean fromModelToBean(Appointment model) {
        if(!model.isInPerson() && !model.isOnline()){
            return new AppointmentBean(psychologistMapper.fromModelToBean(model.getPsychologist()), model.getDay(), model.getTimeSlot(), patientMapper.fromModelToBean(model.getPatient()).getCredentialsBean().getMail());
        }
        else
            return new AppointmentBean(psychologistMapper.fromModelToBean(model.getPsychologist()), model.getDay(), model.getTimeSlot(), model.isInPerson(), model.isOnline());

    }
}
