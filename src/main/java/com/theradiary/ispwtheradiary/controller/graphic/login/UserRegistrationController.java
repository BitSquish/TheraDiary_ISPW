package com.theradiary.ispwtheradiary.controller.graphic.login;


import com.theradiary.ispwtheradiary.controller.application.Login;
import com.theradiary.ispwtheradiary.controller.application.UserRegistration;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public abstract class UserRegistrationController extends CommonController {
    protected UserRegistrationController(Session session) {
        super(session);
    }

    @FXML
    TextField nome, cognome, citta, mail, descrizione;
    @FXML
    PasswordField password;
    @FXML
    CheckBox inPresenza, online;
    @FXML
    Label errorMessage;

    @FXML
    protected void checkFields(TextField[] fields) throws EmptyFieldException {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                throw new EmptyFieldException("Compila tutti i campi");
            }
        }
    }


    @FXML
    protected void registerGenericUser(MouseEvent event, CredentialsBean credentialsBean, LoggedUserBean loggedUserBean) {
        try{
            TextField[] fields = {nome, cognome, citta, mail, descrizione};
            checkFields(fields);
            new UserRegistration(loggedUserBean);
            // Pop-up che segnala successo registrazione
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registrazione");
            alert.setHeaderText(null);
            alert.setContentText("Registrato con successo");
            alert.showAndWait();

            // Se la registrazione va a buon fine, effettua automaticamente il login
            Login login = new Login();
            login.log(credentialsBean);
            session.setUser(credentialsBean);
            goToHomepage(event);
        } catch (MailAlreadyExistsException | WrongEmailOrPasswordException | EmptyFieldException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }

    }
}

