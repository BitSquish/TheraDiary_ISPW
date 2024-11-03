package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Search {
    public void search(List<PsychologistBean> psychologistBeans, TextField nomeP, TextField cognomeP, TextField cittaP, CheckBox inPresenza, CheckBox online, CheckBox pag) throws NoResultException {
        List<Psychologist> psychologists = new ArrayList<>();
        RetrieveDAO.searchPsychologists(psychologists, nomeP.getText(), cognomeP.getText(), cittaP.getText(), inPresenza.isSelected(), online.isSelected(), pag.isSelected());
        for(Psychologist psychologist : psychologists){
            CredentialsBean credentialsBean = new CredentialsBean(psychologist.getCredentials().getMail(), psychologist.getCredentials().getPassword(), psychologist.getCredentials().getRole());
            PsychologistBean psychologistBean = new PsychologistBean(credentialsBean, psychologist.getName(),psychologist.getSurname(), psychologist.getCity(), psychologist.getDescription(), psychologist.isInPerson(), psychologist.isOnline(), psychologist.isPag(), null, null);
            psychologistBeans.add(psychologistBean);
        }
    }
}
