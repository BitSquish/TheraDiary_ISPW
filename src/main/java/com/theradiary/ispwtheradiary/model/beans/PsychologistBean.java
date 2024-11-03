package com.theradiary.ispwtheradiary.model.beans;


import com.theradiary.ispwtheradiary.engineering.enums.Major;

import java.util.ArrayList;

public class PsychologistBean extends LoggedUserBean{
    private ArrayList<PatientBean> patientsBean;
    private ArrayList<Major> majors;
    private boolean pag;
    public PsychologistBean(CredentialsBean credentialsBean, String name, String surname, String city, String description, boolean inPerson, boolean online, boolean pag, ArrayList<PatientBean> patientsBean, ArrayList<Major> majors) {
        super(credentialsBean, name, surname, city, description, inPerson, online, pag);
        this.patientsBean = patientsBean != null ? patientsBean : new ArrayList<PatientBean>();
        this.majors = majors != null ? majors: new ArrayList<Major>();
    }
    public ArrayList<PatientBean> getPatientsBean() {
        return patientsBean;
    }

    public ArrayList<Major> getMajors(){return majors;}
    public void addPatientBean(PatientBean patientBean){
        this.patientsBean.add(patientBean);
    }
    public void removePatientBean(PatientBean patientBean){
        this.patientsBean.remove(patientBean);
    }

    public void addMajor(Major major){this.majors.add(major);}

    public void removeMajor(Major major){this.majors.remove(major);}
}

