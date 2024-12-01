package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.TaskAndToDoDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.ToDoItem;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.ToDoItemBean;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskAndToDo {

    public String getDiaryForToday(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        Optional<String> diaryContent = TaskAndToDoDAO.getDiaryForToday(patient);
        if (diaryContent.isPresent()) {
            patientBean.setDiary(diaryContent.get()); // Imposta il diario nel PatientBean
            return diaryContent.get(); // Restituisci il contenuto del diario
        } else {
            patientBean.setDiary(""); // Imposta un diario vuoto se non trovato
            return ""; // Restituisci una stringa vuota
        }
    }

    public void saveDiary(String diaryContent, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        TaskAndToDoDAO.Diary(patient,diaryContent);
        patientBean.setDiary(diaryContent);
    }

    public String getDiaryEntry(LocalDate selectedDate, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        Optional<String> diaryContent = TaskAndToDoDAO.getDiaryEntry(selectedDate,patient);
        if(diaryContent.isPresent()) {
            return diaryContent.get();
        } else {
            return "";
        }
    }

    public void saveToDoList(ObservableList<ToDoItemBean> savedToDoItems, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        TaskAndToDoDAO.ToDoList(patient,savedToDoItems);
        patientBean.setToDoList(savedToDoItems);
    }


    public void ToDoList(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        List<ToDoItem> toDoItems = TaskAndToDoDAO.retriveToDoList(patient);
        if (toDoItems != null) {
            for (ToDoItem toDoItem : toDoItems) {
                patientBean.addToDoItem(new ToDoItemBean(toDoItem.getToDo(), toDoItem.isCompleted()));
            }
        }
    }
}
