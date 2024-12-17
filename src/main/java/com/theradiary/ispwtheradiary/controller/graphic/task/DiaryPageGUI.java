package com.theradiary.ispwtheradiary.controller.graphic.task;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDoController;
import com.theradiary.ispwtheradiary.controller.graphic.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;


import java.time.LocalDate;

public class DiaryPageGUI extends CommonGUI {
   protected DiaryPageGUI(FXMLPathConfig fxmlPathConfig, Session session) {super(fxmlPathConfig, session);}
    @FXML
    private TextArea diaryPage;
   @FXML
    private DatePicker date;
   @FXML
   public void selectDate(ActionEvent event){
       LocalDate selectedDate = date.getValue();
       if(selectedDate!=null) {
           TaskAndToDoController taskAndToDo = new TaskAndToDoController();
           PatientBean patientBean = (PatientBean) session.getUser();
           diaryPage.setText(taskAndToDo.getDiaryEntry(selectedDate, patientBean));
       }else{
              diaryPage.setText("Seleziona una data");
       }
   }


}
