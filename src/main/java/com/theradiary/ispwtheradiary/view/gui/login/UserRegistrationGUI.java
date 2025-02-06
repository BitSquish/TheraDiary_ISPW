package com.theradiary.ispwtheradiary.view.gui.login;


import com.theradiary.ispwtheradiary.controller.LoginController;
import com.theradiary.ispwtheradiary.controller.UserRegistrationController;
import com.theradiary.ispwtheradiary.view.gui.CommonGUI;
import com.theradiary.ispwtheradiary.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public abstract class UserRegistrationGUI extends CommonGUI {
    protected UserRegistrationGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig ,session);
    }

    private final LoginController login = new LoginController();
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

    private final UserRegistrationController userRegistrationController = new UserRegistrationController();

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
            PasswordField pass = this.password;
            checkFields(fields, checkBoxes,pass,errorMessage);
            if (isValidMail(mail.getText(), errorMessage)){ // Se la mail non è valida
                return;
            }
            userRegistrationController.registerUser(loggedUserBean);
            // Pop-up che segnala successo registrazione
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registrazione");
            alert.setHeaderText(null);
            alert.setContentText("Registrato con successo");
            alert.showAndWait();

            // Se la registrazione va a buon fine, effettua automaticamente il login
            login.log(loggedUserBean.getCredentialsBean());
            session.setUser(loggedUserBean);
            goToHomepage(event);
        } catch (MailAlreadyExistsException | WrongEmailOrPasswordException | EmptyFieldException |
                 LoginAndRegistrationException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }

    }
    public static boolean isValidMail(String mail, Label errorMessage) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!mail.matches(emailRegex)) {
            errorMessage.setText("Mail non valida");
            errorMessage.setVisible(true);
            return true;
        }
        return false;
    }
}

