package com.theradiary.ispwtheradiary.controller.graphic.login;

import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PsychologistRegistrationController extends UserRegistrationController {

    public PsychologistRegistrationController(Session session) {
        super(session);
    }
    @FXML
    CheckBox pag;
    @FXML
    ImageView infoButton;
    @FXML
    Label infoPAG;
    private boolean isVisible = false;

    @FXML
    public void clickInfoButton() {
        // Event handler al clic sull'ImageView
        infoButton.setOnMouseClicked(event -> showInfoLabel());
    }

    private void showInfoLabel() {
        // Alterna la visibilità della label
        isVisible = !isVisible;
        infoPAG.setVisible(isVisible);
    }

    @FXML
    private void registerPsychologist(MouseEvent event) {
        CredentialsBean credentialsBean = new CredentialsBean(mail.getText(), password.getText(), Role.valueOf("PATIENT"));
        PsychologistBean psychologistBean = new PsychologistBean(credentialsBean, nome.getText(), cognome.getText(), citta.getText(), descrizione.getText(), inPresenza.isSelected(), online.isSelected(), pag.isSelected(), null);
        registerGenericUser(event, credentialsBean, psychologistBean);
    }
}