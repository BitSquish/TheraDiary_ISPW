package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.AppointmentPs;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AppointmentPsController extends CommonController{

    @FXML
    private TabPane tabPane;
    protected AppointmentPsController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }


    @FXML
    protected void loadCheckboxes() { //TODO: Entra 2 volte su lunedì
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if(selectedTab == null) {
            System.out.println("Nessuna tab selezionata");
            return;
        }
        String selectedTabText = selectedTab.getText();
        switch (selectedTabText) {
            case "Lunedì":
                initializeCheckboxes(DayOfTheWeek.MONDAY, selectedTab);
                System.out.println("Lunedì");
                break;
            case "Martedì":
                initializeCheckboxes(DayOfTheWeek.TUESDAY, selectedTab);
                System.out.println("Martedì");
                break;
            case "Mercoledì":
                initializeCheckboxes(DayOfTheWeek.WEDNESDAY, selectedTab);
                System.out.println("Mercoledì");
                break;
            case "Giovedì":
                initializeCheckboxes(DayOfTheWeek.THURSDAY, selectedTab);
                System.out.println("Giovedì");
                break;
            case "Venerdì":
                initializeCheckboxes(DayOfTheWeek.FRIDAY, selectedTab);
                System.out.println("Venerdì");
                break;
            default:
                break;
        }
    }

    private void initializeCheckboxes(DayOfTheWeek dayOfTheWeek, Tab selectedTab) {
        AnchorPane anchorPane = (AnchorPane) selectedTab.getContent();  //getContent() restituisce un nodo
        //Nota debug: AnchorPane riconosciuto correttamente
        PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
        AppointmentPs appointmentPs = new AppointmentPs();
        List<TimeSlot> timeSlots = new ArrayList<>();
        // Determina gli ID delle VBox per il giorno specifico
        String inPersonVBoxId = "inPerson" + dayOfTheWeek.name();
        String onlineVBoxId = "online" + dayOfTheWeek.name();

                                // Trova le VBox corrispondenti
        VBox inPersonVBox = (VBox) anchorPane.getScene().lookup("#" + inPersonVBoxId);  //Restituisce null
        VBox onlineVBox = (VBox) anchorPane.getScene().lookup("#" + onlineVBoxId);  //Restituisce null
        //System.out.println("VBox inPerson: " + inPersonVBox.getId());
        //System.out.println("VBox online: " + onlineVBox.getId());
                                    // Le VBOX sono null

        /* if (inPersonVBox == null || onlineVBox == null) {
            throw new IllegalStateException("VBox non trovata per il giorno: " + dayOfTheWeek.name());
        }
        appointmentPs.loadAppointment(timeSlots, psychologistBean, dayOfTheWeek);
        for (javafx.scene.Node node : inPersonVBox.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                //TODO: Settare il valore del checkbox
                if (timeSlots.contains(TimeSlot.valueOf(checkBox.getId()))) {
                    checkBox.setSelected(true);
                }
            }
        }*/
    }

    @FXML
    private void save(MouseEvent event) {
        System.out.println("Salvataggio appuntamento");
    }

}
