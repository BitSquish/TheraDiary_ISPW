package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.beans.RequestBean;
import com.theradiary.ispwtheradiary.model.Request;

public class RequestMapper implements BeanAndModelMapper<RequestBean, Request>{
    PsychologistMapper psychologistMapper = new PsychologistMapper();
    PatientMapper patientMapper = new PatientMapper();
    @Override
    public Request fromBeanToModel(RequestBean bean) {
        return new Request(patientMapper.fromBeanToModel(bean.getPatientBean()), psychologistMapper.fromBeanToModel(bean.getPsychologistBean()), bean.getDate());
    }

    @Override
    public RequestBean fromModelToBean(Request model) {
        return new RequestBean(patientMapper.fromModelToBean(model.getPatient()), psychologistMapper.fromModelToBean(model.getPsychologist()), model.getDate());
    }
}
