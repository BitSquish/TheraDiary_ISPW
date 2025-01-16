package com.theradiary.ispwtheradiary.controller.graphic.modify;


import com.theradiary.ispwtheradiary.controller.application.UserModifyController;
import com.theradiary.ispwtheradiary.controller.graphic.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public abstract class ModifyGUI extends CommonGUI {
    protected ModifyGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig ,session);
    }

    private final UserModifyController userModifyController = new UserModifyController();
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
    protected void modifyGenericUser(MouseEvent event, LoggedUserBean loggedUserBean) {
        errorMessage.setVisible(false);
        successMessage.setVisible(false);
        try{
            TextField[] fields = {nome, cognome, citta, mail, descrizione};
            CheckBox[] checkboxes = {inPresenza, online};
            checkFields(fields,checkboxes,password);
            if (isValidMail(mail.getText(), errorMessage)){
                throw new WrongEmailOrPasswordException("Email non valida");
            }
            userModifyController.userModify(loggedUserBean,session.getUser());  //session.getUser().getCredentialsBean() passa le vecchie credenziali
            session.setUser(loggedUserBean);
            successMessage.setVisible(true);
        } catch (MailAlreadyExistsException | EmptyFieldException | WrongEmailOrPasswordException exception) {
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


