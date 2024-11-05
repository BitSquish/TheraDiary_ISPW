package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPatientController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPsychologistController;
import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PsychologistAccountController extends AccountController {

    public PsychologistAccountController(Session session) {
        super(session);
    }

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
    @FXML
    Button saveMajor;


    @FXML
    private void saveSelectedMajor() {
        PsychologistBean psychologistBean= new PsychologistBean(session.getUser().getCredentialsBean(), session.getUser().getName(), session.getUser().getSurname(), session.getUser().getCity(), session.getUser().getDescription(), session.getUser().isInPerson(), session.getUser().isOnline(), session.getUser().isPag(), null, null);
        CheckBox[] checkbox = {checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8, checkbox9};
        for (int i=0;i<checkbox.length;i++) {
            if (checkbox[i] != null && checkbox[i].isSelected()) {
                Major major= Major.convertIntToMajor(i);
                if (major != null) {
                    psychologistBean.addMajor(major);
                }
            }
        }

        if (!psychologistBean.getMajors().isEmpty()) {
           Account account = new Account();
           account.addMajor(psychologistBean);
        } else {
            System.out.println("Nessuna categoria selezionata.");
        }
    }

    @FXML
    public void goToListPatients(MouseEvent event) {
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            } else {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/PatientList.fxml"));
                loader.setControllerFactory(c -> new PatientListController(session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}