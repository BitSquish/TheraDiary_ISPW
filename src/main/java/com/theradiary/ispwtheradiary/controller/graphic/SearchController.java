package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.Search;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchController extends CommonController{
    private static final String PSYCHOLOGISTS_LIST = "/com/theradiary/ispwtheradiary/view/PsychologistsList.fxml";
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
            searchClass.searchPsychologists(psychologistBeans,nomeP, cognomeP, cittaP, inPresenza, online, pag);
            ArrayList<MedicalOfficeBean> medicalOfficeBeans = new ArrayList<>(psychologistBeans.size());
            MedicalOfficeBean medicalOfficeBean;
            ArrayList<Major> majors = new ArrayList<>(psychologistBeans.size());
            for(int i = 0; i<psychologistBeans.size(); i++){
                medicalOfficeBean = new MedicalOfficeBean(psychologistBeans.get(i).getCredentialsBean().getMail(), psychologistBeans.get(i).getCity(), null, null, null);
                searchClass.searchMedicalOffice(psychologistBeans.get(i), medicalOfficeBean);
                medicalOfficeBeans.add(medicalOfficeBean);
                //searchClass.searchMajors(psychologistBeans.get(i), majors);
            }
            goToPsychologistsList(psychologistBeans, medicalOfficeBeans,event);
        } catch (EmptyFieldException | NoResultException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }
    }

    private void goToPsychologistsList(List<PsychologistBean> psychologistBeans, ArrayList<MedicalOfficeBean> medicalOfficeBeans, MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PSYCHOLOGISTS_LIST));
            loader.setControllerFactory(c -> new PsychologistsListController(session));
            Parent root = loader.load();
            ((PsychologistsListController) loader.getController()).printPsychologists(event, psychologistBeans);
            changeScene(root, event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
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


