package com.theradiary.ispwtheradiary.controller.graphic;


import com.theradiary.ispwtheradiary.engineering.exceptions.SceneLoadingException;
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
            loader.setControllerFactory(c -> new PsychologistDescriptionGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            ((PsychologistDescriptionGUI) loader.getController()).printPsychologist(psychologistBean);
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

}

