package com.theradiary.ispwtheradiary.model.beans;

import javafx.beans.value.ObservableValue;

public class TaskBean {
    private String taskName;
    private String taskDeadline;
    private String taskStatus;

    public TaskBean(String taskName, String taskDeadline, String taskStatus) {
        this.taskName = taskName;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }


}
