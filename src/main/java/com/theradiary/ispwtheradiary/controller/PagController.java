package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;

public class PagController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final UpdateDAO updateDAO = FactoryDAO.getUpdateDAO();
    //Costruttore
    public PagController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    //Metodo per unirsi al Pag
    public void joinPag(LoggedUserBean loggedUserBean) {
        if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
            Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel((PsychologistBean)loggedUserBean, PsychologistBean.class);
            updateDAO.joinPagPsychologist(psychologist);
        }
        else if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
            Patient patient = beanAndModelMapperFactory.fromBeanToModel((PatientBean)loggedUserBean, PatientBean.class);
            updateDAO.joinPagPatient(patient);
        }
    }
}
