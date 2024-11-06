package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private CheckBox[] checkboxes;
    private PatientBean patientBean;
    private Account account;
    @FXML
    private void yourPsychologist(MouseEvent event) {
        //Va inizializzata la label psychologist
        if (psychologist.getText().isEmpty()) {
            goToSearch(event);
        } else
            System.out.println("Hai uno psicologo");
    }
    @FXML
    private void initialize(){
        checkboxes = new CheckBox[]{checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8, checkbox9};
        System.out.println("Checkbox array initialized: " + Arrays.toString(checkboxes));
        patientBean = new PatientBean(session.getUser().getCredentialsBean(), session.getUser().getName(), session.getUser().getSurname(), session.getUser().getCity(), session.getUser().getDescription(), session.getUser().isInPerson(), session.getUser().isOnline(), session.getUser().isPag(), new ArrayList<>(), null);
        account = new Account();
        initializeCategories();
    }

    @FXML
    private void initializeCategories()  {
        if (account.retrieveCategories(patientBean)) {
            for (int i = 0; i < checkboxes.length; i++) {
                if(checkboxes[i]!=null) {
                    Category category = Category.convertIntToCategory(i + 1);
                    checkboxes[i].setSelected(patientBean.getCategories().contains(category));
                }
            }

        }

    }

    @FXML
    private void saveSelectedCategories() {
        account.retrieveCategories(patientBean);
        ArrayList<Category> selectedCategories = new ArrayList<>();
        for (int i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i] != null && checkboxes[i].isSelected()) {
                Category category = Category.convertIntToCategory(i + 1);
                if (category != null && !patientBean.getCategories().contains(category)) {
                    selectedCategories.add(category);
                }
            }
        }
        if (!selectedCategories.isEmpty() ) {
            patientBean.setCategories(selectedCategories);
            account.addCategory(patientBean);
            //popup di conferma
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Salavataggio categorie");
            alert.setHeaderText(null);
            alert.setContentText("Salvate con successo");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Salvataggio categorie");
            alert.setHeaderText(null);
            alert.setContentText("Nessuna nuova categoria selezionata.");
            alert.showAndWait();
        }

    }
}
