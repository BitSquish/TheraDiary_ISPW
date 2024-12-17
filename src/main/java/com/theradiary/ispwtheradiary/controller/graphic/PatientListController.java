package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.PatientList;
import com.theradiary.ispwtheradiary.controller.graphic.task.PatientDetailsController;
import com.theradiary.ispwtheradiary.engineering.exceptions.SceneLoadingException;
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

public class PatientListController extends CommonController {
    public PatientListController(FXMLPathConfig fxmlPathConfig, Session session) {
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

    /**
     * Metodo ausiliario per creare una cell factory con un bottone.
     *
     * @param buttonText Testo del bottone
     * @param action     Azione da eseguire al click del bottone
     * @return Callback per la cell factory
     */
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
            loader.setControllerFactory(c -> new PatientProfileController(fxmlPathConfig, session));
            Parent root = loader.load();
            ((PatientProfileController)loader.getController()).printPatient(patientBean);
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }
    @FXML
    private void goToPatientTask(MouseEvent event,PatientBean patientBean){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_TASK_PATH)));
            loader.setControllerFactory(c -> new PatientDetailsController(fxmlPathConfig, session));
            Parent root = loader.load();
            ((PatientDetailsController)loader.getController()).patientTask(patientBean);
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

    private void goToRequest(List<RequestBean> requestBeans, MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(REQUEST_PATH)));
            loader.setControllerFactory(c -> new RequestController(fxmlPathConfig, session));
            Parent root = loader.load();
            ((RequestController)loader.getController()).initializeObserver();
            ((RequestController)loader.getController()).loadRequest(requestBeans);
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }
    @FXML
    public void seeRequest(MouseEvent event) {
        ArrayList<RequestBean> requestBeans = new ArrayList<>();
        PatientList patientList = new PatientList();
        patientList.getRequests((PsychologistBean)session.getUser(), requestBeans);
        goToRequest(requestBeans, event);
    }
}

