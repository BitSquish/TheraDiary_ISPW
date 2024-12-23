package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;

public class AppointmentMapper implements BeanAndModelMapper<AppointmentBean, Appointment>{

    PsychologistMapper psychologistMapper = new PsychologistMapper();
    PatientMapper patientMapper = new PatientMapper();
    @Override
    public Appointment fromBeanToModel(AppointmentBean bean) {
        if(bean.getDay() != null)
            return new Appointment(psychologistMapper.fromBeanToModel(bean.getPsychologistBean()), bean.getDay(), bean.getTimeSlot(), bean.isInPerson(), bean.isOnline());
        else
            return new Appointment(psychologistMapper.fromBeanToModel(bean.getPsychologistBean()), new Patient(new Credentials(bean.getPatientBean(), Role.PATIENT)));
    }


    //TODO: da modificare insieme a CLI, costruttori del bean devono combaciare con quelli del model
    @Override
    public AppointmentBean fromModelToBean(Appointment model) {
        if(!model.isInPerson() && !model.isOnline()){
            return new AppointmentBean(psychologistMapper.fromModelToBean(model.getPsychologist()), model.getDay(), model.getTimeSlot(), patientMapper.fromModelToBean(model.getPatient()).getCredentialsBean().getMail());
        }
        else
            return new AppointmentBean(psychologistMapper.fromModelToBean(model.getPsychologist()), model.getDay(), model.getTimeSlot(), model.isInPerson(), model.isOnline());

    }
}
