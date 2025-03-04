package com.theradiary.ispwtheradiary.view.gui.modify;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;


import com.theradiary.ispwtheradiary.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ModifyPsychologistGUI extends ModifyGUI {
    public ModifyPsychologistGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    private void modifyPsychologist(MouseEvent event) throws EmptyFieldException {
        // Aggiorna il PsychologistBean con i dati modificati dall'utente
        PsychologistBean psychologistBean = new PsychologistBean(
                new CredentialsBean(mail.getText(), password.getText(), Role.PSYCHOLOGIST),
                nome.getText(),
                cognome.getText(),
                citta.getText(),
                descrizione.getText(),
                inPresenza.isSelected(),
                online.isSelected()
        );
        modifyGenericUser(event, psychologistBean);
    }
}