package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.AppointmentPsController;
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
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AppointmentPsGUI extends CommonGUI {
    @FXML
    private TabPane tabPane;
    @FXML
    private Text modalityChangedMessage;
    @FXML
    private Text successMessage;
    private List<AppointmentBean> allAppointments = new ArrayList<>();


    protected AppointmentPsGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }


    @FXML
    protected void loadCheckboxes() { //metodo chiamato quando viene cambiata la tab
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if(selectedTab == null) {
            System.out.println("Nessuna tab selezionata");  //TODO: gestire l'eccezione
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
            AppointmentPsController appointmentPs = new AppointmentPsController();
            List<TimeSlot> inPersonTimeSlots = new ArrayList<>();
            List<TimeSlot> onlineTimeSlots = new ArrayList<>();
            appointmentPs.getDayOfTheWeekAppointments(allAppointments,dayOfTheWeek, inPersonTimeSlots,onlineTimeSlots);
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
    private void save(MouseEvent event){
        List<Tab> tabs = tabPane.getTabs();
        List<AppointmentBean> appointmentToAdd = new ArrayList<>();
        for(Tab tab:tabs){
            AnchorPane anchorPane = (AnchorPane) tab.getContent();
            VBox vBoxInPerson = (VBox) anchorPane.getChildren().get(0);
            VBox vBoxOnline = (VBox) anchorPane.getChildren().get(1);
            for(int i = 0; i<vBoxInPerson.getChildren().size(); i++){
                CheckBox inPersonCheckBox = (CheckBox) vBoxInPerson.getChildren().get(i);   //checkbox fasce orarie in presenza
                CheckBox onlineCheckBox = (CheckBox) vBoxOnline.getChildren().get(i);   //checkbox fasce orarie online
                if(inPersonCheckBox.isSelected() || onlineCheckBox.isSelected()){
                    AppointmentBean appointmentBean = getAppointmentBean(tab.getId(), TimeSlot.values()[i], inPersonCheckBox, onlineCheckBox);
                    setPatient(appointmentBean);
                    appointmentToAdd.add(appointmentBean);
                }
            }
        }
        if(!appointmentToAdd.isEmpty()){
            AppointmentPsController appointmentPsController = new AppointmentPsController();
            appointmentPsController.saveAppointments((PsychologistBean) session.getUser(), appointmentToAdd);
            allAppointments.clear();
            allAppointments.addAll(appointmentToAdd);
            changeModality(appointmentToAdd);
            successMessage.setVisible(true);
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

    private void setPatient(AppointmentBean appointmentBean) {
        for(AppointmentBean app: allAppointments){
            if(app.getDay().equals(appointmentBean.getDay()) && app.getTimeSlot().equals(appointmentBean.getTimeSlot())){
                appointmentBean.setPatientBean(app.getPatientBean());
            }
        }
    }

    //Recupera tutti gli appuntamenti, questo metodo viene chiamato all'istanziazione del controller
    public void getAllAppointments() {
        PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
        AppointmentPsController appointmentPsController = new AppointmentPsController();
        appointmentPsController.loadAllAppointments(allAppointments, psychologistBean);
        //faccio il load delle checkboxes del primo tab, perchè le checkboxes vengono inizializzate solo quando viene cambiato tab
        initializeCheckboxes(DayOfTheWeek.MONDAY, tabPane.getTabs().get(0));
    }

    private void changeModality(List<AppointmentBean> appointmentsToAdd){
        AppointmentPsController appointmentPs = new com.theradiary.ispwtheradiary.controller.application.AppointmentPsController();
        boolean inPerson = session.getUser().isInPerson();
        boolean online = session.getUser().isOnline();
        boolean hasChanged = appointmentPs.changeModality(appointmentsToAdd);
        if(hasChanged){
            session.getUser().setInPerson(true);
            session.getUser().setOnline(true);
            modalityChangedMessage.setVisible(true);
        }
    }

}
