package com.theradiary.ispwtheradiary.controller.graphic.login;

import com.theradiary.ispwtheradiary.controller.application.Login;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtController;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginController extends CommonController {

    public LoginController(Session session) {
        super(session);
    }

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
            Login login = new Login();
            login.log(credentialsBean);
            if(credentialsBean.getRole() == null){
                throw new WrongEmailOrPasswordException("Mail o password errati");
            }
            if(credentialsBean.getRole().equals(Role.PATIENT)){
                PatientBean patientBean = new PatientBean(credentialsBean, null, null, null, null, false, false, false, null, null);
                login.retrievePatient(patientBean);
                session.setUser(patientBean);
            }
            else if(credentialsBean.getRole().equals(Role.PSYCHOLOGIST)){
                PsychologistBean psychologistBean = new PsychologistBean(credentialsBean, null, null, null, null, false, false, false, null, null);
                login.retrievePsychologist(psychologistBean);
                session.setUser(psychologistBean);
            }
            goToHomepage(event, credentialsBean.getRole());
        }catch(WrongEmailOrPasswordException | EmptyFieldException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }
    }

    //UN METODO DEL GENERE NON PUO ANDARE SU UN CONTROLLER GRAFICO
    /*private Connection getConnection() throws SQLException { //Mai usato???
        // Abstracted database connection method
        return ConnectionFactory.getConnection();
    }*/
    private void validateFields() throws EmptyFieldException {
        if (mail.getText().isEmpty() || password.getText().isEmpty()) {
            throw new EmptyFieldException("Compila tutti campi.");
        }
    }

    private void goToHomepage(MouseEvent event, Role role) throws IOException {
        FXMLLoader loader;
        if (role.equals(Role.PATIENT)) {
            loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/HomepageLoggedPt.fxml"));
            loader.setControllerFactory(c -> new HomepagePtController(session));
        } else {
            loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/HomepageLoggedPs.fxml"));
            loader.setControllerFactory(c -> new HomepagePsController(session));
        }
        Parent root = loader.load();
        changeScene(root, event);
    }


    @FXML
    private void goToPatientRegistration(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/PatientRegistration.fxml"));
            loader.setControllerFactory(c -> new PatientRegistrationController(session));
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @FXML
    private void goToPsychologistRegistration(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/PsychologistRegistration.fxml"));
            loader.setControllerFactory(c -> new PsychologistRegistrationController(session));
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
