package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.Request;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.model.beans.RequestBean;

import java.util.ArrayList;
import java.util.List;

public class PatientList {
    public void getRequests(PsychologistBean psychologistBean, List<RequestBean> requestBeans) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), psychologistBean.getCredentialsBean().getRole()), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        List<Request> requests = new ArrayList<>();
        RetrieveDAO.retrievePatientsRequest(psychologist, requests);
        RequestBean requestBean;
        for(Request request:requests){
            requestBean = new RequestBean(
                    new PatientBean(new CredentialsBean(request.getPatient().getCredentials().getMail(), request.getPatient().getCredentials().getPassword(), request.getPatient().getCredentials().getRole()), request.getPatient().getName(), request.getPatient().getSurname(), request.getPatient().getCity(), request.getPatient().getDescription(), request.getPatient().isInPerson(), request.getPatient().isOnline()),
                    psychologistBean,
                    request.getDate()
            );
            requestBeans.add(requestBean);
        }
    }

}
