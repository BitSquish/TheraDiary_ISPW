package com.theradiary.ispwtheradiary.view.gui.login;

import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PsychologistRegistrationGUI extends UserRegistrationGUI {

    public PsychologistRegistrationGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    private void registerPsychologist(MouseEvent event) {
        PsychologistBean psychologistBean = new PsychologistBean(new CredentialsBean(mail.getText(), password.getText(), Role.PSYCHOLOGIST), nome.getText(), cognome.getText(), citta.getText(), descrizione.getText(), inPresenza.isSelected(), online.isSelected());
        registerGenericUser(event, psychologistBean);
    }
}