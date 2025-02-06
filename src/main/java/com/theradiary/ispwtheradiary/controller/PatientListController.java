package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.Request;

import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.beans.RequestBean;

import java.util.ArrayList;
import java.util.List;

/***********************Parte del caso d'uso: Gestisci paziente*************************/

public class PatientListController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    //Costruttore
    public PatientListController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    //Metodo per recuperare le richieste
    public void getRequests(PsychologistBean psychologistBean, List<RequestBean> requestBeans) {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        List<Request> requests = new ArrayList<>();
        retrieveDAO.retrievePatientsRequest(psychologist, requests);
        RequestBean requestBean;
        for(Request request:requests){
            requestBean = beanAndModelMapperFactory.fromModelToBean(request, Request.class);
            requestBeans.add(requestBean);
        }
        RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
        requestManagerConcreteSubject.loadRequests(requests);
    }
}
