package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PatientProfileController extends CommonController {
    public PatientProfileController(Session session) {
        super(session);
    }

    @FXML
    private Label fullName;
    @FXML
    private Label mail;
    @FXML
    private Label meet;
    @FXML
    private Label city;
    @FXML
    private Label category;
    @FXML
    private Label description;

    protected void printPatient(PatientBean patientBean) {
        //recupero il paziente

        fullName.setText(patientBean.getFullName());
        mail.setText(patientBean.getCredentialsBean().getMail());
       if(patientBean.isInPerson() && patientBean.isOnline())
            meet.setText("In presenza e online");
        else if (patientBean.isInPerson())
            meet.setText("In presenza");
        else
            meet.setText("Online");
        city.setText(patientBean.getCity());
        String categoryString = "";
        for(Category c : patientBean.getCategories()){
            categoryString+=Category.translateCategory(c.getId());
            categoryString+=",";
        }
        category.setText(categoryString);
        description.setText(patientBean.getDescription());
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

