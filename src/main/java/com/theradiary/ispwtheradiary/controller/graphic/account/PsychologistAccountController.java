package com.theradiary.ispwtheradiary.controller.graphic.account;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.EnumMap;
import java.util.List;

public class PsychologistAccountController extends AccountController {
    public PsychologistAccountController(Session session) {
        super(session);
    }
    @FXML
    private VBox categoryVBOXps; //Box del file fxml
    private EnumMap<Major, CheckBox> majorCheckBoxMap;
    @FXML
    public void initialize(){
        majorCheckBoxMap=new EnumMap<>(Major.class);
        Major[] major = Major.values();
        for(int i=0;i< major.length;i++){
            CheckBox checkBox = (CheckBox) categoryVBOXps.getChildren().get(i);
            majorCheckBoxMap.put(major[i],checkBox);
        }
    }
    private List<Major> getSelectedMajor(){
        List<Major> selectedMajor= new java.util.ArrayList<>();
        for(Major major: Major.values()) {
            CheckBox checkBox = majorCheckBoxMap.get(major);
            if (checkBox != null && checkBox.isSelected()) {
                selectedMajor.add(major);
            }
        }
        return selectedMajor;
    }
    @FXML
    private void saveMajor(){
        List<Major> selectedMajor = getSelectedMajor();
        String psychologistName=session.getUser().getMail();
        CategoryAndMajorDAO.saveSelectedMajor(selectedMajor,psychologistName);
    }
    @FXML
    private void handleCheckBoxClick(MouseEvent event) {
        saveMajor();
    }

    public void goToListPatients(MouseEvent event) {
    }
}
