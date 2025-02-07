package com.theradiary.ispwtheradiary.view.gui;


import com.theradiary.ispwtheradiary.controller.SearchController;
import com.theradiary.ispwtheradiary.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
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

public class SearchGUI extends CommonGUI {
    private static final String PSYCHOLOGISTS_LIST = "/com/theradiary/ispwtheradiary/view/PsychologistsList.fxml";
    public SearchGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }
    private final SearchController searchController = new SearchController();

    @FXML
    TextField nomeP;
    @FXML
    TextField cognomeP;
    @FXML
    TextField cittaP;
    @FXML
    CheckBox inPresenza;
    @FXML
    CheckBox online;
    @FXML
    CheckBox pag;
    @FXML
    Label errorMessage;

    @FXML
    public  void search(MouseEvent event){
        errorMessage.setVisible(false);
        try{
            TextField[] fields = {cognomeP, cittaP};    //Almeno uno tra cognome e città è obbligatorio
            checkFields(fields);
            List<PsychologistBean> psychologistBeans = new ArrayList<>();
            //Cerca gli psicologi corrispondenti ai filtri immessi
            searchController.searchPsychologists(psychologistBeans,nomeP.getText(), cognomeP.getText(), cittaP.getText(), inPresenza.isSelected(), online.isSelected(), pag.isSelected());
            goToPsychologistsList(psychologistBeans,event); //porta alla schermata con la lista di psicologi
        } catch (EmptyFieldException | NoResultException exception){    //Eccezioni: campi obbligatori non inseriti, nessun risultato trovato
            //Visualizza messaggio di errore
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }
    }

    //Metodo per passare alla schermata con la lista di psicologi
    @FXML
    public void goToPsychologistsList(List<PsychologistBean> psychologistBeans, MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PSYCHOLOGISTS_LIST));
            loader.setControllerFactory(c -> new PsychologistsListGUI(fxmlPathConfig,session));
            Parent root = loader.load();
            ((PsychologistsListGUI) loader.getController()).printPsychologists(psychologistBeans);
            changeScene(root, event);
        } catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }

    //Metodo per verificare l'immissione dei campi obbligatori
    @FXML
    protected void checkFields(TextField[] fields) throws EmptyFieldException {
        for(TextField field:fields){
            if (!(field.getText().isEmpty()))
                return;
        }
        throw new EmptyFieldException("Inserisci almeno almeno un campo tra cognome e città");
    }

}


