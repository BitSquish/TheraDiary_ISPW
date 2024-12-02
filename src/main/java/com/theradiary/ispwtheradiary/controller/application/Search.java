package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Psychologist;

import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Search {
    public void searchPsychologists(List<PsychologistBean> psychologistBeans, TextField nomeP, TextField cognomeP, TextField cittaP, CheckBox inPresenza, CheckBox online, CheckBox pag) throws NoResultException {
        List<Psychologist> psychologists = new ArrayList<>();
        RetrieveDAO.searchPsychologists(psychologists, nomeP.getText(), cognomeP.getText(), cittaP.getText(), inPresenza.isSelected(), online.isSelected(), pag.isSelected());
        for(Psychologist psychologist : psychologists){
            RetrieveDAO.checkPag(psychologist);
            PsychologistBean psychologistBean = psychologist.toBean();
            psychologistBean.setPag(psychologist.isPag());
            psychologistBeans.add(psychologistBean);
        }
    }

    public void searchMedicalOffice(PsychologistBean psychologistBean, MedicalOfficeBean medicalOfficeBean) {
        Psychologist psychologist = new Psychologist(new Credentials(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCredentialsBean().getPassword(), Role.PSYCHOLOGIST), psychologistBean.getName(), psychologistBean.getSurname(), psychologistBean.getCity(), psychologistBean.getDescription(), psychologistBean.isInPerson(), psychologistBean.isOnline());
        MedicalOffice medicalOffice = new MedicalOffice(psychologist.getCredentials().getMail(), null, null, null, null);
        try{
            boolean flag = RetrieveDAO.retrieveMedicalOffice(medicalOffice);
            if(flag){
                medicalOfficeBean.setPostCode(medicalOffice.getPostCode());
                medicalOfficeBean.setAddress(medicalOffice.getAddress());
                medicalOfficeBean.setOtherInfo(medicalOffice.getOtherInfo());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //DA VERIFICARE ECCEZIONE
        }
    }

}
