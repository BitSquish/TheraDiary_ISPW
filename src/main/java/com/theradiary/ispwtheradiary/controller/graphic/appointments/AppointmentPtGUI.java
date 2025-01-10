package com.theradiary.ispwtheradiary.controller.graphic.appointments;

import com.theradiary.ispwtheradiary.controller.application.AppointmentController;
import com.theradiary.ispwtheradiary.controller.graphic.CommonGUI;
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
import java.util.stream.Stream;

public class AppointmentPtGUI extends CommonGUI {

    private final PsychologistBean psychologistBean = ((PatientBean)session.getUser()).getPsychologistBean();
    private final List<AppointmentBean> allAppointments = new ArrayList<>();
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
    private ComboBox<String> chooseModality;
    @FXML
    private Text psychologistMail;
    @FXML
    private Label success;
    @FXML
    private Text errorMessage;
    @FXML
    private Text contactInfo;

    private static final String IN_PERSON = "In presenza";
    private static final String ONLINE = "Online";

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
        appointmentController.loadAvailableAppointments(allAppointments, (PatientBean) session.getUser());

        // Popola la prima ComboBox con i giorni disponibili (senza duplicati)
        List<String> days = allAppointments.stream()
                .map(appointmentBean -> DayOfTheWeek.translateDay(appointmentBean.getDay().getId()))
                .distinct()
                .toList();
        chooseDay.getItems().addAll(days);

        // Listener sulla prima ComboBox: aggiorna la seconda ComboBox con le fasce orarie
        chooseDay.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Filtra le fasce orarie basate sul giorno selezionato
                List<String> timeSlots = allAppointments.stream()
                        .filter(appointmentBean -> DayOfTheWeek.translateDay(appointmentBean.getDay().getId()).equals(newValue))
                        .map(appointmentBean -> TimeSlot.translateTimeSlot(appointmentBean.getTimeSlot().getId()))
                        .distinct()
                        .toList();
                // Aggiorna la seconda ComboBox
                chooseTimeSlot.getItems().setAll(timeSlots);
                chooseTimeSlot.getSelectionModel().clearSelection(); // Pulisce selezioni precedenti
                chooseModality.getItems().clear(); // Pulisce la modalità precedente
            }
        });

        // Listener sulla seconda ComboBox: aggiorna la terza ComboBox con le modalità
        chooseTimeSlot.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && chooseDay.getSelectionModel().getSelectedItem() != null) {
                String selectedDay = chooseDay.getSelectionModel().getSelectedItem();
                // Filtra gli appuntamenti in base al giorno e alla fascia oraria selezionati
                List<String> modalities = allAppointments.stream()
                        .filter(appointmentBean -> DayOfTheWeek.translateDay(appointmentBean.getDay().getId()).equals(selectedDay) &&
                                TimeSlot.translateTimeSlot(appointmentBean.getTimeSlot().getId()).equals(newValue))
                        .flatMap(appointmentBean -> {
                            boolean inPerson = appointmentBean.isInPerson();
                            boolean online = appointmentBean.isOnline();

                            // Genera una lista di modalità basata sui valori di inPerson e online
                            if (inPerson && online) {
                                return Stream.of(IN_PERSON, ONLINE);
                            } else if (inPerson) {
                                return Stream.of(IN_PERSON);
                            } else if (online) {
                                return Stream.of(ONLINE);
                            } else {
                                return Stream.empty(); // Nessuna modalità disponibile
                            }
                        })
                        .distinct()
                        .toList();

                // Aggiorna la terza ComboBox con le modalità
                chooseModality.getItems().setAll(modalities);
                chooseModality.getSelectionModel().clearSelection(); // Pulisce selezioni precedenti
            }
        });
    }

    @FXML
    private void askForAnAppointment(MouseEvent event) {
        DayOfTheWeek day = DayOfTheWeek.fromStringToDay(chooseDay.getValue());
        TimeSlot timeSlot = TimeSlot.fromStringToTimeSlot(chooseTimeSlot.getValue());
        boolean modality = chooseModality.getValue().equals(IN_PERSON);
        AppointmentBean appointmentBean = new AppointmentBean(psychologistBean, day, timeSlot, modality, !modality);
        appointmentBean.setAvailable(false);
        appointmentBean.setPatientBean(session.getUser().getCredentialsBean().getMail());
        appointmentController.askForAnAppointment(appointmentBean);
        success.setText("Richiesta inviata con successo.");
        success.setVisible(true);
    }

    @FXML
    private void search(MouseEvent event){
        goToSearch(event);
    }
}
