package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;

import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
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
import java.util.List;

import static java.util.Collections.addAll;


public class PsychologistAccountController extends AccountController {

    public PsychologistAccountController(Session session) {
        super(session);
    }







    @Override
    protected void retrieveData(Account account, LoggedUserBean loggedUserBean) {
        account.retrieveMajors((PsychologistBean) loggedUserBean);
    }

    @Override
    protected Iterable<Major> getItems(LoggedUserBean loggedUserBean) {
        return ((PsychologistBean) loggedUserBean).getMajors();
    }
    @FXML
    public void initializeMajors(){
        initializeItems(session.getUser());
    }
   @FXML
   public static void addMajor(PsychologistBean psychologistBean,Major major){
         Account account=new Account();
         account.addMajor(psychologistBean, major);
         psychologistBean.addMajor(major);
   }
   @FXML
   public static void removeMajor(PsychologistBean psychologistBean,Major major){
         Account account=new Account();
         account.removeMajor(psychologistBean, major);
         psychologistBean.removeMajor(major);
   }



    @FXML
    public void goToListPatients(List<PatientBean> patientBeans,MouseEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/PatientList.fxml"));
            loader.setControllerFactory(c -> new PatientListController(session));
            Parent root = loader.load();
            ((PatientListController)loader.getController()).printPatient(event,patientBeans);
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}