package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.beans.MedicalOfficeBean;


public class MedicalOfficeRegistrationController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final UpdateDAO updateDAO = FactoryDAO.getUpdateDAO();
    private final RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    //Costruttore
    public MedicalOfficeRegistrationController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    //Metodo per registrare lo studio medico
    public void register(MedicalOfficeBean medicalOfficeBean) throws  NoResultException {
        MedicalOffice medicalOffice = beanAndModelMapperFactory.fromBeanToModel(medicalOfficeBean, MedicalOfficeBean.class);
        try{
            updateDAO.registerMedicalOffice(medicalOffice);
        }catch(Exception e){
            throw new NoResultException();
        }
    }
    //Metodo per recuperare lo studio medico
    public boolean retrieveMedicalOffice(MedicalOfficeBean medicalOfficeBean) {
        MedicalOffice medicalOffice = beanAndModelMapperFactory.fromBeanToModel(medicalOfficeBean, MedicalOfficeBean.class);
        boolean medOffAlreadyInserted = retrieveDAO.retrieveMedicalOffice(medicalOffice);
        if(medOffAlreadyInserted){
            medicalOfficeBean.setPsychologist(medicalOffice.getPsychologist());
            medicalOfficeBean.setCity(medicalOffice.getCity());
            medicalOfficeBean.setPostCode(medicalOffice.getPostCode());
            medicalOfficeBean.setAddress(medicalOffice.getAddress());
            medicalOfficeBean.setOtherInfo(medicalOffice.getOtherInfo());
        }
        return medOffAlreadyInserted;
    }
    //Metodo per modificare lo studio medico
    public void modify(MedicalOfficeBean medicalOfficeBean) {
        MedicalOffice medicalOffice = beanAndModelMapperFactory.fromBeanToModel(medicalOfficeBean, MedicalOfficeBean.class);
        updateDAO.modifyMedicalOffice(medicalOffice);
    }
}
