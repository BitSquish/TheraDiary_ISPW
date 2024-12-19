package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.PtAndPsDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.*;


import java.sql.SQLException;

public class PsychologistDescriptionController {

    public void searchPsychologistInfo(PsychologistBean psychologistBean, MedicalOfficeBean medicalOfficeBean) {
        //Ricavo studio medico e specializzazioni
        try{
            MedicalOffice medicalOffice = new MedicalOffice(medicalOfficeBean.getPsychologist(), medicalOfficeBean.getCity(), medicalOfficeBean.getPostCode(), medicalOfficeBean.getAddress(), medicalOfficeBean.getOtherInfo());
            RetrieveDAO.retrieveMedicalOffice(medicalOffice);
            medicalOfficeBean.setPostCode(medicalOffice.getPostCode());
            medicalOfficeBean.setAddress(medicalOffice.getAddress());
            medicalOfficeBean.setOtherInfo(medicalOffice.getOtherInfo());
            Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
            RetrieveDAO.retrieveMajors(psychologist);
            psychologistBean.setMajor(psychologist.getMajors());
        } catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendRequest(RequestBean requestBean) {
        Credentials credentialsPat = new Credentials(requestBean.getPatientBean().getCredentialsBean().getMail(), Role.PATIENT);
        Credentials credentialsPsy = new Credentials(requestBean.getPsychologistBean().getCredentialsBean().getMail(), Role.PSYCHOLOGIST);
        Request request= new Request(new Patient(credentialsPat, requestBean.getPatientBean().getName(), requestBean.getPatientBean().getSurname(), requestBean.getPatientBean().getCity(), requestBean.getPatientBean().getDescription(), requestBean.getPatientBean().isInPerson(), requestBean.getPatientBean().isOnline()),
                new Psychologist(credentialsPsy, requestBean.getPsychologistBean().getName(), requestBean.getPsychologistBean().getSurname(), requestBean.getPsychologistBean().getCity(), requestBean.getPsychologistBean().getDescription(), requestBean.getPsychologistBean().isInPerson(), requestBean.getPsychologistBean().isOnline()),
                requestBean.getDate());
        try{
            PtAndPsDAO.sendRequest(request);
            RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
            requestManagerConcreteSubject.addRequest(request);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean hasAlreadySentARequest(PatientBean patientBean, PsychologistBean psychologistBean) {
        Credentials credentialsPat = new Credentials(patientBean.getCredentialsBean().getMail(), Role.PATIENT);
        Credentials credentialsPsy = new Credentials(psychologistBean.getCredentialsBean().getMail(), Role.PSYCHOLOGIST);
        Request request= new Request(new Patient(credentialsPat, patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline()),
                new Psychologist(credentialsPsy, psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline()));
        try{
            return PtAndPsDAO.hasAlreadySentARequest(request);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public boolean hasAlreadyAPsychologist(PatientBean patientBean) {
        Credentials credentialsPat = new Credentials(patientBean.getCredentialsBean().getMail(), Role.PATIENT);
        Patient patient = new Patient(credentialsPat, patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        try{
            return PtAndPsDAO.hasAlreadyAPsychologist(patient);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
