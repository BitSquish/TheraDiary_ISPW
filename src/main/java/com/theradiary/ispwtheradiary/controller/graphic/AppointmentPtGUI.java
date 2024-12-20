package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.AppointmentController;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AppointmentPtGUI extends CommonGUI {

    private final PsychologistBean psychologistBean = ((PatientBean)session.getUser()).getPsychologistBean();
    private List<AppointmentBean> allAppointments = new ArrayList<>();
    private final AppointmentController appointmentController = new AppointmentController();
    public AppointmentPtGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }
    @FXML
    private VBox psychologistNotSetted;
    @FXML
    private VBox appointmentVbox;
    @FXML
    private VBox emptyAppointments;
    @FXML
    private ComboBox<String> chooseDay;
    @FXML
    private ComboBox<String> chooseTimeSlot;
    @FXML
    private Text psychologistMail;
    @FXML
    private Label success;
    @FXML
    private Text errorMessage;
    @FXML
    private Text contactInfo;

    /*
        Casi da distinguere:
        1) Nessuno psicologo
        2) Nessun appuntamento disponibile
        3) Appuntamento già fissato
        4) Richiesta per una specifica fascia oraria già inviata
        5) Richiesta inviata
     */


    @FXML
    public void initializeVbox() {
        //Se il paziente non ha un psicologo associato mostra un bottone che riporta alla ricerca dello psicologo
        if(psychologistBean.getCredentialsBean().getMail() == null) {
            psychologistNotSetted.setVisible(true);
        } else {
            //Carica tutte le fasce orarie registrate dallo psicologo
            appointmentController.loadAllAppointments(allAppointments, psychologistBean);
            //Se lo psicologo associato non ha ancora registrato nessun orario per gli appuntamenti, avvisa il paziente
            if(allAppointments.isEmpty()){
                emptyAppointments.setVisible(true);
                psychologistMail.setText(psychologistBean.getCredentialsBean().getMail());
            }else{
                //Se il paziente ha già un appuntamento associato, lo avvisa.
                AppointmentBean appointmentBean = appointmentController.getAppointmentIfExists((PatientBean) session.getUser(), allAppointments);
                if(appointmentBean != null){
                    emptyAppointments.setVisible(true);
                    errorMessage.setText("Hai già un appuntamento fissato con lo psicologo, ogni " + DayOfTheWeek.translateDay(appointmentBean.getDay().getId()) + " nella fascia oraria " + TimeSlot.translateTimeSlot(appointmentBean.getTimeSlot().getId()) + "");
                    contactInfo.setText("Contatta il tuo psicologo per eventuali modifiche al seguente indirizzo e-mail: ");
                    psychologistMail.setText(psychologistBean.getCredentialsBean().getMail());
                }else{
                    //Se il paziente ha uno psicologo associato e non ha già un appuntamento assegnato, mostra le fasce orarie e i giorni disponibili
                    appointmentVbox.setVisible(true);
                    initializeCombobox();
                }
            }
        }
    }

    @FXML
    private void initializeCombobox() {
        // Carica tutti gli appuntamenti disponibili
        appointmentController.loadAvailableAppointments(allAppointments, (PatientBean)session.getUser());
        // Popola la prima ComboBox con i giorni disponibili (senza duplicati)
        List<String> days = allAppointments.stream().map(appointmentBean -> DayOfTheWeek.translateDay(appointmentBean.getDay().getId())).distinct().toList();
        chooseDay.getItems().addAll(days);
        // Listener sulla prima ComboBox: aggiorna la seconda ComboBox con le fasce orarie
        chooseDay.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Filtra le fasce orarie basate sul giorno selezionato
                // Converte TimeSlot in stringa
                List<String> timeSlots = allAppointments.stream().filter(appointmentBean -> DayOfTheWeek.translateDay(appointmentBean.getDay().getId()).equals(newValue)).map(appointmentBean -> TimeSlot.translateTimeSlot(appointmentBean.getTimeSlot().getId())).distinct().toList();
                // Aggiorna la seconda ComboBox
                chooseTimeSlot.getItems().setAll(timeSlots);
                chooseTimeSlot.getSelectionModel().clearSelection(); // Pulisce selezioni precedenti
            }
        });
    }

    @FXML
    private void askForAnAppointment(MouseEvent event) {
        DayOfTheWeek day = DayOfTheWeek.fromStringToDay(chooseDay.getValue());
        TimeSlot timeSlot = TimeSlot.fromStringToTimeSlot(chooseTimeSlot.getValue());
        if(!appointmentController.hasAlreadySentARequest((PatientBean) session.getUser(), day, timeSlot, allAppointments)) {
            AppointmentBean appointmentBean = new AppointmentBean(psychologistBean, day, timeSlot, session.getUser().getCredentialsBean().getMail());
            appointmentController.askForAnAppointment(appointmentBean);
            success.setText("Richiesta inviata con successo.");
        }
        else {
            success.setText("Hai già fatto richiesta per questa fascia oraria. Attendi che il tuo psicologo confermi o rifiuti l'appuntamento.");
        }
        success.setVisible(true);
    }

    @FXML
    private void search(MouseEvent event){
        goToSearch(event);
    }
}
