package com.theradiary.ispwtheradiary.controller.graphic.modify;


import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;

import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class ModifyPatientController extends ModifyController {
    public ModifyPatientController(Session session) {
        super(session);
    }

    @FXML
    TextField nome, cognome, citta, mail, descrizione;
    @FXML
    PasswordField password;
    @FXML
    CheckBox isInPerson, isOnline;
    @FXML
    public void initialize() {
        // Carica i dati del paziente nei campi di input
        loadPatientData();
    }
    private void loadPatientData() {
        // Popola i campi con i dati esistenti nel PatientBean
        CredentialsBean credentialsBean =  session.getUser();
        PatientBean patientBean = new PatientBean(
                credentialsBean,
                "",
                "",
                "",
                "",
                false,
                false,
                false,
                null,
                null
        );
        nome.setText(patientBean.getName());
        cognome.setText(patientBean.getSurname());
        citta.setText(patientBean.getCity());
        mail.setText(patientBean.getCredentialsBean().getMail());
        password.setText(patientBean.getCredentialsBean().getPassword());
        descrizione.setText(patientBean.getDescription());
        isInPerson.setSelected(patientBean.isInPerson());
        isOnline.setSelected(patientBean.isOnline());
    }
    @FXML
    private void registerPatient(MouseEvent event) {
        // Aggiorna il PatientBean con i dati modificati dall'utente
        PatientBean patientBean = new PatientBean(
                session.getUser(),
                nome.getText(),
                cognome.getText(),
                citta.getText(),
                descrizione.getText(),
                isInPerson.isSelected(),
                isOnline.isSelected(),
                false,
                null,
                null
        );
        patientBean.setName(nome.getText());
        patientBean.setSurname(cognome.getText());
        patientBean.setCity(citta.getText());
        patientBean.getCredentialsBean().setMail(mail.getText());
        patientBean.getCredentialsBean().setPassword(password.getText());
        patientBean.setDescription(descrizione.getText());
        patientBean.setInPerson(isInPerson.isSelected());
        patientBean.setOnline(isOnline.isSelected());

        // Salva i dati aggiornati nel database
    }
}




