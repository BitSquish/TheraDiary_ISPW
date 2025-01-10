package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RegistrationDAOSQL;
import com.theradiary.ispwtheradiary.engineering.dao.RegistrationGenericDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.exceptions.DAOException;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;

import java.sql.SQLException;

public class MedicalOfficeRegistrationController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final UpdateDAO updateDAO = new UpdateDAO();
    private final RetrieveDAO retrieveDAO = new RetrieveDAO();
    private final RegistrationDAOSQL registrationDAO = new RegistrationDAOSQL();     //TODO Da modificare con GenericDAO appena si fa la factory
    public MedicalOfficeRegistrationController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    public void register(MedicalOfficeBean medicalOfficeBean) throws SQLException {
        MedicalOffice medicalOffice = beanAndModelMapperFactory.fromBeanToModel(medicalOfficeBean, MedicalOfficeBean.class);
        try{
            registrationDAO.registerMedicalOffice(medicalOffice);
        }catch(SQLException exception){
            throw new DAOException(exception.getMessage(),exception); //ECCEZIONE DA VERIFICARE
        }
    }

    public boolean retrieveMedicalOffice(MedicalOfficeBean medicalOfficeBean) throws SQLException{
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
        try{
            updateDAO.modifyMedicalOffice(medicalOffice);
        }catch(SQLException exception){
            throw new DAOException(exception.getMessage(),exception);//ECCEZIONE DA VERIFICARE
        }
    }
}
