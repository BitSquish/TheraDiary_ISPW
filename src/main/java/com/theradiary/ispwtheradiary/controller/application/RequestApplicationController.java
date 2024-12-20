package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.Request;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;

import java.util.ArrayList;
import java.util.List;

public class RequestApplicationController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    public RequestApplicationController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    public void deleteRequest(RequestBean requestBean) {
        RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
        Request request = beanAndModelMapperFactory.fromBeanToModel(requestBean, RequestBean.class);
        UpdateDAO.deleteRequest(request);
        List<Request> requests = new ArrayList<>();
        RetrieveDAO.retrievePatientsRequest(request.getPsychologist(), requests);
        requestManagerConcreteSubject.loadRequests(requests);
        requestManagerConcreteSubject.removeRequest(request);
    }

    public void addPsychologistToPatient(PatientBean patientBean) {
        try{
            Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
            Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(patientBean.getPsychologistBean(), PsychologistBean.class);
            patient.setPsychologist(psychologist);
            UpdateDAO.setPatientsPsychologist(patient);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage()); //TODO Da cambiare
        }
    }

    public RequestBean createRequestBean(Request request) {
        return beanAndModelMapperFactory.fromModelToBean(request, Request.class);
    }
}
