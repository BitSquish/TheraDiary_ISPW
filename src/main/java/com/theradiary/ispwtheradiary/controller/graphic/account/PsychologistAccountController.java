package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;

import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PsychologistAccountController extends AccountController {

    public PsychologistAccountController(Session session) {
        super(session);
    }

    private static final String PATIENT_LIST = "/com/theradiary/ispwtheradiary/view/PatientList.fxml";


    @Override
    protected void retrieveData(Account account, LoggedUserBean loggedUserBean) {
        account.retrieveMajors((PsychologistBean) loggedUserBean);
    }

    @Override
    protected Iterable<Major> getItems(LoggedUserBean loggedUserBean) {
        return ((PsychologistBean) loggedUserBean).getMajors();
    }

    @FXML
    public void initializeMajors() {
        initializeItems(session.getUser());
    }

    @FXML
    public static void addMajor(PsychologistBean psychologistBean, Major major) {
        Account account = new Account();
        account.addMajor(psychologistBean, major);
        psychologistBean.addMajor(major);
    }

    @FXML
    public static void removeMajor(PsychologistBean psychologistBean, Major major) {
        Account account = new Account();
        account.removeMajor(psychologistBean, major);
        psychologistBean.removeMajor(major);
    }


    @FXML
    private void goToListPatients(MouseEvent event) {

        try {
            //Recupero lo psicologo dalla sessione
            PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
            //Recupero la lista dei pazienti
            List<PatientBean> patientBeans= new Account().retrievePatientList(psychologistBean);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATIENT_LIST));
            loader.setControllerFactory(c -> new PatientListController(session));
            Parent root = loader.load();
            ((PatientListController) loader.getController()).printPatient(event, patientBeans);
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}