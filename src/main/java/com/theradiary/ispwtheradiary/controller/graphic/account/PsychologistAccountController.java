package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPatientController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPsychologistController;
import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PsychologistAccountController extends AccountController {

    public PsychologistAccountController(Session session) {
        super(session);
    }

    @FXML
    CheckBox checkbox1;
    @FXML
    CheckBox checkbox2;
    @FXML
    CheckBox checkbox3;
    @FXML
    CheckBox checkbox4;
    @FXML
    CheckBox checkbox5;
    @FXML
    CheckBox checkbox6;
    @FXML
    CheckBox checkbox7;
    @FXML
    CheckBox checkbox8;
    @FXML
    CheckBox checkbox9;
    @FXML
    Button saveMajor;
    @FXML
    private CheckBox[] checkboxes;
    private Account account;
    private PsychologistBean psychologistBean;
    @FXML
    private void initialize() {
        checkboxes = new CheckBox[]{checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8, checkbox9};
        psychologistBean = new PsychologistBean(session.getUser().getCredentialsBean(), session.getUser().getName(), session.getUser().getSurname(), session.getUser().getCity(), session.getUser().getDescription(), session.getUser().isInPerson(), session.getUser().isOnline(), session.getUser().isPag(), null, null);
        account=new Account();
        initializeMajors();
    }
    @FXML
    private void initializeMajors(){
        if(account.retrieveMajors(psychologistBean)){
            for (int i = 0; i < checkboxes.length; i++) {
                if(checkboxes[i]!=null) {
                    Major major = Major.convertIntToMajor(i + 1);
                    checkboxes[i].setSelected(psychologistBean.getMajors().contains(major));
                }
            }
        }

    }

    @FXML
    private void saveSelectedMajor() {
        account.retrieveMajors(psychologistBean);
        ArrayList<Major> selectedMajors = new ArrayList<>();
        for (int i=0;i<checkboxes.length;i++) {
            if (checkboxes[i] != null && checkboxes[i].isSelected()) {
                Major major= Major.convertIntToMajor(i+1);
                if (major != null && !psychologistBean.getMajors().contains(major)) {
                    selectedMajors.add(major);
                }
            }
        }

        if (!selectedMajors.isEmpty()) {
            psychologistBean.setMajor(selectedMajors);
            account.addMajor(psychologistBean);
           //pop up di conferma
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Salvataggio specializzazioni");
            alert.setHeaderText(null);
            alert.setContentText("Salvate con successo");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Salvataggio specializzazioni");
            alert.setHeaderText(null);
            alert.setContentText("Nessuna nuova specializzazione selezionata.");
            alert.showAndWait();
        }
    }

    @FXML
    public void goToListPatients(MouseEvent event) {
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            } else {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/PatientList.fxml"));
                loader.setControllerFactory(c -> new PatientListController(session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}