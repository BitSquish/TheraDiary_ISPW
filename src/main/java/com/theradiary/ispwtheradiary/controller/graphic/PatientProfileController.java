package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.task.DiaryAndTasksController;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class PatientProfileController extends CommonController {
    public PatientProfileController(FXMLPathConfig fxmlPathConfig,Session session) {
        super(fxmlPathConfig,session);
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
        if (patientBean.isInPerson() && patientBean.isOnline()) {
            meet.setText("In presenza e online");
        } else if (patientBean.isInPerson()){
            meet.setText("In presenza");
        } else {
            meet.setText("Online");
        }
        city.setText(patientBean.getCity());
        StringJoiner categoryString = new StringJoiner(",");
        Account account = new Account();
        account.retrieveCategories(patientBean);
        if (patientBean.getCategories() != null && !patientBean.getCategories().isEmpty()) {
            for (Category c : patientBean.getCategories()) {
                String translatedCategory = Category.translateCategory(c.getId());
                categoryString.add(translatedCategory);
            }
            category.setText(categoryString.toString());
        } else {
            category.setText("Non specificate");
        }

        description.setText(patientBean.getDescription());
    }
    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            Parent root;
            if (session.getUser() == null) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session));
                root = loader.load();
            } else {
                PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
                //Recupero la lista dei pazienti
                List<PatientBean> patientBeans = new Account().retrievePatientList(psychologistBean);
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_LIST_PATH)));
                loader.setControllerFactory(c -> new PatientListController(fxmlPathConfig, session));
                root = loader.load();
                ((PatientListController) loader.getController()).printPatient(event, patientBeans);
            }
            changeScene(root, event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void goToTasksAndDiary(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(DIARY_AND_TASKS_PATH));
            loader.setControllerFactory(c -> new DiaryAndTasksController(fxmlPathConfig, session));
            Parent rootParent = loader.load();
            changeScene(rootParent, event);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

