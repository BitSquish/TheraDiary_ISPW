package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
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
        Account account = new Account();
        PsychologistBean yourPsychologist= account.yourPsychologist((PatientBean) session.getUser());
        if(yourPsychologist==null){
            psychologist.setText("Non hai uno psicologo assegnato");
            return;
        }
        psychologist.setText(yourPsychologist.getFullName());


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
