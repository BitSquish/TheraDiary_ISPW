package com.theradiary.ispwtheradiary.view.gui.account;

import com.theradiary.ispwtheradiary.controller.AccountController;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;

import javafx.fxml.FXML;


public class PsychologistAccountGUI extends AccountGUI {

    public PsychologistAccountGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    //I seguenti metodi permettono di visualizzare e gestire i major di uno psicologo
    @Override
    protected void retrieveData(AccountController accountController, LoggedUserBean loggedUserBean) {
        accountController.retrieveMajors((PsychologistBean) loggedUserBean);
    }

    @Override
    protected Iterable<Major> getItems(LoggedUserBean loggedUserBean) {
        return ((PsychologistBean) loggedUserBean).getMajors();
    }

    @FXML
    public void initializeMajors() {
        initializeItems(session.getUser());
    }

    //Metodo per aggiungere un major
    @FXML
    public static void addMajor(PsychologistBean psychologistBean, Major major) {
        AccountController accountController = new AccountController();
        accountController.addMajor(psychologistBean, major);
        psychologistBean.addMajor(major);
    }
    //Metodo per rimuovere un major
    @FXML
    public static void removeMajor(PsychologistBean psychologistBean, Major major) {
        AccountController accountController = new AccountController();
        accountController.removeMajor(psychologistBean, major);
        psychologistBean.removeMajor(major);
    }




}