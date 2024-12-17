package com.theradiary.ispwtheradiary.controller.graphic.login;

import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PatientRegistrationGUI extends UserRegistrationGUI {
    public PatientRegistrationGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }


    @FXML
    private void registerPatient(MouseEvent event) {
        PatientBean patientBean = new PatientBean(new CredentialsBean(mail.getText(), password.getText(), Role.PATIENT), nome.getText(), cognome.getText(), citta.getText(), descrizione.getText(), inPresenza.isSelected(), online.isSelected());
        registerGenericUser(event, patientBean);
    }
}
