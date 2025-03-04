package com.theradiary.ispwtheradiary.view.gui;


import com.theradiary.ispwtheradiary.controller.PatientProfileController;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


import java.util.StringJoiner;

public class PatientProfileGUI extends CommonGUI {
    private final PatientBean patientBean;

    public PatientProfileGUI(FXMLPathConfig fxmlPathConfig, Session session, PatientBean patientBean) {
        super(fxmlPathConfig,session);
        this.patientBean = patientBean;
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

    //Metodo per visualizzare il profilo del paziente
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
        if(appointmentBean != null && appointmentBean.getDay() != null && appointmentBean.getPsychologistBean()!=null && appointmentBean.getTimeSlot() != null )
            appointment.setText(DayOfTheWeek.translateDay(appointmentBean.getDay().getId()) + ", fascia oraria: " + TimeSlot.translateTimeSlot(appointmentBean.getTimeSlot().getId()));
        else{
            appointment.setText("Non presente");
        }
        StringJoiner categoryString = new StringJoiner(",");
        //Recupero delle categorie
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

    //Porta alla schermata di assegnazione delle task
    @FXML
    private void handle(MouseEvent event){
        goToPatientTask(event, patientBean);
    }




}

