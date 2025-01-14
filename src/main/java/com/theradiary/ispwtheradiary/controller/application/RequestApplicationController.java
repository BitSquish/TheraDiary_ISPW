package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.Request;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;

import java.util.ArrayList;
import java.util.List;

public class RequestApplicationController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final RetrieveDAO retrieveDAO =FactoryDAO.getRetrieveDAO();
    private final UpdateDAO updateDAO = FactoryDAO.getUpdateDAO();
    public RequestApplicationController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    public void deleteRequest(RequestBean requestBean) {
        RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
        Request request = beanAndModelMapperFactory.fromBeanToModel(requestBean, RequestBean.class);
        updateDAO.deleteRequest(request);
        List<Request> requests = new ArrayList<>();
        retrieveDAO.retrievePatientsRequest(request.getPsychologist(), requests);
        requestManagerConcreteSubject.loadRequests(requests);
        requestManagerConcreteSubject.removeRequest(request);
    }

    public void addPsychologistToPatient(PatientBean patientBean) {
        try{
            Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
            Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(patientBean.getPsychologistBean(), PsychologistBean.class);
            patient.setPsychologist(psychologist);
            updateDAO.setPatientsPsychologist(patient);
        } catch(Exception e){
            Printer.errorPrint(e.getMessage());
        }
    }

    public RequestBean createRequestBean(Request request) {
        return beanAndModelMapperFactory.fromModelToBean(request, Request.class);
    }
}