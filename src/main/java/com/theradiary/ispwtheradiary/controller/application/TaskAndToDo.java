package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.TaskAndToDoDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.ToDoItem;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskAndToDo {

    public static String getDiaryForToday(PatientBean patientBean) {
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

    public static void saveDiary(String diaryContent, PatientBean patientBean,LocalDate selectedDate) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        TaskAndToDoDAO.Diary(patient,diaryContent,selectedDate);
        patientBean.setDiary(diaryContent);
    }

    public String getDiaryEntry(LocalDate selectedDate, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        Optional<String> diaryContent = TaskAndToDoDAO.getDiaryEntry(selectedDate,patient);
        return diaryContent.orElse("");
    }

    public static void saveToDoList(List<ToDoItemBean> savedToDoItems, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        TaskAndToDoDAO.ToDoList(patient,savedToDoItems);
        patientBean.setToDoList(savedToDoItems);
    }
    public static void completeToDoItem(List<ToDoItemBean> completetoDoItems, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        ToDoItem toDoItem = new ToDoItem(completetoDoItems.get(0).getToDo(),completetoDoItems.get(0).isCompleted());
        TaskAndToDoDAO.completeToDoItem(patient,completetoDoItems);
        for(ToDoItemBean toDoItemBean:completetoDoItems){
            patientBean.removeToDoItem(toDoItemBean);
        }
    }


    public static void toDoList(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        List<ToDoItem> toDoItems = TaskAndToDoDAO.retriveToDoList(patient);
        for (ToDoItem toDoItem : toDoItems) {
            patientBean.addToDoItem(new ToDoItemBean(toDoItem.getToDo(), toDoItem.isCompleted()));
        }
    }
}
