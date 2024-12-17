package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.Search;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.exceptions.SceneLoadingException;
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

public class SearchController extends CommonController{
    private static final String PSYCHOLOGISTS_LIST = "/com/theradiary/ispwtheradiary/view/PsychologistsList.fxml";
    public SearchController(FXMLPathConfig fxmlPathConfig, Session session) {
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
            Search searchClass = new Search();
            searchClass.searchPsychologists(psychologistBeans,nomeP.getText(), cognomeP.getText(), cittaP.getText(), inPresenza.isSelected(), online.isSelected(), pag.isSelected());
            goToPsychologistsList(psychologistBeans,event);
        } catch (EmptyFieldException | NoResultException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }
    }

    private void goToPsychologistsList(List<PsychologistBean> psychologistBeans, MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PSYCHOLOGISTS_LIST));
            loader.setControllerFactory(c -> new PsychologistsListController(fxmlPathConfig,session));
            Parent root = loader.load();
            ((PsychologistsListController) loader.getController()).printPsychologists(event, psychologistBeans);
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
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


