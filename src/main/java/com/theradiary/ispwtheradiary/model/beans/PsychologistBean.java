package com.theradiary.ispwtheradiary.model.beans;


import java.util.ArrayList;

public class PsychologistBean extends LoggedUserBean{
    private ArrayList<PatientBean> patientsBean;
    public PsychologistBean(CredentialsBean credentialsBean, String name, String surname, String city, String description, Boolean isInPerson, Boolean isOnline, Boolean isPAG, ArrayList<PatientBean> patientsBean) {
        super(credentialsBean, name, surname, city, description, isInPerson, isOnline, isPAG);
        this.patientsBean = patientsBean != null ? patientsBean : new ArrayList<PatientBean>();
    }
    public ArrayList<PatientBean> getPatientsBean() {
        return patientsBean;
    }
    public void addPatientBean(PatientBean patientBean){
        this.patientsBean.add(patientBean);
    }
    public void removePatientBean(PatientBean patientBean){
        this.patientsBean.remove(patientBean);
    }
}

