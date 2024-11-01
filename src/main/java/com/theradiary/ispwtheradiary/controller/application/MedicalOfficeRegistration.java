package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.beans.MedicalOfficeBean;

import java.sql.SQLException;

public class MedicalOfficeRegistration {
    public void register(MedicalOfficeBean medicalOfficeBean) throws SQLException {
        MedicalOffice medicalOffice = new MedicalOffice(medicalOfficeBean.getMail(), medicalOfficeBean.getCity(), medicalOfficeBean.getPostCode(), medicalOfficeBean.getAddress(), medicalOfficeBean.getOtherInfo());
        try{
            RegistrationDAO.registerMedicalOffice(medicalOffice);
        }catch(SQLException exception){
            throw new RuntimeException(exception.getMessage()); //ECCEZIONE DA VERIFICARE
        }
    }

    public boolean retrieveMedicalOffice(MedicalOfficeBean medicalOfficeBean) throws SQLException{
        MedicalOffice medicalOffice = new MedicalOffice(medicalOfficeBean.getMail(), medicalOfficeBean.getCity(), medicalOfficeBean.getPostCode(), medicalOfficeBean.getAddress(), medicalOfficeBean.getOtherInfo());
        boolean medOffAlreadyInserted = RetrieveDAO.retrieveMedicalOffice(medicalOffice);
        if(medOffAlreadyInserted){
            medicalOfficeBean.setMail(medicalOffice.getMail());
            medicalOfficeBean.setCity(medicalOffice.getCity());
            medicalOfficeBean.setPostCode(medicalOffice.getPostCode());
            medicalOfficeBean.setAddress(medicalOffice.getAddress());
            medicalOfficeBean.setOtherInfo(medicalOffice.getOtherInfo());
        }
        return medOffAlreadyInserted;
    }

    public void modify(MedicalOfficeBean medicalOfficeBean) {
        MedicalOffice medicalOffice = new MedicalOffice(medicalOfficeBean.getMail(), medicalOfficeBean.getCity(), medicalOfficeBean.getPostCode(), medicalOfficeBean.getAddress(), medicalOfficeBean.getOtherInfo());
        try{
            UpdateDAO.modifyMedicalOffice(medicalOffice);
        }catch(SQLException exception){
            throw new RuntimeException(exception.getMessage()); //ECCEZIONE DA VERIFICARE
        }
    }
}
