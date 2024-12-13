package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.AppointmentPs;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class AppointmentPsController extends CommonController{

    @FXML
    private TabPane tabPane;
    protected AppointmentPsController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }


    @FXML
    private void loadCheckboxes() { //TODO: Entra 2 volte su lunedì
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if(selectedTab == null) {
            System.out.println("Nessuna tab selezionata");
            return;
        }
        String selectedTabText = selectedTab.getText();
        switch (selectedTabText) {
            case "Lunedì":
                initializeCheckboxes(DayOfTheWeek.MONDAY);
                System.out.println("Lunedì");
                break;
            case "Martedì":
                initializeCheckboxes(DayOfTheWeek.TUESDAY);
                System.out.println("Martedì");
                break;
            case "Mercoledì":
                initializeCheckboxes(DayOfTheWeek.WEDNESDAY);
                System.out.println("Mercoledì");
                break;
            case "Giovedì":
                initializeCheckboxes(DayOfTheWeek.THURSDAY);
                System.out.println("Giovedì");
                break;
            case "Venerdì":
                initializeCheckboxes(DayOfTheWeek.FRIDAY);
                System.out.println("Venerdì");
                break;
            default:
                break;
        }
    }

    private void initializeCheckboxes(DayOfTheWeek dayOfTheWeek) {
        PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
        AppointmentPs appointmentPs = new AppointmentPs();
        List<AppointmentBean> appointmentsBean = new ArrayList<>();
        appointmentPs.loadAppointment(appointmentsBean, psychologistBean, dayOfTheWeek);
        for(AppointmentBean appointmentBean : appointmentsBean) {
            System.out.println(appointmentBean.getTimeSlot().toString());
        }
    }

    @FXML
    private void save(MouseEvent event) {
        System.out.println("Salvataggio appuntamento");
    }

}
