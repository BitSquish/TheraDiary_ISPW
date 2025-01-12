package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.PersistenceOperationException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.exceptions.DAOException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;


public class MedicalOfficeRegistrationController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final UpdateDAO updateDAO = FactoryDAO.getUpdateDAO();
    private final RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    public MedicalOfficeRegistrationController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    public void register(MedicalOfficeBean medicalOfficeBean) {
        MedicalOffice medicalOffice = beanAndModelMapperFactory.fromBeanToModel(medicalOfficeBean, MedicalOfficeBean.class);
        try{
            updateDAO.registerMedicalOffice(medicalOffice);
        }catch(PersistenceOperationException exception){
            throw new DAOException(exception.getMessage(),exception);
        }
    }

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

    public void modify(MedicalOfficeBean medicalOfficeBean) {
        MedicalOffice medicalOffice = beanAndModelMapperFactory.fromBeanToModel(medicalOfficeBean, MedicalOfficeBean.class);
        updateDAO.modifyMedicalOffice(medicalOffice);
    }
}
