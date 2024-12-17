package com.theradiary.ispwtheradiary.controller.graphic;


import com.theradiary.ispwtheradiary.controller.application.PagController;
import com.theradiary.ispwtheradiary.engineering.exceptions.SceneLoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class PagGUI extends CommonGUI {
    protected PagGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

    @FXML
    Label successMessage;

    @FXML
    protected void joinPag(MouseEvent event){
        try{
            session.getUser().setPag(true);
            LoggedUserBean loggedUserBean = session.getUser();
            PagController pagController = new PagController();
            pagController.joinPag(loggedUserBean);
            //Eccezione da gestire?
            successMessage.setVisible(true);
        }catch(Exception e){
            throw new SceneLoadingException(LOADING_SCENE, e);
        }

    }
}
