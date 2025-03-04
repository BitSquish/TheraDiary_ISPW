package com.theradiary.ispwtheradiary.view.gui.account;


import com.theradiary.ispwtheradiary.controller.AccountController;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
public class PatientAccountGUI extends AccountGUI {

    public PatientAccountGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    Label psychologist;

    //Imposta il bottone relativo al proprio psicologo. Se si ha uno psicologo, porta alla sua descrizione, altrimenti alla ricerca
    @FXML
    private void yourPsychologist(MouseEvent event) {
        if(((PatientBean)session.getUser()).getPsychologistBean() != null && ((PatientBean)session.getUser()).getPsychologistBean().getCredentialsBean().getMail() != null){
            accountController.yourPsychologist(((PatientBean)session.getUser()), ((PatientBean)session.getUser()).getPsychologistBean());
            goToPsychologistDescription(event, ((PatientBean)session.getUser()).getPsychologistBean());
        }else{
            goToSearch(event);
        }
    }

    //Inizializza il campo relativo allo psicologo, stampando il suo nome se si ha uno psicologo associato al proprio profilo
    public void initializePsychologistField(){
        if(((PatientBean)session.getUser()).getPsychologistBean() != null) {
            if (((PatientBean) session.getUser()).getPsychologistBean().getCredentialsBean().getMail() == null) {
                psychologist.setText("Nessuno psicologo");
            } else {
                accountController.yourPsychologist(((PatientBean) session.getUser()), ((PatientBean) session.getUser()).getPsychologistBean());
                psychologist.setText(((PatientBean) session.getUser()).getPsychologistBean().getFullName());
            }
        }
    }


    //Metodi per visualizzare la lista delle categorie
    @Override
    protected void retrieveData(AccountController accountController, LoggedUserBean loggedUserBean) {
        accountController.retrieveCategories((PatientBean) loggedUserBean);
    }

    @Override
    protected Iterable<Category> getItems(LoggedUserBean loggedUserBean) {
        return ((PatientBean) loggedUserBean).getCategories();
    }

    @FXML
    public void initializeCategories()  {
        initializeItems(session.getUser());
    }
    //aggiunta nuova category
    @FXML
    public static void addCategory(PatientBean patientBean, Category category) {
        patientBean.addCategory(category);
        AccountController account=new AccountController();
        account.addCategory(patientBean, category);
    }
    //rimozione category
    @FXML
    public static void removeCategory(PatientBean patientBean, Category category) {
        AccountController account=new AccountController();
        account.removeCategory(patientBean, category);
        patientBean.removeCategory(category);
    }
    //fine metodi category
}
