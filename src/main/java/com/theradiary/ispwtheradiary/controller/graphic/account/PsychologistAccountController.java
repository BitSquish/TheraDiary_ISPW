package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
        // Imposta l'evento per il pulsante di salvataggio
        saveMajorButton.setOnMouseClicked(event -> saveSelectedMajor());
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
                CategoryAndMajorDAO.saveSelectedMajors(selectedMajors, session.getUser ().getMail());
            } catch (Exception e) {
                System.err.println("Error saving categories: " + e.getMessage());
            }
        } else {
            System.out.println("Nessuna categoria selezionata.");
        }
    }


    public void goToListPatients(MouseEvent event) {
    }
}
