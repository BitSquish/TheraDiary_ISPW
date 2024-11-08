package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPatientController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPsychologistController;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class AccountController extends CommonController {

    protected AccountController(Session session) {
        super(session);
    }

    @FXML
    ImageView account;
    @FXML
    CheckBox checkbox1;
    @FXML
    CheckBox checkbox2;
    @FXML
    CheckBox checkbox3;
    @FXML
    CheckBox checkbox4;
    @FXML
    CheckBox checkbox5;
    @FXML
    CheckBox checkbox6;
    @FXML
    CheckBox checkbox7;
    @FXML
    CheckBox checkbox8;
    @FXML
    CheckBox checkbox9;


    // Metodo astratto per recuperare le categorie o majors
    protected abstract void retrieveData(Account account, LoggedUserBean loggedUserBean);

    // Metodo astratto per ottenere le categorie o majors dal bean utente
    protected abstract Iterable<?> getItems(LoggedUserBean loggedUserBean);

    @FXML
    protected void initializeItems(LoggedUserBean loggedUserBean) {
        CheckBox[] checkBoxes = new CheckBox[]{
                checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
                checkbox6, checkbox7, checkbox8, checkbox9
        };

        // Recupera i dati necessari per l'utente (categorie o majors)
        retrieveData(new Account(), loggedUserBean);

        // Ottieni le categorie o majors dall'oggetto `LoggedUserBean`
        Iterable<?> items = getItems(loggedUserBean);
        if (items != null) {
            // Itera sugli elementi (categorie o majors) e seleziona le checkbox corrispondenti
            for (Object item : items) {
                int id = (loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)) ? ((Category) item).getId() : ((Major) item).getId();
                if (id > 0 && id <= checkBoxes.length) {
                    checkBoxes[id - 1].setSelected(true);
                }
            }
        }
    }


    @FXML
    protected void goToModifyScreen(MouseEvent event) {
        try {
            FXMLLoader loader;
            Parent root;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
                root = loader.load();
            }else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/ModifyPatient.fxml"));
                loader.setControllerFactory(c -> new ModifyPatientController(session));
                root = loader.load();
                ((ModifyPatientController)loader.getController()).loadUserData();
            }else{
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/ModifyPsychologist.fxml"));
                loader.setControllerFactory(c -> new ModifyPsychologistController(session));
                root = loader.load();
                ((ModifyPsychologistController)loader.getController()).loadUserData();
            }
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void logout(MouseEvent event) throws IOException {
        session.setUser(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
        loader.setControllerFactory(c -> new LoginController(session));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }


    @FXML
    protected void goBack(MouseEvent event) {
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            }else if (session.getUser().getCredentialsBean().getRole().toString().equals("PATIENT")) {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/HomepageLoggedPt.fxml"));
                loader.setControllerFactory(c -> new HomepagePtController(session));
            } else {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/HomepageLoggedPs.fxml"));
                loader.setControllerFactory(c -> new HomepagePsController(session));
            }
            Parent root = loader.load();
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


