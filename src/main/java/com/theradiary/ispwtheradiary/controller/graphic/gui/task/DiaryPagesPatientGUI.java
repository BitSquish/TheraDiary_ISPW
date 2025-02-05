package com.theradiary.ispwtheradiary.controller.graphic.gui.task;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDoPtController;
import com.theradiary.ispwtheradiary.controller.graphic.gui.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;


import java.time.LocalDate;

public class DiaryPagesPatientGUI extends CommonGUI {
   protected DiaryPagesPatientGUI(FXMLPathConfig fxmlPathConfig, Session session) {super(fxmlPathConfig, session);}
    @FXML
    private TextArea diaryPage;
   @FXML
    private DatePicker date;
   @FXML
   public void selectDate(ActionEvent event){
       LocalDate selectedDate = date.getValue();
       if(selectedDate!=null) {
           TaskAndToDoPtController taskAndToDo = new TaskAndToDoPtController();
           PatientBean patientBean = (PatientBean) session.getUser();
           diaryPage.setText(taskAndToDo.getDiaryEntry(selectedDate, patientBean));
       }else{
              diaryPage.setText("Seleziona una data");
       }
   }


}
