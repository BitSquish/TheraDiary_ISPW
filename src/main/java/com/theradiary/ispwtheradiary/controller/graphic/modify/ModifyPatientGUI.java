package com.theradiary.ispwtheradiary.controller.graphic.modify;


import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;

import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;


public class ModifyPatientGUI extends ModifyGUI {
    public ModifyPatientGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    @FXML
    private void modifyPatient(MouseEvent event) throws EmptyFieldException {
        // Aggiorna il PatientBean con i dati modificati dall'utente
        PatientBean patientBean = new PatientBean(
                new CredentialsBean(mail.getText(), password.getText(), Role.PATIENT),
                nome.getText(),
                cognome.getText(),
                citta.getText(),
                descrizione.getText(),
                inPresenza.isSelected(),
                online.isSelected()
        );
        modifyGenericUser(event, patientBean);
    }
}




