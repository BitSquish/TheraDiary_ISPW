package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPatientController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPsychologistController;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AccountController extends CommonController {

    protected AccountController(Session session) {
        super(session);
    }

    @FXML
    ImageView account;


    @FXML
    protected void goToModifyScreen(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            }else if (session.getUser().getRole().toString().equals("PATIENT")) {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/ModifyPatient.fxml"));
                loader.setControllerFactory(c -> new ModifyPatientController(session));
            } else  {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/ModifyPsychologist.fxml"));
                loader.setControllerFactory(c -> new ModifyPsychologistController(session));
            }
            Parent root = loader.load();
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void joinPag(MouseEvent event){
        session.getUser().setPag(true);
    }//continuare serve salvare il pag nel db

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
    protected void handleCheckBoxSave(CheckBox checkBox) {
        // Implementa la logica per salvare lo stato della CheckBox
    }

    @FXML
    protected void goBack(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            }else if (session.getUser().getRole().toString().equals("PATIENT")) {
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


