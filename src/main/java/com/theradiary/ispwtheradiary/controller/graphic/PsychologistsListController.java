package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PsychologistsListController extends CommonController{
    PsychologistsListController(Session session) {
        super(session);
    }
    @FXML
    private TableView<PsychologistBean> listPsychologist;
    //Primo parametro: tipo tabella, secondo parametro: tipo colonna
    @FXML
    private TableColumn<PsychologistBean, String> fullName;
    @FXML
    private TableColumn<PsychologistBean, String> city;
    @FXML
    private TableColumn<PsychologistBean, String> inPerson;
    @FXML
    private TableColumn<PsychologistBean, String> online;
    @FXML
    private TableColumn<PsychologistBean, String> pag;
    @FXML
    private TableColumn<PsychologistBean, Void> button;


    @FXML
    public void printPsychologists(MouseEvent event, List<PsychologistBean> psychologistBeans){
        ObservableList<PsychologistBean> psychologistsBeansList = FXCollections.observableArrayList(psychologistBeans);
        fullName.setCellValueFactory(new PropertyValueFactory<PsychologistBean, String>("surname"));
        city.setCellValueFactory(new PropertyValueFactory<PsychologistBean, String>("city"));
        inPerson.setCellValueFactory(cellData -> {
            boolean presenza = cellData.getValue().isInPerson();
            return new javafx.beans.property.SimpleStringProperty(presenza ? "Sì" : "No");
        });
        online.setCellValueFactory(cellData -> {
            boolean online = cellData.getValue().isOnline();
            return new javafx.beans.property.SimpleStringProperty(online ? "Sì" : "No");
        });
        pag.setCellValueFactory(cellData -> {
            boolean pag = cellData.getValue().isPag();
            return new javafx.beans.property.SimpleStringProperty(pag ? "Sì" : "No");
        });
        button.setCellFactory(param -> new TableCell<PsychologistBean, Void>() {
                    private final Button btn = new Button("Vedi psicologo");
                    {
                        btn.setOnAction(event -> {
                            PsychologistBean psychologistBean = getTableView().getItems().get(getIndex());
                            goToPsychologistDescription(event, psychologistBean);
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
        listPsychologist.setItems(psychologistsBeansList);

    }
    @FXML
    private void goToPsychologistDescription(ActionEvent event, PsychologistBean psychologistBean) {
        System.out.println("Il bottone funziona");
    }
}

