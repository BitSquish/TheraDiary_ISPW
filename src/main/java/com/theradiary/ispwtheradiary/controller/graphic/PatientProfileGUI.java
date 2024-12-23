package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.AccountController;
import com.theradiary.ispwtheradiary.controller.application.PatientProfileController;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.StringJoiner;

public class PatientProfileGUI extends CommonGUI {
    public PatientProfileGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }
    private final PatientProfileController patientProfileController = new PatientProfileController();

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
    @FXML
    private Label appointment;

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
        //Recupero dell'appuntamento
        AppointmentBean appointmentBean = patientProfileController.retrieveAppointment(patientBean, (PsychologistBean) session.getUser());
        if(appointmentBean.getDay() != null)
            appointment.setText(DayOfTheWeek.translateDay(appointmentBean.getDay().getId()) + ", fascia oraria: " + TimeSlot.translateTimeSlot(appointmentBean.getTimeSlot().getId()));
        StringJoiner categoryString = new StringJoiner(",");
        patientProfileController.retrieveCategories(patientBean);
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

