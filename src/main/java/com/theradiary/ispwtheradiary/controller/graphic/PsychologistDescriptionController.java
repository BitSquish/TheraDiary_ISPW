package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.application.PsychologistDescription;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.*;
import com.theradiary.ispwtheradiary.model.beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.StringJoiner;

public class PsychologistDescriptionController extends CommonController {
    public PsychologistDescriptionController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig ,session);
    }

    @FXML
    private Label nameField;
    @FXML
    private Label surnameField;
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
    @FXML
    private Label message;


    public void printPsychologist(PsychologistBean psychologistBean) {
        MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCity(), null, null, null);
        PsychologistDescription psychologistDescription = new PsychologistDescription();
        psychologistDescription.searchPsychologistInfo(psychologistBean, medicalOfficeBean);
        nameField.setText(psychologistBean.getName());
        surnameField.setText(psychologistBean.getSurname());
        cityField.setText(psychologistBean.getCity());
        mailField.setText(psychologistBean.getCredentialsBean().getMail());
        modalityField.setText(psychologistBean.getModality());
        StringJoiner majorString= new StringJoiner(",");
        Account account = new Account();
        account.retrieveMajors(psychologistBean);
        if(psychologistBean.getMajors() != null && !psychologistBean.getMajors().isEmpty()) {
            for (Major m : psychologistBean.getMajors()) {
                String translatedMajor = Major.translateMajor(m.getId());
                majorString.add(translatedMajor);
            }
            majorsField.setText(majorString.toString());
        }else{
            majorsField.setText("Non specificate");
        }
        String medicalOffice = "";
        if(medicalOfficeBean.getPostCode() == null)
            medicalOffice = "Non specificato";
        else
            medicalOffice = medicalOfficeBean.getAddress()+", "+medicalOfficeBean.getPostCode();
        medicalOfficeField.setText(medicalOffice);
        otherInfoField.setText(medicalOfficeBean.getOtherInfo());
        descriptionField.setText(psychologistBean.getDescription());
    }

    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            // Verifica se l'utente è loggato
            if (session.getUser() == null) {
                // Se non c'è un utente loggato, carica la schermata di login
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session)); // Imposta il controller per la login
            } else {
                // Se l'utente è loggato, carica la schermata dell'account dello psicologo
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(SEARCH_PATH)));
                loader.setControllerFactory(c -> new SearchController(fxmlPathConfig, session));
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
        PatientBean patientBean = (PatientBean) session.getUser();
        PsychologistDescription psychologistDescription = new PsychologistDescription();
        if(patientBean.getPsychologistBean() != null){
            message.setText("Hai già uno psicologo");
            message.setVisible(true);
        }else{
            PsychologistBean psychologistBean = new PsychologistBean(new CredentialsBean(mailField.getText(), Role.PSYCHOLOGIST), nameField.getText(), surnameField.getText(), cityField.getText(), descriptionField.getText(), false, false);
            psychologistBean.setInPerson(psychologistBean.getInPersonFromModality(modalityField.getText()));
            psychologistBean.setOnline(psychologistBean.getOnlineFromModality(modalityField.getText()));
            RequestBean requestBean = new RequestBean(patientBean, psychologistBean, LocalDate.now());
            psychologistDescription.sendRequest(requestBean);
            message.setText("Richiesta inviata con successo");
            message.setVisible(true);
        }
    }
}
