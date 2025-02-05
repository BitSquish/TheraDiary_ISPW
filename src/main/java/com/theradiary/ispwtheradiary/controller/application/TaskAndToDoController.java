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


import java.util.List;
import java.util.Optional;

public abstract class TaskAndToDoController {
    protected  BeanAndModelMapperFactory beanAndModelMapperFactory;
    protected final TaskAndToDoDAO taskAndToDoDAO = FactoryDAO.getTaskAndToDoDAO();
    //Costruttore
    protected TaskAndToDoController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    /*********************Task********************************/
    //Metodo per recuperare le task
    public void retrieveTasks(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        List<Task> tasks = taskAndToDoDAO.retrieveTasks(patient);
        patientBean.clearTasks();
        for (Task task : tasks) {
            patientBean.addTask(new TaskBean(task.getTaskName(), task.getTaskDeadline(), task.getTaskStatus()));
        }
    }
    /*********************Diary********************************/
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
    /*********************To-do********************************/
    //Metodo per eliminare to-do
    public void deleteToDo(ToDoItemBean toDoItemBean, PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        ToDoItem toDoItem = new ToDoItem(toDoItemBean.getToDo(), toDoItemBean.isCompleted());
        taskAndToDoDAO.deleteToDoItem(patient,toDoItem);
        patientBean.removeToDoItem(toDoItemBean);
    }
    //metodo per recuperare le to-do list paziente
    public void retrieveToDoList(PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        List<ToDoItem> toDoItems = taskAndToDoDAO.retriveToDoList(patient);
        for (ToDoItem toDoItem : toDoItems) {
            patientBean.addToDoItem(new ToDoItemBean(toDoItem.getToDo(), toDoItem.isCompleted()));
        }

    }











}
