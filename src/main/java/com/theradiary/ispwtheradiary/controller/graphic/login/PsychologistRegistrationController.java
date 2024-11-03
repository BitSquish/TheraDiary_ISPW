package com.theradiary.ispwtheradiary.controller.graphic.login;

import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PsychologistRegistrationController extends UserRegistrationController {

    public PsychologistRegistrationController(Session session) {
        super(session);
    }

    @FXML
    private void registerPsychologist(MouseEvent event) {
        CredentialsBean credentialsBean = new CredentialsBean(mail.getText(), password.getText(), Role.PSYCHOLOGIST);
        PsychologistBean psychologistBean = new PsychologistBean(credentialsBean, nome.getText(), cognome.getText(), citta.getText(), descrizione.getText(), inPresenza.isSelected(), online.isSelected(), false, null, null);
        registerGenericUser(event, credentialsBean, psychologistBean);
    }
}