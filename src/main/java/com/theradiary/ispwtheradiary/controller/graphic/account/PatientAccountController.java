package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


import java.util.ArrayList;
import java.util.List;

public class PatientAccountController extends AccountController {

    public PatientAccountController(Session session) {
        super(session);
    }

    @FXML
    private CheckBox checkbox1;
    @FXML
    private CheckBox checkbox2;
    @FXML
    private CheckBox checkbox3;
    @FXML
    private CheckBox checkbox4;
    @FXML
    private CheckBox checkbox5;
    @FXML
    private CheckBox checkbox6;
    @FXML
    private CheckBox checkbox7;
    @FXML
    private CheckBox checkbox8;
    @FXML
    private CheckBox checkbox9;
    @FXML
    Button saveCategoryButton;
    @FXML
    Label psychologist;


    @FXML
    private void initialize() {
        if(saveCategoryButton!=null){
            // Imposta l'evento per il pulsante di salvataggio
            saveCategoryButton.setOnMouseClicked(event -> saveSelectedCategories());
        }else{
            System.out.println("saveCategoryButton is null");
        }
    }

    @FXML
    private void yourPsychologist(MouseEvent event) {
        //Va inizializzata la label psychologist
        if(psychologist.getText().isEmpty()){
            goToSearch(event);
        }
        else
            System.out.println("Hai uno psicologo");
    }

    private void saveSelectedCategories() {
        PatientBean patientBean = new PatientBean(session.getUser().getCredentialsBean(), session.getUser().getName(), session.getUser().getSurname(), session.getUser().getCity(), session.getUser().getDescription(), session.getUser().isInPerson(), session.getUser().isOnline(), session.getUser().isPag(), null, null);
        CheckBox[] checkbox = {checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8, checkbox9};
        for (int i = 1; i < checkbox.length; i++) {
            if (checkbox[i] != null && checkbox[i].isSelected()) {
                Category category = Category.convertIntToCategory(i);
                if (category != null) {
                    patientBean.addCategory(category);
                }
            }
        }
        if(!patientBean.getCategories().isEmpty()) {
            Account account=new Account();
            account.addCategory(patientBean);
        }else{
            System.out.println("No category selected");
        }
    }


}