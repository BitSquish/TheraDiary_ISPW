package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.PtAndPsDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.*;



public class PsychologistDescriptionController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final PtAndPsDAO ptAndPsDAO = FactoryDAO.getPtAndPsDAO();
    private final RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    public PsychologistDescriptionController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }

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
    public boolean hasAlreadyAPsychologist(PatientBean patientBean) throws NoResultException {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        try{
            return ptAndPsDAO.hasAlreadyAPsychologist(patient);
        } catch (Exception e){
            throw new NoResultException("Errore nel controllo dello psicologo",e);

        }
    }
}
