package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.PsychologistDescription;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class PsychologistDescriptionController extends CommonController {
    public PsychologistDescriptionController(Session session) {
        super(session);
    }

    @FXML
    private Label fullNameField;
    @FXML
    private Label cityField;
    @FXML
    private Label mailField;
    @FXML
    private Label modalityField;
    @FXML
    private Label descriptionField;
    @FXML
    private Label majorsField;
    @FXML
    private Label medicalOfficeField;
    @FXML
    private Label otherInfoField;


    public void printPsychologist(PsychologistBean psychologistBean) {
        MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCity(), null, null, null);
        PsychologistDescription psychologistDescription = new PsychologistDescription();
        psychologistDescription.searchPsychologistInfo(psychologistBean, medicalOfficeBean);
        fullNameField.setText(psychologistBean.getFullName());
        cityField.setText(psychologistBean.getCity());
        mailField.setText(psychologistBean.getCredentialsBean().getMail());
        String modality = "";
        if(psychologistBean.isInPerson() && psychologistBean.isOnline())
            modality+="In presenza e online";
        else if (psychologistBean.isInPerson())
            modality+="In presenza";
        else
            modality+="Online";
        modalityField.setText(modality);
        String description = "\""+psychologistBean.getDescription()+"\"";
        descriptionField.setText(description);
        String majorsString = "";
        for(Major major: psychologistBean.getMajors()){
            majorsString+=Major.translateMajor(major.getId());
            majorsString+=". ";
        }
        majorsField.setText(majorsString);
        String medicalOffice = "";
        if(medicalOfficeBean.getPostCode() == null)
            medicalOffice = "Non specificato";
        else
            medicalOffice = medicalOfficeBean.getAddress()+", "+medicalOfficeBean.getPostCode();
        medicalOfficeField.setText(medicalOffice);
        otherInfoField.setText(medicalOfficeBean.getOtherInfo());
    }

    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            // Verifica se l'utente è loggato
            if (session.getUser() == null) {
                // Se non c'è un utente loggato, carica la schermata di login
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session)); // Imposta il controller per la login
            } else {
                // Se l'utente è loggato, carica la schermata dell'account dello psicologo
                loader = new FXMLLoader(getClass().getResource(SEARCH_PATH));
                loader.setControllerFactory(c -> new SearchController(session));
            }

            // Carica e cambia scena
            Parent root = loader.load();
            changeScene(root, event);

        } catch (IOException e) {
            e.printStackTrace();
            // Aggiungi un messaggio di errore personalizzato
            throw new RuntimeException("Errore nel caricamento della scena: " + e.getMessage(), e);
        }
    }

    @FXML
    protected void sendRequest(MouseEvent event) {
/*
        PatientBean patientBean = (PatientBean) session.getUser();
        PsychologistDescription psychologistDescription = new PsychologistDescription();
        if(patientBean.getPsychologistBean() == null){
            //eccezione you already have a psychologist
        }else{
            ((PatientBean)session.getUser()).setPsychologistBean(psychologistBean);
            psychologistDescription.sendRequest(patientBean, psychologistBean);
        }*/
    }
}
