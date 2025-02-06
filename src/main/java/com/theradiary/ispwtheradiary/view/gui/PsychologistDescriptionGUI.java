package com.theradiary.ispwtheradiary.view.gui;

import com.theradiary.ispwtheradiary.beans.*;
import com.theradiary.ispwtheradiary.controller.AccountController;
import com.theradiary.ispwtheradiary.controller.PsychologistDescriptionController;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import java.util.StringJoiner;

public class PsychologistDescriptionGUI extends CommonGUI {

    private final PsychologistDescriptionController psychologistDescriptionController = new PsychologistDescriptionController();
    public PsychologistDescriptionGUI(FXMLPathConfig fxmlPathConfig, Session session) {
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
    @FXML
    private Button request;


    //Metodo per visualizzare le informazioni dello psicologo
    public void printPsychologist(PsychologistBean psychologistBean) throws NoResultException {
        boolean hasAlreadySentARequest = psychologistDescriptionController.hasAlreadySentARequest((PatientBean)session.getUser(), psychologistBean);
        //Se il paziente ha già uno psicologo associato o ha già inviato una richiesta per quello psicologo, nasconde il bottone per inviare la richiesta
        if((((PatientBean) session.getUser()).getPsychologistBean() != null && ((PatientBean) session.getUser()).getPsychologistBean().getCredentialsBean().getMail() != null) || hasAlreadySentARequest){
            request.setVisible(false);
        }
        //Recupero delle informazioni sullo psicologo e sul suo studio medico
        MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(psychologistBean.getCredentialsBean().getMail(), psychologistBean.getCity());
        psychologistDescriptionController.searchPsychologistInfo(psychologistBean, medicalOfficeBean);
        //impostazione dei campi
        nameField.setText(psychologistBean.getName());
        surnameField.setText(psychologistBean.getSurname());
        cityField.setText(psychologistBean.getCity());
        mailField.setText(psychologistBean.getCredentialsBean().getMail());
        modalityField.setText(psychologistBean.getModality());
        StringJoiner majorString= new StringJoiner(",");
        psychologistDescriptionController.retrieveMajors(psychologistBean);
        //Traduzione delle specializzazioni (se impostate dallo psicologo)
        if(psychologistBean.getMajors() != null && !psychologistBean.getMajors().isEmpty()) {
            for (Major m : psychologistBean.getMajors()) {
                String translatedMajor = Major.translateMajor(m.getId());
                majorString.add(translatedMajor);
            }
            majorsField.setText(majorString.toString());
        }else{
            majorsField.setText("Non specificate");
        }
        //Impostazione delle informazioni sullo studio medico
        String medicalOffice;
        if(medicalOfficeBean.getPostCode() == null && medicalOfficeBean.getAddress() == null && medicalOfficeBean.getOtherInfo() == null)
            medicalOffice = "Non specificato, contattare privatamente lo psicologo";
        else
            medicalOffice = medicalOfficeBean.getAddress()+", "+medicalOfficeBean.getPostCode();
        medicalOfficeField.setText(medicalOffice);
        otherInfoField.setText(medicalOfficeBean.getOtherInfo());
        //Descrizione dello psicologo
        descriptionField.setText(psychologistBean.getDescription());
    }


    //Metodo per inviare una richiesta di associazione allo psicologo
    @FXML
    protected void sendRequest(MouseEvent event) throws NoResultException {
        PatientBean patientBean = (PatientBean) session.getUser();
        PsychologistBean psychologistBean = new PsychologistBean(new CredentialsBean(mailField.getText(), Role.PSYCHOLOGIST), nameField.getText(), surnameField.getText(), cityField.getText(), descriptionField.getText(), false, false);
        psychologistBean.setInPerson(psychologistBean.getInPersonFromModality(modalityField.getText()));
        psychologistBean.setOnline(psychologistBean.getOnlineFromModality(modalityField.getText()));
        RequestBean requestBean = new RequestBean(patientBean, psychologistBean, LocalDate.now());
        psychologistDescriptionController.sendRequest(requestBean);
        message.setText("Richiesta inviata con successo");
        message.setVisible(true);
    }
}
