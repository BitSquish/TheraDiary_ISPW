package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.DAOException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    public SearchController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    public void searchPsychologists(List<PsychologistBean> psychologistBeans, String nomeP, String cognomeP, String cittaP, boolean inPresenza, boolean online, boolean pag) throws NoResultException {
        List<Psychologist> psychologists = new ArrayList<>();
        RetrieveDAO.searchPsychologists(psychologists, nomeP, cognomeP, cittaP, inPresenza, online, pag);
        for(Psychologist psychologist : psychologists){
            RetrieveDAO.checkPag(psychologist);
            PsychologistBean psychologistBean = beanAndModelMapperFactory.fromModelToBean(psychologist, Psychologist.class);
            psychologistBean.setPag(psychologist.isPag());
            psychologistBeans.add(psychologistBean);
        }
    }


}
