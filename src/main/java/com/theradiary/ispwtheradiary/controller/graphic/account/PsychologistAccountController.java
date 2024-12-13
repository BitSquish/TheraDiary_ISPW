package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;

import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.List;


public class PsychologistAccountController extends AccountController {

    public PsychologistAccountController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
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




}