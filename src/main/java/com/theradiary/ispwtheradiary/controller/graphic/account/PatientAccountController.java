package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class PatientAccountController extends AccountController {
    public PatientAccountController(Session session) {
        super(session);
    }
    @FXML
    private VBox categoryVBox; //Box del file fxml
    private EnumMap<Category,CheckBox> categoryCheckBoxMap;
    @FXML
    public void initialize(){
        categoryCheckBoxMap=new EnumMap<>(Category.class);
        Category[] categories = Category.values();
        for(int i=0;i< categories.length;i++){
            CheckBox checkBox = (CheckBox) categoryVBox.getChildren().get(i);
            categoryCheckBoxMap.put(categories[i],checkBox);
        }
    }
    private List<Category> getSelectedCategories(){
        List<Category> selectedCategories= new ArrayList<>();
        for(Category category: Category.values()) {
            CheckBox checkBox = categoryCheckBoxMap.get(category);
            if (checkBox != null && checkBox.isSelected()) {
                selectedCategories.add(category);
            }
        }
        return selectedCategories;
    }
    @FXML
    private void saveCategory(){
        List<Category> selectedCategories = getSelectedCategories();
        String patientName=session.getUser().getMail();
        CategoryAndMajorDAO.saveSelectedCategories(selectedCategories,patientName);
    }
    @FXML
    private void handleCheckBoxClickC(MouseEvent event) {
        saveCategory();
    }
}


