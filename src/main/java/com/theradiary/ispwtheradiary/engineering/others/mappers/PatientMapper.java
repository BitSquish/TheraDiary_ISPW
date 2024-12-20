package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.Patient;

public class PatientMapper implements BeanAndModelMapper<PatientBean, Patient> {
    private final CredentialsMapper credentialsMapper = new CredentialsMapper();
    @Override
    public Patient fromBeanToModel(PatientBean bean) {
        if(bean.getName() != null)
            return new Patient(credentialsMapper.fromBeanToModel(bean.getCredentialsBean()), bean.getName(), bean.getSurname(), bean.getCity(), bean.getDescription(), bean.isInPerson(), bean.isOnline());
        else
            return new Patient(credentialsMapper.fromBeanToModel(bean.getCredentialsBean()));
    }

    @Override
    public PatientBean fromModelToBean(Patient model) {
        if(model.getName() != null)
            return new PatientBean(credentialsMapper.fromModelToBean(model.getCredentials()), model.getName(), model.getSurname(), model.getCity(), model.getDescription(), model.isInPerson(), model.isOnline());
        else
            return new PatientBean(credentialsMapper.fromModelToBean(model.getCredentials()));
    }
}
