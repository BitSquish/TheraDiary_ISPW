package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;


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
    private void initialize() {
        if(saveCategoryButton!=null){
            // Imposta l'evento per il pulsante di salvataggio
            saveCategoryButton.setOnMouseClicked(event -> saveSelectedCategories());
        }else{
            System.out.println("saveCategoryButton is null");
        }
    }

    private void saveSelectedCategories() {
        List<Category> selectedCategories = new ArrayList<>();
        CheckBox[] checkboxes = {checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox8, checkbox9};

        for (CheckBox checkbox : checkboxes) {
            if (checkbox != null && checkbox.isSelected()) {
                try {
                    // l'ID della checkbox corrisponda a un valore di enum Category
                    selectedCategories.add(Category.valueOf(checkbox.getId().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid category: " + checkbox.getId());
                }
            }
        }

        if (!selectedCategories.isEmpty()) {
            try {
                // Salva le categorie selezionate
                CategoryAndMajorDAO.saveSelectedCategories(selectedCategories, session.getUser ().getMail());
            } catch (Exception e) {
                System.err.println("Error saving categories: " + e.getMessage());
            }
        } else {
            System.out.println("Nessuna categoria selezionata.");
        }
    }


}