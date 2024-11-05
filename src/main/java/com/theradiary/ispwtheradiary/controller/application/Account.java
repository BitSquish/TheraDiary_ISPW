package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;

public class Account {
    public void addCategory(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline(), patientBean.isPag(), patientBean.getCategories(), null);
        for (int i = 0; i < patientBean.getCategories().size(); i++) {
            patient.addCategory(patientBean.getCategories().get(i));
        }
        CategoryAndMajorDAO.addCategory(patient);
    }
}
