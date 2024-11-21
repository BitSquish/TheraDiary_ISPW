package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.PatientList;
import com.theradiary.ispwtheradiary.controller.graphic.account.PsychologistAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.model.beans.RequestBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientListController extends CommonController {
    public PatientListController(Session session) {
        super(session);
    }
    @FXML
    private TableView<PatientBean> patientTable;
    @FXML
    private TableColumn<PatientBean,String> fullName;
    @FXML
    private TableColumn<PatientBean,String> cityName;
    @FXML
    private TableColumn<PatientBean,String> inPresenza;
    @FXML
    private TableColumn<PatientBean,String> online;
    @FXML
    private TableColumn<PatientBean,Void> checkTask;
    @FXML
    private TableColumn<PatientBean,Void> checkProfile;
    @FXML
    private static final String REQUEST_PATH = "/com/theradiary/ispwtheradiary/view/Request.fxml";
    @FXML
    private static final String PATIENT_TASK_PATH = "/com/theradiary/ispwtheradiary/view/PatientTask.fxml";


    @FXML
    public void printPatient(MouseEvent event, List<PatientBean> patientBeans){
        ObservableList<PatientBean> patientBeansList = FXCollections.observableArrayList(patientBeans);
        fullName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        cityName.setCellValueFactory(new PropertyValueFactory<>("city"));
        inPresenza.setCellValueFactory(cellData->{
            boolean presenza = cellData.getValue().isInPerson();
            return new javafx.beans.property.SimpleStringProperty(presenza ? "Sì" : "No");
        });
        online.setCellValueFactory(cellData->{
            boolean online = cellData.getValue().isOnline();
            return new javafx.beans.property.SimpleStringProperty(online ? "Sì" : "No");
        });
        checkProfile.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Vedi Task");

            {
                btn.setOnMouseClicked(event -> {
                    PatientBean patientBean = getTableView().getItems().get(getIndex());
                    goToPatientTask(event, patientBean);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
        checkProfile.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Vedi profilo");

            {
                btn.setOnMouseClicked(event -> {
                    PatientBean patientBean = getTableView().getItems().get(getIndex());
                    goToPatientProfile(event, patientBean);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
        patientTable.setItems(patientBeansList);
    }
    @FXML
    private void goToPatientProfile(MouseEvent event, PatientBean patientBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATIENT_PROFILE_PATH));
            loader.setControllerFactory(c -> new PatientProfileController(session));
            Parent root = loader.load();
            ((PatientProfileController)loader.getController()).printPatient(patientBean);
            changeScene(root, event);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena: " + e.getMessage(), e);
        }
    }
    @FXML
    private void goToPatientTask(MouseEvent event,PatientBean patientBean){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATIENT_TASK_PATH));
            loader.setControllerFactory(c -> new PatientTaskController(session));
            Parent root = loader.load();
            ((PatientTaskController)loader.getController()).printPatientTask(patientBean);
            changeScene(root, event);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena: " + e.getMessage(), e);
        }
    }
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
                // Se l'utente è loggato, carica la schermata dell'account dello psicologo
                loader = new FXMLLoader(getClass().getResource(PSYCHOLOGIST_ACCOUNT_PATH));
                loader.setControllerFactory(c -> new PsychologistAccountController(session)); // Controller per l'account psicologo
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

    //DA COMPLETARE
    private void goToRequest(List<RequestBean> requestBeans, MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(REQUEST_PATH));
            loader.setControllerFactory(c -> new RequestController(session));
            Parent root = loader.load();
            ((RequestController)loader.getController()).loadRequest(requestBeans);
            changeScene(root, event);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena: " + e.getMessage(), e);
        }
    }


    //DA COMPLETARE
    @FXML
    public void seeRequest(MouseEvent event) {
        ArrayList<RequestBean> requestBeans = new ArrayList<>();
        PatientList patientList = new PatientList();
        patientList.getRequests((PsychologistBean)session.getUser(), requestBeans);
        goToRequest(requestBeans, event);
    }
}

