package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
public class PatientAccountController extends AccountController {

    public PatientAccountController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }


    @FXML
    Label psychologist;

    @FXML
    private void yourPsychologist(MouseEvent event) {
        //Va inizializzata la label psychologist
        if (psychologist.getText().isEmpty()) {
            goToSearch(event);
        } else
            System.out.println("Hai uno psicologo");
    }


    @Override
    protected void retrieveData(Account account, LoggedUserBean loggedUserBean) {
        account.retrieveCategories((PatientBean) loggedUserBean);
    }

    @Override
    protected Iterable<Category> getItems(LoggedUserBean loggedUserBean) {
        return ((PatientBean) loggedUserBean).getCategories();
    }

    @FXML
    public void initializeCategories()  {
        initializeItems(session.getUser());
    }
    @FXML
    public static void addCategory(PatientBean patientBean, Category category) {
        patientBean.addCategory(category);
        Account account=new Account();
        account.addCategory(patientBean, category);
    }
    @FXML
    public static void removeCategory(PatientBean patientBean, Category category) {
        Account account=new Account();
        account.removeCategory(patientBean, category);
        patientBean.removeCategory(category);
    }
}
