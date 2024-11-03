package com.theradiary.ispwtheradiary.controller.graphic.modify;

import com.theradiary.ispwtheradiary.engineering.others.Session;

import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;

import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ModifyPsychologistController extends ModifyController {
    public ModifyPsychologistController(Session session) {
        super(session);
    }
    @FXML
    public void initialize() {
        // Carica i dati del psicologo nei campi di input
        loadUserData();
    }

    @FXML
    private void registerPsychologist(MouseEvent event) {
        // Aggiorna il PsychologistBean con i dati modificati dall'utente
        PsychologistBean psychologistBean = new PsychologistBean(
                session.getUser().getCredentialsBean(),
                nome.getText(),
                cognome.getText(),
                citta.getText(),
                descrizione.getText(),
                inPresenza.isSelected(),
                online.isSelected(),
                false,
                null,
                null
        );
        psychologistBean.setName(nome.getText());
        psychologistBean.setSurname(cognome.getText());
        psychologistBean.setCity(citta.getText());
        psychologistBean.getCredentialsBean().setMail(mail.getText());
        psychologistBean.getCredentialsBean().setPassword(password.getText());
        psychologistBean.setDescription(descrizione.getText());
        psychologistBean.setInPerson(inPresenza.isSelected());
        psychologistBean.setOnline(online.isSelected());

        // Salva i dati aggiornati nel database
    }
}