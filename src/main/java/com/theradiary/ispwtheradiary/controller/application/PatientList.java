package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.Request;

import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;

import java.util.ArrayList;
import java.util.List;

public class PatientList {
    public void getRequests(PsychologistBean psychologistBean, List<RequestBean> requestBeans) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), psychologistBean.getCredentialsBean().getRole()), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        List<Request> requests = new ArrayList<>();
        RetrieveDAO.retrievePatientsRequest(psychologist, requests);
        RequestBean requestBean;
        for(Request request:requests){
            requestBean = request.toBean();
            requestBeans.add(requestBean);
        }
        RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
        requestManagerConcreteSubject.loadRequests(requests);
    }
}
