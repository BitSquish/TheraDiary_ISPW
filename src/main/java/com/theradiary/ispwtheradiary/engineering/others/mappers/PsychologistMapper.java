package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.model.Psychologist;

public class PsychologistMapper implements BeanAndModelMapper<PsychologistBean, Psychologist> {
    CredentialsMapper credentialsMapper = new CredentialsMapper();
    @Override
    public Psychologist fromBeanToModel(PsychologistBean bean) {
        if(bean.getName() != null)
            return new Psychologist(credentialsMapper.fromBeanToModel(bean.getCredentialsBean()), bean.getName(), bean.getSurname(), bean.getCity(), bean.getDescription(), bean.isInPerson(), bean.isOnline());
        else
            return new Psychologist(credentialsMapper.fromBeanToModel(bean.getCredentialsBean()));
    }

    @Override
    public PsychologistBean fromModelToBean(Psychologist model) {
        if(model.getName() != null)
            return new PsychologistBean(credentialsMapper.fromModelToBean(model.getCredentials()), model.getName(), model.getSurname(), model.getCity(), model.getDescription(), model.isInPerson(), model.isOnline());
        else
            return new PsychologistBean(credentialsMapper.fromModelToBean(model.getCredentials()));
    }
}
