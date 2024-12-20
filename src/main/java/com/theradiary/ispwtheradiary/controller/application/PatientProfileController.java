package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;

public class PatientProfileController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    public PatientProfileController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    public void retrieveCategories(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        boolean categoriesAlreadyInserted = RetrieveDAO.retrieveCategories(patient);
        if (categoriesAlreadyInserted) {
            patientBean.setCategories(patient.getCategories());
        }
    }
}
