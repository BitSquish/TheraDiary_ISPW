package com.theradiary.ispwtheradiary.controller.graphic.task;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
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

public class DiaryPageController extends CommonController {
   protected DiaryPageController(FXMLPathConfig fxmlPathConfig, Session session) {super(fxmlPathConfig, session);}
    @FXML
    private TextArea diaryPage;
   @FXML
    private DatePicker date;
   @FXML
   public void selectDate(ActionEvent event){
       LocalDate selectedDate = date.getValue();
       if(selectedDate!=null) {
           TaskAndToDo taskAndToDo = new TaskAndToDo();
           PatientBean patientBean = (PatientBean) session.getUser();
           diaryPage.setText(taskAndToDo.getDiaryEntry(selectedDate, patientBean));
       }else{
              diaryPage.setText("Seleziona una data");
       }
   }


}
