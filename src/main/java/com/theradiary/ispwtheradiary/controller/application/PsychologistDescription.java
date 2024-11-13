package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import java.sql.SQLException;

public class PsychologistDescription {

    public void searchPsychologistInfo(PsychologistBean psychologistBean, MedicalOfficeBean medicalOfficeBean) {
        //Ricavo studio medico e specializzazioni
        try{
            MedicalOffice medicalOffice = new MedicalOffice(medicalOfficeBean.getMail(), medicalOfficeBean.getCity(), medicalOfficeBean.getPostCode(), medicalOfficeBean.getAddress(), medicalOfficeBean.getOtherInfo());
            RetrieveDAO.retrieveMedicalOffice(medicalOffice);
            medicalOfficeBean.setPostCode(medicalOffice.getPostCode());
            medicalOfficeBean.setAddress(medicalOffice.getAddress());
            medicalOfficeBean.setOtherInfo(medicalOffice.getOtherInfo());
            Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), null, null), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline(), psychologistBean.isPag(), null, null);
            RetrieveDAO.retrieveMajors(psychologist);
            psychologistBean.setMajor(psychologist.getMajors());
        } catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
