package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.StringJoiner;

public class PatientProfileController extends CommonController {
    public PatientProfileController(FXMLPathConfig fxmlPathConfig,Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    private Label fullName;
    @FXML
    private Label mail;
    @FXML
    private Label meet;
    @FXML
    private Label city;
    @FXML
    private Label category;
    @FXML
    private Label description;

    protected void printPatient(PatientBean patientBean) {
        //recupero il paziente
        fullName.setText(patientBean.getFullName());
        mail.setText(patientBean.getCredentialsBean().getMail());
        if (patientBean.isInPerson() && patientBean.isOnline()) {
            meet.setText("In presenza e online");
        } else if (patientBean.isInPerson()){
            meet.setText("In presenza");
        } else {
            meet.setText("Online");
        }
        city.setText(patientBean.getCity());
        StringJoiner categoryString = new StringJoiner(",");
        Account account = new Account();
        account.retrieveCategories(patientBean);
        if (patientBean.getCategories() != null && !patientBean.getCategories().isEmpty()) {
            for (Category c : patientBean.getCategories()) {
                String translatedCategory = Category.translateCategory(c.getId());
                categoryString.add(translatedCategory);
            }
            category.setText(categoryString.toString());
        } else {
            category.setText("Non specificate");
        }
        description.setText(patientBean.getDescription());
    }


}

