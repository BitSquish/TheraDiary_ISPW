package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.controller.graphic.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPatientController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPsychologistController;
import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    Button saveMajorButton;

    @FXML
    private void initialize() {
        if(saveMajorButton!=null) {
            saveMajorButton.setOnMouseClicked(event -> saveSelectedMajor());
        }
        System.out.println("ERRORE è null");
    }

    private void saveSelectedMajor() {
        List<Major> selectedMajors = new ArrayList<>();
        CheckBox[] checkboxes = {checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8, checkbox9};

        for (CheckBox checkbox : checkboxes) {
            if (checkbox != null && checkbox.isSelected()) {
                try {
                    // l'ID della checkbox corrisponda a un valore di enum Major
                    selectedMajors.add(Major.valueOf(checkbox.getId().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid category: " + checkbox.getId());
                }
            }
        }

        if (!selectedMajors.isEmpty()) {
            try {
                // Salva le specializzazioni selezionate
                CategoryAndMajorDAO.saveSelectedMajors(selectedMajors, session.getUser().getMail());
            } catch (Exception e) {
                System.err.println("Error saving categories: " + e.getMessage());
            }
        } else {
            System.out.println("Nessuna categoria selezionata.");
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