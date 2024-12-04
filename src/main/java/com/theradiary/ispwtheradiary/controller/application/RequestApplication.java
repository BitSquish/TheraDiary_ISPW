package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.Request;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;

public class RequestApplication {
    public void deleteRequest(RequestBean requestBean) {
        //TODO dovresti eliminare tutte le richieste a nome di quel paziente
        RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
        Request request = new Request(new Patient(new Credentials(requestBean.getPatientBean().getCredentialsBean().getMail(), requestBean.getPatientBean().getCredentialsBean().getRole()), requestBean.getPatientBean().getName(), requestBean.getPatientBean().getSurname(), requestBean.getPatientBean().getCity(), requestBean.getPatientBean().getDescription(), requestBean.getPatientBean().isInPerson(), requestBean.getPatientBean().isOnline()),
                new Psychologist(new Credentials(requestBean.getPsychologistBean().getCredentialsBean().getMail(), requestBean.getPsychologistBean().getCredentialsBean().getRole()), requestBean.getPsychologistBean().getName(), requestBean.getPsychologistBean().getSurname(), requestBean.getPsychologistBean().getCity(), requestBean.getPsychologistBean().getDescription(), requestBean.getPsychologistBean().isInPerson(), requestBean.getPsychologistBean().isOnline()),
                requestBean.getDate());
        UpdateDAO.deleteRequest(request);
        requestManagerConcreteSubject.removeRequest(request);
    }

    public void addPsychologistToPatient(PatientBean patientBean) {
        try{
            Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getRole()), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
            Psychologist psychologist = new Psychologist(new Credentials(patientBean.getPsychologistBean().getCredentialsBean().getMail(), patientBean.getPsychologistBean().getCredentialsBean().getRole()), patientBean.getPsychologistBean().getName(), patientBean.getPsychologistBean().getSurname(), patientBean.getPsychologistBean().getCity(), patientBean.getPsychologistBean().getDescription(), patientBean.getPsychologistBean().isInPerson(), patientBean.getPsychologistBean().isOnline());
            patient.setPsychologist(psychologist);
            UpdateDAO.setPatientsPsychologist(patient);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage()); //Da cambiare
        }
    }
}
