package com.theradiary.ispwtheradiary.controller;


import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.TaskBean;
import com.theradiary.ispwtheradiary.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;

import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;

/***********************Caso d'uso: Consulta materiale paziente, assegna task, assegna to-do*************************/

public class TaskAndToDoPsController extends TaskAndToDoController {


    //Costruttore
    public TaskAndToDoPsController() {
        super();
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    /*********************Task********************************/

    
    //Metodo per elimanre la task
    public void deleteTask(TaskBean selectedTask, PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Task task = new Task(selectedTask.getTaskName(),selectedTask.getTaskDeadline(), selectedTask.getTaskStatus());
        taskAndToDoDAO.deleteTask(patient,task);
        patientBean.removeTask(selectedTask);
    }

    //Metodo per salvare la task
    public void saveTask(PatientBean patientBean, TaskBean taskBean) {
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
    /*********************To-do********************************/



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

}
