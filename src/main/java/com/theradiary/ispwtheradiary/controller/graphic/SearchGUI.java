package com.theradiary.ispwtheradiary.controller.graphic;


import com.theradiary.ispwtheradiary.controller.application.SearchController;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchGUI extends CommonGUI{
    private static final String PSYCHOLOGISTS_LIST = "/com/theradiary/ispwtheradiary/view/PsychologistsList.fxml";
    public SearchGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    TextField nomeP, cognomeP, cittaP;
    @FXML
    CheckBox inPresenza, online, pag;
    @FXML
    Label errorMessage;

    @FXML
    private void search(MouseEvent event){
        errorMessage.setVisible(false);
        try{
            TextField[] fields = {cognomeP, cittaP};
            checkFields(fields);
            List<PsychologistBean> psychologistBeans = new ArrayList<>();
            SearchController searchController = new SearchController();
            searchController.searchPsychologists(psychologistBeans,nomeP.getText(), cognomeP.getText(), cittaP.getText(), inPresenza.isSelected(), online.isSelected(), pag.isSelected());
            goToPsychologistsList(psychologistBeans,event);
        } catch (EmptyFieldException | NoResultException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }
    }

    private void goToPsychologistsList(List<PsychologistBean> psychologistBeans, MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PSYCHOLOGISTS_LIST));
            loader.setControllerFactory(c -> new PsychologistsListGUI(fxmlPathConfig,session));
            Parent root = loader.load();
            ((PsychologistsListGUI) loader.getController()).printPsychologists(event, psychologistBeans);
            changeScene(root, event);
        } catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    protected void checkFields(TextField[] fields) throws EmptyFieldException {
        for(TextField field:fields){
            if (!(field.getText().isEmpty()))
                return;
        }
        throw new EmptyFieldException("Inserisci almeno almeno un campo tra cognome e città");
    }

}


