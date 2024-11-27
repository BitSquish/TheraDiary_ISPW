package com.theradiary.ispwtheradiary.controller.graphic.modify;

import com.theradiary.ispwtheradiary.controller.application.UserModify;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PatientAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PsychologistAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.Validator;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public abstract class ModifyController extends CommonController {
    protected ModifyController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig ,session);
    }

    @FXML
    TextField nome;
    @FXML
    TextField cognome;
    @FXML
    TextField citta;
    @FXML
    TextField mail;
    @FXML
    TextField descrizione;
    @FXML
    PasswordField password;
    @FXML
    CheckBox inPresenza;
    @FXML
    CheckBox online;
    @FXML
    Label errorMessage;
    @FXML
    Label successMessage;

    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session));
            }else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_ACCOUNT_PATH)));
                loader.setControllerFactory(c -> new PatientAccountController(fxmlPathConfig, session));
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PSYCHOLOGIST_ACCOUNT_PATH)));
                loader.setControllerFactory(c -> new PsychologistAccountController(fxmlPathConfig, session));
            }
            Parent root = loader.load();
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void checkFields(TextField[] fields,CheckBox[] checkBoxes, PasswordField password) throws EmptyFieldException {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                throw new EmptyFieldException("Compila tutti i campi");
            }
        }
        if(!checkBoxes[0].isSelected() && !checkBoxes[1].isSelected()){
            throw new EmptyFieldException("Seleziona almeno una modalità di visita");
        }
        if(password.getText().isEmpty()){
            throw new EmptyFieldException("Inserisci la password");
        }
    }

    public void loadUserData() {
        // Popola i campi con i dati esistenti dalla sessione
        nome.setText(session.getUser().getName());
        cognome.setText(session.getUser().getSurname());
        citta.setText(session.getUser().getCity());
        mail.setText(session.getUser().getCredentialsBean().getMail());
        password.setText(session.getUser().getCredentialsBean().getPassword());
        descrizione.setText(session.getUser().getDescription());
        inPresenza.setSelected(session.getUser().isInPerson());
        online.setSelected(session.getUser().isOnline());
    }

    @FXML
    protected void modifyGenericUser(MouseEvent event, LoggedUserBean loggedUserBean) throws EmptyFieldException {
        errorMessage.setVisible(false);
        successMessage.setVisible(false);
        try{
            TextField[] fields = {nome, cognome, citta, mail, descrizione};
            CheckBox[] checkboxes = {inPresenza, online};
            checkFields(fields,checkboxes,password);
            if (!Validator.isValidMail(mail.getText(), errorMessage)){//!Validator.isValidPassword(password.getText(), errorMessage)) {
                return; //Lanciare eccezione apposita
            }
            UserModify userModify = new UserModify();
            userModify.userModify(loggedUserBean,session.getUser());  //session.getUser().getCredentialsBean() passa le vecchie credenziali
            session.setUser(loggedUserBean);
            successMessage.setVisible(true);
        } catch (MailAlreadyExistsException | EmptyFieldException exception) {
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }

    }

}


