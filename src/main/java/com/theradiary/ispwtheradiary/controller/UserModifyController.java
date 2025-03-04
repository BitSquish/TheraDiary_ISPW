package com.theradiary.ispwtheradiary.controller;

import com.theradiary.ispwtheradiary.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;


/***********************Parte del caso d'uso: Gestisci account*************************/
public class UserModifyController {
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final UpdateDAO updateDAO = FactoryDAO.getUpdateDAO();
    //Costruttore
    public UserModifyController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    //Metodo per modificare l'utente
    public void userModify(LoggedUserBean loggedUserBean, LoggedUserBean oldLoggedUserBean) throws MailAlreadyExistsException {
        try{
            if(loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)){
                PatientBean patientBean = (PatientBean) loggedUserBean;
                modifyPatient(patientBean, oldLoggedUserBean);
            }
            else if (loggedUserBean.getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
                PsychologistBean psychologistBean = (PsychologistBean) loggedUserBean;
                modifyPsychologist(psychologistBean, oldLoggedUserBean);
            }
        } catch (MailAlreadyExistsException e){
            throw new MailAlreadyExistsException(e.getMessage());
        }

    }
    //Metodo per modificare lo psicologo

    private void modifyPsychologist(PsychologistBean psychologistBean, LoggedUserBean oldLoggedUserBean) throws MailAlreadyExistsException {
        Psychologist psychologist = beanAndModelMapperFactory.fromBeanToModel(psychologistBean, PsychologistBean.class);
        try{
            updateDAO.modifyCredentials(psychologist.getCredentials(), new Credentials(oldLoggedUserBean.getCredentialsBean().getMail(), oldLoggedUserBean.getCredentialsBean().getPassword(), oldLoggedUserBean.getCredentialsBean().getRole()));
            updateDAO.modifyPsychologist(psychologist);
            psychologistBean.setCredentialsBean(new CredentialsBean(psychologist.getCredentials().getMail(), psychologist.getCredentials().getPassword(), psychologist.getCredentials().getRole()));
            psychologistBean.setName(psychologist.getName());
            psychologistBean.setSurname(psychologist.getSurname());
            psychologistBean.setCity(psychologist.getCity());
            psychologistBean.setDescription(psychologist.getDescription());
            psychologistBean.setInPerson(psychologist.isInPerson());
            psychologistBean.setOnline(psychologist.isOnline());
        }catch (MailAlreadyExistsException e){
            throw new MailAlreadyExistsException(e.getMessage());
        }

    }
    //Metodo per modificare il paziente

    private void modifyPatient(PatientBean patientBean, LoggedUserBean oldLoggedUserBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        try {
            updateDAO.modifyCredentials(patient.getCredentials(), new Credentials(oldLoggedUserBean.getCredentialsBean().getMail(), oldLoggedUserBean.getCredentialsBean().getPassword(), oldLoggedUserBean.getCredentialsBean().getRole()));
            updateDAO.modifyPatient(patient);
            patientBean.setCredentialsBean(new CredentialsBean(patient.getCredentials().getMail(), patient.getCredentials().getPassword(), patient.getCredentials().getRole()));
            patientBean.setName(patient.getName());
            patientBean.setSurname(patient.getSurname());
            patientBean.setCity(patient.getCity());
            patientBean.setDescription(patient.getDescription());
            patientBean.setInPerson(patient.isInPerson());
            patientBean.setOnline(patient.isOnline());
        } catch (Exception e){
            Printer.errorPrint(e.getMessage());
        }
    }
}
