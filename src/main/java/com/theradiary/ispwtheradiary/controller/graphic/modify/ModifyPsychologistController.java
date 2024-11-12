package com.theradiary.ispwtheradiary.controller.graphic.modify;

import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.others.Session;

import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ModifyPsychologistController extends ModifyController {
    public ModifyPsychologistController(Session session) {
        super(session);
    }

    @FXML
    private void modifyPsychologist(MouseEvent event) throws EmptyFieldException {
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
        modifyGenericUser(event, psychologistBean);
    }
}