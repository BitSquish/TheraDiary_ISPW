package com.theradiary.ispwtheradiary.controller;


import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.TaskBean;

import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;

import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;


import java.time.LocalDate;

import java.util.Optional;

/***********************Caso d'uso: aggiorna diario, completa task, completa to-do*************************/

public class TaskAndToDoPtController  extends TaskAndToDoController {


    //Costruttore
    public TaskAndToDoPtController() {
        super();
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    /*********************Task********************************/
    //Metodo per aggiornare le task
    public void updateTasks(PatientBean patientBean, TaskBean taskBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Task task = new Task(taskBean.getTaskName(),taskBean.getTaskDeadline(), taskBean.getTaskStatus());
        taskAndToDoDAO.updateTask(patient,task);
        patientBean.removeTask(taskBean);
    }


    /*********************Diary********************************/

    //Metodo per salvare il diario del paziente

    public void saveDiary(String diaryContent, PatientBean patientBean,LocalDate selectedDate) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        taskAndToDoDAO.diary(patient,diaryContent,selectedDate);
        patientBean.setDiary(diaryContent);
    }

    //Metodo per recuperare il diario del paziente
    public String getDiary(LocalDate selectedDate, PatientBean patientBean) {
        Patient patient = beanAndModelMapperFactory.fromBeanToModel(patientBean, PatientBean.class);
        Optional<String> diaryContent = taskAndToDoDAO.getDiaryEntry(selectedDate,patient);
        return diaryContent.orElse("");
    }






}
