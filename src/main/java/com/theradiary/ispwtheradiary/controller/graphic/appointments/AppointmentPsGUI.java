
package com.theradiary.ispwtheradiary.controller.graphic.appointments;

import com.theradiary.ispwtheradiary.controller.application.AppointmentController;
import com.theradiary.ispwtheradiary.controller.graphic.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentPsGUI extends CommonGUI {
    @FXML
    private TabPane tabPane;
    @FXML
    private Text modalityChangedMessage;
    @FXML
    private Text successMessage;
    @FXML
    private Label infoLabel;
    @FXML
    private Label clickForInfo;
    private List<AppointmentBean> allAppointments = new ArrayList<>();


    public AppointmentPsGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    private final AppointmentController appointmentPs = new AppointmentController();

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
        if(!allAppointments.isEmpty()) {    //Se sono stati precedentemente memorizzati degli appuntamenti, li carica
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
            //Preparo gli array per ricavare gli orari di visita dello psicologo già registrati
            List<TimeSlot> inPersonTimeSlots = new ArrayList<>();
            List<TimeSlot> onlineTimeSlots = new ArrayList<>();
            //Recupero gli appuntamenti del giorno selezionato
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
        List<AppointmentBean> appointmentToAdd = new ArrayList<>(); //lista da riempire con gli appuntamenti da salvare (vecchi e nuovi)
        //Ottengo il giorno della settimana
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        DayOfTheWeek day = DayOfTheWeek.valueOf(tab.getId());

        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        VBox vBoxInPerson = (VBox) anchorPane.getChildren().get(0); //vbox delle visite in presenza
        VBox vBoxOnline = (VBox) anchorPane.getChildren().get(1);   //vbox delle visite online
        for(int i = 0; i<vBoxInPerson.getChildren().size(); i++){   //itero tutte le fasce orarie
            CheckBox inPersonCheckBox = (CheckBox) vBoxInPerson.getChildren().get(i);   //checkbox fasce orarie in presenza
            CheckBox onlineCheckBox = (CheckBox) vBoxOnline.getChildren().get(i);   //checkbox fasce orarie online
            if(inPersonCheckBox.isSelected() || onlineCheckBox.isSelected()){
                AppointmentBean appointmentBean = getAppointmentBean(day, TimeSlot.values()[i], inPersonCheckBox, onlineCheckBox);
                setPatientAndAvailability(appointmentBean); //se la checkbox corrisponde a un appuntamento fissato, salvo il paziente associato
                appointmentToAdd.add(appointmentBean);
            }
        }
        if(!appointmentToAdd.isEmpty()){
            appointmentPs.saveAppointments((PsychologistBean) session.getUser(), appointmentToAdd);
            allAppointments.removeIf(appointment -> appointment.getDay() == day);   //aggiorno la lista
            allAppointments.addAll(appointmentToAdd);   //aggiorno la lista
            changeModality(appointmentToAdd);
            successMessage.setText("Appuntamenti di " + DayOfTheWeek.translateDay(day.getId()) + " salvati.");
            successMessage.setVisible(true);
        }
    }


    //Metodo che crea un oggetto di tipo AppointmentBean con i parametri passati
    private AppointmentBean getAppointmentBean(DayOfTheWeek day, TimeSlot timeSlot, CheckBox inPersonCheckBox, CheckBox onlineCheckBox) {
        return new AppointmentBean(
                (PsychologistBean) session.getUser(),
                day,
                timeSlot,
                inPersonCheckBox.isSelected(),
                onlineCheckBox.isSelected()
        );
    }

    private void setPatientAndAvailability(AppointmentBean appointmentBean) {
        for(AppointmentBean app: allAppointments){
            if(app.getDay().equals(appointmentBean.getDay()) && app.getTimeSlot().equals(appointmentBean.getTimeSlot()) && app.getPatientBean() != null){
                appointmentBean.setPatientBean(app.getPatientBean());
                appointmentBean.setAvailable(app.isAvailable());
                if(appointmentBean.getPatientBean() != null) {
                    appointmentBean.setInPerson(app.isInPerson());
                    appointmentBean.setOnline(app.isOnline());
                }
                break;
            }
        }
    }

    //Recupera tutti gli appuntamenti, questo metodo viene chiamato all'istanziazione del controller
    public void getAllAppointments() {
        PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
        appointmentPs.loadAllAppointments(allAppointments, psychologistBean);
        successMessage.setVisible(false);
        //faccio il load delle checkboxes del primo tab, perchè le checkboxes vengono inizializzate solo quando viene cambiato tab
        initializeCheckboxes(DayOfTheWeek.MONDAY, tabPane.getTabs().get(0));
    }


    //Se lo psicologo ha inserito una visita non concorde alle modalità specificate in fase di registrazione, aggiorna il profilo
    private void changeModality(List<AppointmentBean> appointmentsToAdd){
        boolean hasChanged = appointmentPs.changeModality(appointmentsToAdd);
        if(hasChanged){
            session.getUser().setInPerson(true);
            session.getUser().setOnline(true);
            modalityChangedMessage.setVisible(true);
        }
    }

    @FXML
    private void goToSummary(MouseEvent event) {
        try{
            if(session.getUser() == null){
                goToLogin(event);
            } else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(APPOINTMENT_SUMMARY_PATH)));
                loader.setControllerFactory(c -> new AppointmentSummaryGUI(fxmlPathConfig, session));
                Parent root = loader.load();
                List<AppointmentBean> allPatientAppointments = allAppointments.stream()
                        .filter(appointmentBean -> !appointmentBean.isAvailable())  // Filtra gli appuntamenti non disponibili
                        .toList();         // Raccoglie i risultati in una nuova lista
                ((AppointmentSummaryGUI)loader.getController()).printAppointment(event, allPatientAppointments);
                changeScene(root, event);
            }
        }catch(IOException e){
            throw new LoadingException(LOADING_SCENE, e);
        }

    }

    @FXML
    private void clickForInformation(MouseEvent event){
        if(infoLabel.isVisible()){
            infoLabel.setVisible(false);
            clickForInfo.setText("Clicca qui per informazioni aggiuntive sul salvataggio degli appuntamenti");
        } else {
            infoLabel.setVisible(true);
            clickForInfo.setText("Clicca qui per chiudere il messaggio informativo.");
        }
    }

}
