package com.theradiary.ispwtheradiary.controller.graphic.gui.task;


import com.theradiary.ispwtheradiary.controller.graphic.gui.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class TaskAndToDoPtGUI extends CommonGUI {
     public TaskAndToDoPtGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
    }
    PatientBean patientBean = (PatientBean) session.getUser();



    @FXML
    protected void goToTest(MouseEvent event){
        showMessage(Alert.AlertType.INFORMATION, "test", "Work in progress");
    }



    @FXML
    protected void goToDo(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(TODO_PATH)));
            loader.setControllerFactory(c -> new ToDoPatientGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            ((ToDoPatientGUI) loader.getController()).initializeToDoList(patientBean);
            changeScene(root, event);
        }catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }
    @FXML
    protected void goToTaskPatient(MouseEvent event){
        try{
            if(((PatientBean)session.getUser()).getPsychologistBean() != null && ((PatientBean)session.getUser()).getPsychologistBean().getCredentialsBean().getMail() != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LIST_TASK_PATIENT_PATH)));
                loader.setControllerFactory(c -> new TaskPatientGUI(fxmlPathConfig, session));
                Parent root = loader.load();
                ((TaskPatientGUI) loader.getController()).initializeTaskList(patientBean);
                changeScene(root, event);
            }
            else {
                goToSearch(event);
            }
        }catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }

    }
}


