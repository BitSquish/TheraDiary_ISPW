package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.MedicalOfficeRegistration;
import com.theradiary.ispwtheradiary.engineering.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.MedicalOfficeBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class MedicalOfficeController extends CommonController{
    protected MedicalOfficeController(Session session) {
        super(session);
    }

    @FXML
    TextField citta, cap, via, altreInfo;
    @FXML
    Label errorMessage, successMessage;
    Boolean medOffAlreadyInserted;


    //Questo metodo fa sì che le textfield siano inizializzate con i dati dello studio medico se esso è già stato inserito
    @FXML
    protected void initializeTextFields() throws SQLException {
        MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(session.getUser().getMail(), null, null, null, null);
        MedicalOfficeRegistration medicalOfficeRegistration = new MedicalOfficeRegistration();
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
        //VA GESTITO IL CASO DI MODIFICA DEI DATI
        errorMessage.setVisible(false);
        successMessage.setVisible(false);
        try{
            TextField[] fields = {citta, cap, via};
            checkFields(fields);
            MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(session.getUser().getMail(), citta.getText(), cap.getText(), via.getText(), altreInfo.getText());
            MedicalOfficeRegistration medicalOfficeRegistration = new MedicalOfficeRegistration();
            if(medOffAlreadyInserted)
                medicalOfficeRegistration.modify(medicalOfficeBean);
            else
                medicalOfficeRegistration.register(medicalOfficeBean);
            successMessage.setVisible(true);
        }catch(EmptyFieldException exception){
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e); //DA VERIFICARE ECCEZIONE
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

