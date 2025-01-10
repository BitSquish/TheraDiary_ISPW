package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.PtAndPsDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.DAOException;
import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.*;


import java.sql.SQLException;

public class PsychologistDescriptionController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final PtAndPsDAO ptAndPsDAO = new PtAndPsDAO();
    private final RetrieveDAO retrieveDAO = new RetrieveDAO();
    public PsychologistDescriptionController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }

    public void searchPsychologistInfo(PsychologistBean psychologistBean, MedicalOfficeBean medicalOfficeBean) {
        //Ricavo studio medico e specializzazioni
        try{
            MedicalOffice medicalOffice = beanAndModelMapperFactory.fromBeanToModel(medicalOfficeBean, MedicalOfficeBean.class);
            retrieveDAO.retrieveMedicalOffice(medicalOffice);
            medicalOfficeBean.setPostCode(medicalOffice.getPostCode());
            medicalOfficeBean.setAddress(medicalOffice.getAddress());
            medicalOfficeBean.setOtherInfo(medicalOffice.getOtherInfo());
            Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
            retrieveDAO.retrieveMajors(psychologist);
            psychologistBean.setMajor(psychologist.getMajors());
        } catch(SQLException e){
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void sendRequest(RequestBean requestBean) {
        Request request = beanAndModelMapperFactory.fromBeanToModel(requestBean, RequestBean.class);
        try{
            ptAndPsDAO.sendRequest(request);
            RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
            requestManagerConcreteSubject.addRequest(request);
        } catch (Exception e){
            throw new DAOException(e.getMessage(), e);
        }
    }

    public boolean hasAlreadySentARequest(PatientBean patientBean, PsychologistBean psychologistBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        Request request= new Request(patient, psychologist);
        try{
            return ptAndPsDAO.hasAlreadySentARequest(request);
        } catch (Exception e){
            throw new DAOException(e.getMessage(), e);
        }
    }
    public boolean hasAlreadyAPsychologist(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        try{
            return ptAndPsDAO.hasAlreadyAPsychologist(patient);
        } catch (Exception e){
            throw new DAOException(e.getMessage(), e);
        }
    }
}
