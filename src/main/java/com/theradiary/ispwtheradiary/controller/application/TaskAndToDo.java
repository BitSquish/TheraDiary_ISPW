package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.TaskAndToDoDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskAndToDo {
    /*-------------------------DIARIO-------------------------*/
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

    public static void deleteTask(TaskBean selectedTask, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        Task task = new Task(selectedTask.getTaskName(),selectedTask.getTaskDeadline(), selectedTask.getTaskStatus());
        TaskAndToDoDAO.deleteTask(patient,task);
        patientBean.removeTask(selectedTask);
    }


    public String getDiaryEntry(LocalDate selectedDate, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        Optional<String> diaryContent = TaskAndToDoDAO.getDiaryEntry(selectedDate,patient);
        return diaryContent.orElse("");
    }
    /*-------------------------TODOLIST-------------------------*/

    public static void deleteToDo(ToDoItemBean toDoItemBean, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        ToDoItem toDoItem = new ToDoItem(toDoItemBean.getToDo(), toDoItemBean.isCompleted());
        TaskAndToDoDAO.deleteToDoItem(patient,toDoItem);
        patientBean.removeToDoItem(toDoItemBean);
    }


    public static void saveToDo(ToDoItemBean toDoItemBean, PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        ToDoItem toDoItem = new ToDoItem(toDoItemBean.getToDo(), toDoItemBean.isCompleted());
        if(!patient.getToDoList().contains(toDoItem) ){
            TaskAndToDoDAO.saveToDo(patient,toDoItem);
            patientBean.addToDoItem(toDoItemBean);
        }else{
            TaskAndToDoDAO.deleteToDoItem(patient,toDoItem);
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
    /*-------------------------TASKS-------------------------*/
    public static void retrieveTasks(PatientBean patientBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        List<Task> tasks = TaskAndToDoDAO.retriveTasks(patient);
        for (Task task : tasks) {
            patientBean.addTask(new TaskBean(task.getTaskName(),task.getTaskDeadline(), task.getTaskStatus()));
        }
    }
    public static void saveTasks(PatientBean patientBean,TaskBean taskBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        Task task = new Task(taskBean.getTaskName(),taskBean.getTaskDeadline(), taskBean.getTaskStatus());
        if(!patient.getTasks().contains(task)) {
            TaskAndToDoDAO.saveTask(patient, task);
            patientBean.addTask(taskBean);
        }else{
            TaskAndToDoDAO.deleteTask(patient,task);
            patientBean.removeTask(taskBean);
        }


    }
    public static void updateTasks(PatientBean patientBean,TaskBean taskBean) {
        Patient patient = new Patient(new Credentials(patientBean.getCredentialsBean().getMail(), patientBean.getCredentialsBean().getPassword(), Role.PATIENT), patientBean.getName(), patientBean.getSurname(), patientBean.getCity(), patientBean.getDescription(), patientBean.isInPerson(), patientBean.isOnline());
        Task task = new Task(taskBean.getTaskName(),taskBean.getTaskDeadline(), taskBean.getTaskStatus());
        TaskAndToDoDAO.updateTask(patient,task);
    }
}
