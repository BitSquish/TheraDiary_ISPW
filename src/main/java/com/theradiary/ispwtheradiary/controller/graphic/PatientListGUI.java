package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.PatientListController;
import com.theradiary.ispwtheradiary.controller.graphic.task.PatientDetailsGUI;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;
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
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class PatientListGUI extends CommonGUI {
    public PatientListGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
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
    public void printPatient(MouseEvent event, List<PatientBean> patientBeans) {
        // Creazione della lista osservabile
        ObservableList<PatientBean> patientBeansList = FXCollections.observableArrayList(patientBeans);

        // Configurazione delle colonne
        fullName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        cityName.setCellValueFactory(new PropertyValueFactory<>("city"));

        inPresenza.setCellValueFactory(cellData -> {
            boolean inPerson = cellData.getValue().isInPerson();
            return new javafx.beans.property.SimpleStringProperty(inPerson ? "Sì" : "No");
        });

        online.setCellValueFactory(cellData -> {
            boolean onlineStatus = cellData.getValue().isOnline();
            return new javafx.beans.property.SimpleStringProperty(onlineStatus ? "Sì" : "No");
        });

        // Configurazione delle colonne con bottoni
        checkTask.setCellFactory(createButtonCellFactory("Vedi Task", this::goToPatientTask));
        checkProfile.setCellFactory(createButtonCellFactory("Vedi Profilo", this::goToPatientProfile));

        // Imposta i dati nella tabella
        patientTable.setItems(patientBeansList);
    }

    //Metodo per creare una cell factory con un bottone. Prende come parametri il testo del bottone e l'azione da eseguire al suo click.
    private Callback<TableColumn<PatientBean, Void>, TableCell<PatientBean, Void>> createButtonCellFactory(
            String buttonText, BiConsumer<MouseEvent, PatientBean> action) {
        return param -> new TableCell<>() {
            private final Button btn = new Button(buttonText);
            {
                btn.setOnMouseClicked(event -> {
                    PatientBean patientBean = getTableView().getItems().get(getIndex());
                    action.accept(event, patientBean);
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
        };
    }

    @FXML
    private void goToPatientProfile(MouseEvent event, PatientBean patientBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_PROFILE_PATH)));
            loader.setControllerFactory(c -> new PatientProfileGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            ((PatientProfileGUI)loader.getController()).printPatient(patientBean);
            changeScene(root, event);
        } catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }
    @FXML
    private void goToPatientTask(MouseEvent event,PatientBean patientBean){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_TASK_PATH)));
            loader.setControllerFactory(c -> new PatientDetailsGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            ((PatientDetailsGUI)loader.getController()).patientTask(patientBean);
            changeScene(root, event);
        } catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }

    private void goToRequest(List<RequestBean> requestBeans, MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(REQUEST_PATH)));
            loader.setControllerFactory(c -> new RequestGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            ((RequestGUI)loader.getController()).initializeObserver();
            ((RequestGUI)loader.getController()).loadRequest(requestBeans);
            changeScene(root, event);
        } catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }
    @FXML
    public void seeRequest(MouseEvent event) {
        ArrayList<RequestBean> requestBeans = new ArrayList<>();
        PatientListController patientListController = new PatientListController();
        patientListController.getRequests((PsychologistBean)session.getUser(), requestBeans);
        goToRequest(requestBeans, event);
    }
}

