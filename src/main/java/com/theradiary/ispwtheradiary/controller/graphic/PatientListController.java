package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.graphic.account.PatientAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PsychologistAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private TableColumn<PatientBean,String> description;
    @FXML
    private TableColumn<PatientBean,String> inPresenza;
    @FXML
    private TableColumn<PatientBean,String> online;
    @FXML
    private TableColumn<PatientBean,Void> checkProfile;
    @FXML
    public void printPatient(MouseEvent event, List<PatientBean> patientBeans){
        ObservableList<PatientBean> patientBeansList = FXCollections.observableArrayList(patientBeans);
        fullName.setCellValueFactory(new PropertyValueFactory<PatientBean,String>("surname"));
        cityName.setCellValueFactory(new PropertyValueFactory<PatientBean,String>("city"));
        description.setCellValueFactory(new PropertyValueFactory<PatientBean,String>("description"));
        inPresenza.setCellValueFactory(cellData->{
            boolean presenza = cellData.getValue().isInPerson();
            return new javafx.beans.property.SimpleStringProperty(presenza ? "Sì" : "No");
        });
        online.setCellValueFactory(cellData->{
            boolean online = cellData.getValue().isOnline();
            return new javafx.beans.property.SimpleStringProperty(online ? "Sì" : "No");
        });
        checkProfile.setCellFactory(param -> new TableCell<PatientBean, Void>() {
            private final Button btn = new Button("Vedi profilo");
            {
                btn.setOnAction(event -> {
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
    private void goToPatientProfile(ActionEvent event, PatientBean patientBean) {
        //TODO
    }
    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;

            // Verifica se l'utente è loggato
            if(session.getUser() == null) {
                // Se non c'è un utente loggato, carica la schermata di login
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session)); // Imposta il controller per la login
            } else {
                // Se l'utente è loggato, carica la schermata dell'account dello psicologo
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/PsychologistAccount.fxml"));
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



}

