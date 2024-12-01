package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;

import java.io.IOException;
import java.time.LocalDate;

public class DiaryPageController extends CommonController{
   DiaryPageController(FXMLPathConfig fxmlPathConfig, Session session) {super(fxmlPathConfig, session);}
    @FXML
    private TextArea diaryPage;
   @FXML
    private DatePicker date;
   @FXML
   public void selectDate(ActionEvent event){
       LocalDate selectedDate = date.getValue();
       System.out.println(selectedDate);
       if(selectedDate!=null) {
           TaskAndToDo taskAndToDo = new TaskAndToDo();
           PatientBean patientBean = (PatientBean) session.getUser();
           diaryPage.setText(taskAndToDo.getDiaryEntry(selectedDate, patientBean));
       }else{
              diaryPage.setText("Seleziona una data");
       }
   }

   @FXML
    public void back(MouseEvent event){
        try{
            FXMLLoader loader;
            if(session.getUser()==null) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session));
            }else{
                PatientBean patientBean = (PatientBean) session.getUser();
                loader=new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DIARY_PATH)));
                loader.setControllerFactory(c->new DiaryController(fxmlPathConfig,session));
                ((DiaryController) loader.getController()).initializeDiary(patientBean);
            }
            Parent root=loader.load();
            changeScene(root,event);
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento della scena:" + e.getMessage(), e);
        }
   }


}
