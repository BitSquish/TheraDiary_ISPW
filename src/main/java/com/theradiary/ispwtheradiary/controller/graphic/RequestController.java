package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.RequestBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RequestController extends CommonController{
    public RequestController(Session session) {
        super(session);
    }

    @FXML
    private TableView<PatientBean> patients;
    @FXML
    private TableColumn<PatientBean, String> fullName;
    @FXML
    private TableColumn<PatientBean, String> cityName;
    @FXML
    private TableColumn<PatientBean, LocalDate> requestDate;
    @FXML
    private TableColumn<PatientBean, Void> acceptButton;
    @FXML
    private TableColumn<PatientBean, Void> rejectButton;


    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;

            // Verifica se l'utente è loggato
            if(session.getUser() == null) {
                // Se non c'è un utente loggato, carica la schermata di login
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session)); // Imposta il controller per la login
            } else {
                // Se l'utente è loggato, carica la schermata dei pazienti
                loader = new FXMLLoader(getClass().getResource(PATIENT_LIST));
                loader.setControllerFactory(c -> new PatientListController(session));
            }

            // Carica e cambia scena
            Parent root = loader.load();
            changeScene(root, event);

        } catch (IOException e) {
            e.printStackTrace();
            // Aggiungi un messaggio di errore personalizzato
            throw new RuntimeException("Errore nel caricamento della scena: " + e.getMessage(), e);
        }
    }

    public void loadRequest(List<RequestBean> requestBeans) {
        System.out.println("Caricamento richieste");
        for (RequestBean requestBean : requestBeans) {
            System.out.println(requestBean.getPatientBean().getFullName());
            System.out.println(requestBean.getPsychologistBean().getFullName());
            System.out.println(requestBean.getDate());
        }
    }
}
