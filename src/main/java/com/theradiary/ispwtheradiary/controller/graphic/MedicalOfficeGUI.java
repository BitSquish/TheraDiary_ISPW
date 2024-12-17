package com.theradiary.ispwtheradiary.controller.graphic;


import com.theradiary.ispwtheradiary.controller.application.MedicalOfficeRegistrationController;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class MedicalOfficeGUI extends CommonGUI {
    protected MedicalOfficeGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    TextField citta;
    @FXML
    TextField cap;
    @FXML
    TextField via;
    @FXML
    TextField altreInfo;
    @FXML
    Label errorMessage;
    @FXML
    Label successMessage;
    boolean medOffAlreadyInserted;


    //Questo metodo fa sì che le textfield siano inizializzate con i dati dello studio medico se esso è già stato inserito
    @FXML
    protected void initializeTextFields() throws SQLException {
        MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(session.getUser().getCredentialsBean().getMail(), null, null, null, null);
        MedicalOfficeRegistrationController medicalOfficeRegistration = new MedicalOfficeRegistrationController();
        if (medicalOfficeRegistration.retrieveMedicalOffice(medicalOfficeBean)) {
            medOffAlreadyInserted = true;
            citta.setText(medicalOfficeBean.getCity());
            cap.setText(medicalOfficeBean.getPostCode());
            via.setText(medicalOfficeBean.getAddress());
            altreInfo.setText(medicalOfficeBean.getOtherInfo());
        }
        else
            medOffAlreadyInserted = false;
    }

    @FXML
    private void register(MouseEvent event){
        errorMessage.setVisible(false);
        successMessage.setVisible(false);
        try{
            TextField[] fields = {citta, cap, via};
            checkFields(fields);
            MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(session.getUser().getCredentialsBean().getMail(), citta.getText(), cap.getText(), via.getText(), altreInfo.getText());
            MedicalOfficeRegistrationController medicalOfficeRegistration = new MedicalOfficeRegistrationController();
            if(medOffAlreadyInserted)
                medicalOfficeRegistration.modify(medicalOfficeBean);
            else
                medicalOfficeRegistration.register(medicalOfficeBean);
            successMessage.setVisible(true);
        }catch(EmptyFieldException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        } catch (SQLException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }

    }

    @FXML
    protected void checkFields(TextField[] fields) throws EmptyFieldException {
        for(TextField field:fields){
            if (field.getText().isEmpty())
                throw new EmptyFieldException("Devi obbligatoriamente inserire città, cap e via.");
        }
    }
}

