package com.theradiary.ispwtheradiary.controller.graphic.modify;

import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PatientAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PsychologistAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public abstract class ModifyController extends CommonController {
    protected ModifyController(Session session) {
        super(session);
    }

    @FXML
    TextField nome, cognome, citta, mail, descrizione;
    @FXML
    PasswordField password;
    @FXML
    CheckBox inPresenza, online;

    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            }else if (session.getUser().getCredentialsBean().getRole().toString().equals("PATIENT")) {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/PatientAccount.fxml"));
                loader.setControllerFactory(c -> new PatientAccountController(session));
            } else {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/PsychologistAccount.fxml"));
                loader.setControllerFactory(c -> new PsychologistAccountController(session));
            }
            Parent root = loader.load();
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadUserData() {
        //ERRORE: NOME E COGNOME NON VENGONO CARICATI
        System.out.println("Nome: " + session.getUser().getName());
        System.out.println("Cognome: " + session.getUser().getSurname());
        System.out.println("Mail" + session.getUser().getCredentialsBean().getMail());
        // Popola i campi con i dati esistenti dalla sessione
        LoggedUserBean loggedUserBean =  session.getUser();
        nome.setText(loggedUserBean.getName());
        cognome.setText(loggedUserBean.getSurname());
        citta.setText(loggedUserBean.getCity());
        mail.setText(loggedUserBean.getCredentialsBean().getMail());
        password.setText(loggedUserBean.getCredentialsBean().getPassword());
        descrizione.setText(loggedUserBean.getDescription());
        inPresenza.setSelected(loggedUserBean.isInPerson());
        online.setSelected(loggedUserBean.isOnline());
    }

}


