package com.theradiary.ispwtheradiary.view.gui;


import com.theradiary.ispwtheradiary.controller.MedicalOfficeRegistrationController;
import com.theradiary.ispwtheradiary.exceptions.EmptyFieldException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.MedicalOfficeBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;



public class MedicalOfficeGUI extends CommonGUI {
    protected MedicalOfficeGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    private final MedicalOfficeRegistrationController medicalOfficeRegistration = new MedicalOfficeRegistrationController();

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
    protected void initializeTextFields()  {
        MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(session.getUser().getCredentialsBean().getMail());
        if (medicalOfficeRegistration.retrieveMedicalOffice(medicalOfficeBean)) {   // Controlla se l'ufficio medico è già stato inserito
            medOffAlreadyInserted = true;
            citta.setText(medicalOfficeBean.getCity());
            cap.setText(medicalOfficeBean.getPostCode());
            via.setText(medicalOfficeBean.getAddress());
            altreInfo.setText(medicalOfficeBean.getOtherInfo());
        }
        else
            medOffAlreadyInserted = false;
    }

    //Metodo per registrare o modificare lo studio medico inserito
    @FXML
    private void register(MouseEvent event){
        errorMessage.setVisible(false);
        successMessage.setVisible(false);
        try{
            TextField[] fields = {citta, cap, via}; //campi obbligatori
            checkFields(fields);
            MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(session.getUser().getCredentialsBean().getMail(), citta.getText(), cap.getText(), via.getText(), altreInfo.getText());
            if(medOffAlreadyInserted)   //se l'ufficio medico è già stato inserito precedentemente, lo modifica
                medicalOfficeRegistration.modify(medicalOfficeBean);
            else    //altrimenti registra il nuovo studio medico
                medicalOfficeRegistration.register(medicalOfficeBean);
            successMessage.setVisible(true);    //conferma dell'avvenuta modifica/registrazione
        }catch(EmptyFieldException exception){  //gestisce l'eccezione dovuta al mancato inserimento di campi obbligatori mostrando un messaggio di errore all'utente
            errorMessage.setText(exception.getMessage());
            errorMessage.setVisible(true);
        }

    }

    //Controllo inserimento campi obbligatori
    @FXML
    protected void checkFields(TextField[] fields) throws EmptyFieldException {
        for(TextField field:fields){
            if (field.getText().isEmpty())
                throw new EmptyFieldException("Devi obbligatoriamente inserire città, cap e via.");
        }
    }
}



