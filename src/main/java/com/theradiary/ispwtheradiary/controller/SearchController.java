package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import java.util.ArrayList;
import java.util.List;

/***********************Parte del caso d'uso: Richiedi psicologo*************************/

public class SearchController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    //Costruttore
    public SearchController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    //Metodo per cercare gli psicologi secondo i filtri immessi
    public void searchPsychologists(List<PsychologistBean> psychologistBeans, String nomeP, String cognomeP, String cittaP, boolean inPresenza, boolean online, boolean pag) throws NoResultException {
        List<Psychologist> psychologists = new ArrayList<>();
        retrieveDAO.searchPsychologists(psychologists, nomeP, cognomeP, cittaP, inPresenza, online, pag);
        for(Psychologist psychologist : psychologists){
            retrieveDAO.checkPag(psychologist); //imposta il valore di pag
            PsychologistBean psychologistBean = beanAndModelMapperFactory.fromModelToBean(psychologist, Psychologist.class);
            psychologistBean.setPag(psychologist.isPag());
            psychologistBeans.add(psychologistBean);
        }
    }


}
