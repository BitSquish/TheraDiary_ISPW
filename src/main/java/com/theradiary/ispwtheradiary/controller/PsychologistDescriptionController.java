package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.PtAndPsDAO;
import com.theradiary.ispwtheradiary.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.beans.RequestBean;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.*;

/***********************Parte del caso d'uso: Richiedi psicologo*************************/

public class PsychologistDescriptionController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final PtAndPsDAO ptAndPsDAO = FactoryDAO.getPtAndPsDAO();
    private final RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    //Costruttore
    public PsychologistDescriptionController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    //Metodo per le informazioni dello psicologo nel profilo
    public void searchPsychologistInfo(PsychologistBean psychologistBean, MedicalOfficeBean medicalOfficeBean) {
        //Ricavo studio medico e specializzazioni
        MedicalOffice medicalOffice = beanAndModelMapperFactory.fromBeanToModel(medicalOfficeBean, MedicalOfficeBean.class);
        if(retrieveDAO.retrieveMedicalOffice(medicalOffice)){
            medicalOfficeBean.setPostCode(medicalOffice.getPostCode());
            medicalOfficeBean.setAddress(medicalOffice.getAddress());
            medicalOfficeBean.setOtherInfo(medicalOffice.getOtherInfo());
        }else{
            medicalOfficeBean.setPostCode(null);
            medicalOfficeBean.setAddress(null);
            medicalOfficeBean.setOtherInfo(null);
        }
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        retrieveDAO.retrieveMajors(psychologist);
        psychologistBean.setMajor(psychologist.getMajors());
    }
    //Metodo per inviare una richiesta

    public void sendRequest(RequestBean requestBean) throws NoResultException {
        Request request = beanAndModelMapperFactory.fromBeanToModel(requestBean, RequestBean.class);
        try{
            ptAndPsDAO.sendRequest(request);
            RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
            requestManagerConcreteSubject.addRequest(request);
        } catch (Exception e){
            throw new NoResultException("Errore nell'invio della richiesta",e);

        }
    }
    //Metodo per controllare se è già stata inviata una richiesta

    public boolean hasAlreadySentARequest(PatientBean patientBean, PsychologistBean psychologistBean) throws NoResultException {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        Request request= new Request(patient, psychologist);
        try{
            return ptAndPsDAO.hasAlreadySentARequest(request);
        } catch (Exception e){
            throw new NoResultException("Errore nel controllo delle richieste",e);
        }
    }
    //Metodo per controllare se è già presente uno psicologo
    public boolean hasAlreadyAPsychologist(PatientBean patientBean) throws NoResultException {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        try{
            return ptAndPsDAO.hasAlreadyAPsychologist(patient);
        } catch (Exception e){
            throw new NoResultException("Errore nel controllo dello psicologo",e);

        }
    }

    public void retrieveMajors(PsychologistBean psychologistBean) {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        boolean majorsAlreadyInserted = retrieveDAO.retrieveMajors(psychologist);
        if (majorsAlreadyInserted) {
            psychologistBean.setMajor(psychologist.getMajors());
        }
    }
}
