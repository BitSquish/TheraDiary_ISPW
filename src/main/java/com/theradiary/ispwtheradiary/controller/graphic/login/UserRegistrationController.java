package com.theradiary.ispwtheradiary.controller.graphic.login;


import com.theradiary.ispwtheradiary.controller.application.Login;
import com.theradiary.ispwtheradiary.controller.application.UserRegistration;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.Validator;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public abstract class UserRegistrationController extends CommonController {
    protected UserRegistrationController(Session session) {
        super(session);
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
    protected void checkFields(TextField[] fields,CheckBox[] checkBoxes, PasswordField password,Label errorMessage) throws EmptyFieldException {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                throw new EmptyFieldException("Compila tutti i campi");
            }
        }
        if(!checkBoxes[0].isSelected() && !checkBoxes[1].isSelected()){
            throw new EmptyFieldException("Seleziona almeno un tipo di terapia");
        }
        if(password.getText().isEmpty()){
            throw new EmptyFieldException("Inserisci una password");
        }

    }


    @FXML
    protected void registerGenericUser(MouseEvent event, LoggedUserBean loggedUserBean) {
        try{
            TextField[] fields = {nome, cognome, citta, mail, descrizione};
            CheckBox[] checkBoxes = {inPresenza, online};
            PasswordField password = this.password;
            checkFields(fields, checkBoxes,password,errorMessage);
            if (!Validator.isValidMail(mail.getText(), errorMessage))// || !Validator.isValidPassword(password.getText(), errorMessage)) {
                return;
            //}
            new UserRegistration(loggedUserBean);
            // Pop-up che segnala successo registrazione
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registrazione");
            alert.setHeaderText(null);
            alert.setContentText("Registrato con successo");
            alert.showAndWait();

            // Se la registrazione va a buon fine, effettua automaticamente il login
            Login login = new Login();
            login.log(loggedUserBean.getCredentialsBean());
            session.setUser(loggedUserBean);
            goToHomepage(event);
        } catch (MailAlreadyExistsException | WrongEmailOrPasswordException | EmptyFieldException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }

    }
}

