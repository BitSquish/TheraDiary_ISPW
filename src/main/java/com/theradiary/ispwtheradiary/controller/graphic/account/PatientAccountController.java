package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;



import java.util.ArrayList;
import java.util.Arrays;


public class PatientAccountController extends AccountController {

    public PatientAccountController(Session session) {
        super(session);
    }


    @FXML
    Button saveCategoryButton;
    @FXML
    Label psychologist;
    //private PatientBean patientBean;
    //private Account account;
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
    private void updateCategories(){
      /*  ArrayList<Category> categoriesToAdd= new ArrayList<>();
        ArrayList<Category> categoriesToRemove= new ArrayList<>();
        Account account = new Account();
        PatientBean patientBean = new PatientBean(session.getUser().getCredentialsBean(), session.getUser().getName(), session.getUser().getSurname(), session.getUser().getCity(), session.getUser().getDescription(), session.getUser().isInPerson(), session.getUser().isOnline(), session.getUser().isPag(), new ArrayList<>(), null);
        for(int i=0;i<checkboxes.length;i++){
            if(checkboxes[i]!=null){
                Category category= Category.convertIntToCategory(i+1);
                if(checkboxes[i].isSelected()&& !patientBean.getCategories().contains(category)){
                    categoriesToAdd.add(category);
                }else if(!checkboxes[i].isSelected()&& patientBean.getCategories().contains(category)){
                    categoriesToRemove.add(category);
                }
            }
        }
        boolean modified=false;
        if(!categoriesToAdd.isEmpty()){
            patientBean.getCategories().addAll(categoriesToAdd);
            account.addCategory(patientBean);
            modified=true;
        }
        if(!categoriesToRemove.isEmpty()){
            patientBean.getCategories().removeAll(categoriesToRemove);
            account.removeCategory(patientBean);
            modified=true;
        }
        if(modified){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Salvataggio categorie");
            alert.setHeaderText(null);
            alert.setContentText("Salvate con successo");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Salvataggio categorie");
            alert.setHeaderText(null);
            alert.setContentText("Nessuna nuova categoria selezionata.");
            alert.showAndWait();
        }*/
    }

}
