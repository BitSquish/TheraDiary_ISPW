package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.PAG;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class PAGController extends CommonController{
    protected PAGController(Session session) {
        super(session);
    }

    @FXML
    Label successMessage;

    @FXML
    protected void joinPag(MouseEvent event){
        try{
            session.getUser().setPag(true);
            LoggedUserBean loggedUserBean = session.getUser();
            PAG pag = new PAG();
            pag.joinPag(loggedUserBean);
            //Eccezione da gestire?
            successMessage.setVisible(true);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
