package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.AppointmentPs;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private List<AppointmentBean> allAppointments = new ArrayList<>();

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
                break;
            case "Martedì":
                initializeCheckboxes(DayOfTheWeek.TUESDAY, selectedTab);
                break;
            case "Mercoledì":
                initializeCheckboxes(DayOfTheWeek.WEDNESDAY, selectedTab);
                break;
            case "Giovedì":
                initializeCheckboxes(DayOfTheWeek.THURSDAY, selectedTab);
                break;
            case "Venerdì":
                initializeCheckboxes(DayOfTheWeek.FRIDAY, selectedTab);
                break;
            default:
                break;
        }
    }

    private void initializeCheckboxes(DayOfTheWeek dayOfTheWeek, Tab selectedTab) {
        if(!allAppointments.isEmpty()) {
            AnchorPane anchorPane = (AnchorPane) selectedTab.getContent();  //il metodo getContent() restituisce un nodo
            VBox vBoxInPerson = (VBox) anchorPane.getChildren().getFirst(); //il primo nodo figlio dell'anchorpane è la vbox per le visite di persona
            VBox vBoxOnline = (VBox) anchorPane.getChildren().get(1);   //il secondo nodo figlio dell?anchorpane è la vbox per le visite online
            //Ricavo le checkbox dalle VBox
            List<CheckBox> inPersonCheckboxes = new ArrayList<>();
            List<CheckBox> onlineCheckboxes = new ArrayList<>();
            for(Node node : vBoxInPerson.getChildren()) {
                inPersonCheckboxes.add((CheckBox) node);
            }
            for(Node node : vBoxOnline.getChildren()) {
                onlineCheckboxes.add((CheckBox) node);
            }
            //Ricavo gli orari di visita dello psicologo già registrati
            PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
            AppointmentPs appointmentPs = new AppointmentPs();
            List<TimeSlot> inPersonTimeSlots = new ArrayList<>();
            List<TimeSlot> onlineTimeSlots = new ArrayList<>();
            appointmentPs.getDayOfTheWeekAppointments(allAppointments,dayOfTheWeek, inPersonTimeSlots,onlineTimeSlots, psychologistBean);
            //Setto le checkbox
            for(int i = 0; i<inPersonCheckboxes.size(); i++) {
                if(inPersonTimeSlots.contains(TimeSlot.values()[i])) {
                    inPersonCheckboxes.get(i).setSelected(true);
                }
                if(onlineTimeSlots.contains(TimeSlot.values()[i])) {
                    onlineCheckboxes.get(i).setSelected(true);
                }
            }
        }
    }

    @FXML
    private void save(MouseEvent event) {
        List<Tab> tabs = tabPane.getTabs();
        List<AppointmentBean> appointmentToAdd = new ArrayList<>();
        List<AppointmentBean> appointmentToRemove = new ArrayList<>();
        for(Tab tab : tabs){    //per ogni tab, quindi per ogni giorno della settimana
            AnchorPane anchorPane = (AnchorPane) tab.getContent();
            VBox vBoxInPerson = (VBox) anchorPane.getChildren().getFirst(); //fasce orarie in presenza
            VBox vBoxOnline = (VBox) anchorPane.getChildren().get(1);   //fasce orarie online
            //Itero per tutte le fasce orarie
            for(int i = 0; i<vBoxInPerson.getChildren().size(); i++){
                CheckBox inPersonCheckBox = (CheckBox) vBoxInPerson.getChildren().get(i);
                CheckBox onlineCheckBox = (CheckBox) vBoxOnline.getChildren().get(i);
                //Aggiorno la lista con le nuove fasce orarie scelte
                if(inPersonCheckBox.isSelected() || onlineCheckBox.isSelected()){
                    AppointmentBean appointmentBean = getAppointmentBean(tab.getId(),TimeSlot.values()[i], inPersonCheckBox, onlineCheckBox);
                    if(!allAppointments.contains(appointmentBean)){
                        appointmentToAdd.add(appointmentBean);
                    }
                }
                //Aggiorno la lista con le fasce orarie da rimuovere
                else{
                    AppointmentBean appointmentBean = getAppointmentBean(tab.getId(), TimeSlot.values()[i], inPersonCheckBox, onlineCheckBox);
                    if(allAppointments.contains(appointmentBean)){
                        appointmentToRemove.add(appointmentBean);
                    }
                }
            }
            if(!(appointmentToAdd.isEmpty() && appointmentToRemove.isEmpty())){
                AppointmentPs appointmentPs = new AppointmentPs();
                appointmentPs.saveAppointments((PsychologistBean)session.getUser(),appointmentToAdd, appointmentToRemove);
                //TODO Mostra messaggio di successo
                //TODO Se l'utente inserisce modalità di visita non concordi a quelle del profilo, aggiornare il profilo
            }
        }
    }

    private AppointmentBean getAppointmentBean(String id, TimeSlot timeSlot, CheckBox inPersonCheckBox, CheckBox onlineCheckBox) {
        return new AppointmentBean(
                (PsychologistBean) session.getUser(),
                DayOfTheWeek.valueOf(id.toUpperCase()),
                timeSlot,
                inPersonCheckBox.isSelected(),
                onlineCheckBox.isSelected()
                );
    }

    //Recupera tutti gli appuntamenti, questo metodo viene chiamato all'istanziazione del controller
    public void getAllAppointments() {
        PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
        AppointmentPs appointmentPs = new AppointmentPs();
        appointmentPs.loadAllAppointments(allAppointments, psychologistBean);
        //faccio il load delle checkboxes del primo tab, perchè le checkboxes vengono inizializzate solo quando viene cambiato tab
        initializeCheckboxes(DayOfTheWeek.MONDAY, tabPane.getTabs().get(0));
    }

}
