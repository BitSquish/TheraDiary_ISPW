package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;

import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.ArrayList;



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
    private CheckBox[] checkboxes;
    private Account account;
    private PsychologistBean psychologistBean;
    @FXML
    private void initialize() {
        checkboxes = new CheckBox[]{checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8, checkbox9};
        psychologistBean = new PsychologistBean(session.getUser().getCredentialsBean(), session.getUser().getName(), session.getUser().getSurname(), session.getUser().getCity(), session.getUser().getDescription(), session.getUser().isInPerson(), session.getUser().isOnline(), session.getUser().isPag(), null, null);
        account=new Account();
        initializeMajors();
    }
    @FXML
    private void initializeMajors(){
        if(account.retrieveMajors(psychologistBean)){
            for (int i = 0; i < checkboxes.length; i++) {
                if(checkboxes[i]!=null) {
                    Major major = Major.convertIntToMajor(i + 1);
                    checkboxes[i].setSelected(psychologistBean.getMajors().contains(major));
                }
            }
        }

    }
    @FXML
    private void updateMajors(){
        account.retrieveMajors(psychologistBean);
        ArrayList<Major> majorsToAdd= new ArrayList<>();
        ArrayList<Major> majorsToRemove= new ArrayList<>();

        for (int i=0;i<checkboxes.length;i++) {
            if (checkboxes[i] != null) {
                Major major= Major.convertIntToMajor(i+1);
                if(checkboxes[i].isSelected() && !psychologistBean.getMajors().contains(major)) {
                    majorsToAdd.add(major);
                } else if (!checkboxes[i].isSelected() && psychologistBean.getMajors().contains(major)) {
                    majorsToRemove.add(major);
                }
            }
        }
        boolean modified=false;
        if (!majorsToAdd.isEmpty()) {
            psychologistBean.getMajors().addAll(majorsToAdd);
            account.addMajor(psychologistBean);
            modified=true;
        }
        if (!majorsToRemove.isEmpty()) {
            psychologistBean.getMajors().removeAll(majorsToRemove);
            account.removeMajor(psychologistBean);
            modified=true;
        }
        if(modified){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Salvataggio specializzazioni");
            alert.setHeaderText(null);
            alert.setContentText("Modifiche salvate con successo");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Salvataggio specializzazioni");
            alert.setHeaderText(null);
            alert.setContentText("Nessuna modifica effettuata");
            alert.showAndWait();
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