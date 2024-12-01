package com.theradiary.ispwtheradiary.controller.graphic.task;

import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class DiaryAndTasksController extends CommonController {
     public DiaryAndTasksController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }
    PatientBean patientBean = (PatientBean) session.getUser();
    @FXML
    protected void goToBacheca(MouseEvent event){

    }
    @FXML
    protected void goToDiary(MouseEvent event ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DIARY_PATH)));
            loader.setControllerFactory(c -> new DiaryController(fxmlPathConfig, session));
            Parent root = loader.load();
            ((DiaryController) loader.getController()).initializeDiary(patientBean);
            changeScene(root, event);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena:" + e.getMessage(), e);
        }
    }
    @FXML
    protected void goToTest(MouseEvent event){

    }
    @FXML
    protected void goToDo(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(TODO_PATH)));
            loader.setControllerFactory(c -> new ToDoController(fxmlPathConfig, session));
            Parent root = loader.load();
            ((ToDoController) loader.getController()).initializeToDoList(patientBean);
            changeScene(root, event);
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena:" + e.getMessage(), e);
        }
    }
    @FXML
    protected void goToTask(MouseEvent event){

    }
    @FXML
    protected void back(MouseEvent event){
        try{
            FXMLLoader loader;
            if(session.getUser()==null) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session));
            }else{
                loader=new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(HOMEPAGE_LOGGED_PT_PATH)));
                loader.setControllerFactory(c->new HomepagePtController(fxmlPathConfig,session));

            }
            Parent root=loader.load();
            changeScene(root,event);
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena:" + e.getMessage(), e);
        }
    }
}


