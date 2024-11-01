package com.theradiary.ispwtheradiary.controller.graphic.login;

import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PatientRegistrationController extends UserRegistrationController {
    public PatientRegistrationController(Session session) {
        super(session);
    }


    @FXML
    private void registerPatient(MouseEvent event) {
        CredentialsBean credentialsBean = new CredentialsBean(mail.getText(), password.getText(), Role.valueOf("PATIENT"));
        PatientBean patientBean = new PatientBean(credentialsBean, nome.getText(), cognome.getText(), citta.getText(), descrizione.getText(), inPresenza.isSelected(), online.isSelected(), false, null, null);
        registerGenericUser(event, credentialsBean, patientBean);
    }
}
