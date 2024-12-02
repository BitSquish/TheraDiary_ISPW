package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.List;

public class PsychologistsListController extends CommonController {
    PsychologistsListController(FXMLPathConfig fxmlPathConfig, Session session) {
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
    public void printPsychologists(MouseEvent event, List<PsychologistBean> psychologistBeans) {
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
        buttonColumn.setCellFactory(param -> new TableCell<PsychologistBean, Void>() {
            private final Button btn = new Button("Vedi psicologo");

            {
                btn.setOnMouseClicked(event -> {
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
    private void goToPsychologistDescription(MouseEvent event, PsychologistBean psychologistBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PSYCHOLOGIST_DESCRIPTION_PATH)));
            loader.setControllerFactory(c -> new PsychologistDescriptionController(fxmlPathConfig, session));
            Parent root = loader.load();
            ((PsychologistDescriptionController) loader.getController()).printPsychologist(psychologistBean);
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
            if (session.getUser() == null) {
                // Se non c'è un utente loggato, carica la schermata di login
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session)); // Imposta il controller per la login
            } else {
                // Se l'utente è loggato, carica la schermata dell'account dello psicologo
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(SEARCH_PATH)));
                loader.setControllerFactory(c -> new SearchController(fxmlPathConfig, session));
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

