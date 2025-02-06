package com.theradiary.ispwtheradiary.view.gui;


import com.theradiary.ispwtheradiary.controller.PagController;
import com.theradiary.ispwtheradiary.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class PagGUI extends CommonGUI {
    protected PagGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }
    private final PagController pagController = new PagController();
    @FXML
    Label successMessage;

    @FXML
    protected void joinPag(MouseEvent event){
        try{
            session.getUser().setPag(true);
            LoggedUserBean loggedUserBean = session.getUser();
            pagController.joinPag(loggedUserBean);
            successMessage.setVisible(true);
        }catch(Exception e){
            throw new LoadingException(LOADING_SCENE, e);
        }

    }
}
