package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PatientProfileController extends CommonController {
    public PatientProfileController(Session session) {
        super(session);
    }

    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session));
            } else {
                loader = new FXMLLoader(getClass().getResource(PATIENT_LIST_PATH));
                loader.setControllerFactory(c -> new PatientListController(session));
            }
            Parent rootParent = loader.load();
            changeScene(rootParent, event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

