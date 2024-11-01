package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.Search;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class SearchController extends CommonController{
    public SearchController(Session session) {
        super(session);
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
            searchClass.search(psychologistBeans,nomeP, cognomeP, cittaP, inPresenza, online, pag);
            for(PsychologistBean psychologistBean:psychologistBeans){
                System.out.println("Mail: " + psychologistBean.getCredentialsBean().getMail());
                System.out.println("Nome: " + psychologistBean.getName());
                System.out.println("Cognome: " + psychologistBean.getSurname());
                System.out.println("Città: " + psychologistBean.getCity());
                System.out.println("Descrizione: " + psychologistBean.getDescription());
                System.out.println("In presenza: " + psychologistBean.isInPerson());
                System.out.println("Online: " + psychologistBean.isOnline());
                System.out.println("Adesione PAG: " + psychologistBean.isPag());
            }
        } catch (EmptyFieldException | NoResultException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
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


