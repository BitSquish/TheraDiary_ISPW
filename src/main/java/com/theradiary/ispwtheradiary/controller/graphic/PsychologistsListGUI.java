package com.theradiary.ispwtheradiary.controller.graphic;


import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PsychologistsListGUI extends CommonGUI {
    PsychologistsListGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    private TableView<PsychologistBean> listPsychologist;
    //Primo parametro: tipo tabella, secondo parametro: tipo colonna
    @FXML
    private TableColumn<PsychologistBean, String> fullNameColumn;
    @FXML
    private TableColumn<PsychologistBean, String> cityColumn;
    @FXML
    private TableColumn<PsychologistBean, String> inPersonColumn;
    @FXML
    private TableColumn<PsychologistBean, String> onlineColumn;
    @FXML
    private TableColumn<PsychologistBean, String> pagColumn;
    @FXML
    private TableColumn<PsychologistBean, Void> buttonColumn;


    @FXML
    public void printPsychologists(List<PsychologistBean> psychologistBeans) {
        ObservableList<PsychologistBean> psychologistsBeansList = FXCollections.observableArrayList(psychologistBeans);
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        inPersonColumn.setCellValueFactory(cellData -> {
            boolean presenza = cellData.getValue().isInPerson();
            return new javafx.beans.property.SimpleStringProperty(presenza ? "Sì" : "No");
        });
        onlineColumn.setCellValueFactory(cellData -> {
            boolean online = cellData.getValue().isOnline();
            return new javafx.beans.property.SimpleStringProperty(online ? "Sì" : "No");
        });
        pagColumn.setCellValueFactory(cellData -> {
            boolean pag = cellData.getValue().isPag();
            return new javafx.beans.property.SimpleStringProperty(pag ? "Sì" : "No");
        });
        buttonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = createButton();
            private Button createButton() {
                Button button = new Button("Vedi psicologo");
                button.setOnMouseClicked(event -> {
                    PsychologistBean psychologistBean = getTableView().getItems().get(getIndex());
                    goToPsychologistDescription(event, psychologistBean);
                });
                return button;
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


}

