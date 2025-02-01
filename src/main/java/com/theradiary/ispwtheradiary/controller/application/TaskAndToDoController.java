package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.TaskAndToDoDAO;
import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskAndToDoController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final TaskAndToDoDAO taskAndToDoDAO = FactoryDAO.getTaskAndToDoDAO();
    //Costruttore
    public TaskAndToDoController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    /*-------------------------DIARIO-------------------------*/
    //Metodo per recuperare il diario del paziente
    public String getDiaryForToday(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Optional<String> diaryContent = taskAndToDoDAO.getDiaryForToday(patient);
        if (diaryContent.isPresent()) {
            patientBean.setDiary(diaryContent.get()); // Imposta il diario nel PatientBean
            return diaryContent.get(); // Restituisci il contenuto del diario
        } else {
            patientBean.setDiary(""); // Imposta un diario vuoto se non trovato
            return ""; // Restituisci una stringa vuota
        }
    }
    //Metodo per salvare il diario del paziente
    public void saveDiary(String diaryContent, PatientBean patientBean,LocalDate selectedDate) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        taskAndToDoDAO.diary(patient,diaryContent,selectedDate);
        patientBean.setDiary(diaryContent);
    }

    //Metodo per recuperare il diario del paziente
    public String getDiaryEntry(LocalDate selectedDate, PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Optional<String> diaryContent = taskAndToDoDAO.getDiaryEntry(selectedDate,patient);
        return diaryContent.orElse("");
    }
    /*-------------------------TODOLIST-------------------------*/
    //Metodo per salvare la toDoList del paziente
    public void deleteToDo(ToDoItemBean toDoItemBean, PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        ToDoItem toDoItem = new ToDoItem(toDoItemBean.getToDo(), toDoItemBean.isCompleted());
        taskAndToDoDAO.deleteToDoItem(patient,toDoItem);
        patientBean.removeToDoItem(toDoItemBean);
    }

    //Metodo per salvare la toDoList del paziente
    public void saveToDo(ToDoItemBean toDoItemBean, PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        ToDoItem toDoItem = new ToDoItem(toDoItemBean.getToDo(), toDoItemBean.isCompleted());
        if(!patient.getToDoList().contains(toDoItem) ){
            taskAndToDoDAO.saveToDo(patient,toDoItem);
            patientBean.addToDoItem(toDoItemBean);
        }else{
            taskAndToDoDAO.deleteToDoItem(patient,toDoItem);
            patientBean.removeToDoItem(toDoItemBean);
        }
    }
    //Metodo per recuperare la toDoList del paziente
    public void toDoList(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        List<ToDoItem> toDoItems = taskAndToDoDAO.retriveToDoList(patient);
        for (ToDoItem toDoItem : toDoItems) {
            patientBean.addToDoItem(new ToDoItemBean(toDoItem.getToDo(), toDoItem.isCompleted()));
        }
    }
    /*-------------------------TASKS-------------------------*/
    //Metodo per recuperare le task
    public void retrieveTasks(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        List<Task> tasks = taskAndToDoDAO.retrieveTasks(patient);
        patientBean.clearTasks();
        for (Task task : tasks) {
            patientBean.addTask(new TaskBean(task.getTaskName(), task.getTaskDeadline(), task.getTaskStatus()));
        }
    }
    //Metodo per salvare le task
    public void saveTasks(PatientBean patientBean,TaskBean taskBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Task task = new Task(taskBean.getTaskName(),taskBean.getTaskDeadline(), taskBean.getTaskStatus());
        if(!patient.getTasks().contains(task)) {
            taskAndToDoDAO.saveTask(patient, task);
            patientBean.addTask(taskBean);
        }else{
            taskAndToDoDAO.deleteTask(patient,task);
            patientBean.removeTask(taskBean);
        }


    }
    //Metodo per aggiornare le task
    public void updateTasks(PatientBean patientBean,TaskBean taskBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Task task = new Task(taskBean.getTaskName(),taskBean.getTaskDeadline(), taskBean.getTaskStatus());
        taskAndToDoDAO.updateTask(patient,task);
        patientBean.removeTask(taskBean);
    }
    //Metodo per elimanre la task
    public void deleteTask(TaskBean selectedTask, PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Task task = new Task(selectedTask.getTaskName(),selectedTask.getTaskDeadline(), selectedTask.getTaskStatus());
        taskAndToDoDAO.deleteTask(patient,task);
        patientBean.removeTask(selectedTask);
    }

}
