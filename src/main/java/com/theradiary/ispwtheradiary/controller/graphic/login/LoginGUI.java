package com.theradiary.ispwtheradiary.controller.graphic.login;

import com.theradiary.ispwtheradiary.controller.application.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.CommonGUI;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsGUI;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtGUI;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginGUI extends CommonGUI {

    public LoginGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    private final LoginController login = new LoginController();
    @FXML
    TextField mail;
    @FXML
    PasswordField password;
    @FXML
    Label errorMessage;

    @FXML
    private void setCredentials(MouseEvent event) throws IOException {
        errorMessage.setVisible(false);
        try{
            validateFields();
            CredentialsBean credentialsBean = new CredentialsBean(mail.getText(), password.getText(), null);
            login.log(credentialsBean);
            if(credentialsBean.getRole() == null){
                throw new WrongEmailOrPasswordException("Mail o password errati");
            }
            if(credentialsBean.getRole().equals(Role.PATIENT)){
                PatientBean patientBean = new PatientBean(credentialsBean);
                login.retrievePatient(patientBean);
                session.setUser(patientBean);
            }
            else if(credentialsBean.getRole().equals(Role.PSYCHOLOGIST)){
                PsychologistBean psychologistBean = new PsychologistBean(credentialsBean);
                login.retrievePsychologist(psychologistBean);
                session.setUser(psychologistBean);
            }
            goToHomepage(event, credentialsBean.getRole());
        }catch(WrongEmailOrPasswordException | EmptyFieldException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }
    }

    private void validateFields() throws EmptyFieldException {
        if (mail.getText().isEmpty() || password.getText().isEmpty()) {
            throw new EmptyFieldException("Compila tutti campi.");
        }
    }

    private void goToHomepage(MouseEvent event, Role role) throws IOException {
        FXMLLoader loader;
        if (role.equals(Role.PATIENT)) {
            loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(HOMEPAGE_LOGGED_PT_PATH)));
            loader.setControllerFactory(c -> new HomepagePtGUI(fxmlPathConfig, session));
        } else {
            loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(HOMEPAGE_LOGGED_PS_PATH)));
            loader.setControllerFactory(c -> new HomepagePsGUI(fxmlPathConfig, session));
        }
        Parent root = loader.load();
        changeScene(root, event);
    }


    @FXML
    private void goToPatientRegistration(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_REGISTRATION_PATH)));
            loader.setControllerFactory(c -> new PatientRegistrationGUI(fxmlPathConfig,session));
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    private void goToPsychologistRegistration(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PSYCHOLOGIST_REGISTRATION_PATH)));
            loader.setControllerFactory(c -> new PsychologistRegistrationGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }
}
