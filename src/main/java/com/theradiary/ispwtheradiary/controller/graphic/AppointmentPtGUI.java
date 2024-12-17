package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;

public class AppointmentPtGUI extends CommonGUI {
    public AppointmentPtGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }

    @FXML
    private VBox psychologistNotSetted;
    @FXML
    private VBox appointmentVbox;


    @FXML
    public void initializeVbox() {
        if(((PatientBean)session.getUser()).getPsychologistBean().getCredentialsBean().getMail() == null) {
            psychologistNotSetted.setVisible(true);
            appointmentVbox.setVisible(false);
        } else {
            psychologistNotSetted.setVisible(false);
            appointmentVbox.setVisible(true);
        }
    }
    @FXML
    private void askForAnAppointment(MouseEvent event) {
        //TODO: implementare
    }

    @FXML
    private void search(MouseEvent event){
        goToSearch(event);
    }
}
