package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class PatientAccountController extends AccountController {
    public PatientAccountController(Session session) {
        super(session);
    }

    @FXML
    VBox categoryVBox; //Box del file fxml
    public EnumMap<Category, CheckBox> categoryCheckBoxMap;

    @FXML
    public void initialize() { //la funzione adesso controlla il numero preciso di checkbox
       if(categoryVBox==null){
           System.err.println("Error: categoryVBox is null");
           return;
       }
       categoryCheckBoxMap = new EnumMap<>(Category.class);
       List<Node> children = categoryVBox.getChildren();
       Category[] categories = Category.values();
       for (int i = 0; i < categories.length && i < children.size(); i++) {
            if (children.get(i) instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) children.get(i);
                categoryCheckBoxMap.put(categories[i], checkBox);
            }

       }
    }

    private List<Category> getSelectedCategories() {
        List<Category> selectedCategories = new ArrayList<>();
        for (Category category : Category.values()) {
            CheckBox checkBox = categoryCheckBoxMap.get(category);
            if (checkBox != null && checkBox.isSelected()) {
                selectedCategories.add(category);
            }
        }
        return selectedCategories;
    }

    @FXML
    private void saveCategory() {
        List<Category> selectedCategories = getSelectedCategories();
        String patientName = session.getUser().getMail();
        try {
            CategoryAndMajorDAO.saveSelectedCategories(selectedCategories, patientName);
        } catch (Exception e) {
            System.err.println("Errore nel salvataggio delle categorie");
        }
    }
}